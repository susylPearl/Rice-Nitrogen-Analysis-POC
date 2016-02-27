package com.example.sushil.androidmaterialtest;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    //keep track of camera capture intent
    final int CAMERA_CAPTURE = 1;
    //keep track of cropping intent
    final int PIC_CROP = 2;
    Button cropBtn;
    private Toolbar toolbar;
    //captured picture uri
    private Uri picUri;
    private int PIC_OPEN = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        cropBtn = (Button) findViewById(R.id.cropbtn);
        cropBtn.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }

        //go to another activity
        if (id == R.id.camera) {
            try {
                //use standard intent to capture an image
                Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                //we will handle the returned data in onActivityResult
                startActivityForResult(captureIntent, CAMERA_CAPTURE);
            } catch (ActivityNotFoundException anfe) {
                //display an error message
                String errorMessage = "Whoops - your device doesn't support capturing images!";
                Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
                toast.show();
            }
        } else if (id == R.id.pic) {

            //used for importing image from memory
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(intent, PIC_OPEN);
            return true;
        }

        return super.onOptionsItemSelected(item);

    }


    @Override
    public void onClick(View v) {
        //Cropping Button
        if (v.getId() == R.id.cropbtn) {

            Uri uri = picUri;
            if (uri == null) {
                Toast.makeText(this, getString(R.string.crop_error), Toast.LENGTH_SHORT).show();
            } else {

                //function called for image cropping
                performCrop();
            }

        }
    }


    //onActivityResult
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            picUri = data.getData();
            //user is returning from capturing an image using the camera
            if (requestCode == CAMERA_CAPTURE) {
                //get the Uri for the captured image
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                ImageView image;
                image = (ImageView) findViewById(R.id.picture);
                image.setImageBitmap(photo);

                //createImageFromBitmap(photo);

            }


            //user is returning from cropping the image
            else if (requestCode == PIC_CROP) {
                //get the returned data

                Bundle extras = data.getExtras();

                //get the cropped bitmap
                Bitmap thePic = extras.getParcelable("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                thePic.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                //Start new activity and passing cropped bitmap
                Intent intent = new Intent(this, SecondaryActivity.class);
                intent.putExtra("pic", byteArray);
                startActivity(intent);


            } else if (requestCode == PIC_OPEN) {
                try {

                    //getting image from SD card and setting it to imageView
                    InputStream stream = getContentResolver().openInputStream(
                            data.getData());
                    Bitmap bitmap = BitmapFactory.decodeStream(stream);
                    ImageView image;
                    image = (ImageView) findViewById(R.id.picture);
                    image.setImageBitmap(bitmap);
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }


    //Crop Operation
    private void performCrop() {
        try {

            /*
            CropImageIntentBuilder cropImage = new CropImageIntentBuilder(200, 200, picUri);
            cropImage.setOutlineColor(0xFF03A9F4);
            cropImage.setSourceImage(picUri);
            */


            //Android implicit intent for image cropping
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*");
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 128);
            cropIntent.putExtra("outputY", 128);
            // retrieve data on return
            cropIntent.putExtra("return-data", true);

            //start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, PIC_CROP);
        } catch (ActivityNotFoundException anfe) {
            //display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    //Saving Image on SD card
    public void createImageFromBitmap(Bitmap bmp) {

        FileOutputStream fileOutputStream = null;
        try {

            // create a File object for the parent directory
            File wallpaperDirectory = new File("/sdcard/capture/");
            // have the object build the directory structure, if needed.
            wallpaperDirectory.mkdirs();

            //Capture is folder name and file name with date and time
            fileOutputStream = new FileOutputStream(String.format(
                    "/sdcard/Capture/%d.jpg",
                    System.currentTimeMillis()));

            // Here we Resize the Image ...
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream); // bmp is the bitmap object
            byte[] bsResized = byteArrayOutputStream.toByteArray();


            fileOutputStream.write(bsResized);
            fileOutputStream.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }

    }

}



