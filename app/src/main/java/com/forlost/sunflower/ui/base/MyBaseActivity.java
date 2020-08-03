package com.forlost.sunflower.ui.base;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 没有登录状态验证
 * Created by LQBO on 2017/6/26.
 * email 149296291@qq.com
 */
public class MyBaseActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks {

    public static final String TAG = "EasyPermissions";
    public static final int RQ_PERMISSION_CODE = 147;
    String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // Do something after user returned from app settings screen, like showing a Toast.
//            Toast.makeText(this, "设置权限回来", Toast.LENGTH_SHORT)
//                    .show();
            if (!EasyPermissions.hasPermissions(this, perms)) {
                Log.e(TAG, "onActivityResult finish");
                finish();
            }
        }
    }

    @AfterPermissionGranted(RQ_PERMISSION_CODE)
    protected void methodRequiresPermission() {
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission, do the thing

            Log.e(TAG, "hasPermissions");
            // ...
        } else {
            Log.e(TAG, "Do not have permissions, request them now");
            // Do not have permissions, request them now
            //这个方法是用户在拒绝权限之后，再次申请权限，才会弹出自定义的dialog，详情可以查看下源码 shouldShowRequestPermissionRationale()方法
            EasyPermissions.requestPermissions(this, "请授权此APP的系统权限，以继续使用；建议授权所有此APP所需权限。",
                    RQ_PERMISSION_CODE, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        //接受系统权限的处理，这里交给EasyPermissions来处理，回调到 EasyPermissions.PermissionCallbacks接口
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
// Some permissions have been granted

        Log.e(TAG, "onPermissionsGranted");
//        methodRequiresPermission();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Log.e(TAG, "onPermissionsDenied: 用户取消授权");
        Log.e(TAG, perms.toString());
//如果用户点击永远禁止，这个时候就需要跳到系统设置页面去手动打开了
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).setRationale("请授权此APP的系统权限，以继续使用；建议授权所有此APP所需权限。").build().show();
        }else {
            methodRequiresPermission();
        }
    }

    //弹出自定义dialog时，用户点击接受按钮
    @Override
    public void onRationaleAccepted(int requestCode) {
        Log.d(TAG, "onRationaleAccepted: 用户再次授权");
    }

    //弹出自定义dialog时，用户点击拒绝按钮
    @Override
    public void onRationaleDenied(int requestCode) {
        Log.d(TAG, "onRationaleDenied: 用户取消授权");
        finish();
    }
}
