package com.example.sushil.androidmaterialtest;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;

/**
 * Created by Su syl on 7/14/2015.
 */
public class CountDown extends CountDownTimer {

    private Activity Activity_act;
    private Class _cls;

    public CountDown(long millisInFuture, long countDownInterval, Activity act, Class cls) {
        super(millisInFuture, countDownInterval);
        Activity_act = act;
        _cls = cls;
    }

    @Override
    public void onFinish() {
        Activity_act.startActivity(new Intent(Activity_act, _cls));
        Activity_act.finish();
    }

    @Override
    public void onTick(long millisUntilFinished) {

    }


}
