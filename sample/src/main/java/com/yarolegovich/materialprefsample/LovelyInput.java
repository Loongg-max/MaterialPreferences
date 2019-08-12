package com.yarolegovich.materialprefsample;

import android.content.Context;
import android.support.annotation.ColorInt;

import com.yarolegovich.mp.io.UserInputModule;

import java.util.Collections;
import java.util.Map;

/**
 * Created by yarolegovich on 16.05.2016.
 * <br>Edit by loongg-max on 19.08.12
 */
public class LovelyInput implements UserInputModule.Factory {

    private Map<String, Integer> keyIconMappings;
    private Map<String, CharSequence> keyTitleMappings;
    private Map<String, CharSequence> keyMessageMappings;

    private int color;

    private boolean isVip;//add by me

    private LovelyInput() {
        keyIconMappings = Collections.emptyMap();
        keyTitleMappings = Collections.emptyMap();
        keyMessageMappings = Collections.emptyMap();
        isVip = false;//add by me
    }

    @Override
    public UserInputModule create(Context context) {
        return new LovelyInputModule(context)
                .setKeyMessageMapping(keyMessageMappings)
                .setKeyTitleMapping(keyTitleMappings)
                .setKeyIconMappings(keyIconMappings)
                .setTopColor(color)
                .setVip(isVip);//add by me
    }

    /**
     *
     * 在初始化输入模式的时候先从Builder中得到值，然后在create中传给LovelyInputModule
     *
     * */
    public static class Builder {

        private LovelyInput factory;

        public Builder() {
            factory = new LovelyInput();
        }

        public Builder setTopColor(@ColorInt int color) {
            factory.color = color;
            return this;
        }

        public Builder setKeyTitleMappings(Map<String, CharSequence> mappings) {
            factory.keyTitleMappings = mappings;
            return this;
        }

        public Builder setKeyMessageMappings(Map<String, CharSequence> mappings) {
            factory.keyMessageMappings = mappings;
            return this;
        }

        public Builder setKeyIconMappings(Map<String, Integer> mappings) {
            factory.keyIconMappings = mappings;
            return this;
        }

        /**
         * 设置当前是否是vip用户
         * */
        public Builder setVip(boolean isVip){
            factory.isVip = isVip;
            return this;
        }

        public LovelyInput build() {
            return factory;
        }


    }
}
