package com.forlost.sunflower.ui.chat;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


public class MessageListViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    int dialogType;
    long dialogId;

    public MessageListViewModelFactory(int dialogType, long dialogId) {
        this.dialogType = dialogType;
        this.dialogId = dialogId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MessageListViewModel(dialogType, dialogId);
    }
}
