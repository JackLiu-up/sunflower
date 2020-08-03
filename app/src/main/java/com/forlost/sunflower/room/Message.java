package com.forlost.sunflower.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.forlost.sunflower.helper.ApplicationHelper;
import com.google.gson.JsonElement;

import java.util.Date;

import static com.forlost.sunflower.conf.Config.MESSAGE_TYPE_AUDIO;
import static com.forlost.sunflower.conf.Config.MESSAGE_TYPE_TEXT;
import static com.forlost.sunflower.conf.Config.MESSAGE_TYPE_VIDEO;

@Entity
public class Message {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "server_msg_id")
    public String serverMsgId;

    @ColumnInfo(name = "dialog_type")
    public int dialogType;

    @ColumnInfo(name = "create_at")
    public Date createAt;

    @ColumnInfo(name = "from_account")
    public int fromAccount;


    @ColumnInfo(name = "dialog_id")
    public long dialogId;

    @ColumnInfo(name = "message_type")
    public int messageType;

    @ColumnInfo(name = "payload")
    public JsonElement payload;

    @ColumnInfo(name = "status")
    public int status;

    public String getDialogViewText() {
        switch (messageType) {
            case MESSAGE_TYPE_TEXT:
                JsonElement jsonElement = payload.getAsJsonObject().get("content");
                if (jsonElement == null) {
                    return "";
                } else {
                    return jsonElement.getAsString();
                }
            case MESSAGE_TYPE_AUDIO:
                return "发送了一条[语音]";
            case MESSAGE_TYPE_VIDEO:
                return "发送了一条[视频]";
            default:
                return "发送了一条未知消息";
        }
    }

    public User getSendUser() {
        if (fromAccount <= 0) {
            return null;
        }
        return ApplicationHelper.getInstance().getMyRoomDatabase().userDao().findByUid(fromAccount);
    }
}
