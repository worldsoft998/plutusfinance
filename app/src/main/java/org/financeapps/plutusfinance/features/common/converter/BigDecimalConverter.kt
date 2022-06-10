package org.financeapps.plutusfinance.features.common.converter

import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor
import java.math.BigDecimal

/**
 * Created by Mark Chen on 10-5-19.
 */

/**
 * Used by Kotlin's [Serializable] to serialize [BigDecimal]
 */
@Serializer(forClass = BigDecimal::class)
class BigDecimalSerializer : KSerializer<BigDecimal> {
    override val descriptor: SerialDescriptor = StringDescriptor

    override fun serialize(encoder: Encoder, obj: BigDecimal) {
        encoder.encodeString(obj.toPlainString())
    }

    override fun deserialize(decoder: Decoder): BigDecimal {
        return BigDecimal(decoder.decodeString())
    }
}