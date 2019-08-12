package com.yarolegovich.mp.io;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.pavelsikun.vintagechroma.ChromaDialog;
import com.pavelsikun.vintagechroma.IndicatorMode;
import com.pavelsikun.vintagechroma.OnColorSelectedListener;
import com.pavelsikun.vintagechroma.colormode.ColorMode;
import com.yarolegovich.mp.R;
import com.yarolegovich.mp.util.Utils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by yarolegovich on 06.05.2016.
 * <br>Edit by loongg-max on 19.08.12
 */
public class StandardUserInputModule implements UserInputModule {

    protected Context context;

    public StandardUserInputModule(Context context) {
        this.context = context;
    }

    @Override
    public void showEditTextInput(
            String key,
            CharSequence title,
            CharSequence defaultValue,
            final Listener<String> listener) {
        final View view = LayoutInflater.from(context).inflate(R.layout.dialog_edittext, null);
        final EditText inputField = (EditText) view.findViewById(R.id.mp_text_input);

        if (defaultValue != null) {
            inputField.setText(defaultValue);
            inputField.setSelection(defaultValue.length());
        }

        final Dialog dialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setView(view)
                .show();
        view.findViewById(R.id.mp_btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onInput(inputField.getText().toString());
                dialog.dismiss();
            }
        });
    }

    @Override
    public void showSingleChoiceInput(
            String key,
            CharSequence title,
            CharSequence[] displayItems,
            final CharSequence[] values,
            int selected,
            final Listener<String> listener) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setSingleChoiceItems(displayItems, selected, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selected = values[which].toString();
                        listener.onInput(selected);
                        dialog.dismiss();
                    }
                })
                /*.setItems(displayItems, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selected = values[which].toString();
                        listener.onInput(selected);
                    }
                })*/
                .show();
    }

    /**
     * Add by Loongg-max 19.08.10<br>
     *
     * <br>在默认标准用户输入模式中，没有实现vip的单选处理
     * <br>这里是直接复制的默认代码<br>
     * todo 以后可以用自定义view实现vip的处理
     *
     * */
    public void showSingleChoiceInputWithVip(
            String key,
            CharSequence title,
            CharSequence[] displayItems,
            final CharSequence[] values,
            boolean[] isNeedVip,
            int selected,
            final Listener<String> listener) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setSingleChoiceItems(displayItems, selected, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selected = values[which].toString();
                        listener.onInput(selected);
                        dialog.dismiss();
                    }
                })

                .show();
    }

    @Override
    public void showMultiChoiceInput(
            String key,
            CharSequence title,
            CharSequence[] displayItems,
            final CharSequence[] values,
            final boolean[] itemStates,
            final Listener<Set<String>> listener) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMultiChoiceItems(displayItems, itemStates, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        itemStates[which] = isChecked;
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Set<String> result = new HashSet<>();
                        for (int i = 0; i < values.length; i++) {
                            if (itemStates[i]) {
                                result.add(values[i].toString());
                            }
                        }
                        listener.onInput(result);
                    }
                })
                .show();
    }

    @Override
    public void showColorSelectionInput(
            String key,
            CharSequence title,
            int defaultColor,
            final Listener<Integer> colorListener) {
        FragmentActivity activity;
        try {
            activity = (FragmentActivity) context;
        } catch (ClassCastException exc) {
            throw new AssertionError(context.getString(R.string.exc_not_frag_activity_subclass));
        }
        String tag = colorListener.getClass().getSimpleName();
        new ChromaDialog.Builder()
                .initialColor(defaultColor)
                .colorMode(ColorMode.ARGB)
                .indicatorMode(IndicatorMode.HEX)
                .onColorSelected(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int color) {
                        colorListener.onInput(color);
                    }
                })
                .create()
                .show(activity.getSupportFragmentManager(), tag);
    }
}
