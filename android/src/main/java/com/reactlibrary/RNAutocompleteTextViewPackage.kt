package com.reactlibrary;

import com.facebook.react.ReactPackage
import com.facebook.react.bridge.JavaScriptModule
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ViewManager
import java.util.*

class RNAutocompleteTextViewPackage : ReactPackage {
    override fun createJSModules(): MutableList<Class<out JavaScriptModule>> {
        TODO("Not yet implemented")
    }

    override fun createNativeModules(reactContext: ReactApplicationContext): List<NativeModule> {
        return emptyList()
    }

    override fun createViewManagers(reactContext: ReactApplicationContext): List<ViewManager<*, *>> {
        return Arrays.asList<ViewManager<*, *>>(
                RNAutoCompleteTextViewManager()
        )
    }
}