package com.MyEBike.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.MyEBike.api.ApiWrapper;
import com.MyEBike.api.AppManager;

import java.lang.reflect.Field;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public class BaseActivity extends AppCompatActivity {

    protected ApiWrapper apiWrapper;
    protected CompositeDisposable mCompositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager(this).addActivity(this);
        //7.0以上TRANSLUCENT_STATUS时部分手机状态栏有灰色遮罩，去掉它
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            View decorView = getWindow().getDecorView();
            /* getWindow().getDecorView()被调用后, getWindow().getAttributes().flags才有值。
             * getDecorView()正常调用时机：
             * ①setContentView()中；
             * ②onCreate()和onPostCreate()之间；
             * ③④⑤等等...
             * 所以下面的代码应当放在setContentView()或onPostCreate()中，
             * 但有的activity没有setContentView()，有的activity会在onCreate()中finish()，
             * 所以此处在onCreate()中手动调用一下getDecorView()。
             */
            if ((getWindow().getAttributes().flags & WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS) != 0) {
                try {
                    Field field = decorView.getClass().getDeclaredField("mSemiTransparentStatusBarColor");
                    field.setAccessible(true);
                    field.setInt(decorView, Color.TRANSPARENT);
                } catch (Exception ignored) {
                }
            }
        }
        apiWrapper = new ApiWrapper();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager(this).finishActivity(this);
        unDispose();
    }

    public void addDispose(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);//将所有 Disposable 放入容器集中处理
    }

    /**
     * 停止集合中正在执行的 RxJava 任务
     */
    public void unDispose() {
        Log.d("BasePresenter", "unDispose");
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();//保证 Activity 结束时取消所有正在执行的订阅
        }
    }

    public void finishActivity(View view) {
        finish();
    }

}
