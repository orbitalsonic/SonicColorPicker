package com.orbitalsonic.soniccolorpicker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.orbitalsonic.soniccolorpicker.picker.ColorPickerPopup

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnShow).setOnClickListener {
            popupColorPicker()
        }
    }

    private fun popupColorPicker() {
        ColorPickerPopup.Builder(this)
            .enableAlpha(true)
            .okTitle("Choose")
            .cancelTitle("Cancel")
            .showIndicator(true)
            .showValue(true)
            .onlyUpdateOnTouchEventUp(true)
            .build()
            .show(object : ColorPickerPopup.ColorPickerObserver() {
                override fun onColorPicked(color: Int) {
                    setColors(color)
                }

            })
    }

    private fun setColors(color: Int) {
        findViewById<View>(R.id.colorScreenView).setBackgroundColor(color)
    }
}