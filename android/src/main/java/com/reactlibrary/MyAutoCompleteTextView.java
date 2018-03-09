package com.reactlibrary;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;

/**
 * Created by rajsuvariya on 7/8/17.
 */

public class MyAutoCompleteTextView extends android.support.v7.widget.AppCompatAutoCompleteTextView {


    public MyAutoCompleteTextView(Context context) {
        super(context);
        setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
    }

}
