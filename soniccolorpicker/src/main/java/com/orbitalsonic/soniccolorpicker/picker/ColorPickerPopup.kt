package com.orbitalsonic.soniccolorpicker.picker

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import com.orbitalsonic.soniccolorpicker.R
import com.orbitalsonic.soniccolorpicker.interfaces.ColorObserver
import com.orbitalsonic.soniccolorpicker.views.ColorPickerView
import java.util.*


class ColorPickerPopup private constructor(builder: Builder) {
    private val context: Context
    private var popupWindow: PopupWindow? = null
    private val initialColor: Int
    private val enableBrightness: Boolean
    private val enableAlpha: Boolean
    private val okTitle: String
    private val cancelTitle: String
    private val showIndicator: Boolean
    private val showValue: Boolean
    private val onlyUpdateOnTouchEventUp: Boolean

    init {
        context = builder.context
        initialColor = builder.initialColor
        enableBrightness = builder.enableBrightness
        enableAlpha = builder.enableAlpha
        okTitle = builder.okTitle
        cancelTitle = builder.cancelTitle
        showIndicator = builder.showIndicator
        showValue = builder.showValue
        onlyUpdateOnTouchEventUp = builder.onlyUpdateOnTouchEventUp
    }

    fun show(observer: ColorPickerObserver) {
        show(null, observer)
    }

    fun show(parent: View?, observer: ColorPickerObserver) {
        var mParent = parent
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layout: View = inflater.inflate(R.layout.sonic_color_picker_popup, null)
        val colorPickerView: ColorPickerView = layout.findViewById(R.id.colorPickerView)
        popupWindow = PopupWindow(
            layout, ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        popupWindow?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        popupWindow?.isOutsideTouchable = true
        colorPickerView.setInitialColor(initialColor)
        colorPickerView.setEnabledBrightness(enableBrightness)
        colorPickerView.setEnabledAlpha(enableAlpha)
        colorPickerView.setOnlyUpdateOnTouchEventUp(onlyUpdateOnTouchEventUp)
        colorPickerView.subscribe(observer)
        val cancel = layout.findViewById<TextView>(R.id.cancel)
        cancel.text = cancelTitle
        cancel.setOnClickListener { popupWindow?.dismiss() }
        val ok = layout.findViewById<TextView>(R.id.ok)
        ok.text = okTitle
        ok.setOnClickListener {
            popupWindow?.dismiss()
            observer.onColorPicked(colorPickerView.color)
        }
        val colorIndicator = layout.findViewById<View>(R.id.colorIndicator)
        val colorHex = layout.findViewById<TextView>(R.id.colorHex)
        colorIndicator.visibility = if (showIndicator) View.VISIBLE else View.GONE
        colorHex.visibility = if (showValue) View.VISIBLE else View.GONE
        if (showIndicator) {
            colorIndicator.setBackgroundColor(initialColor)
        }
        if (showValue) {
            colorHex.text = colorHex(initialColor)
        }
        colorPickerView.subscribe(object : ColorObserver {
            override fun onColor(color: Int, fromUser: Boolean, shouldPropagate: Boolean) {
                if (showIndicator) {
                    colorIndicator.setBackgroundColor(color)
                }
                if (showValue) {
                    colorHex.text = colorHex(color)
                }
            }
        })
        if (Build.VERSION.SDK_INT >= 21) {
            popupWindow?.elevation = 10.0f
        }
        popupWindow?.animationStyle = R.style.SonicColorPickerPopupAnimation
        if (mParent == null) mParent = layout
        popupWindow?.showAtLocation(mParent, Gravity.CENTER, 0, 0)
    }

    fun dismiss() {
        if (popupWindow != null) {
            popupWindow?.dismiss()
        }
    }

    class Builder(val context: Context) {
        var initialColor = Color.MAGENTA
        var enableBrightness = true
        var enableAlpha = false
        var okTitle = "OK"
        var cancelTitle = "Cancel"
        var showIndicator = true
        var showValue = true
        var onlyUpdateOnTouchEventUp = false
        fun initialColor(color: Int): Builder {
            initialColor = color
            return this
        }

        fun enableBrightness(enable: Boolean): Builder {
            enableBrightness = enable
            return this
        }

        fun enableAlpha(enable: Boolean): Builder {
            enableAlpha = enable
            return this
        }

        fun okTitle(title: String): Builder {
            okTitle = title
            return this
        }

        fun cancelTitle(title: String): Builder {
            cancelTitle = title
            return this
        }

        fun showIndicator(show: Boolean): Builder {
            showIndicator = show
            return this
        }

        fun showValue(show: Boolean): Builder {
            showValue = show
            return this
        }

        fun onlyUpdateOnTouchEventUp(only: Boolean): Builder {
            onlyUpdateOnTouchEventUp = only
            return this
        }

        fun build(): ColorPickerPopup {
            return ColorPickerPopup(this)
        }
    }

    private fun colorHex(color: Int): String {
        val a = Color.alpha(color)
        val r = Color.red(color)
        val g = Color.green(color)
        val b = Color.blue(color)
        return String.format(Locale.getDefault(), "0x%02X%02X%02X%02X", a, r, g, b)
    }

    abstract class ColorPickerObserver : ColorObserver {
        abstract fun onColorPicked(color: Int)
        override fun onColor(color: Int, fromUser: Boolean, shouldPropagate: Boolean) {}
    }
}
