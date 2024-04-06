package com.orbitalsonic.soniccolorpicker.views

import android.content.Context
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.util.AttributeSet

/**
 * @Author: Muhammad Yaqoob
 * @Date: 06,April,2024.
 * @Accounts
 *      -> https://github.com/orbitalsonic
 *      -> https://www.linkedin.com/in/myaqoob7
 */
class BrightnessSliderView : ColorSliderView {
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun resolveValue(color: Int): Float {
        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)
        return hsv[2]
    }

    override fun configurePaint(colorPaint: Paint) {
        val hsv = FloatArray(3)
        Color.colorToHSV(baseColor, hsv)
        hsv[2] = 0f
        val startColor = Color.HSVToColor(hsv)
        hsv[2] = 1f
        val endColor = Color.HSVToColor(hsv)
        val shader: Shader = LinearGradient(
            0f,
            0f,
            width.toFloat(),
            height.toFloat(),
            startColor,
            endColor,
            Shader.TileMode.CLAMP
        )
        colorPaint.shader = shader
    }

    override fun assembleColor(): Int {
        val hsv = FloatArray(3)
        Color.colorToHSV(baseColor, hsv)
        hsv[2] = currentValue
        return Color.HSVToColor(hsv)
    }
}
