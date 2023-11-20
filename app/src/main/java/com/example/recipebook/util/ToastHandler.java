package com.example.recipebook.util;

import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ToastHandler extends AppCompatActivity {

    public ToastHandler() {
    }

    /**
     * Shows a long toast message
     * @param context the context of the activity
     * @param message the message to be displayed
     */
    public void showLongToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Shows a short toast message
     * @param context the context of the activity
     * @param message the message to be displayed
     */
    public void showShortToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}