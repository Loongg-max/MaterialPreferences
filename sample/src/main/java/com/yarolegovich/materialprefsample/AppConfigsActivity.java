package com.yarolegovich.materialprefsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yarolegovich.mp.MaterialChoicePreference;
import com.yarolegovich.mp.MaterialPreferenceScreen;

import java.util.Arrays;

/**
 * Created by yarolegovich on 15.05.2016.
 * edit by loongg-max on 19.08.12
 */
public class AppConfigsActivity extends ToolbarActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        setupToolbar();

        MaterialPreferenceScreen screen = findViewById(R.id.preference_screen);
        screen.setVisibilityController(R.id.pref_auto_loc, Arrays.asList(R.id.pref_location), false);

        //add by loongg-max
        //用代码设置参数
        MaterialChoicePreference materialChoicePreference = findViewById(R.id.choice);

        CharSequence[] entries = {"第一个", "第二个", "第三个"};
        CharSequence[] entryValue = {"1", "2", "3"};
        boolean[] isNeedVip = {false, true, true};

        materialChoicePreference.setEntries(entries);
        materialChoicePreference.setEntryValues(entryValue);
        materialChoicePreference.setIsNeedVip(isNeedVip);
    }
}
