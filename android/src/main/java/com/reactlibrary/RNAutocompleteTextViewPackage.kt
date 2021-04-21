package com.reactlibrary;

import com.facebook.react.ReactPackage
import com.facebook.react.bridge.JavaScriptModule
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ViewManager
import java.util.*

// NOTE: Will show error in Android Studio because createJSModules needs to implemented, but it lies?
// Not needed, in fact deprecated after RN 0.47
class RNAutocompleteTextViewPackage : ReactPackage {
//    fun createJSModules(): MutableList<Class<out JavaScriptModule>> {
//        TODO("Not yet implemented")
//    }

    override fun createNativeModules(reactContext: ReactApplicationContext): List<NativeModule> {
        return emptyList()
    }

    override fun createViewManagers(reactContext: ReactApplicationContext): List<ViewManager<*, *>> {
        return Arrays.asList<ViewManager<*, *>>(
                RNAutoCompleteTextViewManager()
        )
    }
}