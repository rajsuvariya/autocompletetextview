package com.rtsfinancial.carrierpro

import android.content.Context
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.ReactContext
import com.facebook.react.bridge.ReadableArray
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.annotations.ReactProp
import com.facebook.react.uimanager.events.RCTEventEmitter
import org.apache.commons.lang3.text.StrSubstitutor
import org.json.JSONArray
import java.util.*


class RNAutoCompleteTextViewManager : SimpleViewManager<RNAutoCompleteTextView>() {
    var mContext: Context? = null
    var lastInputText: String? = null
    var settingFromJS = false
    var enteringText = false
    var delay = 200
    var lastEditTime: Long = 0
    var handler = Handler()
    var optionList: ArrayList<String>? = null
    var optionsMap = hashMapOf<String, Int>()
    var textWatcher: NativeTextWatcher? = null
    var autocomplete: RNAutoCompleteTextView? = null
    val COMMAND_FOCUS = 1;
    val COMMAND_BLUR = 2;
    private val InputFinishChecker = Runnable {
        if (System.currentTimeMillis() > lastEditTime + delay) {
            enteringText = false
        }
    }

    inner class NativeTextWatcher(var reactContext: ThemedReactContext, var view: AutoCompleteTextView) : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
            val inputText = charSequence.toString()
            if (inputText == lastInputText || view.isPerformingCompletion) {
                return
            }
            enteringText = !settingFromJS
            lastInputText = inputText
            val event = Arguments.createMap()
            event.putString("text", inputText)
            reactContext.getJSModule(RCTEventEmitter::class.java).receiveEvent(
                    view.id,
                    "topChange",
                    event)
            handler.removeCallbacks(InputFinishChecker)
        }

        override fun afterTextChanged(editable: Editable) {
            lastEditTime = System.currentTimeMillis()
            handler.postDelayed(InputFinishChecker, delay.toLong())
        }

    }

    override fun getName(): String {
        return "RNAutoCompleteTextView"
    }

    override fun createViewInstance(reactContext: ThemedReactContext): RNAutoCompleteTextView {
        mContext = reactContext
        autocomplete = RNAutoCompleteTextView(reactContext)
        autocomplete!!.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            onChangeFocus(hasFocus, view)
        }
        textWatcher = NativeTextWatcher(reactContext, autocomplete!!)
        autocomplete!!.addTextChangedListener(textWatcher)
        autocomplete!!.threshold = 1
        return autocomplete!!
    }

    fun onChangeFocus(focused: Boolean, view: View) {
        val params = Arguments.createMap()
        params.putBoolean("focused", focused)
        val reactContext = view.context as ReactContext
        reactContext.getJSModule(RCTDeviceEventEmitter::class.java)
                .emit("onFocus", params)
    }

    @ReactProp(name = "dataSource")
    fun setDataSource(view: RNAutoCompleteTextView, data: ReadableMap) {
        val template = data.getString("itemFormat")
        val data = data.getString("dataSource")
        val jsonArray = JSONArray(data)
        optionList = ArrayList()
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val keys = jsonObject.keys()
            val valuesMap = hashMapOf<String, String>()
            keys.forEach {
                val value = jsonObject.optString(it)
                if (value is String) {
                    valuesMap.put(it, value)
                }
            }
            val sub = StrSubstitutor(valuesMap)
            val resolvedString = sub.replace(template)
            optionList!!.add(resolvedString)
            optionsMap.put(resolvedString, i)
        }
        view.setAdapter(ArrayAdapter(mContext, R.layout.simple_spinner_dropdown_item, optionList))
        view.onItemClickListener = AdapterView.OnItemClickListener {
            parent: AdapterView<*>?,
            view: View,
            position: Int,
            id: Long -> onItemClick(parent, view, position, id)
        }
    }

    fun onItemClick(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
        enteringText = false
        val reactContext = view.context as ReactContext
        val item = parent!!.getItemAtPosition(position)
        val originalId = optionsMap.get(item)
        reactContext.getJSModule(RCTDeviceEventEmitter::class.java)
                .emit("onItemClick", originalId)
        showDropDown(autocomplete!!, false)
    }

    @ReactProp(name = "value")
    fun setValue(view: RNAutoCompleteTextView, value: String?) {
        settingFromJS = true
        if (!enteringText) {
            view.removeTextChangedListener(textWatcher)
            view.setText(value)
            if (value != null) {
                view.setSelection(value.length)
            }
            view.addTextChangedListener(textWatcher)
        }
        settingFromJS = false
    }

    @ReactProp(name = "hint")
    fun setHint(view: RNAutoCompleteTextView, value: String?) {
        view.hint = value
    }

    @ReactProp(name = "showDropDown")
    fun showDropDown(view: RNAutoCompleteTextView, show: Boolean) {
        if (show) {
            if (optionList!!.size > 0) {
                view.showDropDown()
            }
        }
    }

    override fun getCommandsMap(): Map<String?, Int?>? {
        return mapOf<String?, Int?>("focus" to COMMAND_FOCUS, "blur" to COMMAND_BLUR)
    }

    override fun receiveCommand(view: RNAutoCompleteTextView, commandId: Int, args: ReadableArray?) {
        println("receive command")
        when (commandId) {
            COMMAND_FOCUS -> autocomplete!!.requestFocus()
            COMMAND_BLUR -> autocomplete!!.clearFocus()
        }
    }
}