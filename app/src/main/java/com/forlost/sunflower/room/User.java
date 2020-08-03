package com.forlost.sunflower.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @NonNull
    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "user_name")
    public String userName;

    @ColumnInfo(name = "avatar")
    public String avatar;

    @ColumnInfo(name = "is_friend")
    public Boolean isFriend;
}
