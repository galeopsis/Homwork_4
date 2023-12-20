package com.galeopsis.myfirstmvvmapplication.vm

import android.content.ClipboardManager
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.galeopsis.myfirstmvvmapplication.ui.SingleLiveEvent
import com.galeopsis.myfirstmvvmapplication.ui.ViewEffect
import com.galeopsis.myfirstmvvmapplication.ui.ViewEvent
import com.galeopsis.myfirstmvvmapplication.ui.activity.MainActivity
import org.mariuszgromada.math.mxparser.Expression
import java.math.BigDecimal
import java.util.*

class MainViewModel : ViewModel() {

    val expression = MutableLiveData<String>()
    val answer = MutableLiveData<String>()
    val viewEffect = SingleLiveEvent<ViewEffect>()

    private var number = ""
    private var isEqualEntered = false
    private var clipboardData = ""


    init {
        expression.value = ""
        answer.value = ""
    }

    fun event(event: ViewEvent) {
        when (event) {
            is ViewEvent.DigitClick -> enterDigit(event.digit)
            is ViewEvent.CommaClick -> enterComma()
            is ViewEvent.ACClick -> enterAC()
            is ViewEvent.LeftBracketClick -> enterLeftBracket()
            is ViewEvent.RightBracketClick -> enterRightBracket()
            is ViewEvent.DivideClick -> enterDivide()
            is ViewEvent.MultiplyClick -> enterMultiply()
            is ViewEvent.MinusClick -> enterMinus()
            is ViewEvent.PlusClick -> enterPlus()
            is ViewEvent.EraseClick -> enterErase()
            is ViewEvent.EqualClick -> enterEqual()
            is ViewEvent.SqrtClick -> sqrtClick()
            is ViewEvent.SinClick -> sinClick()
            is ViewEvent.CosClick -> cosClick()
            is ViewEvent.RaiseClick -> raiseClick()
        }
    }

    private fun enterDigit(digit: String) {
        clearAllIfEqualEntered()
        val expressionValue = expression.value?.replace(" ", "")
        if (number.filter { it.isDigit() }.length < 15) {
            if (expressionValue?.isEmpty() == true || expressionValue?.last() != ')') {
                number += digit
                expression.value = expression.value + digit
            }
        }
    }

    private fun enterComma() {
        clearAllIfEqualEntered()
        val expressionValue = expression.value?.replace(" ", "")
        if (expressionValue?.isNotEmpty() == true
            && expressionValue.last() != '+' && expressionValue.last() != '-'
            && expressionValue.last() != '×' && expressionValue.last() != '÷'
            && expressionValue.last() != '(' && expressionValue.last() != ')'
            && expressionValue.last() != '.'
        ) {
            number += "."
            expression.value = expression.value + "."
        }
    }

    private fun enterAC() {
        if (isEqualEntered) {
            isEqualEntered = false
        }
        number = ""
        expression.value = ""
        answer.value = ""
    }

    private fun sqrtClick() {
        clearAllIfEqualEntered()
        number = ""
        expression.value = expression.value + "sqrt("
    }

    private fun sinClick() {
        clearAllIfEqualEntered()
        number = ""
        expression.value = expression.value + "sin("
    }

    private fun cosClick() {
        clearAllIfEqualEntered()
        number = ""
        expression.value = expression.value + "cos("
    }

    private fun raiseClick() {
        clearAllIfEqualEntered()
        val expressionValue = expression.value?.replace(" ", "")
        if (expressionValue?.isNotEmpty() == true
            && expressionValue.last() != '+' && expressionValue.last() != '-'
            && expressionValue.last() != '×' && expressionValue.last() != '÷'
            && expressionValue.last() != '(' && expressionValue.last() != '.'
        ) {
            number = ""
            expression.value = expression.value + " ^ "
        }
    }

    private fun enterLeftBracket() {
        clearAllIfEqualEntered()
        val expressionValue = expression.value?.replace(" ", "")
        if (expressionValue?.isEmpty() == true
            || (expressionValue?.last() != ')' && expressionValue?.last() != '.'
                    && expressionValue?.last()?.isDigit() == false)
        ) {
            number = ""
            expression.value = expression.value + "("
        }
    }

    private fun enterRightBracket() {
        clearAllIfEqualEntered()
        val expressionValue = expression.value?.replace(" ", "")
        if (expressionValue?.isNotEmpty() == true && expressionValue.last() != '('
            && expressionValue.last() != '.' && expressionValue.last() != '+'
            && expressionValue.last() != '-' && expressionValue.last() != '×'
            && expressionValue.last() != '÷'
        ) {
            number = ""
            expression.value = expression.value + ")"
        }
    }

    private fun enterDivide() {
        clearAllIfEqualEntered()
        val expressionValue = expression.value?.replace(" ", "")
        if (expressionValue?.isNotEmpty() == true
            && expressionValue.last() != '+' && expressionValue.last() != '-'
            && expressionValue.last() != '×' && expressionValue.last() != '÷'
            && expressionValue.last() != '(' && expressionValue.last() != '.'
        ) {
            number = ""
            expression.value = expression.value + " ÷ "
        }
    }

    private fun enterMultiply() {
        clearAllIfEqualEntered()
        val expressionValue = expression.value?.replace(" ", "")
        if (expressionValue?.isNotEmpty() == true
            && expressionValue.last() != '+' && expressionValue.last() != '-'
            && expressionValue.last() != '×' && expressionValue.last() != '÷'
            && expressionValue.last() != '(' && expressionValue.last() != '.'
        ) {
            number = ""
            expression.value = expression.value + " × "
        }
    }

    private fun enterMinus() {
        clearAllIfEqualEntered()
        val expressionValue = expression.value?.replace(" ", "")
        if (expressionValue?.isEmpty() == true || expressionValue?.last() == '(') {
            expression.value = expression.value + "-"
        } else if (expressionValue?.last() != '+' && expressionValue?.last() != '-'
            && expressionValue?.last() != '×' && expressionValue?.last() != '÷'
            && expressionValue?.last() != '.'
        ) {
            number = ""
            expression.value = expression.value + " - "
        }
    }

    private fun enterPlus() {
        clearAllIfEqualEntered()
        val expressionValue = expression.value?.replace(" ", "")
        if (expressionValue?.isNotEmpty() == true
            && expressionValue.last() != '+' && expressionValue.last() != '-'
            && expressionValue.last() != '×' && expressionValue.last() != '÷'
            && expressionValue.last() != '(' && expressionValue.last() != '.'
        ) {
            number = ""
            expression.value = expression.value + " + "
        }
    }

    private fun enterErase() {
        clearAllIfEqualEntered()
        val expressionValue = expression.value
        if (expressionValue?.isNotEmpty() == true) {
            if (expressionValue.takeLast(3) != " + " && expressionValue.takeLast(3) != " - "
                && expressionValue.takeLast(3) != " × " && expressionValue.takeLast(3) != " ÷ "
            ) {
                expression.value = expressionValue.substring(0, expressionValue.length - 1)
                if (number.isNotEmpty()) {
                    number = number.substring(0, number.length - 1)
                }
            } else {
                expression.value = expressionValue.substring(0, expressionValue.length - 3)
            }
        }
    }

    private fun enterEqual() {
        val expressionValue = expression.value
        if (expressionValue?.isNotEmpty() == true) {
            val expression = Expression(
                expressionValue
                    .replace('×', '*')
                    .replace('÷', '/')
                    .replace(" ", "")
            )
            val result = expression.calculate()
            try {
                number = ""
                isEqualEntered = true
                answer.value = BigDecimal(String.format(Locale.US, "%.10f", result))
                    .stripTrailingZeros()
                    .toPlainString()
            } catch (e: Exception) {
                isEqualEntered = false
                viewEffect.value = ViewEffect.ShowToast
            }
        }
    }

    fun evaluateClipboardExpression(context: Context?) {
        val clipboard = context?.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
        val clipData = clipboard?.primaryClip
        expression.value = clipboardData

        if (clipData != null && clipData.itemCount > 0) {
            val clipboardText = clipData.getItemAt(0).text?.toString()?.replace(" ", "")
            if (!clipboardText.isNullOrBlank()) {
                try {
                    val expression = Expression(clipboardText.replace('×', '*').replace('÷', '/'))
                    val result = expression.calculate()
                    clipboardData = clipboardText
                    answer.value = BigDecimal(String.format(Locale.US, "%.10f", result))
                        .stripTrailingZeros()
                        .toPlainString()
                } catch (e: Exception) {
                    viewEffect.value = ViewEffect.ShowToast
                    Log.e("MainViewModel", "Error evaluating clipboard expression", e)
                }
            }
        }
    }

    private fun clearAllIfEqualEntered() {
//        if (isEqualEntered) {
//            isEqualEntered = false
//            number = ""
//            expression.value = ""
//            answer.value = ""
//        }
    }
}