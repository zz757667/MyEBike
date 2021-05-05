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

public class LoginActivity extends BaseActivity {

    EditText login_account_edit;
    EditText login_password_edit;
    Button login_btn;
    Button login_register_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

    }

    private void initView() {
        login_account_edit = findViewById(R.id.login_account_edit);
        login_password_edit = findViewById(R.id.login_password_edit);
        login_btn = findViewById(R.id.login_btn);
        login_register_btn = findViewById(R.id.login_register_btn);
        login_btn.setOnClickListener(this::login);
        login_register_btn.setOnClickListener(this::register);

    }




    public void login(View view) {

        if (login_account_edit.getText().toString().isEmpty()) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
        } else if (login_password_edit.getText().toString().isEmpty()) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
        } else {
            apiWrapper.getService().login(login_account_edit.getText().toString(), login_password_edit.getText().toString())
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
            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
        }

    }


    public void register(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}
