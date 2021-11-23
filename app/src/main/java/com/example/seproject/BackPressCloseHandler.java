package com.example.seproject;

import android.app.Activity;
import android.widget.Toast;

public class BackPressCloseHandler {
    private long backkeyPressedTime = 0;
    private Toast toast;
    private Activity activity;

    public BackPressCloseHandler(Activity context) {
        this.activity = context;
    }

    public void onBackPressed() {
        if (System.currentTimeMillis() > backkeyPressedTime + 500) {
            backkeyPressedTime = System.currentTimeMillis();
            showGuide();
            return;
        }
        if (System.currentTimeMillis() <= backkeyPressedTime + 300) {
            activity.finish();
            toast.cancel();
        }
    }

    public void showGuide() {
        toast = Toast.makeText(activity, "뒤로가기 버튼을 한번 더 누르시면 로그아웃됩니다.", Toast.LENGTH_SHORT);
        toast.show();
    }
}
