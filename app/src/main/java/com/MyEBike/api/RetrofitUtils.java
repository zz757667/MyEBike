package com.MyEBike.api;


import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public abstract class RetrofitUtils {

    private Retrofit mRetrofit = null;
    private OkHttpClient mOkHttpClient;

    /**
     * 获取Retrofit对象
     *
     * @return
     */
    public Retrofit getRetrofit() {
        if (null == mRetrofit) {
            if (null == mOkHttpClient) {
                OkHttp3Utils okHttp3Utils = new OkHttp3Utils();
                mOkHttpClient = okHttp3Utils.getOkHttpClient();
            }
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BaseUrlUtil.BaseServiceUrl)
                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(mOkHttpClient)
                    .build();
        }
        return mRetrofit;
    }


    public class NullOnEmptyConverterFactory extends Converter.Factory {
        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            final Converter<ResponseBody, ?> delegate = retrofit.nextResponseBodyConverter(this, type, annotations);
            return new Converter<ResponseBody, Object>() {
                @Override
                public Object convert(ResponseBody body) throws IOException {
                    if (body.contentLength() == 0) return null;
                    return delegate.convert(body);
                }
            };
        }
    }

}
