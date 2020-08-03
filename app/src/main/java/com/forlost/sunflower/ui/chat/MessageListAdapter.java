package com.forlost.sunflower.ui.chat;

import android.content.Context;
import android.util.Log;
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
import com.forlost.sunflower.helper.SecuredPreferenceHelper;
import com.forlost.sunflower.room.Message;
import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;
import static com.forlost.sunflower.conf.Config.MESSAGE_TYPE_IMAGE;

public class MessageListAdapter extends PagedListAdapter<Message, MessageListAdapter.MessageViewHolder> {
    public static final String TAG = "MessageListAdapter";
    static Gson gson = new Gson();
    Context context;
    int uid = 0;

    protected MessageListAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
        uid = SecuredPreferenceHelper.getInstance().getSecurePreferences().getInt(Config.DTD_USER_UID_KEY, 0);
        Log.e(TAG, String.valueOf(uid));

    }

    private static DiffUtil.ItemCallback<Message> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Message>() {
                // Concert details may have changed if reloaded from the database,
                // but ID is fixed.
                @Override
                public boolean areItemsTheSame(Message oldMessage, Message newMessage) {
                    return oldMessage.id == newMessage.id;
                }

                @Override
                public boolean areContentsTheSame(@NotNull Message oldMessage,
                                                  @NotNull Message newMessage) {
                    return gson.toJson(oldMessage).equals(gson.toJson(oldMessage));
                }
            };


    @NonNull
    @Override
    public MessageListAdapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return getHolder(parent, viewType);
    }

    MessageViewHolder getHolder(ViewGroup parent, int viewType) {

        int layout = R.layout.item_outcoming_text_message;
        if (viewType == Config.MESSAGE_TYPE_TEXT) {//我发的文字消息
            layout = R.layout.item_outcoming_text_message;
        } else if (viewType == -Config.MESSAGE_TYPE_TEXT) {//我收到的文字消息;
            layout = R.layout.item_incoming_text_message;
        } else if (viewType == MESSAGE_TYPE_IMAGE) {//我发的图片消息
            layout = R.layout.item_outcoming_image_message;
        } else if (viewType == -MESSAGE_TYPE_IMAGE) {//我收到的图片消息
            layout = R.layout.item_incoming_image_message;
        } else {
            layout = R.layout.item_outcoming_text_message;
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        Message message = getItem(position);
        Log.e(TAG, message.toString());
        if (message == null) {
            return Config.MESSAGE_TYPE_TEXT;
        }
        if (message.fromAccount == uid) {
            return message.messageType;
        } else {
            return -message.messageType;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageListAdapter.MessageViewHolder holder, int position) {
        Message message = getItem(position);
        if (message == null) {
            return;
        }
        holder.bindTo(message);
    }


    static class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView messageText;
        Message message;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.messageText);
        }

        private void bindTo(Message message) {
            this.message = message;
            if (message == null) {
                return;
            }
            if (message.payload == null) {
                return;
            }
            if (message.payload.getAsJsonObject() == null) {
                return;
            }
            if (message.payload.getAsJsonObject().get("content") == null) {
                return;
            }
            String s = message.payload.getAsJsonObject().get("content").getAsString();
            messageText.setText(s);
        }
    }
}
