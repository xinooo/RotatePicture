package com.example.RotatePicture;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private ImageView img, img2;
    private SeekBar seekBar;
    private int originalAngle = -150;   //初始角度
    private int newAngle;               //目前角度
    private int mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = (ImageView)findViewById(R.id.pic);
        img2 = (ImageView)findViewById(R.id.pic2);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        reset();
    }

    private void reset(){
        img2.setVisibility(View.GONE);
        img.setImageBitmap(rotateBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.pic2, null), originalAngle));
        seekBar.setThumb(getResources().getDrawable(R.drawable.seekbar_thumb_start));
        seekBar.setSplitTrack(false);
        seekBar.setProgress(0);
        seekBar.setThumbOffset(0);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                mProgress = progress;
                newAngle = progress+originalAngle;
                newAngle = newAngle <0? 360+ newAngle : newAngle;  //角度轉正
                img.setImageBitmap(rotateBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.pic2, null), newAngle));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(final SeekBar seekBar) {
                if (newAngle > -15 && newAngle < 15){
                    img2.setImageResource(getResources().getIdentifier("a_src_success", "mipmap", getPackageName()));
                    img2.setVisibility(View.VISIBLE);
                    seekBar.setThumb(getResources().getDrawable(R.drawable.seekbar_thumb_success));
                    seekBar.setThumbOffset(dp2px(30)*mProgress/360);
                    seekBar.setSplitTrack(false);
                    seekBar.setEnabled(false);
                }else {
                    img2.setImageResource(getResources().getIdentifier("a_src_error", "mipmap", getPackageName()));
                    img2.setVisibility(View.VISIBLE);
                    seekBar.setThumb(getResources().getDrawable(R.drawable.seekbar_thumb_failed));
                    seekBar.setThumbOffset(dp2px(30)*mProgress/360);
                    seekBar.setSplitTrack(false);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            reset();
                        }
                    },1000);
                }
            }
        });
    }

    private int dp2px(float dp) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        return (int) (dp * (metrics.densityDpi / 160f) + 0.5F);
    }


    public Bitmap rotateBitmap(Bitmap bitmap, int degrees) {
        if (degrees == 0 || null == bitmap) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        matrix.setRotate(degrees, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
        Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (null != bitmap) {
            bitmap.recycle();
        }
        return bmp;
    }
}