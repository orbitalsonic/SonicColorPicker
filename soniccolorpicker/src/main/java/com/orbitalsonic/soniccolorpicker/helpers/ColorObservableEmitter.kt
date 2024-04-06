package com.orbitalsonic.soniccolorpicker.helpers

import com.orbitalsonic.soniccolorpicker.interfaces.ColorObservable
import com.orbitalsonic.soniccolorpicker.interfaces.ColorObserver

/**
 * @Author: Muhammad Yaqoob
 * @Date: 06,April,2024.
 * @Accounts
 *      -> https://github.com/orbitalsonic
 *      -> https://www.linkedin.com/in/myaqoob7
 */
internal class ColorObservableEmitter : ColorObservable {
    private val observers: MutableList<ColorObserver> = ArrayList()
    override var color = 0
        private set

    override fun subscribe(observer: ColorObserver) {
        observers.add(observer)
    }

    override fun unsubscribe(observer: ColorObserver) {
        observers.remove(observer)
    }

    fun onColor(color: Int, fromUser: Boolean, shouldPropagate: Boolean) {
        this.color = color
        for (observer in observers) {
            observer.onColor(color, fromUser, shouldPropagate)
        }
    }
}
