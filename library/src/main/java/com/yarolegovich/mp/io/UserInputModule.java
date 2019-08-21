package com.yarolegovich.mp.io;

import android.content.Context;

import com.yarolegovich.mp.view.ListEntry;

import java.util.List;
import java.util.Set;

/**
 * Created by yarolegovich on 05.05.2016.
 * <br>Edit by loongg-max on 19.08.12
 */
public interface UserInputModule {

    void showEditTextInput(
            String key,
            CharSequence title,
            CharSequence defaultValue,
            Listener<String> listener);

    void showSingleChoiceInput(
            String key,
            CharSequence title,
            CharSequence[] displayItems,
            CharSequence[] values,
            int selected,
            Listener<String> listener);
    /**
     * Add by Loongg-max 19.08.18
     * <br>带vip、new以及自定义颜色 功能的单选对话框
     * */
    void showSingleChoiceInputWithVip(
            String key,
            CharSequence title,
            CharSequence[] entryNames,
            CharSequence[] entryValues,
            int[] entryColors,
            boolean[] isNeedVip,
            boolean[] isNew,
            int selected,
            Listener<String> listener);

    /**
     * Add by Loongg-max 19.08.18
     * <br>带vip、new以及自定义颜色 功能的单选对话框
     * */
    void showSingleChoiceInputWithVip(
            String key,
            CharSequence title,
            List<ListEntry> entries,
            int selected,
            Listener<String> listener);


    void showMultiChoiceInput(
            String key,
            CharSequence title,
            CharSequence[] displayItems,
            CharSequence[] values,
            boolean[] defaultSelection,
            Listener<Set<String>> listener);

    void showColorSelectionInput(
            String key,
            CharSequence title,
            int defaultColor,
            Listener<Integer> color);

    interface Factory {
        UserInputModule create(Context context);
    }

    interface Listener<T> {
        void onInput(T value);
    }
}
