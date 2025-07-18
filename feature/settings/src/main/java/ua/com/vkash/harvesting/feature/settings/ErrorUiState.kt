package ua.com.vkash.harvesting.feature.settings

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed interface ErrorUiState {
    data object None : ErrorUiState
    data class Error(
        val msg: String? = null,
        @StringRes val msgId: Int = R.string.unknown_error
    ) : ErrorUiState {
        constructor(throwable: Throwable) : this(
            msg = throwable.localizedMessage ?: throwable.message
        )

        @Composable
        fun message(): String = msg.takeUnless { it.isNullOrBlank() } ?: stringResource(msgId)
    }
}
