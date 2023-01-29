package com.orbitalsonic.soniccolorpicker.helpers

import android.view.MotionEvent
import com.orbitalsonic.soniccolorpicker.interfaces.Updatable


internal class ThrottledTouchEventHandler private constructor(
    minInterval: Int,
    updatable: Updatable
) {
    private var minInterval = Constants.EVENT_MIN_INTERVAL
    private val updatable: Updatable?
    private var lastPassedEventTime: Long = 0

    constructor(updatable: Updatable) : this(Constants.EVENT_MIN_INTERVAL, updatable) {}

    init {
        this.minInterval = minInterval
        this.updatable = updatable
    }

    fun onTouchEvent(event: MotionEvent) {
        if (updatable == null) return
        val current = System.currentTimeMillis()
        if (current - lastPassedEventTime <= minInterval) {
            return
        }
        lastPassedEventTime = current
        updatable.update(event)
    }
}
