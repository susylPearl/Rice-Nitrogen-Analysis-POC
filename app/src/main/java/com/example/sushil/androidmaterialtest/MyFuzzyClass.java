package com.example.sushil.androidmaterialtest;

import android.content.Context;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.rule.Variable;

import java.io.InputStream;

/**
 * Created by Su syl on 8/14/2015.
 */
public class MyFuzzyClass {
    private Context context;
    private Integer color;

    public MyFuzzyClass(SecondaryActivity mainActivity) {
        context = mainActivity;
        //this.color = extractColor;
    }

    public double FuzzyEngine(Integer color) throws Exception {
        // Load from 'FCL' file
        InputStream inputStream = context.getAssets().open("tipper.fcl");
        FIS fis = FIS.load(inputStream, true);

        // Error while loading?
        if (fis == null) {
            System.err.println("Can't load file: '" + inputStream + "'");
            return 0;
        }

        // Show
//        JFuzzyChart.get().chart(fis);

        // Set inputs
        fis.setVariable("color", color);

        // Evaluate
        fis.evaluate();

        // Show output variable's chart
        Variable weight = fis.getVariable("weight");
        double wght = weight.getValue();
        //      JFuzzyChart.get().chart(weight, weight.getDefuzzifier(), true);

        // Print ruleSet

        System.out.println("Output weight is:" + fis.getVariable("weight").getValue());
        return wght;
    }
}
