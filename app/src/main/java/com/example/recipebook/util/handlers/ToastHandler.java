package com.example.recipebook.util.handlers;

import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ToastHandler extends AppCompatActivity {
   private final Context context;

   /**
    * Constructor for ToastHandler
    *
    * @param context The context
    */
   public ToastHandler(Context context) {
      this.context = context;
   }

   /**
    * Shows a long toast message
    *
    * @param message the message to be displayed
    */
   public void showLongToast(String message) {
      Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
   }
}