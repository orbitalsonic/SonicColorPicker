package com.orbitalsonic.soniccolorpicker.interfaces

interface ColorObservable {
    fun subscribe(observer: ColorObserver)
    fun unsubscribe(observer: ColorObserver)
    val color: Int
}