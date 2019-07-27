package com.android.baselibrary.widget.toast;

import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.baselibrary.R;
import com.android.baselibrary.base.BaseApplication;


/**
 * Created by gyq on 2016/7/25.
 */
public class ToastUtil {

    public final static int TOP_DRAWABLE = 1;
    public final static int LEFT_DRAWABLE = 2;
    public final static int RIGHT_DRAWABLE = 3;
    public final static int BOTTOM_DRAWABLE = 4;

    private final int SHOWTIME = 1500;

    private static Toast tast;

    public static void showToast(String txt){
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            ToastCompat.makeText(BaseApplication.getInstance().getContext(),txt,ToastCompat.LENGTH_SHORT).show();
        } else {
            tast= Toast.makeText(BaseApplication.getInstance().getContext(), txt, Toast.LENGTH_SHORT);
            tast.setGravity(Gravity.CENTER, 0, 0);
            View view= LayoutInflater.from(BaseApplication.getInstance().getContext()).inflate(R.layout.custom_toast, null);
            TextView tvmsg = (TextView)view.findViewById(R.id.tvMsg);
            tvmsg.setText(txt);
            tast.setView(view);
            tast.show();
        }
    }

    public static void showLongToast(String txt){
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            ToastCompat.makeText(BaseApplication.getInstance().getContext(),txt,ToastCompat.LENGTH_LONG).show();
        } else {
            tast= Toast.makeText(BaseApplication.getInstance().getContext(), txt, Toast.LENGTH_LONG);
            tast.setGravity(Gravity.CENTER, 0, 0);
            View view = LayoutInflater.from(BaseApplication.getInstance().getContext()).inflate(R.layout.custom_toast, null);
            TextView tvmsg = (TextView)view.findViewById(R.id.tvMsg);
            tvmsg.setText(txt);
            tast.setView(view);
            tast.show();
        }

    }



    public static void dismissToast(){
        if(tast != null){
            tast.cancel();
        }
    }
}
