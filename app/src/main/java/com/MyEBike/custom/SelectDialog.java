package com.MyEBike.custom;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import com.MyEBike.R;

/**
 * 未打开gps 提示dialog 点确定跳出开启权限
 * Created by Created by wwj on 2021/4/9.
 */
public class SelectDialog extends Dialog implements View.OnClickListener {
    private TextView confirm;
    private Context context;

    public SelectDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_confirm);

        confirm = findViewById(R.id.confirm);
        confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.confirm:

                Intent intent = new Intent(
                        Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
                cancel();
                break;

            default:
                break;
        }
    }

}
