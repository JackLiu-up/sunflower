package com.forlost.sunflower.room;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface DialogDao {
    @Query("SELECT * FROM dialog WHERE dialogId = :dialogId LIMIT 1")
    Dialog findById(int dialogId);

    @Insert
    void insert(Dialog dialog);

    @Update
    void update(Dialog dialog);

    @Query("UPDATE dialog SET last_local_message_id = :last_local_message_id WHERE dialogId=:dialogId")
    void update(String dialogId, String last_local_message_id);

    @Query("SELECT * from dialog  ORDER BY is_top DESC,last_local_message_id DESC")
    DataSource.Factory<Integer, Dialog> getDialogList();

}
