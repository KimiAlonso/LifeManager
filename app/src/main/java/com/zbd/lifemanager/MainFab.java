package com.zbd.lifemanager;

import android.content.Context;
import android.view.View;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.gordonwong.materialsheetfab.AnimatedFab;


public class MainFab extends FloatingActionButton implements AnimatedFab{

    public MainFab(Context context) {
        super(context);
    }

    @Override
    public void show() {
        show(0, 0);
    }

    @Override
    public void show(float translationX, float translationY) {
        setVisibility(View.VISIBLE);
    }

    @Override
    public void hide() {
        setVisibility(View.INVISIBLE);
    }
}
