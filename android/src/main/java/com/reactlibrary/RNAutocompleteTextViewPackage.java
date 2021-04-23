package com.reactlibrary;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import java.util.Arrays;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;


public final class RNAutocompleteTextViewPackage implements ReactPackage {
    @NotNull
    public List createNativeModules(@NotNull ReactApplicationContext reactContext) {
        Intrinsics.checkParameterIsNotNull(reactContext, "reactContext");
        return CollectionsKt.emptyList();
    }

    @Override
    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return null;
    }

    @NotNull
    public List createViewManagers(@NotNull ReactApplicationContext reactContext) {
        Intrinsics.checkParameterIsNotNull(reactContext, "reactContext");
        List var10000 = Arrays.asList((ViewManager)(new RNAutoCompleteTextViewManager()));
        Intrinsics.checkExpressionValueIsNotNull(var10000, "Arrays.asList<ViewManage…xtViewManager()\n        )");
        return var10000;
    }
}
