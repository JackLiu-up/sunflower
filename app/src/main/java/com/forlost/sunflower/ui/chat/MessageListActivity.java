package com.forlost.sunflower.ui.chat;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.forlost.sunflower.MyRoomDatabase;
import com.forlost.sunflower.conf.Config;
import com.forlost.sunflower.helper.SecuredPreferenceHelper;
import com.forlost.sunflower.helper.httphelper.DtdHttpCallback;
import com.forlost.sunflower.helper.httphelper.DtdHttpHelper;
import com.forlost.sunflower.room.Message;
import com.forlost.sunflower.ui.base.AdBaseActivity;
import com.forlost.sunflower.R;
import com.forlost.sunflower.ui.chatkit.my.messages.MyMessageInput;
import com.google.gson.JsonObject;
import java.util.Date;
import static com.forlost.sunflower.conf.Config.DIALOGID_KEY;
import static com.forlost.sunflower.conf.Config.DIALOGNAME_KEY;
import static com.forlost.sunflower.conf.Config.DIALOGTYPE_KEY;
import static com.forlost.sunflower.conf.Config.MESSAGE_TYPE_KEY;

public class MessageListActivity extends AdBaseActivity {
    private int dialogType;
    private long dialogId;
    public static final String TAG = "MessageListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Intent intent = getIntent();
        dialogType = intent.getIntExtra(DIALOGTYPE_KEY, 0);
        dialogId = intent.getLongExtra(DIALOGID_KEY, 0);
        if (dialogType == 0 || dialogId == 0) {
            Log.e("finish", dialogType + " " + dialogId);
            //提示错误
            finish();
        }
        String dialogName = intent.getStringExtra(DIALOGNAME_KEY);
        if (dialogName == null) {
            dialogName = getResources().getString(R.string.app_name);
        }
        setTitle(dialogName);

        MessageListViewModelFactory messageListViewModelFactory = new MessageListViewModelFactory(dialogType, dialogId);
        MessageListViewModel messageListViewModel = new ViewModelProvider(this, messageListViewModelFactory).get(MessageListViewModel.class);
//        MyMessageInput myMessageInput = findViewById(R.id.messageInput);
        MessageInput messageInput = findViewById(R.id.messageInput);
        MessageListView messageListView = findViewById(R.id.messagesList);
        final MessageListAdapter messageListAdapter = new MessageListAdapter(this);
        messageListView.setAdapter(messageListAdapter);

        messageListViewModel.messageListLiveData.observe(this, new Observer<PagedList<Message>>() {
            @Override
            public void onChanged(PagedList<Message> messages) {
                messageListAdapter.submitList(messages);
            }
        });

//        myMessageInput.setInputListener(new MyMessageInput.InputListener() {
//            @Override
//            public boolean onSubmit(CharSequence input) {
//                JsonObject jsonObject = new JsonObject();
//                jsonObject.addProperty("content", input.toString());
//                sendMessage(Config.MESSAGE_TYPE_TEXT, jsonObject);
//                Log.e(TAG, input.toString());
//                return true;
//            }
//        });
    }

    //带参打开MessageListActivity
    public static void open(Context context, int dialogType, long dialogId, String dialogName) {
        Intent intent = new Intent(context, MessageListActivity.class);
        intent.putExtra(DIALOGTYPE_KEY, dialogType);
        intent.putExtra(DIALOGID_KEY, dialogId);
        intent.putExtra(DIALOGNAME_KEY, dialogName);
        context.startActivity(intent);
    }

    //发送消息
    private void sendMessage(int messageType, JsonObject jsonObject) {
        final com.forlost.sunflower.room.Message message = new com.forlost.sunflower.room.Message();
        message.dialogType = dialogType;
        message.createAt = new Date();
        message.fromAccount = SecuredPreferenceHelper.getInstance().getSecurePreferences().getInt(Config.DTD_USER_UID_KEY, 0);
        message.dialogId = dialogId;
        message.messageType = messageType;
        message.payload = jsonObject;
        //插入数据库
        MyRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, message.payload.toString());
                MyRoomDatabase.getDatabase().messageDao().insert(message);
            }
        });
        if (1 == 1) {
            return;
        }
        //http发送
        jsonObject.addProperty(MESSAGE_TYPE_KEY, messageType);
        jsonObject.addProperty(DIALOGTYPE_KEY, dialogType);
        jsonObject.addProperty(DIALOGID_KEY, dialogId);
        DtdHttpHelper.getInstance().doPost("message/sendMessage", jsonObject, JsonObject.class, new DtdHttpCallback<JsonObject>() {
            @Override
            public void onSuccess(JsonObject result) {
                super.onSuccess(result);
                String messageId = result.get("messageId").getAsString();
                afterSendMessage(message, messageId);
            }

            @Override
            public void onFailure(String msg) {
                super.onFailure(msg);
            }
        });
    }

    private void afterSendMessage(com.forlost.sunflower.room.Message message, String serverMsgId) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.message_list_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_chat_settings:
                //跳转聊天设置
                break;

        }

        return super.onOptionsItemSelected(item);
    }
}