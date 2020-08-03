package com.forlost.sunflower.helper;

import android.content.Context;
import android.util.Log;

import com.securepreferences.SecurePreferences;

import java.security.GeneralSecurityException;

public class SecuredPreferenceHelper {
    private static SecuredPreferenceHelper sInstance;
    private SecurePreferences securePreferences;

    private SecuredPreferenceHelper() {
    }

    public static SecuredPreferenceHelper getInstance() {
        if (sInstance == null) {
            synchronized (SecuredPreferenceHelper.class) {
                if (sInstance == null) {
                    sInstance = new SecuredPreferenceHelper();
                }
            }
        }
        return sInstance;
    }
    public SecurePreferences getSecurePreferences(){
        return securePreferences;
    }

    public void init(Context context) {
        securePreferences = new SecurePreferences(context, "\"sdsdkj(*(*(*DAKJKSDKA\"", "sunflower.xml");
    }

    public Boolean handlePasswordChange(String password) {
        Context mContext = ApplicationHelper.getInstance().getMyApplication();
        try {
            securePreferences.handlePasswordChange(password, mContext);
            return true;
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            return false;
        }
    }
}
