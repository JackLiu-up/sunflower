package com.forlost.sunflower.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user WHERE uid = :uid LIMIT 1")
    User findByUid(int uid);

    @Insert
    void insert(User user);
}
