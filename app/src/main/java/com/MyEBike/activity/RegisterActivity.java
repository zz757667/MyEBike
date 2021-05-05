package com.MyEBike.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.MyEBike.MainActivity;
import com.MyEBike.R;
import com.MyEBike.api.BaseResponseModel;
import com.MyEBike.base.BaseActivity;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RegisterActivity extends BaseActivity {

    EditText register_account_edit;
    EditText register_password_edit;
    EditText register_password_repeat_edit;
    EditText register_phone_edit;
    EditText register_idcard_edit;
    Button register_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        register_account_edit = findViewById(R.id.register_account_edit);
        register_password_edit = findViewById(R.id.register_password_edit);
        register_password_repeat_edit = findViewById(R.id.register_password_repeat);
        register_phone_edit = findViewById(R.id.register_phone_edit);
        register_idcard_edit = findViewById(R.id.register_idcard_edit);
        register_btn = findViewById(R.id.register_btn);
        register_btn.setOnClickListener(this::register);

    }

    public void register(View view) {
        if (register_account_edit.getText().toString().isEmpty()) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
        } else if (register_password_edit.getText().toString().isEmpty()) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
        }else if(register_password_repeat_edit.getText().toString().isEmpty()) {
            Toast.makeText(this, "请再次输入密码", Toast.LENGTH_SHORT).show();
        }else if(register_phone_edit.getText().toString().isEmpty()) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
        }else if(register_idcard_edit.getText().toString().isEmpty()) {
            Toast.makeText(this, "请输入身份证号码", Toast.LENGTH_SHORT).show();
        }else {
            apiWrapper.getService().register(register_account_edit.getText().toString(), register_password_edit.getText().toString(),register_phone_edit.getText().toString(),register_idcard_edit.getText().toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<BaseResponseModel>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            addDispose(d);
                        }

                        @Override
                        public void onSuccess(@NonNull BaseResponseModel baseResponseModel) {

                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }
                    });
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

}
