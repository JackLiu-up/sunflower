package com.forlost.sunflower.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.forlost.sunflower.R;
import com.forlost.sunflower.conf.Config;
import com.forlost.sunflower.room.Dialog;
import com.google.gson.Gson;

public class DialogListAdapter extends PagedListAdapter<Dialog, DialogListAdapter.DialogViewHolder> {
    public static final String TAG = "DialogListAdapter";
    static Gson gson = new Gson();
    Context context;
    private static OnDialogClickListener onDialogClickListener;

    protected DialogListAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    private static DiffUtil.ItemCallback<Dialog> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Dialog>() {
                @Override
                public boolean areItemsTheSame(@NonNull Dialog oldItem, @NonNull Dialog newItem) {
                    return oldItem.dialogId == newItem.dialogId;
                }

                @Override
                public boolean areContentsTheSame(@NonNull Dialog oldItem, @NonNull Dialog newItem) {
                    return gson.toJson(oldItem).equals(gson.toJson(newItem));
                }

            };

    @NonNull
    @Override
    public DialogListAdapter.DialogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dialog, parent, false);
        return new DialogListAdapter.DialogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DialogListAdapter.DialogViewHolder holder, int position) {
        Dialog dialog = getItem(position);
        if (dialog != null) {
            holder.bindTo(dialog);
        } else {
            return;
        }
    }

    @Override
    public int getItemViewType(int position) {
        Dialog dialog = getItem(position);
        if (dialog == null) {
            return Config.DIALOG_TYPE_P2P;
        }
        if (dialog.dialogType == Config.DIALOG_TYPE_P2P) {
            return Config.DIALOG_TYPE_P2P;
        } else if (dialog.dialogType == Config.DIALOG_TYPE_TRIBE) {
            return Config.DIALOG_TYPE_TRIBE;
        } else {
            return Config.DIALOG_TYPE_P2P;
        }
    }


    static class DialogViewHolder extends RecyclerView.ViewHolder {
        TextView dialogName;
        Dialog dialog;
        protected ViewGroup container;

        public DialogViewHolder(@NonNull View itemView) {
            super(itemView);
            dialogName = itemView.findViewById(R.id.dialogName);
            container = itemView.findViewById(R.id.dialogContainer);
        }

        private void bindTo(final Dialog dialog) {
            this.dialog = dialog;
            if (dialog == null) {
                return;
            }
            String name = dialog.dialogName;
            dialogName.setText(name);
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onDialogClickListener != null) {
                        onDialogClickListener.onDialogClick(dialog);
                    }
                }
            });
        }

    }

    public void setOnDialogClickListener(OnDialogClickListener onDialogClickListener) {
        DialogListAdapter.onDialogClickListener = onDialogClickListener;
    }

    public interface OnDialogClickListener {
        void onDialogClick(Dialog dialog);
    }

}
