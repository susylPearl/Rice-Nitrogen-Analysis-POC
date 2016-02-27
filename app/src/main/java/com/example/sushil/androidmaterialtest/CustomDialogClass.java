package com.example.sushil.androidmaterialtest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Su syl on 8/14/2015.
 */
public class CustomDialogClass extends Dialog implements View.OnClickListener {

    public Dialog d;
    public Button yes;
    public TextView textView;
    private Context c;
    private float weight;

    public CustomDialogClass(Activity a, float weight) {
        super(a);
        this.c = a;
        this.weight = weight;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_screen);
        textView = (TextView) findViewById(R.id.wght);
        textView.setText(String.valueOf(weight) + "\t" + c.getString(R.string.katha));

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
