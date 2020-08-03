package com.forlost.sunflower.ui.chat;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import com.forlost.sunflower.MyRoomDatabase;
import com.forlost.sunflower.room.Message;
import com.forlost.sunflower.room.MessageDao;

public class MessageListViewModel extends ViewModel {
    PagedList.Config myPagingConfig;
    LiveData<PagedList<Message>> messageListLiveData;

    public MessageListViewModel(int dialogType,long dialogId) {
        MessageDao messageDao = MyRoomDatabase.getDatabase().messageDao();
        myPagingConfig = new PagedList.Config.Builder()
                .setPageSize(50)
                .setPrefetchDistance(150)
                .setEnablePlaceholders(false)
                .build();
        messageListLiveData=new LivePagedListBuilder<>(messageDao.getOneDialogMessages(dialogType,dialogId), myPagingConfig)
                .build();
    }

}
