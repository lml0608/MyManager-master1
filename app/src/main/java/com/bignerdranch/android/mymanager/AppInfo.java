package com.bignerdranch.android.mymanager;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by lfs-ios on 18/5/17.
 */

public class AppInfo implements Serializable {

    private String name;
    private String apk;
    private String version;
    private String source;
    private String data;
    //不进行序列化，否则会报错
    private transient Drawable icon;
    private Boolean system;

    public AppInfo(String name, String apk,  String version,
                   String source, String data, Drawable icon, Boolean system) {

        this.name = name;
        this.apk = apk;
        this.version = version;
        this.source = source;
        this.data = data;
        this.icon = icon;
        this.system = system;

    }

    public AppInfo(String string) {
        String[] split = string.split("##");
        if (split.length == 6) {
            this.name = split[0];
            this.apk = split[1];
            this.version = split[2];
            this.source = split[3];
            this.data = split[4];
            this.system = Boolean.getBoolean(split[5]);
        }
    }

    public String getName() {
        return name;
    }

    public String getAPK() {
        return apk;
    }

    public String getVersion() {
        return version;
    }

    public String getSource() {
        return source;
    }

    public String getData() {
        return data;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public Boolean isSystem() {
        return system;
    }

    @Override
    public String toString() {
        return getName() + "##" + getAPK() + "##" + getVersion() + "##" + getSource() + "##" + getData() + "##" + isSystem();
    }
}
