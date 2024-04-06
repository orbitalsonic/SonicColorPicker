package com.orbitalsonic.soniccolorpicker.interfaces

/**
 * @Author: Muhammad Yaqoob
 * @Date: 06,April,2024.
 * @Accounts
 *      -> https://github.com/orbitalsonic
 *      -> https://www.linkedin.com/in/myaqoob7
 */
interface ColorObservable {
    fun subscribe(observer: ColorObserver)
    fun unsubscribe(observer: ColorObserver)
    val color: Int
}