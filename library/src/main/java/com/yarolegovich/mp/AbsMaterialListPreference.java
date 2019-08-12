package com.yarolegovich.mp;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import static com.yarolegovich.mp.R.styleable.*;

/**
 * Created by yarolegovich on 06.05.2016.
 *
 * <br>edit by loongg-max on 19.08.12
 */
abstract class AbsMaterialListPreference<T> extends AbsMaterialTextValuePreference<T> {

    protected CharSequence[] entries;
    protected CharSequence[] entryValues;

    /**
     * add by loongg-max
     * <br>列表中哪些项需要vip，则该项值设为true
     */
    protected boolean[] isNeedVip;

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
                entries = ta.getTextArray(AbsMaterialListPreference_mp_entry_descriptions);
            }

            if (ta.hasValue(AbsMaterialListPreference_mp_entry_values)) {
                entryValues = ta.getTextArray(AbsMaterialListPreference_mp_entry_values);
            }
        } finally {
            ta.recycle();
        }

        if (entries == null || entryValues == null) {
            if (entries != null) {
                entryValues = entries;
            } else if (entryValues != null) {
                entries = entryValues;
            } else {
                throw new AssertionError(getContext().getString(R.string.exc_no_entries_to_list_provided));
            }
        }
    }

    /**
     * Add by loongg-max on 19.08.12<br>
     * <br>代码设置列表中哪些项需要vip，如果不设置，默认全为false
     * <br>默认entries和entryValues在xml中至少需要设置一项，不然会抛出异常
     * */
    public void setIsNeedVip(boolean[] isNeedVip){
        this.isNeedVip = isNeedVip;
    }

    /**
     * Add by loongg-max on 19.08.12<br>
     * <br>代码设置单选列表项名称
     * <br>默认entries和entryValues在xml中至少需要设置一项，不然会抛出异常
     * */
    public void setEntries(CharSequence[] entries) {
        this.entries = entries;
    }

    /**
     * Add by loongg-max on 19.08.12<br>
     * <br>代码设置单选列表项的值
     * <br>默认entries和entryValues在xml中至少需要设置一项，不然会抛出异常
     * */
    public void setEntryValues(CharSequence[] entryValues) {
        this.entryValues = entryValues;
    }
}
