package com.forlost.sunflower.room;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.forlost.sunflower.helper.ApplicationHelper;

@Entity
public class Dialog {
    @PrimaryKey
    public long dialogId;

    @ColumnInfo(name = "dialog_type")
    public int dialogType;

    @ColumnInfo(name = "dialog_name")
    public String dialogName;

    @ColumnInfo(name = "last_local_message_id")
    public long lastLocalMessageId;

    @ColumnInfo(name = "is_top")
    public Boolean isTop;

    @ColumnInfo(name = "unread_count")
    public int unreadCount;

    @ColumnInfo(name = "photo")
    public String photo;

    public Message getLastMessage() {
        if (lastLocalMessageId <= 0) {
            return null;
        }
        return ApplicationHelper.getInstance().getMyRoomDatabase().messageDao().findById(lastLocalMessageId);
    }

}
