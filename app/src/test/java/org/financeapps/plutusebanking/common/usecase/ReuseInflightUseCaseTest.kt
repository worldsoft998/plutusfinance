package org.financeapps.plutusfinance.common.usecase

import org.financeapps.plutusfinance.features.common.usecase.ReuseInflightUseCase
import org.financeapps.plutusfinance.features.common.usecase.UseCase
import org.financeapps.plutusfinance.features.common.usecase.reuseInflight
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by Mark Chen on 16-4-19.
 *
 * TODO: Migrate the test to use runBlockingTest. In order for everything to work properly,
 * we need to be able to either inject the test dispatcher in the inflight request use case,
 * or use a service loader just like we do with [Dispatchers.setMain(testDispatcher)].
 */

@ExperimentalCoroutinesApi
class ReuseInflightUseCaseTest {
    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun `should return a value`() {
        val testUseCase = object : UseCase<Unit, Int> {
            override suspend operator fun invoke(param: Unit): Int {
                return 42
            }
        }.reuseInflight()

        testScope.runBlockingTest {
            // When we get the value
            val result = testUseCase(Unit)

            // then we return the value
            assertEquals(42, result)
        }
    }

    @Test
    fun `should increment only once when a call is in flight`() {
        val count = AtomicInteger(0)
        val workTime = 100L

        // Given value
        val testUseCase = object : UseCase<Unit, Int> {
            override suspend operator fun invoke(param: Unit): Int {
                return withContext(Dispatchers.Default) {
                    delay(workTime)
                    count.getAndIncrement()
                }
            }
        }.reuseInflight()

        runBlocking {
            // Use the same key multiple times sequentially
            GlobalScope.launch {
                for (i in 1..5) {
                    testUseCase(Unit)
                }
            }

            delay(500)

            // Expect to have a single call of the underlying usecase
            assertEquals(1, count.get())
        }
    }

    @Test
    fun `should increment twice for different keys while two calls are in flight`() {
        val count = AtomicInteger(0)
        val workTime = 100L

        // Given value
        val testUseCase = object : UseCase<String, Int> {
            override suspend operator fun invoke(param: String): Int {
                return withContext(testDispatcher) {
                    delay(workTime)
                    count.getAndIncrement()
                }
            }
        }.reuseInflight()

        testScope.runBlockingTest {
            // When we get the same key multiple times in parallel
            testScope.launch {
                for (i in 1..5) {
                    testUseCase("key1")
                }
            }

            testScope.launch {
                for (i in 1..5) {
                    testUseCase("key2")
                }
            }

            // Since we are running async in a test, we'll need to keep the test running
            // for little more than the amount of time required by the usecase
            delay(workTime + 100)

            // Expect to have two calls of the underlying usecase since we queried for different keys
            assertEquals(2, count.get())
        }
    }

    @Test
    fun `should increment twice if a call is made after previous one has completed`() {
        val count = AtomicInteger(0)
        val workTime = 200L
        val callTimes = 5

        // Given value
        val testUseCase = object : UseCase<Unit, Int> {
            override suspend operator fun invoke(param: Unit): Int {
                return withContext(Dispatchers.Default) {
                    delay(workTime)
                    count.getAndIncrement()
                }
            }
        }.reuseInflight()

        // TODO: Migrate to runBlockingTest{} that supports time advancing
        runBlocking {
            GlobalScope.launch {
                for (i in 1..callTimes) {
                    testUseCase(Unit)
                }
            }.join()

            GlobalScope.launch {
                // Ensure to request once the result from the actual invocation has finished
                delay(workTime * callTimes + 200)

                // Expect to have a single call of the underlying usecase so far
                assertEquals(1, count.get())

                // Invoke the usecase again for couple of times
                for (i in 1..callTimes) {
                    testUseCase(Unit)
                }
            }.join()

            // Expect the usecase was called only twice
            assertEquals(2, count.get())
        }
    }

    @Test(expected = TestException::class)
    fun `should throw an exception when a usecase errors`() {
        // Given value
        val testUseCase = object : UseCase<Unit, Int> {
            override suspend operator fun invoke(param: Unit): Int {
                throw TestException()
            }
        }.reuseInflight()

        runBlocking {
            testUseCase(Unit)
        }
    }

    @Test(expected = IllegalStateException::class)
    fun `should throw an exception when two in flight use cases are decorated`() {
        // Given an inflight usecase
        val testUseCase = object : UseCase<Unit, Int> {
            override suspend operator fun invoke(param: Unit): Int {
                return 1
            }
        }.reuseInflight()

        // When it is decorated with another inflight usecase
        ReuseInflightUseCase(testUseCase)

        // .. should throw an IllegalStateException exception
    }

    @Test(expected = IllegalStateException::class)
    fun `should throw an exception when two in flight use cases are composed`() {
        // Given an inflight usecase
        val inflightUseCase1 = object : UseCase<Unit, Int> {
            override suspend operator fun invoke(param: Unit): Int {
                return 1
            }
        }.reuseInflight()

        // When an inflight usecase is composed with a second one
        val inflightUseCase2 = inflightUseCase1.reuseInflight()

        // .. should throw an IllegalStateException exception
    }
}

class TestException : RuntimeException()