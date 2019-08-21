package com.yarolegovich.materialprefsample;

import android.os.Bundle;

import com.yarolegovich.mp.MaterialChoicePreference;
import com.yarolegovich.mp.MaterialPreferenceScreen;
import com.yarolegovich.mp.view.ListEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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



        //------------------方式一，直接在xml中设置（必须设置）--------------------------



        //------------- 方式二 (设置方式二后，一失效)------------------------
        CharSequence[] entryName = {"第一个", "第二个", "第三个"};
        CharSequence[] entryValue = {"1", "2", "3"};
        boolean[] isNeedVip = {false, true, true};

        materialChoicePreference.setEntryNames(entryName);
        materialChoicePreference.setEntryValues(entryValue);
        materialChoicePreference.setIsNeedVip(isNeedVip);


        //--------------方式三 (设置方式三后，一二失效)-----------------------------------
        List<ListEntry> entries = new ArrayList<>();
        entries.add(new ListEntry("xxxxx", "xxx", 0, false, false));
        entries.add(new ListEntry("sssssss", "sss", 0, false, true));
        entries.add(new ListEntry("yyyyy", "yyy", 0, true, true));
        materialChoicePreference.setEntries(entries);
    }
}
