package com.trj.ipc.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.trj.ipc.R;
import com.trj.ipc.remote.BookManagerServiceConnection;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BookManagerServiceConnection.getInstance().init(this);
    }

    public void sendMessage(View view) {
        String message = "i am a message " + Math.random();
        BookManagerServiceConnection.getInstance().sendMessage(message);
        Log.i("sendMessage ", message);
    }

    public void getConversation(View view) {
        String conversation = BookManagerServiceConnection.getInstance().getConversation();
        Log.i("conversation ", conversation);
    }
}
