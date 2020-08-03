package com.forlost.sunflower.helper.httphelper;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.forlost.sunflower.conf.Config;
import com.forlost.sunflower.helper.SecuredPreferenceHelper;
import com.forlost.sunflower.model.httpModel.DtdBaseResponseModel;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;

import javax.net.ssl.X509TrustManager;

import static com.forlost.sunflower.conf.Config.DTD_API_HTTP_URL;

public class DtdHttpHelper {
    private OkHttpClient okHttpClient;
    private static DtdHttpHelper instance;
    private Gson gson = new Gson();
    private Handler mDelivery;              //用于在主线程执行的调度器
    private final static String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) SunflowerApp/1.0.20.0409";
    private X509TrustManager trustManager = new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
            //不校检客户端证书
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
            //不校检服务器证书
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
            //OKhttp3.0以前返回null,3.0以后返回new X509Certificate[]{};
        }
    };

    //私有的构造方法
    private DtdHttpHelper() {
        mDelivery = new Handler(Looper.getMainLooper());
        //日志拦截器
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addNetworkInterceptor(interceptor)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory(), trustManager)//配置
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier())//配置
                .build();
    }

    private void runOnUiThread(Runnable runnable) {
        getmDelivery().post(runnable);
    }

    private Handler getmDelivery() {
        return mDelivery;
    }

    //单例模式
    public static DtdHttpHelper getInstance() {

        if (instance == null) {
            synchronized (DtdHttpHelper.class) {
                if (instance == null) {
                    instance = new DtdHttpHelper();
                }
            }
        }
        return instance;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public <T> void doFailure(final DtdHttpCallback<T> dtdHttpCallback, final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dtdHttpCallback.onFailure(s);
            }
        });
    }
    public <T> void doSuccess(final DtdHttpCallback<T> dtdHttpCallback, final T res) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dtdHttpCallback.onSuccess(res);
            }
        });
    }

    //获取假用户名和密码
    public void getUserNameAndPassword(String qrCode, final DtdHttpCallback<JsonObject> dtdHttpCallback) {
        Headers headers = new Headers.Builder().add("User-Agent", USER_AGENT).build();
        Request request = new Request.Builder()
                .url(qrCode)
                .headers(headers)
                .get()
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                doFailure(dtdHttpCallback,e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                ResponseBody responseBody = response.body();
                if (responseBody == null) {
                    doFailure(dtdHttpCallback,"服务器错误");
                    return;
                }
                String resStr = responseBody.string();

                DtdBaseResponseModel dtdBaseResponseModel = gson.fromJson(resStr, DtdBaseResponseModel.class);

                if (dtdBaseResponseModel.getCode() != 1) {
//                    if (dtdBaseResponseModel.getCode() == 999) {
//
//                    }
                    doFailure(dtdHttpCallback,dtdBaseResponseModel.getMsg());
                    return;
                }

                doSuccess(dtdHttpCallback,gson.fromJson(dtdBaseResponseModel.getData(), JsonObject.class));

            }
        });
    }

    public void login(final String username, String password, final DtdHttpCallback<Void> dtdHttpCallback) {
        Headers headers = new Headers.Builder().add("User-Agent", USER_AGENT).build();
        //"grant_type": "password",
        //"client_id": 1001002,
        //"username": username,
        //"password": password,
        FormBody formBody = new FormBody.Builder()
                .add("grant_type", "password")
                .add("client_id", Config.DTD_API_CLIENT_ID)
                .add("username", username)
                .add("password", password)
                .build();
        Request request = new Request.Builder()
                .url(DTD_API_HTTP_URL + "oauth2/token")
                .headers(headers)
                .post(formBody)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                doFailure(dtdHttpCallback,e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                ResponseBody responseBody = response.body();
                if (responseBody == null) {
                    doFailure(dtdHttpCallback,"服务器错误");
                    return;
                }
                String resStr = responseBody.string();
                Log.e("loginresStr",resStr);
                //{"access_token":"","expires_in":2592000,"token_type":"Bearer","scope":"","refresh_token":""}
                JsonObject jsonObject = gson.fromJson(resStr, JsonObject.class);
                if (!jsonObject.has("access_token")) {
                    doFailure(dtdHttpCallback,"登录失败");
                    return;
                }
                String accessToken = jsonObject.get("access_token").getAsString();

                SecuredPreferenceHelper.getInstance().getSecurePreferences().edit().putString(Config.DTD_USER_TOKEN_KEY, accessToken).apply();
                doSuccess(dtdHttpCallback,null);

            }
        });
    }

    public <T> void doPost(String pathUrl, JsonObject jsonObject, final Class<T> clazz, final DtdHttpCallback<T> dtdHttpCallback) {
        FormBody.Builder builder = new FormBody.Builder();
        for (String k : jsonObject.keySet()) {
            builder.add(k, jsonObject.get(k).getAsString());
        }
        FormBody formBody = builder.build();
        Headers headers = new Headers.Builder()
                .add("User-Agent", USER_AGENT)
                .add("authorization", "Bearer " + SecuredPreferenceHelper.getInstance().getSecurePreferences().getString(Config.DTD_USER_TOKEN_KEY, ""))
                .build();
        Request request = new Request.Builder()
                .url(DTD_API_HTTP_URL + pathUrl)
                .headers(headers)
                .post(formBody)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                doFailure(dtdHttpCallback,e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                ResponseBody responseBody = response.body();
                if (responseBody == null) {
                    doFailure(dtdHttpCallback,"服务器错误");
                    return;
                }
                String resStr = responseBody.string();
                DtdBaseResponseModel dtdBaseResponseModel = gson.fromJson(resStr, DtdBaseResponseModel.class);
                if (dtdBaseResponseModel.getCode() != 1) {
//                    if (dtdBaseResponseModel.getCode() == 999) {
//
//                    }

                    doFailure(dtdHttpCallback,dtdBaseResponseModel.getMsg());
                    return;
                }
                doSuccess(dtdHttpCallback,gson.fromJson(dtdBaseResponseModel.getData(), clazz));
            }
        });
    }
}
