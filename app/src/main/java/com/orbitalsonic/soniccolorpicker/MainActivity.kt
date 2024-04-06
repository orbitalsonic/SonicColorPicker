package com.orbitalsonic.soniccolorpicker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.orbitalsonic.soniccolorpicker.picker.ColorPickerPopup

/**
 * @Author: Muhammad Yaqoob
 * @Date: 06,April,2024.
 * @Accounts
 *      -> https://github.com/orbitalsonic
 *      -> https://www.linkedin.com/in/myaqoob7
 */
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
                override fun onCancel() {}
            })
    }

    private fun setColors(color: Int) {
        findViewById<View>(R.id.colorScreenView).setBackgroundColor(color)
    }
}