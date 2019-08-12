package com.yarolegovich.materialprefsample;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.pavelsikun.vintagechroma.ChromaDialog;
import com.pavelsikun.vintagechroma.IndicatorMode;
import com.pavelsikun.vintagechroma.OnColorSelectedListener;
import com.pavelsikun.vintagechroma.colormode.ColorMode;
import com.yarolegovich.lovelydialog.AbsLovelyDialog;
import com.yarolegovich.lovelydialog.LovelyChoiceDialog;
import com.yarolegovich.lovelydialog.LovelyTextInputDialog;
import com.yarolegovich.mp.io.StandardUserInputModule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by yarolegovich on 16.05.2016.
 *
 * edit by loongg-max on 19.08.12
 */
public class LovelyInputModule extends StandardUserInputModule {

    private Map<String, Integer> keyIconMappings;
    private Map<String, CharSequence> keyTitleMapping;
    private Map<String, CharSequence> keyMessageMapping;

    private int topColor;


    /**
     * add by Loongg-max on 19.08.09
     * <br>当前用户是否是vip会员
     */
    private boolean isVip;


    public LovelyInputModule(Context context) {
        super(context);
    }


    /**
     *
     * */
    @Override
    public void showEditTextInput(
            String key,
            CharSequence title,
            CharSequence defaultValue,
            final Listener<String> listener) {
        if(!isVip){
            standardInit(new LovelyTextInputDialog(context)
                    .setNegativeButton(android.R.string.no, null)
                    .setHint("在这里输入你想展示的内容")
                    .setInitialInput(defaultValue.toString())
                    .setConfirmButton(android.R.string.ok, new LovelyTextInputDialog.OnTextInputConfirmListener() {
                        @Override
                        public void onTextInputConfirmed(String text) {
                            listener.onInput(text);
                        }
                    }), key, title)
                    .show();
        }else {
            //你还不是会员
            Toast.makeText(context, "该内容为会员专享，请先成为会员", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void showSingleChoiceInput(
            String key,
            CharSequence title,
            CharSequence[] displayItems,
            final CharSequence[] values,
            int selected,
            final Listener<String> listener) {
        standardInit(new LovelyChoiceDialog(context)
                .setItems(displayItems, new LovelyChoiceDialog.OnItemSelectedListener<CharSequence>() {
                    @Override
                    public void onItemSelected(int position, CharSequence item) {
                        listener.onInput(values[position].toString());
                    }
                }), key, title)
                .show();
    }


    /**
     * add by Loongg-max 19.08.10
     * <p>说明：
     * <br>如果某一选项需要vip权限，则在isNeedVip数组中将值设为true<p/>
     *
     * @param key preference的key值
     * @param title 标题
     * @param displayItems 选项名称数组
     * @param values 选项的值数组
     * @param isNeedVip 每一项是否需要vip的boolean数组，不设置的话默认都是false
     * @param selected 默认选中项的position
     * @param listener 监听器
     *
     * */
    @Override
    public void showSingleChoiceInputWithVip(
            String key,
            CharSequence title,
            CharSequence[] displayItems,
            final CharSequence[] values,
            boolean[] isNeedVip,
            int selected,
            final Listener<String> listener) {

        ArrayList<Entry> entries = new ArrayList<>();
        for(int i = 0; i < displayItems.length; i++){
            if(null == isNeedVip || isNeedVip.length == 0){
                entries.add(new Entry(displayItems[i], values[i], false));
            }else {
                entries.add(new Entry(displayItems[i], values[i], isNeedVip[i]));
            }
        }

        standardInit(new LovelyChoiceDialog(context)
                .setItems(new MyAdapter(context, selected, entries), new LovelyChoiceDialog.OnItemSelectedListener<Entry>() {
                    @Override
                    public void onItemSelected(int position, Entry item) {
                        Log.e("xxxxxxxxxxxx", "position==" + position + "    Entry==" + item.toString());

                        if(!isVip && item.isNeedVip()){
                            //显示需要vip权限
                            Toast.makeText(context, "该内容为会员专享，请先成为会员", Toast.LENGTH_SHORT).show();
                        }else {
                            listener.onInput(values[position].toString());
                        }

                    }
                }), key, title)
                .show();
    }



    @Override
    public void showMultiChoiceInput(
            String key,
            CharSequence title,
            CharSequence[] displayItems,
            final CharSequence[] values,
            boolean[] itemStates,
            final Listener<Set<String>> listener) {
        standardInit(new LovelyChoiceDialog(context)
                .setItemsMultiChoice(displayItems, itemStates, new LovelyChoiceDialog.OnItemsSelectedListener<CharSequence>() {
                    @Override
                    public void onItemsSelected(List<Integer> positions, List<CharSequence> items) {
                        Set<String> selected = new HashSet<>();
                        for (int position : positions) {
                            selected.add(values[position].toString());
                        }
                        listener.onInput(selected);
                    }
                }), key, title)
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
            throw new AssertionError(context.getString(com.yarolegovich.mp.R.string.exc_not_frag_activity_subclass));
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


    private AbsLovelyDialog standardInit(AbsLovelyDialog dialog, String key, CharSequence prefTitle) {
        CharSequence title = getTitle(key, prefTitle);
        CharSequence message = keyMessageMapping.get(key);
        if (!TextUtils.isEmpty(title)) {
            dialog.setTitle(title);
        }
        if (!TextUtils.isEmpty(message)) {
            dialog.setMessage(message);
        }
        return dialog.setTopColor(topColor).setIcon(keyIconMappings.get(key));
    }

    private CharSequence getTitle(String key, CharSequence prefTitle) {
        return keyTitleMapping.keySet().contains(key) ?
                keyTitleMapping.get(key) :
                prefTitle;
    }

    public LovelyInputModule setKeyIconMappings(Map<String, Integer> keyIconMappings) {
        this.keyIconMappings = keyIconMappings;
        return this;
    }

    public LovelyInputModule setKeyTitleMapping(Map<String, CharSequence> keyTitleMapping) {
        this.keyTitleMapping = keyTitleMapping;
        return this;
    }

    public LovelyInputModule setKeyMessageMapping(Map<String, CharSequence> keyMessageMapping) {
        this.keyMessageMapping = keyMessageMapping;
        return this;
    }

    public LovelyInputModule setTopColor(int topColor) {
        this.topColor = topColor;
        return this;
    }

    /**
     * add by Loongg-max on 2019.08.09<br>
     * 设置当前用户是否是vip
     */
    public LovelyInputModule setVip(boolean isVip){
        this.isVip = isVip;
        return this;
    }


    /**
     * @author loongg-max
     * add by Loongg-max on 9.8.2019.
     *
     * singleChoice 的自定义 adapter<br>
     *
     * 实现如果某项需要vip则在某项后面显示vip图标
     *
     */
    public class MyAdapter extends ArrayAdapter<Entry>{

        private List<Entry> entries;
        private Context context;
        private int selected;

        /**
         * @param context context
         * @param selected 默认选中项（本来是传item layout 的sourceId）
         * @param objects 选项的对象数组 entries
         *
         * */
        public MyAdapter(Context context, int selected, List<Entry> objects) {
            super(context, R.layout.dialog_singlechoice_item, objects);
            this.context = context;
            this.entries = objects;
            this.selected = selected;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ViewHolder viewHolder;
            if(null == convertView){
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.dialog_singlechoice_item, parent, false);
                viewHolder.radioButton = convertView.findViewById(R.id.radioButton);
                viewHolder.textView_name = convertView.findViewById(R.id.textView_name);
                viewHolder.imageView_vip = convertView.findViewById(R.id.imageView_vip);
                convertView.setTag(viewHolder);

            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.textView_name.setText(entries.get(position).getEntryName());
            if(position == selected){
                viewHolder.radioButton.setChecked(true);
            }else {
                viewHolder.radioButton.setChecked(false);
            }
            if(entries.get(position).isNeedVip()){
                viewHolder.imageView_vip.setImageResource(R.drawable.ic_vip);
            }else {
                viewHolder.imageView_vip.setVisibility(View.GONE);
            }
            Log.e("getView", "position==" + position + "  isNeedVip==  " + entries.get(position).isNeedVip());
            return convertView;
        }

        class ViewHolder{
            RadioButton radioButton;
            TextView textView_name;
            ImageView imageView_vip;
        }

    }


}
