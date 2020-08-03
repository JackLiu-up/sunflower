package com.forlost.sunflower.ui;

import android.content.Intent;
import android.os.Bundle;

import com.forlost.sunflower.MainActivity;
import com.forlost.sunflower.conf.Config;
import com.forlost.sunflower.helper.SecuredPreferenceHelper;
import com.forlost.sunflower.helper.httphelper.DtdHttpCallback;
import com.forlost.sunflower.helper.httphelper.DtdHttpHelper;
import com.forlost.sunflower.ui.base.MyBaseActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.forlost.sunflower.R;
import com.google.gson.JsonObject;
import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.forlost.sunflower.conf.Config.DTD_API_HTTP_URL;

public class LoginActivity extends MyBaseActivity {
    public static final int RQ_QRCODE_FOR_LOGIN = 101;
    public static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        methodRequiresPermission();
        Button button = findViewById(R.id.loginbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onQrButtonClick(view);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RQ_QRCODE_FOR_LOGIN && resultCode == RESULT_OK) {
            //请求二维码成功
            if (data == null) {
                return;
            }
            HmsScan hmsScan = data.getParcelableExtra(ScanUtil.RESULT);
            if (hmsScan != null) {
                String url = hmsScan.getOriginalValue();//获取条形码信息，只有当条形码编码格式为UTF-8时才可以使用，对非UTF-8格式的条形码使用getOriginValueByte()代替。
                //报非法二维码
                if (!url.startsWith("https://larkplus.cn/e?e=")) {
                    Toast.makeText(getApplicationContext(), "非法二维码",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                //get用户名密码
                DtdHttpHelper.getInstance().getUserNameAndPassword(url, new DtdHttpCallback<JsonObject>() {
                    //code等于1
                    @Override
                    public void onSuccess(JsonObject result) {
                        super.onSuccess(result);
                        //根据用户名密码登录
                        loginHttp(result);
                    }

                    //code不等于1
                    @Override
                    public void onFailure(String msg) {
                        super.onFailure(msg);
//                        Toast.makeText(getApplicationContext(), msg,
//                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    private void loginHttp(JsonObject jsonObject) {
        Log.e(TAG, jsonObject.toString());
        DtdHttpHelper.getInstance().login(jsonObject.get("username").getAsString(), jsonObject.get("password").getAsString(), new DtdHttpCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                super.onSuccess(result);
                Toast.makeText(LoginActivity.this, "登录成功",
                        Toast.LENGTH_SHORT).show();
                // 调到main
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(String msg) {
                super.onFailure(msg);
                Toast.makeText(LoginActivity.this, msg,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    //扫码
    public void onQrButtonClick(View view) {
        HmsScanAnalyzerOptions hmsScanAnalyzerOptions = new HmsScanAnalyzerOptions.Creator()
                .setHmsScanTypes(HmsScan.QRCODE_SCAN_TYPE)//二维码
                .setPhotoMode(false)//设置Bitmap扫码模式为相机模式还是图片模式 false：设置Bitmap扫码模式为相机模式，默认为false true：设置Bitmap扫码模式为图片模式
                .create();
        ScanUtil.startScan(this, RQ_QRCODE_FOR_LOGIN, hmsScanAnalyzerOptions);
    }
}