package com.example.sushil.androidmaterialtest;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class SecondaryActivity extends ActionBarActivity implements View.OnClickListener {

    private static final int PIC_OPEN = 0;
    public SecondaryActivity context;
    Button btn, btn1;
    private Toolbar toolbar;

    public SecondaryActivity() {
        context = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);
        toolbar = (Toolbar) findViewById(R.id.app_secondarybar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //retrieve a reference to the ImageView
        ImageView picView = (ImageView) findViewById(R.id.imageView2);

        //display the returned cropped image
        Bundle extras = getIntent().getExtras();
        byte[] byteArray = extras.getByteArray("pic");

        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        picView.setImageBitmap(applySmoothEffect(bmp, 5));

        btn = (Button) findViewById(R.id.ex_btn);
        btn1 = (Button) findViewById(R.id.proc_btn);
        btn.setOnClickListener(this);
        btn1.setOnClickListener(this);
    }


    public Bitmap applySmoothEffect(Bitmap bmp, double value) {
        ConvolutionMatrix convMatrix = new ConvolutionMatrix(3);
        convMatrix.setAll();
        //convMatrix.Matrix[1][1] = value;
        // set weight of factor and offset
        convMatrix.Factor = value + 8;
        convMatrix.Offset = 1;
        return ConvolutionMatrix.computeConvolution3x3(bmp, convMatrix);

    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ex_btn) {
            Bundle extras = getIntent().getExtras();
            byte[] byteArray = extras.getByteArray("pic");

            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

            try {


                ImageColour imageColour = new ImageColour(context, bmp);
                String color1 = imageColour.returnColour();
                Integer color = Integer.parseInt(color1, 16);
                Toast.makeText(this, "The color integer value is:" + color, Toast.LENGTH_SHORT).show();
                String obtainedColor = "#" + (color1.toString());

                View vibrantView = findViewById(R.id.obtainedcolor);
                getGradientDrawable(vibrantView).setColor(Color.parseColor(obtainedColor));
                Toast t = new Toast(this);
                //vibrantView.setBackgroundColor(0);
                //t.makeText(this, getString(R.string.photo_color) +obtainedColor, Toast.LENGTH_LONG).show();
                t.makeText(this, getString(R.string.photo_color) + "#" + color1, Toast.LENGTH_LONG).show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (v.getId() == R.id.proc_btn) {

            Bundle extras = getIntent().getExtras();
            byte[] byteArray = extras.getByteArray("pic");

            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);


            ImageColour imageColour = null;
            try {
                imageColour = new ImageColour(context, bmp);
                String color1 = imageColour.returnColour();
                Integer color = Integer.parseInt(color1, 16);

                MyFuzzyClass fuzzyClassObj = new MyFuzzyClass(this);
                double weight = fuzzyClassObj.FuzzyEngine(color);
                //DialogClass.showAlertDialog(context,(int)weight,"Your required urea in Kg:",true);
                CustomDialogClass customDialogClass = new CustomDialogClass(context, (float) weight);
                customDialogClass.show();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private GradientDrawable getGradientDrawable(View colorShape) {
        return (GradientDrawable) colorShape.getBackground();
    }

    //onActivityResult
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            if (requestCode == PIC_OPEN) {
                try {
                    InputStream stream = getContentResolver().openInputStream(
                            data.getData());
                    Bitmap bitmap = BitmapFactory.decodeStream(stream);
                    ImageView image;
                    image = (ImageView) findViewById(R.id.imageView2);
                    image.setImageBitmap(bitmap);
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_second, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        } else if (item.getItemId() == R.id.pic) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(intent, PIC_OPEN);
            return true;

        }
        return super.onOptionsItemSelected(item);
    }
}







