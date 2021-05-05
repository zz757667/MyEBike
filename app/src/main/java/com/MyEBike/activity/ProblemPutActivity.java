package com.MyEBike.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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

public class ProblemPutActivity extends BaseActivity {
    EditText problem_put_edit1;
    RadioGroup radioGroup;
    RadioButton radio_Btn1, radio_Btn2, radio_Btn3;
    Button button;
    TextView type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_put);
        initView();

    }

    private void initView() {
        problem_put_edit1 = findViewById(R.id.problem_put_edit1);
        radioGroup = findViewById(R.id.radioGroup);
        radio_Btn1 = findViewById(R.id.radiobutton1);
        radio_Btn2 = findViewById(R.id.radiobutton2);
        radio_Btn3 = findViewById(R.id.radiobutton3);
        button = findViewById(R.id.problem_put_button);
        button.setOnClickListener(this::problemPut);

    }

    public void problemPut(View view) {

        if (problem_put_edit1.getText().toString().isEmpty()) {
            Toast.makeText(this, "请输入您的反馈~", Toast.LENGTH_SHORT).show();
        } else {
            int len = radioGroup.getChildCount();

            for (int i = 0; i < len; i++) {
                RadioButton radio = (RadioButton) radioGroup.getChildAt(i);
                if (radio.isChecked()) {
                    type.setText(radio.getText());
                }
                apiWrapper.getService().problemput(problem_put_edit1.getText().toString(), type.getText().toString())
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

            }

        }


    }
}