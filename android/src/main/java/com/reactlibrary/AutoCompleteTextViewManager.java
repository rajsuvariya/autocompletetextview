package com.reactlibrary;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.view.inputmethod.EditorInfo;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.facebook.react.views.text.DefaultStyleValuesUtil;

import java.util.ArrayList;
import javax.annotation.Nullable;

/**
 * Created by rajsuvariya on 7/8/17.
 */

public class AutoCompleteTextViewManager extends SimpleViewManager<MyAutoCompleteTextView> {

    Context mContext;
    private Integer mThreshold = 0;

    @Override
    public String getName() {
        return "RCTAutoCompleteTextView";
    }

    @Override
    protected MyAutoCompleteTextView createViewInstance(final ThemedReactContext reactContext) {
        this.mContext = reactContext;
        final MyAutoCompleteTextView view = new MyAutoCompleteTextView(reactContext);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mThreshold == 0 || ((MyAutoCompleteTextView)view).enoughToFilter()) {
                    ((MyAutoCompleteTextView)view).showDropDown();
                }
                ((MyAutoCompleteTextView)view).requestFocus();
            }
        });
        view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (mThreshold == 0 || ((MyAutoCompleteTextView)view).enoughToFilter()) {
                    ((MyAutoCompleteTextView)view).showDropDown();
                }
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
                // ReactContext reactContext = (ReactContext) view.getContext();
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

    @ReactProp(name = "disableFullscreenUI", defaultBoolean = false)
    public void setDisableFullscreenUI(MyAutoCompleteTextView view, boolean disableFullscreenUI) {
        int imeOptions = view.getImeOptions();
        if (disableFullscreenUI) {
            view.setImeOptions(imeOptions | EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        } else {
            view.setImeOptions(imeOptions & EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        }
    }

    @ReactProp(name = "editable", defaultBoolean = true)
    public void setEditable(MyAutoCompleteTextView view, boolean editable) {
        view.setEnabled(editable);
    }

    @ReactProp(name = "placeholder")
    public void setHint(MyAutoCompleteTextView view, String value){
        view.setHint(value);
    }

    @ReactProp(name = "placeholderTextColor", customType = "Color")
    public void setPlaceholderTextColor(MyAutoCompleteTextView view, @Nullable Integer color) {
        if (color == null) {
            view.setHintTextColor(DefaultStyleValuesUtil.getDefaultTextColorHint(view.getContext()));
        } else {
            view.setHintTextColor(color);
        }
    }

    @ReactProp(name = "selectTextOnFocus", defaultBoolean = false)
    public void setSelectTextOnFocus(MyAutoCompleteTextView view, boolean selectTextOnFocus) {
        view.setSelectAllOnFocus(selectTextOnFocus);
    }

    @ReactProp(name = "showDropDown")
    public void showDropDown(MyAutoCompleteTextView view, boolean show){
        if (show) {
            view.showDropDown();
        } else {
            view.dismissDropDown();
        }
    }

    @ReactProp(name = "threshold")
    public void setThreshold(MyAutoCompleteTextView view, @Nullable Integer threshold) {
        // we must maintain mThreshold, because threshold can never be set < 1. so we need to get read the threshold prop here and see if its 0, if it is then showDropDown, in the onFocus, onClick
        if (threshold == null) {
            view.setThreshold(1);
            mThreshold = 0;
        } else {
            view.setThreshold(threshold);
            mThreshold = threshold;
        }
    }

    @ReactProp(name = "value")
    public void setValue(MyAutoCompleteTextView view, String value){
        view.setText(value);
        if (value!=null) {
            view.setSelection(value.length());
        }
    }
}
