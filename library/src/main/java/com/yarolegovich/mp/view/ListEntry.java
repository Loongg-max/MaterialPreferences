package com.yarolegovich.mp.view;

/**
 * created by loongg-max on 19.08.18
 * <br>单选（多选）列表条目的实例
 * <br>包括名称、值、颜色、是否需要vip以及是否是新加的
 * */
public class ListEntry {

    /**
     * 条目名称
     * */
    private CharSequence entryName;
    /**
     * 条目值（用于保存优先级）
     * */
    private CharSequence entryValue;
    /**
     * 条目文字颜色
     * */
    private int entryColor;
    /**
     * 条目是否需要vip
     * */
    private boolean isNeedVip;
    /**
     * 条目是否是新加的
     * */
    private boolean isNew;


    public ListEntry() {
    }

    public ListEntry(CharSequence entryName, CharSequence entryValue, int entryColor, boolean isNeedVip, boolean isNew) {
        this.entryName = entryName;
        this.entryValue = entryValue;
        this.entryColor = entryColor;
        this.isNeedVip = isNeedVip;
        this.isNew = isNew;
    }

    public CharSequence getEntryName() {
        return entryName;
    }

    public void setEntryName(String entryName) {
        this.entryName = entryName;
    }

    public CharSequence getEntryValue() {
        return entryValue;
    }

    public void setEntryValue(String entryValue) {
        this.entryValue = entryValue;
    }

    public int getEntryColor() {
        return entryColor;
    }

    public void setEntryColor(int entryColor) {
        this.entryColor = entryColor;
    }

    public boolean isNeedVip() {
        return isNeedVip;
    }

    public void setNeedVip(boolean needVip) {
        isNeedVip = needVip;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    @Override
    public String toString() {
        return "ListEntry{" +
                "entryName=" + entryName +
                ", entryValue=" + entryValue +
                ", entryColor=" + entryColor +
                ", isNeedVip=" + isNeedVip +
                ", isNew=" + isNew +
                '}';
    }
}
