package ua.com.priberry.harvesting.feature.calculator

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ua.com.priberry.harvesting.feature.calculator.navigation.CalculatorRoute

@HiltViewModel
class CalculatorScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val args = savedStateHandle.toRoute<CalculatorRoute>()
    private var input = StringBuilder(args.inputValue.toString().removeSuffix(".0"))

    val isFractional = args.isFractional
    val inputKey = args.inputKey

    private val _displayText = MutableStateFlow("")
    val displayText = _displayText.asStateFlow()

    val value: Double
        get() = input.toString().toDoubleOrNull() ?: 0.0

    init {
        updateState()
    }

    fun inputDigit(digit: Char) {
        if (digit !in '0'..'9') return
        if (digit == '0' && input.isEmpty()) return
        if (digit in '1'..'9' && input.length == 1 && input.toString() == "0") input.clear()

        input.append(digit)
        updateState()
    }

    fun inputDot() {
        if ('.' in input) return
        if (input.isEmpty()) {
            input.append("0.")
        } else {
            input.append('.')
        }
        updateState()
    }

    fun backspace() {
        if (input.isNotEmpty()) {
            input.deleteAt(input.length - 1)
            updateState()
        }
    }

    fun clear() {
        input.clear()
        updateState()
    }

    private fun updateState() {
        _displayText.value = if (input.isEmpty()) "0" else input.toString()
    }
}
