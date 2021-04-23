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

    // NOTE : needs this for the standalone module but causes error in RN project because it is not overridable
//    @Override
//    public List<Class<? extends JavaScriptModule>> createJSModules() {
//        return null;
//    }

    public List createViewManagers(ReactApplicationContext reactContext) {
        List var10000 = Arrays.asList((ViewManager)(new RNAutoCompleteTextViewManager()));
        return var10000;
    }
}
