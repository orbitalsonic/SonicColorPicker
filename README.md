[![](https://jitpack.io/v/orbitalsonic/SonicColorPicker.svg)](https://jitpack.io/#orbitalsonic/SonicColorPicker)
# SonicColorPicker
SonicColorPicker library allows you to display a ColorPicker for Android. Pick a color using color wheel and slider (HSV & alpha)

## Gradle Files

Add maven repository in project level build.gradle or in latest project setting.gradle file

```
repositories {
    google()
    mavenCentral()
    maven { url 'https://jitpack.io' }
}

```

Add SonicColorPicker dependencies in App level build.gradle.

```
  implementation 'com.github.orbitalsonic:SonicColorPicker:1.2'
 
 ```
 
 
 ## Android Coding
   
   ###### Using ColorPickerPopup
   
   ``` 
         ColorPickerPopup.Builder(this)
            .initialColor(Color.RED)
            .enableBrightness(true)
            .enableAlpha(true)
            .okTitle("Choose")
            .cancelTitle("Cancel")
            .showIndicator(true)
            .showValue(true)
            .onlyUpdateOnTouchEventUp(true)
            .build()
            .show(object : ColorPickerPopup.ColorPickerObserver() {
                override fun onColorPicked(color: Int) {
                    
                }

                override fun onColor(color: Int, fromUser: Boolean, shouldPropagate: Boolean) {
                    super.onColor(color, fromUser, shouldPropagate)
                }
            })
   
   
 ```
   ###### Using ColorPickerView    
        
 ```
    <com.orbitalsonic.soniccolorpicker.views.ColorPickerView
    android:id="@+id/colorPicker"
    android:layout_width="0dp"
    android:layout_height="wrap_content"
    app:enableBrightness="true"
    app:enableAlpha="true"
    app:layout_constraintLeft_toLeftOf="parent"
    app:layout_constraintRight_toRightOf="parent"
    app:layout_constraintTop_toTopOf="parent"/>
    
    
 ```
   
   * Implement `ColorObserver` and subscribe to `ColorPickerView` to receive color updates from the `ColorPickerView`
   
 ```
 colorPickerView.subscribe { color, fromUser -> }
 ```
 
* Set initial color:

```
colorPickerView.setInitialColor(0x7F313C93)
```
* Reset to initial color:

```
colorPickerView.reset()
```
      
## LICENSE
Copyright 2023 Engr. Muhammad Yaqoob

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.


## Screenshots
![alt text](https://github.com/orbitalsonic/SonicColorPicker/blob/master/Screenshots/Screenshot.png?raw=true)

