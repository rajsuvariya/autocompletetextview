package com.reactlibrary;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public final class RNAutocompleteTextViewPackage implements ReactPackage {
    public List createNativeModules(ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }

    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return null;
    }

    public List createViewManagers(ReactApplicationContext reactContext) {
        return Arrays.asList((ViewManager)(new RNAutoCompleteTextViewManager()));
    }
}
