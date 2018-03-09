package com.reactlibrary;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.RCTEventEmitter;

import java.util.ArrayList;

/**
 * Created by rajsuvariya on 7/8/17.
 */

public class AutoCompleteTextViewManager extends SimpleViewManager<MyAutoCompleteTextView> {

    Context mContext;

    @Override
    public String getName() {
        return "RCTAutoCompleteTextView";
    }

    @Override
    protected MyAutoCompleteTextView createViewInstance(ThemedReactContext reactContext) {
        this.mContext = reactContext;
        final MyAutoCompleteTextView view = new MyAutoCompleteTextView(reactContext);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MyAutoCompleteTextView)view).showDropDown();
                ((MyAutoCompleteTextView)view).requestFocus();
            }
        });
        view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                ((MyAutoCompleteTextView)view).showDropDown();
                ((MyAutoCompleteTextView)view).requestFocus();
            }
        });
        view.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                WritableMap event = Arguments.createMap();
                event.putString("text", charSequence.toString());
                ReactContext reactContext = (ReactContext) view.getContext();
                reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
                        view.getId(),
                        "topChange",
                        event);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        view.setThreshold(1);
        return view;

    }

    @ReactProp(name = "dataSource")
    public void setDataSource(MyAutoCompleteTextView view, ReadableArray array){
        ArrayList<String> optionList = new ArrayList<>();
        for (Object option : array.toArrayList()){
            optionList.add((String)option);
        }

        view.setAdapter(new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, optionList));
    }

    @ReactProp(name = "value")
    public void setValue(MyAutoCompleteTextView view, String value){
        view.setText(value);
        if (value!=null) {
            view.setSelection(value.length());
        }
    }

    @ReactProp(name = "hint")
    public void setHint(MyAutoCompleteTextView view, String value){
        view.setHint(value);
    }

    @ReactProp(name = "showDropDown")
    public void showDropDown(MyAutoCompleteTextView view, boolean show){
        if (show){
            view.showDropDown();
        }
    }
}
