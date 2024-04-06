package com.orbitalsonic.soniccolorpicker.interfaces

/**
 * @Author: Muhammad Yaqoob
 * @Date: 06,April,2024.
 * @Accounts
 *      -> https://github.com/orbitalsonic
 *      -> https://www.linkedin.com/in/myaqoob7
 */
interface ColorObserver {
    /**
     * Color has changed.
     *
     * @param color the new color
     * @param fromUser if this color is changed by user or not (programmatically)
     * @param shouldPropagate should this event be propagated to the observers (you can ignore this)
     */
    fun onColor(color: Int, fromUser: Boolean, shouldPropagate: Boolean)
}