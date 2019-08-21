package com.yarolegovich.mp;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.yarolegovich.mp.view.ListEntry;

import java.util.List;

import static com.yarolegovich.mp.R.styleable.*;

/**
 * Created by yarolegovich on 06.05.2016.
 *
 * <br>edit by loongg-max on 19.08.12
 */
abstract class AbsMaterialListPreference<T> extends AbsMaterialTextValuePreference<T> {

    protected CharSequence[] entryNames;
    protected CharSequence[] entryValues;


    /**
     * add by loongg-max
     * <br>列表中哪些项是新加的，则该项值设为true
     */
    protected int[] entryColors;

    /**
     * add by loongg-max
     * <br>列表中哪些项需要vip，则该项值设为true
     */
    protected boolean[] isNeedVip;

    /**
     * add by loongg-max
     * <br>列表中哪些项是新加的，则该项值设为true
     */
    protected boolean[] isNew;


    /**
     * add by loongg-max
     * <br>列表项的实例
     */
    protected List<ListEntry> entries;



    public AbsMaterialListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AbsMaterialListPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AbsMaterialListPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onCollectAttributes(AttributeSet attrs) {
        super.onCollectAttributes(attrs);

        TypedArray ta = getContext().obtainStyledAttributes(attrs, AbsMaterialListPreference);
        try {
            if (ta.hasValue(AbsMaterialListPreference_mp_entry_descriptions)) {
                entryNames = ta.getTextArray(AbsMaterialListPreference_mp_entry_descriptions);
            }

            if (ta.hasValue(AbsMaterialListPreference_mp_entry_values)) {
                entryValues = ta.getTextArray(AbsMaterialListPreference_mp_entry_values);
            }
        } finally {
            ta.recycle();
        }

        if (entryNames == null || entryValues == null) {
            if (entryNames != null) {
                entryValues = entryNames;
            } else if (entryValues != null) {
                entryNames = entryValues;
            } else {
                throw new AssertionError(getContext().getString(R.string.exc_no_entries_to_list_provided));
            }
        }
    }


    //----------------------------------set---------------------------------------------

    /**
     * Add by loongg-max on 19.08.12<br>
     * <br>代码设置单选列表项名称
     * <br>默认entryName和entryValues在xml中至少需要设置一项，不然会抛出异常
     * */
    public void setEntryNames(CharSequence[] entryNames) {
        this.entryNames = entryNames;
        //设置name和value后，一定要更新显示当前选中项
        showNewValueIfNeeded(toRepresentation(getValue()));
    }

    /**
     * Add by loongg-max on 19.08.12<br>
     * <br>代码设置单选列表项的值
     * <br>默认entryName和entryValues在xml中至少需要设置一项，不然会抛出异常
     * */
    public void setEntryValues(CharSequence[] entryValues) {
        this.entryValues = entryValues;
        //设置name和value后，一定要更新显示当前选中项
        showNewValueIfNeeded(toRepresentation(getValue()));
    }

    /**
     * Add by loongg-max on 19.08.18<br>
     * <br>代码设置列表中项的颜色
     * <br>默认entries和entryValues在xml中至少需要设置一项，不然会抛出异常
     * */
    public void setEntryColors(int[] entryColors) {
        this.entryColors = entryColors;
    }

    /**
     * Add by loongg-max on 19.08.12<br>
     * <br>代码设置列表中哪些项需要vip，如果不设置，默认全为false
     * <br>默认entryName和entryValues在xml中至少需要设置一项，不然会抛出异常
     * */
    public void setIsNeedVip(boolean[] isNeedVip){
        this.isNeedVip = isNeedVip;
    }

    /**
     * Add by loongg-max on 19.08.18<br>
     * <br>代码设置列表中哪些项是新加的，如果不设置，默认全为false
     * <br>默认entryName和entryValues在xml中至少需要设置一项，不然会抛出异常
     * */
    public void setIsNew(boolean[] isNew) {
        this.isNew = isNew;
    }

    /**
     * Add by loongg-max on 19.08.18<br>
     * <br>代码设置列表中条目的实例
     * <br>默认entries和entryValues在xml中至少需要设置一项，不然会抛出异常
     * */
    public void setEntries(List<ListEntry> entries) {
        this.entries = entries;
        int size = entries.size();
        //设置了Entry实例的时候，这两个也要初始化（因为原来的一些逻辑都是根据这两个来的）
        if(size > 0){
            entryNames = new CharSequence[size];
            entryValues = new CharSequence[size];
            for(int i = 0; i < size; i++){
                entryNames[i] = entries.get(i).getEntryName();
                entryValues[i] = entries.get(i).getEntryValue();
            }
        }
        //设置选项实例后后，一定要更新显示当前选中项
        showNewValueIfNeeded(toRepresentation(getValue()));
    }

    //-------------------------------get-------------------------------------------

    /**
     * Add by loongg-max on 19.08.12<br>
     * <br>得到单选列表项名称
     * */
    public CharSequence[] getEntryNames() {
        return entryNames;
    }

    /**
     * Add by loongg-max on 19.08.12<br>
     * <br>得到单选列表项的值
     * */
    public CharSequence[] getEntryValues() {
        return entryValues;
    }

    /**
     * Add by loongg-max on 19.08.18<br>
     * <br>得到列表中项的颜色
     * */
    public int[] getEntryColors() {
        return entryColors;
    }

    /**
     * Add by loongg-max on 19.08.18<br>
     * <br>得到列表中哪些项需要vip
     * */
    public boolean[] getIsNeedVip() {
        return isNeedVip;
    }

    /**
     * Add by loongg-max on 19.08.18<br>
     * <br>得到列表中哪些项是新加的
     * */
    public boolean[] getIsNew() {
        return isNew;
    }

    /**
     * Add by loongg-max on 19.08.18<br>
     * <br>得到列表中条目的实例
     * */
    public List<ListEntry> getEntries() {
        return entries;
    }

}
