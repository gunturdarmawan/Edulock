package com.example.edulockapp.model;

import android.graphics.drawable.Drawable;

public class AppItem {

    private Drawable icone;
    private String name;
    private String packageName;

    public AppItem(Drawable icone, String name, String packageName) {
        this.icone = icone;
        this.name = name;
        this.packageName = packageName;
    }

    public Drawable getIcone() {
        return icone;
    }

    public void setIcone(Drawable icone) {
        this.icone = icone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
