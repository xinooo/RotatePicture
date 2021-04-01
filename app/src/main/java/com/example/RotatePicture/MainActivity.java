package com.example.RotatePicture;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private ImageView img;
    private SeekBar seekBar;
    private int agree = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = (ImageView)findViewById(R.id.pic);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                img.setImageDrawable(rotateBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.pic2, null), progress));
//                img.animate().rotation(progress);
                agree = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(final SeekBar seekBar) {
                seekBar.setThumb(getResources().getDrawable(R.drawable.seekbar_thumb_failed));
                seekBar.setThumbOffset(dp2px(30)*agree/360);
                seekBar.setSplitTrack(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        seekBar.setThumb(getResources().getDrawable(R.drawable.seekbar_thumb));
                        seekBar.setSplitTrack(false);
                        seekBar.setProgress(0);
                        seekBar.setThumbOffset(0);
                    }
                },1000);

            }
        });
    }

    private int dp2px(float dp) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        return (int) (dp * (metrics.densityDpi / 160f) + 0.5F);
    }


    public Drawable rotateBitmap(Bitmap bitmap, int degrees) {
        if (degrees == 0 || null == bitmap) {
            return new BitmapDrawable(this.getResources(), bitmap);
        }
        Matrix matrix = new Matrix();
        matrix.setRotate(degrees, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
        Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (null != bitmap) {
            bitmap.recycle();
        }
        return new BitmapDrawable(this.getResources(), bmp);
    }
}