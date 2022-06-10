package org.financeapps.plutusfinance.features.transactions.ui

import org.financeapps.plutusfinance.features.accounts.vo.Account
import java.math.BigDecimal

data class AddTransactionDTO(
    val sourceAccount: Account,
    val targetAccount: Account,
    val isDeposit: Boolean,
    val comment: String?,
    val amount: BigDecimal
)