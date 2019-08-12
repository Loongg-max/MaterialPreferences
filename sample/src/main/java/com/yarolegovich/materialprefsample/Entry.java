package com.yarolegovich.materialprefsample;

import android.support.annotation.NonNull;


/**
 * @author loongg-max
 *
 * 单选列表的选项entry<br>
 * */
public class Entry {

    private CharSequence entryName;
    private CharSequence entryValue;
    private boolean isNeedVip;

    public Entry(CharSequence entryName, CharSequence entryValue, boolean isNeedVip) {
        this.entryName = entryName;
        this.entryValue = entryValue;
        this.isNeedVip = isNeedVip;
    }

    public CharSequence getEntryName() {
        return entryName;
    }

    public void setEntryName(CharSequence entryName) {
        this.entryName = entryName;
    }

    public CharSequence getEntryValue() {
        return entryValue;
    }

    public void setEntryValue(CharSequence entryValue) {
        this.entryValue = entryValue;
    }

    public boolean isNeedVip() {
        return isNeedVip;
    }

    public void setNeedVip(boolean needVip) {
        isNeedVip = needVip;
    }

    @NonNull
    @Override
    public String toString() {
        return "Entry{" +
                "entryName=" + entryName +
                ", entryValue=" + entryValue +
                ", isNeedVip=" + isNeedVip +
                '}';
    }
}
