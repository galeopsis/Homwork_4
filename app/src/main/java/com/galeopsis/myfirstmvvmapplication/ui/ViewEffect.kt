package com.galeopsis.myfirstmvvmapplication.ui

sealed class ViewEffect {
    object ShowToast : ViewEffect()
}