package com.forlost.sunflower.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import com.forlost.sunflower.MyRoomDatabase;
import com.forlost.sunflower.room.Dialog;
import com.forlost.sunflower.room.DialogDao;

public class DialogListViewModel extends ViewModel {
    PagedList.Config myPagingConfig;
    LiveData<PagedList<Dialog>> dialogLiveData;

    public DialogListViewModel() {
        DialogDao dialogDao = MyRoomDatabase.getDatabase().dialogDao();
        myPagingConfig = new PagedList.Config.Builder()
                .setPageSize(50)
                .setPrefetchDistance(150)
                .setEnablePlaceholders(false)
                .build();
        dialogLiveData=new LivePagedListBuilder<>(dialogDao.getDialogList(), myPagingConfig)
                .build();
    }
}
