package com.MyEBike.api;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.MyEBike.activity.LoginActivity;
import com.MyEBike.application.DemoApplication;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;


public class OkHttp3Utils implements Handler.Callback {
    Activity activity = AppManager.topActivity();
    private OkHttpClient mOkHttpClient;
    private final Handler updateHandler = new Handler();

    //设置缓存目录
    private final File cacheDirectory = new File(DemoApplication.getInstance().getApplicationContext().getCacheDir().getAbsolutePath(), "MyCache");
    private final Cache cache = new Cache(cacheDirectory, 10 * 1024 * 1024);

    /**
     * 获取OkHttpClient对象
     *
     * @return
     */
    public OkHttpClient getOkHttpClient() {

        if (null == mOkHttpClient) {
            // log拦截器  打印所有的log
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    if (message.length() > 3500) {
                        for (int i = 0; i < message.length(); i += 3500) {
                            //当前截取的长度<总长度则继续截取最大的长度来打印
                            if (i + 3500 < message.length()) {
                                Log.i("HttpLogging" + i, message.substring(i, i + 3500));
                            } else {
                                //当前截取的长度已经超过了总长度，则打印出剩下的全部信息
                                Log.i("HttpLogging" + i, message.substring(i));
                            }
                        }
                    } else {
                        //直接打印
                        Log.i("HttpLogging", message);
                    }
                }
            });
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            //同样okhttp3后也使用build设计模式
            mOkHttpClient = new OkHttpClient.Builder()

                    //添加拦截器
                    .addInterceptor(new MyIntercepter())
                    .addInterceptor(interceptor)
                    //设置一个自动管理cookies的管理器
                    .cookieJar(new CookiesManager())
                    //添加网络连接器
                    //.addNetworkInterceptor(new CookiesInterceptor(MyApplication.getInstance().getApplicationContext()))
                    //设置请求读写的超时时间
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
//                    .cache(cache)//设置缓存
//                    .retryOnConnectionFailure(true)//自动重试 默认true
                    .build();
        }
        return mOkHttpClient;
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        if (msg.what == 300) {
            Toast.makeText(activity, "暂无网络", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    /**
     * 判断网络是否可用
     *
     * @param context Context对象
     */
    public Boolean isNetworkReachable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = cm.getActiveNetworkInfo();
        if (current == null) {
            return false;
        }
        return (current.isAvailable());
    }

    /**
     * 拦截器
     */
    private class MyIntercepter implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            if (!isNetworkReachable(DemoApplication.instance.getApplicationContext())) {
                updateHandler.sendEmptyMessage(300);
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)//无网络时只从缓存中读取
                        .build();
            }

            Request.Builder req = request.newBuilder();

            Response response = chain.proceed(req.build());

            int code = response.code();

            if (code == 401) {
                //跳转到登录页面
                if (activity != null && !activity.isDestroyed()) {
                    AppManager.getAppManager().finishAllActivity();
                    activity.startActivity(new Intent(activity, LoginActivity.class));
                    if (response.body() != null) {
                        BaseResponseModel baseResponseModel = new Gson().fromJson(response.body().string(), BaseResponseModel.class);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (response.body() != null) {
                                    Toast.makeText(activity, baseResponseModel.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            } else if (code == 400) {
                if (response.body() != null) {
                    BaseResponseModel baseResponseModel = new Gson().fromJson(response.body().string(), BaseResponseModel.class);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (response.body() != null) {
                                Toast.makeText(activity, baseResponseModel.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
            updateHandler.sendEmptyMessage(code);
            return response;
        }
    }

    /**
     * 自动管理Cookies
     */
    private static class CookiesManager implements CookieJar {
        private final PersistentCookieStore cookieStore = new PersistentCookieStore(DemoApplication.getInstance().getApplicationContext());

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            if (cookies != null && cookies.size() > 0) {
                for (Cookie item : cookies) {
                    cookieStore.add(url, item);
                }
            }
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies = cookieStore.get(url);
            return cookies;
        }
    }
}
