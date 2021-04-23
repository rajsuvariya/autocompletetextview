package com.reactlibrary;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListAdapter;
import android.widget.AdapterView.OnItemClickListener;
import androidx.annotation.RequiresApi;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.reactlibrary.R.layout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.TypeCastException;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class RNAutoCompleteTextViewManager extends SimpleViewManager {
    @Nullable
    private Context mContext;
    @Nullable
    private String lastInputText;
    private boolean settingFromJS;
    private boolean enteringText;
    private int delay = 200;
    private long lastEditTime;
    @NotNull
    private Handler handler = new Handler();
    @Nullable
    private ArrayList optionList;
    @NotNull
    private HashMap optionsMap;
    @Nullable
    private RNAutoCompleteTextViewManager.NativeTextWatcher textWatcher;
    @Nullable
    private RNAutoCompleteTextView autocomplete;
    private final int COMMAND_FOCUS;
    private final int COMMAND_BLUR;
    private final Runnable InputFinishChecker;

    @Nullable
    public final Context getMContext() {
        return this.mContext;
    }

    public final void setMContext(@Nullable Context var1) {
        this.mContext = var1;
    }

    @Nullable
    public final String getLastInputText() {
        return this.lastInputText;
    }

    public final void setLastInputText(@Nullable String var1) {
        this.lastInputText = var1;
    }

    public final boolean getSettingFromJS() {
        return this.settingFromJS;
    }

    public final void setSettingFromJS(boolean var1) {
        this.settingFromJS = var1;
    }

    public final boolean getEnteringText() {
        return this.enteringText;
    }

    public final void setEnteringText(boolean var1) {
        this.enteringText = var1;
    }

    public final int getDelay() {
        return this.delay;
    }

    public final void setDelay(int var1) {
        this.delay = var1;
    }

    public final long getLastEditTime() {
        return this.lastEditTime;
    }

    public final void setLastEditTime(long var1) {
        this.lastEditTime = var1;
    }

    @NotNull
    public final Handler getHandler() {
        return this.handler;
    }

    public final void setHandler(@NotNull Handler var1) {
        Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
        this.handler = var1;
    }

    @Nullable
    public final ArrayList getOptionList() {
        return this.optionList;
    }

    public final void setOptionList(@Nullable ArrayList var1) {
        this.optionList = var1;
    }

    @NotNull
    public final HashMap getOptionsMap() {
        return this.optionsMap;
    }

    public final void setOptionsMap(@NotNull HashMap var1) {
        Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
        this.optionsMap = var1;
    }

    @Nullable
    public final RNAutoCompleteTextViewManager.NativeTextWatcher getTextWatcher() {
        return this.textWatcher;
    }

    public final void setTextWatcher(@Nullable RNAutoCompleteTextViewManager.NativeTextWatcher var1) {
        this.textWatcher = var1;
    }

    @Nullable
    public final RNAutoCompleteTextView getAutocomplete() {
        return this.autocomplete;
    }

    public final void setAutocomplete(@Nullable RNAutoCompleteTextView var1) {
        this.autocomplete = var1;
    }

    public final int getCOMMAND_FOCUS() {
        return this.COMMAND_FOCUS;
    }

    public final int getCOMMAND_BLUR() {
        return this.COMMAND_BLUR;
    }

    @NotNull
    public String getName() {
        return "RNAutoCompleteTextView";
    }

    @RequiresApi(21)
    @NotNull
    protected RNAutoCompleteTextView createViewInstance(@NotNull ThemedReactContext reactContext) {
        Intrinsics.checkParameterIsNotNull(reactContext, "reactContext");
        this.mContext = (Context)reactContext;
        this.autocomplete = new RNAutoCompleteTextView((Context)reactContext);
        RNAutoCompleteTextView var10000 = this.autocomplete;
        if (var10000 == null) {
            Intrinsics.throwNpe();
        }

        var10000.setOnFocusChangeListener((OnFocusChangeListener)(new OnFocusChangeListener() {
            public final void onFocusChange(View view, boolean hasFocus) {
                RNAutoCompleteTextViewManager var10000 = RNAutoCompleteTextViewManager.this;
                Intrinsics.checkExpressionValueIsNotNull(view, "view");
                var10000.onChangeFocus(hasFocus, view);
            }
        }));
        this.textWatcher = new RNAutoCompleteTextViewManager.NativeTextWatcher(reactContext, this.autocomplete);;
        var10000 = this.autocomplete;
        if (var10000 == null) {
            Intrinsics.throwNpe();
        }

        var10000.addTextChangedListener((TextWatcher)this.textWatcher);
        var10000 = this.autocomplete;
        if (var10000 == null) {
            Intrinsics.throwNpe();
        }

        var10000.setThreshold(1);
        var10000 = this.autocomplete;
        if (var10000 == null) {
            Intrinsics.throwNpe();
        }

        return var10000;
    }

    public final void onChangeFocus(boolean focused, @NotNull View view) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        WritableMap params = Arguments.createMap();
        params.putBoolean("focused", focused);
        Context var10000 = view.getContext();
        if (var10000 == null) {
            throw new TypeCastException("null cannot be cast to non-null type com.facebook.react.bridge.ReactContext");
        } else {
            ReactContext reactContext = (ReactContext)var10000;
            ((RCTDeviceEventEmitter)reactContext.getJSModule(RCTDeviceEventEmitter.class)).emit("onFocus", params);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @ReactProp(
            name = "dataSource"
    )
    public final void setDataSource(@NotNull RNAutoCompleteTextView view, @NotNull ReadableMap data) throws JSONException {
        Intrinsics.checkParameterIsNotNull(view, "view");
        Intrinsics.checkParameterIsNotNull(data, "data");
        String template = data.getString("itemFormat");
        String listData = data.getString("dataSource");
        JSONArray jsonArray = new JSONArray(listData);
        this.optionList = new ArrayList();
        int i = 0;

        for(int var7 = jsonArray.length(); i < var7; ++i) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Iterator keys = jsonObject.keys();
            HashMap valuesMap = new HashMap();
            Intrinsics.checkExpressionValueIsNotNull(keys, "keys");
            Iterator var13 = keys;

            while(var13.hasNext()) {
                Object element$iv = var13.next();
                String it = (String)element$iv;
                String value = jsonObject.optString(it);
                if (value instanceof String) {
                    valuesMap.put(it, value);
                }
            }

            StrSubstitutor sub = new StrSubstitutor((Map)valuesMap);
            String resolvedString = sub.replace(template);
            ArrayList var10000 = this.optionList;
            if (var10000 == null) {
                Intrinsics.throwNpe();
            }

            var10000.add(resolvedString);
            this.optionsMap.put(resolvedString, i);
        }

        RNAutoCompleteTextView var23 = view;
        Context var10001 = this.mContext;
        ArrayAdapter var25;
        if (var10001 != null) {
            Context var21 = var10001;
            boolean var22 = false;
            ArrayAdapter var24 = new ArrayAdapter(var21, layout.simple_spinner_dropdown_item, this.optionList);
            ArrayAdapter var20 = var24;
            var23 = view;
            var25 = var20;
        } else {
            var25 = null;
        }

        var23.setAdapter((ListAdapter)var25);
        view.setOnItemClickListener((OnItemClickListener)(new OnItemClickListener() {
            public final void onItemClick(@Nullable AdapterView parent, @NotNull View view, int position, long id) {
                Intrinsics.checkParameterIsNotNull(view, "view");
                RNAutoCompleteTextViewManager.this.onItemClick(parent, view, position, id);
            }
        }));
    }

    public final void onItemClick(@Nullable AdapterView parent, @NotNull View view, int position, long id) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        this.enteringText = false;
        Context var10000 = view.getContext();
        if (var10000 == null) {
            throw new TypeCastException("null cannot be cast to non-null type com.facebook.react.bridge.ReactContext");
        } else {
            ReactContext reactContext = (ReactContext)var10000;
            if (parent == null) {
                Intrinsics.throwNpe();
            }

            Object item = parent.getItemAtPosition(position);
            Map var9 = (Map)this.optionsMap;
            Integer originalId = (Integer)var9.get(item);
            ((RCTDeviceEventEmitter)reactContext.getJSModule(RCTDeviceEventEmitter.class)).emit("onItemClick", originalId);
            RNAutoCompleteTextView var10001 = this.autocomplete;
            if (var10001 == null) {
                Intrinsics.throwNpe();
            }

            this.showDropDown(var10001, false);
        }
    }

    @ReactProp(
            name = "value"
    )
    public final void setValue(@NotNull RNAutoCompleteTextView view, @Nullable String value) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        this.settingFromJS = true;
        if (!this.enteringText) {
            view.removeTextChangedListener((TextWatcher)this.textWatcher);
            view.setText((CharSequence)value);
            if (value != null) {
                view.setSelection(value.length());
            }

            view.addTextChangedListener((TextWatcher)this.textWatcher);
        }

        this.settingFromJS = false;
    }

    @ReactProp(
            name = "hint"
    )
    public final void setHint(@NotNull RNAutoCompleteTextView view, @Nullable String value) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        view.setHint((CharSequence)value);
    }

    @ReactProp(
            name = "showDropDown"
    )
    public final void showDropDown(@NotNull RNAutoCompleteTextView view, boolean show) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        if (show) {
            ArrayList var10000 = this.optionList;
            if (var10000 == null) {
                Intrinsics.throwNpe();
            }

            if (var10000.size() > 0) {
                view.showDropDown();
            }
        }

    }

    @Nullable
    public Map getCommandsMap() {
        return MapsKt.mapOf(new Pair[]{TuplesKt.to("focus", this.COMMAND_FOCUS), TuplesKt.to("blur", this.COMMAND_BLUR)});
    }

    public void receiveCommand(@NotNull RNAutoCompleteTextView view, int commandId, @Nullable ReadableArray args) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        String var4 = "receive command";
        System.out.println(var4);
        RNAutoCompleteTextView var10000;
        if (commandId == this.COMMAND_FOCUS) {
            var10000 = this.autocomplete;
            if (var10000 == null) {
                Intrinsics.throwNpe();
            }

            var10000.requestFocus();
        } else if (commandId == this.COMMAND_BLUR) {
            var10000 = this.autocomplete;
            if (var10000 == null) {
                Intrinsics.throwNpe();
            }

            var10000.clearFocus();
        }

    }

    // $FF: synthetic method
    // $FF: bridge method
    public void receiveCommand(View var1, int var2, ReadableArray var3) {
        this.receiveCommand((RNAutoCompleteTextView)var1, var2, var3);
    }

    public RNAutoCompleteTextViewManager() {
        HashMap var2 = new HashMap();
        this.optionsMap = var2;
        this.COMMAND_FOCUS = 1;
        this.COMMAND_BLUR = 2;
        this.InputFinishChecker = (Runnable)(new Runnable() {
            public final void run() {
                if (System.currentTimeMillis() > RNAutoCompleteTextViewManager.this.getLastEditTime() + (long)RNAutoCompleteTextViewManager.this.getDelay()) {
                    RNAutoCompleteTextViewManager.this.setEnteringText(false);
                }

            }
        });
    }

    @Metadata(
            mv = {1, 1, 16},
            bv = {1, 0, 3},
            k = 1,
            d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\r\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J(\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u0017H\u0016J(\u0010\u001a\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u001b\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0017H\u0016R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u001c"},
            d2 = {"Lcom/reactlibrary/RNAutoCompleteTextViewManager$NativeTextWatcher;", "Landroid/text/TextWatcher;", "reactContext", "Lcom/facebook/react/uimanager/ThemedReactContext;", "view", "Landroid/widget/AutoCompleteTextView;", "(Lcom/reactlibrary/RNAutoCompleteTextViewManager;Lcom/facebook/react/uimanager/ThemedReactContext;Landroid/widget/AutoCompleteTextView;)V", "getReactContext", "()Lcom/facebook/react/uimanager/ThemedReactContext;", "setReactContext", "(Lcom/facebook/react/uimanager/ThemedReactContext;)V", "getView", "()Landroid/widget/AutoCompleteTextView;", "setView", "(Landroid/widget/AutoCompleteTextView;)V", "afterTextChanged", "", "editable", "Landroid/text/Editable;", "beforeTextChanged", "charSequence", "", "start", "", "count", "after", "onTextChanged", "before", "android"}
    )
    public final class NativeTextWatcher implements TextWatcher {
        @NotNull
        private ThemedReactContext reactContext;
        @NotNull
        private AutoCompleteTextView view;

        public void beforeTextChanged(@NotNull CharSequence charSequence, int start, int count, int after) {
            Intrinsics.checkParameterIsNotNull(charSequence, "charSequence");
        }

        public void onTextChanged(@NotNull CharSequence charSequence, int start, int before, int count) {
            Intrinsics.checkParameterIsNotNull(charSequence, "charSequence");
            String inputText = charSequence.toString();
            if (!Intrinsics.areEqual(inputText, RNAutoCompleteTextViewManager.this.getLastInputText()) && !this.view.isPerformingCompletion()) {
                RNAutoCompleteTextViewManager.this.setEnteringText(!RNAutoCompleteTextViewManager.this.getSettingFromJS());
                RNAutoCompleteTextViewManager.this.setLastInputText(inputText);
                WritableMap event = Arguments.createMap();
                event.putString("text", inputText);
                ((RCTEventEmitter)this.reactContext.getJSModule(RCTEventEmitter.class)).receiveEvent(this.view.getId(), "topChange", event);
                RNAutoCompleteTextViewManager.this.getHandler().removeCallbacks(RNAutoCompleteTextViewManager.this.InputFinishChecker);
            }
        }

        public void afterTextChanged(@NotNull Editable editable) {
            Intrinsics.checkParameterIsNotNull(editable, "editable");
            RNAutoCompleteTextViewManager.this.setLastEditTime(System.currentTimeMillis());
            RNAutoCompleteTextViewManager.this.getHandler().postDelayed(RNAutoCompleteTextViewManager.this.InputFinishChecker, (long)RNAutoCompleteTextViewManager.this.getDelay());
        }

        @NotNull
        public final ThemedReactContext getReactContext() {
            return this.reactContext;
        }

        public final void setReactContext(@NotNull ThemedReactContext var1) {
            Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
            this.reactContext = var1;
        }

        @NotNull
        public final AutoCompleteTextView getView() {
            return this.view;
        }

        public final void setView(@NotNull AutoCompleteTextView var1) {
            Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
            this.view = var1;
        }

        public NativeTextWatcher(@NotNull ThemedReactContext reactContext, @NotNull AutoCompleteTextView view) {
            super();
            Intrinsics.checkParameterIsNotNull(reactContext, "reactContext");
            Intrinsics.checkParameterIsNotNull(view, "view");
            this.reactContext = reactContext;
            this.view = view;
        }
    }
}
