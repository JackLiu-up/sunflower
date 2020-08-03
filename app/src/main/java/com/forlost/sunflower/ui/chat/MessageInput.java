package com.forlost.sunflower.ui.chat;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.emoji.widget.EmojiEditText;

import com.forlost.sunflower.R;
import com.forlost.sunflower.room.Message;
import com.google.gson.JsonObject;

import static android.view.MotionEvent.ACTION_DOWN;

public class MessageInput extends RelativeLayout {
    protected EmojiEditText messageEditText;
    protected ImageButton choseVoiceButton;
    protected ImageButton choseKeyboardButton1;
    protected ImageButton choseEmoJiButton;
    protected ImageButton choseKeyboardButton2;
    protected ImageButton messageSendButton;
    protected ImageButton attachmentButton;
    protected Button recordButton;
    protected LinearLayout attachmentLayout;
    private OnSubmitListener onSubmitListener;
    private Message message = new Message();

    public MessageInput(Context context) {
        super(context);
        init(context);
    }

    public MessageInput(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MessageInput(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    public interface OnSubmitListener {
        boolean onSubmit(Message message);
    }

    private void init(Context context, AttributeSet attrs) {
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.messages_input, this);
//        messageEditText = findViewById(R.id.messagesEditText);
        messageSendButton = findViewById(R.id.sendButton);
        attachmentButton = findViewById(R.id.addButton);
        choseVoiceButton = findViewById(R.id.voiceButton);
        choseKeyboardButton1 = findViewById(R.id.keyboardButton1);
        choseKeyboardButton2 = findViewById(R.id.keyboardButton2);
        choseEmoJiButton = findViewById(R.id.emojiButton);
        recordButton = findViewById(R.id.recordButton);
        attachmentLayout = findViewById(R.id.attachmentLayout);

        choseVoiceButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                choseVoiceButton.setVisibility(GONE);
                choseKeyboardButton1.setVisibility(VISIBLE);
//                messageEditText.setVisibility(GONE);
                recordButton.setVisibility(VISIBLE);
                choseKeyboardButton2.setVisibility(GONE);
                choseEmoJiButton.setVisibility(VISIBLE);
                messageSendButton.setVisibility(GONE);
                attachmentButton.setVisibility(VISIBLE);
                attachmentLayout.setVisibility(GONE);
            }
        });
        choseKeyboardButton1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                choseKeyboardButton1.setVisibility(GONE);
                choseVoiceButton.setVisibility(VISIBLE);
                recordButton.setVisibility(GONE);
            }
        });
        choseEmoJiButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                choseKeyboardButton1.setVisibility(GONE);
                choseVoiceButton.setVisibility(VISIBLE);
                choseEmoJiButton.setVisibility(GONE);
                choseKeyboardButton2.setVisibility(VISIBLE);
                recordButton.setVisibility(GONE);
            }
        });
        choseKeyboardButton2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                choseKeyboardButton2.setVisibility(GONE);
                choseEmoJiButton.setVisibility(VISIBLE);
            }
        });

        messageSendButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onSubmitListener != null) {
                    onSubmitListener.onSubmit(message);
                }
            }
        });
        attachmentButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attachmentLayout.setVisibility(VISIBLE);
                choseVoiceButton.setVisibility(VISIBLE);
                choseKeyboardButton1.setVisibility(GONE);
                choseEmoJiButton.setVisibility(VISIBLE);
                choseKeyboardButton2.setVisibility(GONE);
                recordButton.setVisibility(GONE);
            }
        });
//        messageEditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (message == null) {
//                    message = new Message();
//                }
//                if (message.payload == null) {
//                    message.payload = new JsonObject();
//                }
//                message.payload.getAsJsonObject().addProperty("content", charSequence.toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                String s = editable.toString();
//                if (s.length() <= 0) {
//                    messageSendButton.setVisibility(GONE);
//                    attachmentButton.setVisibility(VISIBLE);
//                } else {
//                    messageSendButton.setVisibility(VISIBLE);
//                    attachmentButton.setVisibility(GONE);
//                }
//            }
//        });
//        messageEditText.setText("");
//        messageEditText.setOnFocusChangeListener(new OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean hasFocus) {
//
//            }
//        });
    }
}
