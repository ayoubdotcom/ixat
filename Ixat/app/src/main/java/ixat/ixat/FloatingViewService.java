package ixat.ixat;


import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import java.io.DataOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FloatingViewService extends Service {

    private WindowManager mWindowManager;
    private ImageView mImgFloatingView;
    private boolean mIsFloatingViewAttached = false;
    private boolean mIsFloatingViewShow = false;
    private String packageName;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!mIsFloatingViewAttached){
            mWindowManager.addView(mImgFloatingView, mImgFloatingView.getLayoutParams());
        }
        if(intent != null && startId == 1)
        {
            Bundle bundle = intent.getExtras();
            this.setPackageName(bundle.getString("packageName"));
            Log.e("Package name", getPackageName());
        }
        else
            Log.e("Package name", "No name");



        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.getDefaultDisplay();

        mImgFloatingView = new ImageView(this);
        mImgFloatingView.setImageResource(R.drawable.back);


        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.LEFT;

        params.height = 200;
        params.width = 200;

        mWindowManager.addView(mImgFloatingView, params);

        mIsFloatingViewAttached = true;

        mImgFloatingView.setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return false;
                    case MotionEvent.ACTION_UP:
                        return false;
                    case MotionEvent.ACTION_MOVE:
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);
                        mWindowManager.updateViewLayout(mImgFloatingView, params);
                        return true;
                }

                return false;
            }


        });

        mImgFloatingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mIsFloatingViewShow){
                    stopService(new Intent(getApplicationContext(), FloatingViewService.class));
                    mIsFloatingViewShow = false;
                }
                else{
                    startService(new Intent(getApplicationContext(), FloatingViewService.class));
                    mIsFloatingViewShow = true;
                }
            }
        });
    }

    public void removeView() {
        if (mImgFloatingView != null){
            mWindowManager.removeView(mImgFloatingView);
            mIsFloatingViewAttached = false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        removeView();
        Intent startIxat = new Intent(getApplicationContext(), MainActivity.class);
        startIxat.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startIxat);
    }

    @Override
    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}