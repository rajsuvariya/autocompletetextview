package com.reactlibrary;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import androidx.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.AdapterView.OnItemClickListener;
import androidx.annotation.RequiresApi;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
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
import java.util.Map;
import org.apache.commons.lang3.text.StrSubstitutor;
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
    private Handler handler = new Handler();
    @Nullable
    private ArrayList optionList;
    private HashMap optionsMap;
    @Nullable
    private RNAutoCompleteTextViewManager.NativeTextWatcher textWatcher;
    @Nullable
    private RNAutoCompleteTextView autocomplete;
    private final int COMMAND_FOCUS;
    private final int COMMAND_BLUR;
    private final Runnable InputFinishChecker;

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

    public final void setEnteringText(boolean var1) {
        this.enteringText = var1;
    }

    public final int getDelay() {
        return this.delay;
    }

    public final long getLastEditTime() {
        return this.lastEditTime;
    }

    public final void setLastEditTime(long var1) {
        this.lastEditTime = var1;
    }

    public final Handler getHandler() {
        return this.handler;
    }

    public String getName() {
        return "RNAutoCompleteTextView";
    }

    @RequiresApi(21)
    protected RNAutoCompleteTextView createViewInstance(ThemedReactContext reactContext) {
        this.mContext = (Context)reactContext;
        this.autocomplete = new RNAutoCompleteTextView((Context)reactContext);
        this.autocomplete.setOnFocusChangeListener((OnFocusChangeListener)(new OnFocusChangeListener() {
            public final void onFocusChange(View view, boolean hasFocus) {
                RNAutoCompleteTextViewManager.this.onChangeFocus(hasFocus, view);
            }
        }));
        this.textWatcher = new RNAutoCompleteTextViewManager.NativeTextWatcher(reactContext, this.autocomplete);;
        this.autocomplete.addTextChangedListener((TextWatcher)this.textWatcher);
        this.autocomplete.setThreshold(1);
        return this.autocomplete;
    }

    public final void onChangeFocus(boolean focused, View view) {
        WritableMap params = Arguments.createMap();
        params.putBoolean("focused", focused);
        ReactContext reactContext = (ReactContext)view.getContext();
        ((RCTDeviceEventEmitter)reactContext.getJSModule(RCTDeviceEventEmitter.class)).emit("onFocus", params);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @ReactProp(
            name = "dataSource"
    )
    public final void setDataSource(RNAutoCompleteTextView view, ReadableMap data) throws JSONException {
        String template = data.getString("itemFormat");
        String listData = data.getString("dataSource");
        JSONArray jsonArray = new JSONArray(listData);
        this.optionList = new ArrayList();
        int i = 0;

        for(int var7 = jsonArray.length(); i < var7; ++i) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Iterator keys = jsonObject.keys();
            HashMap valuesMap = new HashMap();
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
            this.optionList.add(resolvedString);
            this.optionsMap.put(resolvedString, i);
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(this.mContext, layout.simple_spinner_dropdown_item, this.optionList);
        view.setAdapter(arrayAdapter);
        view.setOnItemClickListener((OnItemClickListener)(new OnItemClickListener() {
            public final void onItemClick(@Nullable AdapterView parent, View view, int position, long id) {
                RNAutoCompleteTextViewManager.this.onItemClick(parent, view, position, id);
            }
        }));
    }

    public final void onItemClick(@Nullable AdapterView parent, View view, int position, long id) {
        this.enteringText = false;
        ReactContext reactContext = (ReactContext)view.getContext();
        Object item = parent.getItemAtPosition(position);
        Integer originalId = (Integer)this.optionsMap.get(item);
        ((RCTDeviceEventEmitter)reactContext.getJSModule(RCTDeviceEventEmitter.class)).emit("onItemClick", originalId);
        this.showDropDown(this.autocomplete, false);
    }

    @ReactProp(
            name = "value"
    )
    public final void setValue(RNAutoCompleteTextView view, @Nullable String value) {
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
    public final void setHint(RNAutoCompleteTextView view, @Nullable String value) {
        view.setHint((CharSequence)value);
    }

    @ReactProp(
            name = "showDropDown"
    )
    public final void showDropDown(RNAutoCompleteTextView view, boolean show) {
        if (show) {
            if (this.optionList.size() > 0) {
                view.showDropDown();
            }
        }

    }

    // NOTR: not used but has to be converted to Java
//    @Nullable
//    public Map getCommandsMap() {
//        return MapsKt.mapOf(new Pair[]{TuplesKt.to("focus", this.COMMAND_FOCUS), TuplesKt.to("blur", this.COMMAND_BLUR)});
//    }
//
//    public void receiveCommand(RNAutoCompleteTextView view, int commandId, @Nullable ReadableArray args) {
//        String var4 = "receive command";
//        System.out.println(var4);
//        RNAutoCompleteTextView var10000;
//        if (commandId == this.COMMAND_FOCUS) {
//            this.autocomplete.requestFocus();
//        } else if (commandId == this.COMMAND_BLUR) {
//            this.autocomplete.clearFocus();
//        }
//
//    }
//
//    // $FF: synthetic method
//    // $FF: bridge method
//    public void receiveCommand(View var1, int var2, ReadableArray var3) {
//        this.receiveCommand((RNAutoCompleteTextView)var1, var2, var3);
//    }

    public RNAutoCompleteTextViewManager() {
        this.optionsMap = new HashMap();
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

    public final class NativeTextWatcher implements TextWatcher {
        private ThemedReactContext reactContext;
        private AutoCompleteTextView view;

        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            String inputText = charSequence.toString();
            if (!inputText.equals(RNAutoCompleteTextViewManager.this.getLastInputText()) && !this.view.isPerformingCompletion()) {
                RNAutoCompleteTextViewManager.this.setEnteringText(!RNAutoCompleteTextViewManager.this.getSettingFromJS());
                RNAutoCompleteTextViewManager.this.setLastInputText(inputText);
                WritableMap event = Arguments.createMap();
                event.putString("text", inputText);
                ((RCTEventEmitter)this.reactContext.getJSModule(RCTEventEmitter.class)).receiveEvent(this.view.getId(), "topChange", event);
                RNAutoCompleteTextViewManager.this.getHandler().removeCallbacks(RNAutoCompleteTextViewManager.this.InputFinishChecker);
            }
        }

        public void afterTextChanged(Editable editable) {
            RNAutoCompleteTextViewManager.this.setLastEditTime(System.currentTimeMillis());
            RNAutoCompleteTextViewManager.this.getHandler().postDelayed(
                    RNAutoCompleteTextViewManager.this.InputFinishChecker,
                    (long)RNAutoCompleteTextViewManager.this.getDelay());
        }

        public NativeTextWatcher(ThemedReactContext reactContext, AutoCompleteTextView view) {
            super();
            this.reactContext = reactContext;
            this.view = view;
        }
    }
}
