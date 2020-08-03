package com.forlost.sunflower.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import com.forlost.sunflower.MyRoomDatabase;
import com.forlost.sunflower.R;
import com.forlost.sunflower.conf.Config;
import com.forlost.sunflower.room.Dialog;
import com.forlost.sunflower.ui.chat.MessageListActivity;

public class DialogListFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.dialog_list_fragment, container, false);
        DialogListView dialogListView = root.findViewById(R.id.dialogsList);
        DialogListViewModel dialogListViewModel = new ViewModelProvider(this).get(DialogListViewModel.class);
        final DialogListAdapter dialogListAdapter = new DialogListAdapter(getActivity());
        dialogListAdapter.setOnDialogClickListener(new DialogListAdapter.OnDialogClickListener() {
            @Override
            public void onDialogClick(Dialog dialog) {
                MessageListActivity.open(getActivity(), dialog.dialogType, dialog.dialogId, dialog.dialogName);
            }
        });
        dialogListView.setAdapter(dialogListAdapter);
//        insertDialog();
        dialogListViewModel.dialogLiveData.observe(getViewLifecycleOwner(), new Observer<PagedList<Dialog>>() {
            @Override
            public void onChanged(PagedList<Dialog> dialogs) {
                dialogListAdapter.submitList(dialogs);
            }
        });
        return root;
    }

    private void insertDialog() {
        final Dialog dialog = new Dialog();
        dialog.dialogId = 6;
        dialog.dialogType = Config.DIALOG_TYPE_P2P;
        dialog.dialogName = "雷军";
        dialog.lastLocalMessageId = 0;
        dialog.isTop = false;
        dialog.unreadCount = 13;
        dialog.photo = null;
        MyRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                MyRoomDatabase.getDatabase().dialogDao().insert(dialog);
            }
        });
    }

}