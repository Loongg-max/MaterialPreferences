package com.yarolegovich.mp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.yarolegovich.mp.io.StorageModule;

/**
 * Created by yarolegovich on 01.05.2016.
 */
public class MaterialChoicePreference extends AbsMaterialListPreference<String> {

    public MaterialChoicePreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MaterialChoicePreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MaterialChoicePreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    public String getValue() {
        return storageModule.getString(key, defaultValue);
    }

    @Override
    public void setValue(String value) {
        storageModule.saveString(key, value);
        showNewValueIfNeeded(toRepresentation(value));
    }

    @Override
    public void setStorageModule(StorageModule storageModule) {
        super.setStorageModule(storageModule);
        showNewValueIfNeeded(toRepresentation(getValue()));
    }


    /**
     * 换成了我自己写的带vip功能的
     * */
    @Override
    public void onClick(View v) {
        /*userInputModule.showSingleChoiceInput(
                key, getTitle(), entries,
                entryValues,
                getItemPosition(getValue()),
                this);*/

        userInputModule.showSingleChoiceInputWithVip(
                key, getTitle(), entries,
                entryValues,
                isNeedVip,
                getItemPosition(getValue()),
                this
        );
    }

    @Override
    protected CharSequence toRepresentation(String value) {
        for (int i = 0; i < entryValues.length; i++) {
            if (entryValues[i].equals(value)) {
                return entries[i];
            }
        }
        return null;
    }

    protected int getItemPosition(String value) {
        for (int i = 0; i < entryValues.length; i++) {
            if (entryValues[i].equals(value)) {
                return i;
            }
        }
        return -1;
    }
}
