package com.example.loghouse.logs;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.cookpad.android.loghouse.Log;
import com.cookpad.android.loghouse.plugins.OutLogcat;
import com.google.gson.annotations.SerializedName;

public class PvLog extends Log {
    @SerializedName("screen_name")
    private String sceenName;

    @Override
    public String type() {
        return OutLogcat.TYPE;
    }

    public PvLog(Activity activity) {
        this(activity.getClass().getSimpleName());
    }

    public PvLog(Fragment fragment) {
        this(fragment.getClass().getSimpleName());
    }

    public PvLog(String sceenName) {
        this.sceenName = sceenName;
    }
}
