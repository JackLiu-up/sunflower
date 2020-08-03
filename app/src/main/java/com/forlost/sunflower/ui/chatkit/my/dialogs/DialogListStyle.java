/*******************************************************************************
 * Copyright 2016 stfalcon.com
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/

package com.forlost.sunflower.ui.chatkit.my.dialogs;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.forlost.sunflower.R;
import com.forlost.sunflower.ui.chatkit.commons.Style;


/**
 * Style for DialogList customization by xml attributes
 */
@SuppressWarnings("WeakerAccess")
class DialogListStyle extends Style {

    private int dialogTitleTextColor;
    private int dialogTitleTextSize;
    private int dialogTitleTextStyle;
    private int dialogUnreadTitleTextColor;
    private int dialogUnreadTitleTextStyle;

    private int dialogMessageTextColor;
    private int dialogMessageTextSize;
    private int dialogMessageTextStyle;
    private int dialogUnreadMessageTextColor;
    private int dialogUnreadMessageTextStyle;

    private int dialogDateColor;
    private int dialogDateSize;
    private int dialogDateStyle;
    private int dialogUnreadDateColor;
    private int dialogUnreadDateStyle;

    private boolean dialogUnreadBubbleEnabled;
    private int dialogUnreadBubbleTextColor;
    private int dialogUnreadBubbleTextSize;
    private int dialogUnreadBubbleTextStyle;
    private int dialogUnreadBubbleBackgroundColor;

    private int dialogAvatarWidth;
    private int dialogAvatarHeight;

    private boolean dialogMessageAvatarEnabled;
    private int dialogMessageAvatarWidth;
    private int dialogMessageAvatarHeight;

    private boolean dialogDividerEnabled;
    private int dialogDividerColor;
    private int dialogDividerLeftPadding;
    private int dialogDividerRightPadding;

    private int dialogItemBackground;
    private int dialogUnreadItemBackground;

    static DialogListStyle parse(Context context, AttributeSet attrs) {
        DialogListStyle style = new DialogListStyle(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyDialogsList);

        //Item background
        style.dialogItemBackground = typedArray.getColor(R.styleable.MyDialogsList_dialogItemBackground,
                style.getColor(R.color.transparent));
        style.dialogUnreadItemBackground = typedArray.getColor(R.styleable.MyDialogsList_dialogUnreadItemBackground,
                style.getColor(R.color.transparent));

        //Title text
        style.dialogTitleTextColor = typedArray.getColor(R.styleable.MyDialogsList_dialogTitleTextColor,
                style.getColor(R.color.dialog_title_text));
        style.dialogTitleTextSize = typedArray.getDimensionPixelSize(R.styleable.MyDialogsList_dialogTitleTextSize,
                context.getResources().getDimensionPixelSize(R.dimen.dialog_title_text_size));
        style.dialogTitleTextStyle = typedArray.getInt(R.styleable.MyDialogsList_dialogTitleTextStyle, Typeface.NORMAL);

        //Title unread text
        style.dialogUnreadTitleTextColor = typedArray.getColor(R.styleable.MyDialogsList_dialogUnreadTitleTextColor,
                style.getColor(R.color.dialog_title_text));
        style.dialogUnreadTitleTextStyle = typedArray.getInt(R.styleable.MyDialogsList_dialogUnreadTitleTextStyle, Typeface.NORMAL);

        //Message text
        style.dialogMessageTextColor = typedArray.getColor(R.styleable.MyDialogsList_dialogMessageTextColor,
                style.getColor(R.color.dialog_message_text));
        style.dialogMessageTextSize = typedArray.getDimensionPixelSize(R.styleable.MyDialogsList_dialogMessageTextSize,
                context.getResources().getDimensionPixelSize(R.dimen.dialog_message_text_size));
        style.dialogMessageTextStyle = typedArray.getInt(R.styleable.MyDialogsList_dialogMessageTextStyle, Typeface.NORMAL);

        //Message unread text
        style.dialogUnreadMessageTextColor = typedArray.getColor(R.styleable.MyDialogsList_dialogUnreadMessageTextColor,
                style.getColor(R.color.dialog_message_text));
        style.dialogUnreadMessageTextStyle = typedArray.getInt(R.styleable.MyDialogsList_dialogUnreadMessageTextStyle, Typeface.NORMAL);

        //Date text
        style.dialogDateColor = typedArray.getColor(R.styleable.MyDialogsList_dialogDateColor,
                style.getColor(R.color.dialog_date_text));
        style.dialogDateSize = typedArray.getDimensionPixelSize(R.styleable.MyDialogsList_dialogDateSize,
                context.getResources().getDimensionPixelSize(R.dimen.dialog_date_text_size));
        style.dialogDateStyle = typedArray.getInt(R.styleable.MyDialogsList_dialogDateStyle, Typeface.NORMAL);

        //Date unread text
        style.dialogUnreadDateColor = typedArray.getColor(R.styleable.MyDialogsList_dialogUnreadDateColor,
                style.getColor(R.color.dialog_date_text));
        style.dialogUnreadDateStyle = typedArray.getInt(R.styleable.MyDialogsList_dialogUnreadDateStyle, Typeface.NORMAL);

        //Unread bubble
        style.dialogUnreadBubbleEnabled = typedArray.getBoolean(R.styleable.MyDialogsList_dialogUnreadBubbleEnabled, true);
        style.dialogUnreadBubbleBackgroundColor = typedArray.getColor(R.styleable.MyDialogsList_dialogUnreadBubbleBackgroundColor,
                style.getColor(R.color.dialog_unread_bubble));

        //Unread bubble text
        style.dialogUnreadBubbleTextColor = typedArray.getColor(R.styleable.MyDialogsList_dialogUnreadBubbleTextColor,
                style.getColor(R.color.dialog_unread_text));
        style.dialogUnreadBubbleTextSize = typedArray.getDimensionPixelSize(R.styleable.MyDialogsList_dialogUnreadBubbleTextSize,
                context.getResources().getDimensionPixelSize(R.dimen.dialog_unread_bubble_text_size));
        style.dialogUnreadBubbleTextStyle = typedArray.getInt(R.styleable.MyDialogsList_dialogUnreadBubbleTextStyle, Typeface.NORMAL);

        //Avatar
        style.dialogAvatarWidth = typedArray.getDimensionPixelSize(R.styleable.MyDialogsList_dialogAvatarWidth,
                context.getResources().getDimensionPixelSize(R.dimen.dialog_avatar_width));
        style.dialogAvatarHeight = typedArray.getDimensionPixelSize(R.styleable.MyDialogsList_dialogAvatarHeight,
                context.getResources().getDimensionPixelSize(R.dimen.dialog_avatar_height));

        //Last message avatar
        style.dialogMessageAvatarEnabled = typedArray.getBoolean(R.styleable.MyDialogsList_dialogMessageAvatarEnabled, true);
        style.dialogMessageAvatarWidth = typedArray.getDimensionPixelSize(R.styleable.MyDialogsList_dialogMessageAvatarWidth,
                context.getResources().getDimensionPixelSize(R.dimen.dialog_last_message_avatar_width));
        style.dialogMessageAvatarHeight = typedArray.getDimensionPixelSize(R.styleable.MyDialogsList_dialogMessageAvatarHeight,
                context.getResources().getDimensionPixelSize(R.dimen.dialog_last_message_avatar_height));

        //Divider
        style.dialogDividerEnabled = typedArray.getBoolean(R.styleable.MyDialogsList_dialogDividerEnabled, true);
        style.dialogDividerColor = typedArray.getColor(R.styleable.MyDialogsList_dialogDividerColor, style.getColor(R.color.dialog_divider));
        style.dialogDividerLeftPadding = typedArray.getDimensionPixelSize(R.styleable.MyDialogsList_dialogDividerLeftPadding,
                context.getResources().getDimensionPixelSize(R.dimen.dialog_divider_margin_left));
        style.dialogDividerRightPadding = typedArray.getDimensionPixelSize(R.styleable.MyDialogsList_dialogDividerRightPadding,
                context.getResources().getDimensionPixelSize(R.dimen.dialog_divider_margin_right));

        typedArray.recycle();

        return style;
    }

    private DialogListStyle(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected int getDialogTitleTextColor() {
        return dialogTitleTextColor;
    }

    protected int getDialogTitleTextSize() {
        return dialogTitleTextSize;
    }

    protected int getDialogTitleTextStyle() {
        return dialogTitleTextStyle;
    }

    protected int getDialogUnreadTitleTextColor() {
        return dialogUnreadTitleTextColor;
    }

    protected int getDialogUnreadTitleTextStyle() {
        return dialogUnreadTitleTextStyle;
    }

    protected int getDialogMessageTextColor() {
        return dialogMessageTextColor;
    }

    protected int getDialogMessageTextSize() {
        return dialogMessageTextSize;
    }

    protected int getDialogMessageTextStyle() {
        return dialogMessageTextStyle;
    }

    protected int getDialogUnreadMessageTextColor() {
        return dialogUnreadMessageTextColor;
    }

    protected int getDialogUnreadMessageTextStyle() {
        return dialogUnreadMessageTextStyle;
    }

    protected int getDialogDateColor() {
        return dialogDateColor;
    }

    protected int getDialogDateSize() {
        return dialogDateSize;
    }

    protected int getDialogDateStyle() {
        return dialogDateStyle;
    }

    protected int getDialogUnreadDateColor() {
        return dialogUnreadDateColor;
    }

    protected int getDialogUnreadDateStyle() {
        return dialogUnreadDateStyle;
    }

    protected boolean isDialogUnreadBubbleEnabled() {
        return dialogUnreadBubbleEnabled;
    }

    protected int getDialogUnreadBubbleTextColor() {
        return dialogUnreadBubbleTextColor;
    }

    protected int getDialogUnreadBubbleTextSize() {
        return dialogUnreadBubbleTextSize;
    }

    protected int getDialogUnreadBubbleTextStyle() {
        return dialogUnreadBubbleTextStyle;
    }

    protected int getDialogUnreadBubbleBackgroundColor() {
        return dialogUnreadBubbleBackgroundColor;
    }

    protected int getDialogAvatarWidth() {
        return dialogAvatarWidth;
    }

    protected int getDialogAvatarHeight() {
        return dialogAvatarHeight;
    }

    protected boolean isDialogDividerEnabled() {
        return dialogDividerEnabled;
    }

    protected int getDialogDividerColor() {
        return dialogDividerColor;
    }

    protected int getDialogDividerLeftPadding() {
        return dialogDividerLeftPadding;
    }

    protected int getDialogDividerRightPadding() {
        return dialogDividerRightPadding;
    }

    protected int getDialogItemBackground() {
        return dialogItemBackground;
    }

    protected int getDialogUnreadItemBackground() {
        return dialogUnreadItemBackground;
    }

    protected boolean isDialogMessageAvatarEnabled() {
        return dialogMessageAvatarEnabled;
    }

    protected int getDialogMessageAvatarWidth() {
        return dialogMessageAvatarWidth;
    }

    protected int getDialogMessageAvatarHeight() {
        return dialogMessageAvatarHeight;
    }
}
