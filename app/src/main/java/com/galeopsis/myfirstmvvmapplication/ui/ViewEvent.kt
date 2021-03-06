package com.galeopsis.myfirstmvvmapplication.ui

sealed class ViewEvent {
    data class DigitClick(val digit: String) : ViewEvent()
    object CommaClick : ViewEvent()
    object ACClick : ViewEvent()
    object LeftBracketClick : ViewEvent()
    object RightBracketClick : ViewEvent()
    object DivideClick : ViewEvent()
    object MultiplyClick : ViewEvent()
    object MinusClick : ViewEvent()
    object PlusClick : ViewEvent()
    object EraseClick : ViewEvent()
    object EqualClick : ViewEvent()
    object SqrtClick : ViewEvent()
    object SinClick : ViewEvent()
    object CosClick : ViewEvent()
}