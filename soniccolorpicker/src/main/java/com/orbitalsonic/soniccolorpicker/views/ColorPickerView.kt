package com.orbitalsonic.soniccolorpicker.views


import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.LinearLayout
import com.orbitalsonic.soniccolorpicker.interfaces.ColorObservable
import com.orbitalsonic.soniccolorpicker.interfaces.ColorObserver
import com.orbitalsonic.soniccolorpicker.R

class ColorPickerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    LinearLayout(context, attrs, defStyleAttr), ColorObservable {
    private val colorWheelView: ColorWheelView
    private var brightnessSliderView: BrightnessSliderView? = null
    private var alphaSliderView: AlphaSliderView? = null
    private var observableOnDuty: ColorObservable? = null
    private var onlyUpdateOnTouchEventUp: Boolean
    private var initialColor = Color.BLACK
    private val sliderMargin: Int
    private val sliderHeight: Int
    fun setOnlyUpdateOnTouchEventUp(onlyUpdateOnTouchEventUp: Boolean) {
        this.onlyUpdateOnTouchEventUp = onlyUpdateOnTouchEventUp
        updateObservableOnDuty()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val maxWidth = MeasureSpec.getSize(widthMeasureSpec)
        val maxHeight = MeasureSpec.getSize(heightMeasureSpec)
        var desiredWidth = maxHeight - (paddingTop + paddingBottom) + (paddingLeft + paddingRight)
        if (brightnessSliderView != null) {
            desiredWidth -= sliderMargin + sliderHeight
        }
        if (alphaSliderView != null) {
            desiredWidth -= sliderMargin + sliderHeight
        }

        val width = Math.min(maxWidth, desiredWidth)
        var height = width - (paddingLeft + paddingRight) + (paddingTop + paddingBottom)
        if (brightnessSliderView != null) {
            height += sliderMargin + sliderHeight
        }
        if (alphaSliderView != null) {
            height += sliderMargin + sliderHeight
        }
        super.onMeasure(
            MeasureSpec.makeMeasureSpec(width, MeasureSpec.getMode(widthMeasureSpec)),
            MeasureSpec.makeMeasureSpec(height, MeasureSpec.getMode(heightMeasureSpec))
        )
    }

    fun setInitialColor(color: Int) {
        initialColor = color
        colorWheelView.setColor(color, true)
    }

    fun setEnabledBrightness(enable: Boolean) {
        if (enable) {
            if (brightnessSliderView == null) {
                brightnessSliderView = BrightnessSliderView(context)
                val params = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, sliderHeight)
                params.topMargin = sliderMargin
                addView(brightnessSliderView, 1, params)
            }
            brightnessSliderView?.bind(colorWheelView)
            updateObservableOnDuty()
        } else {
            if (brightnessSliderView != null) {
                brightnessSliderView?.unbind()
                removeView(brightnessSliderView)
                brightnessSliderView = null
            }
            updateObservableOnDuty()
        }
        if (alphaSliderView != null) {
            setEnabledAlpha(true)
        }
    }

    fun setEnabledAlpha(enable: Boolean) {
        if (enable) {
            if (alphaSliderView == null) {
                alphaSliderView = AlphaSliderView(context)
                val params = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, sliderHeight)
                params.topMargin = sliderMargin
                addView(alphaSliderView, params)
            }
            var bindTo: ColorObservable? = brightnessSliderView
            if (bindTo == null) {
                bindTo = colorWheelView
            }
            alphaSliderView?.bind(bindTo)
            updateObservableOnDuty()
        } else {
            if (alphaSliderView != null) {
                alphaSliderView?.unbind()
                removeView(alphaSliderView)
                alphaSliderView = null
            }
            updateObservableOnDuty()
        }
    }

    private fun updateObservableOnDuty() {
        if (observableOnDuty != null) {
            for (observer in observers!!) {
                observableOnDuty!!.unsubscribe(observer)
            }
        }
        colorWheelView.setOnlyUpdateOnTouchEventUp(false)
        if (brightnessSliderView != null) {
            brightnessSliderView?.setOnlyUpdateOnTouchEventUp(false)
        }
        if (alphaSliderView != null) {
            alphaSliderView?.setOnlyUpdateOnTouchEventUp(false)
        }
        if (brightnessSliderView == null && alphaSliderView == null) {
            observableOnDuty = colorWheelView
            colorWheelView.setOnlyUpdateOnTouchEventUp(onlyUpdateOnTouchEventUp)
        } else {
            if (alphaSliderView != null) {
                observableOnDuty = alphaSliderView
                alphaSliderView?.setOnlyUpdateOnTouchEventUp(onlyUpdateOnTouchEventUp)
            } else {
                observableOnDuty = brightnessSliderView
                brightnessSliderView?.setOnlyUpdateOnTouchEventUp(onlyUpdateOnTouchEventUp)
            }
        }
        if (observers != null) {
            for (observer in observers!!) {
                observableOnDuty!!.subscribe(observer)
                observer.onColor(observableOnDuty!!.color, false, true)
            }
        }
    }

    fun reset() {
        colorWheelView.setColor(initialColor, true)
    }

    var observers: MutableList<ColorObserver>? = ArrayList()

    init {
        orientation = VERTICAL
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ColorPickerView)
        val enableAlpha = typedArray.getBoolean(R.styleable.ColorPickerView_enableAlpha, false)
        val enableBrightness =
            typedArray.getBoolean(R.styleable.ColorPickerView_enableBrightness, true)
        onlyUpdateOnTouchEventUp =
            typedArray.getBoolean(R.styleable.ColorPickerView_onlyUpdateOnTouchEventUp, false)
        typedArray.recycle()
        colorWheelView = ColorWheelView(context)
        val density = resources.displayMetrics.density
        val margin = (8 * density).toInt()
        sliderMargin = 2 * margin
        sliderHeight = (24 * density).toInt()
        val params = LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        addView(colorWheelView, params)
        setEnabledBrightness(enableBrightness)
        setEnabledAlpha(enableAlpha)
        setPadding(margin, margin, margin, margin)
    }

    override fun subscribe(observer: ColorObserver) {
        observableOnDuty?.subscribe(observer)
        observers?.add(observer)
    }

    override fun unsubscribe(observer: ColorObserver) {
        observableOnDuty?.unsubscribe(observer)
        observers?.remove(observer)
    }

    override val color: Int
        get() = observableOnDuty!!.color
}
