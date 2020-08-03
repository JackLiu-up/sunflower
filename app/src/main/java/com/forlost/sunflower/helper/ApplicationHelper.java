package com.forlost.sunflower.helper;

import android.util.Log;
import androidx.core.provider.FontRequest;
import androidx.emoji.text.EmojiCompat;
import androidx.emoji.text.FontRequestEmojiCompatConfig;
import com.forlost.sunflower.MyApplication;
import com.forlost.sunflower.MyRoomDatabase;
import com.forlost.sunflower.conf.Config;

public class ApplicationHelper {
    private static ApplicationHelper sInstance;
    private MyApplication myApplication;
    private MyRoomDatabase myRoomDatabase;

    private ApplicationHelper() {
    }

    public static ApplicationHelper getInstance() {
        if (sInstance == null) {
            synchronized (ApplicationHelper.class) {
                if (sInstance == null) {
                    sInstance = new ApplicationHelper();
                }
            }
        }
        return sInstance;
    }

    public MyApplication getMyApplication() {
        return myApplication;
    }

    //初始化的方法都要写到这里
    public synchronized void init(MyApplication myApplication) {
        this.myApplication = myApplication;
        SecuredPreferenceHelper.getInstance().init(myApplication);
//        FontRequest fontRequest = new FontRequest(
//                "com.forlost.sunflower",
//                "com.forlost",
//                "emoji compat Font Query",
//                CERTIFICATES);
//        EmojiCompat.Config config = new FontRequestEmojiCompatConfig(myApplication.getApplicationContext(), fontRequest);
//        EmojiCompat.init(config);

//        initRoomDatabase();
    }

//    private void initRoomDatabase() {
//        myRoomDatabase = Room.databaseBuilder(myApplication,
//                MyRoomDatabase.class, "sunflower").build();
//    }

    public MyRoomDatabase getMyRoomDatabase() {
        return myRoomDatabase;
    }

    //是否已经登录
    public Boolean isLogined() {
        String token = SecuredPreferenceHelper.getInstance().getSecurePreferences().getString(Config.DTD_USER_TOKEN_KEY, "");
        Log.e("isLogined", token);
        return token != null && token.length() >= 8;
    }
}
