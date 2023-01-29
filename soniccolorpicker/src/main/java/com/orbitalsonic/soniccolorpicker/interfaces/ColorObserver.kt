package com.orbitalsonic.soniccolorpicker.interfaces

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