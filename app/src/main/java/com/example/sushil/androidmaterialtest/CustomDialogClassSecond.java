package com.example.sushil.androidmaterialtest;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Su syl on 9/3/2015.
 */
public class CustomDialogClassSecond extends Dialog implements View.OnClickListener {
    public Button yes;
    public TextView textView;
    private Context c;

    public CustomDialogClassSecond(Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_screen_second);

        yes = (Button) findViewById(R.id.btn_yes);
        yes.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}
