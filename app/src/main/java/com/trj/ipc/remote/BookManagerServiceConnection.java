package com.trj.ipc.remote;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.trj.ipc.IBookManager;

/**
 * @author TRJ
 * @date 2018/6/2
 * Description:
 */
public class BookManagerServiceConnection implements ServiceConnection {

    private static BookManagerServiceConnection bookManagerServiceConnection = new BookManagerServiceConnection();

    private OnMessageStub onMessageStub = new OnMessageStub();

    private IBookManager mBookManager;

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        mBookManager = IBookManager.Stub.asInterface(service);
        //设置DeathRecipient监听
        try {
            service.linkToDeath(new IBinder.DeathRecipient() {
                @Override
                public void binderDied() {
                    Log.i("binderDied","远程服务挂掉了");
                }
            },0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        registerListener();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        Log.i("onServiceDisconnected","解绑成功");
        mBookManager = null;
        //远程服务意外挂掉了，在此处进行重连，或者设置DeathRecipient监听，不同的是DeathRecipient是在客户端binder线程池中回调，onServiceDisconnected是在UI线程中回调
    }

    public static BookManagerServiceConnection getInstance() {
        return bookManagerServiceConnection;
    }

    public void init(Context context) {
        Intent intent = new Intent(context, BookManagerService.class);
        context.bindService(intent, bookManagerServiceConnection, Service.BIND_AUTO_CREATE);
    }

    public void exit(Context context) {
        Log.i("exit","解绑");
        unregisterListener();
        context.unbindService(bookManagerServiceConnection);
    }

    private boolean isAvailable() {
        return mBookManager != null;
    }

    public void sendMessage(String message) {
        if (isAvailable()) {
            try {
                mBookManager.sendMessage(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public String getConversation() {
        if (isAvailable()) {
            try {
                return mBookManager.getConversation();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void registerListener() {
        if (isAvailable()) {
            try {
                mBookManager.registerListener(onMessageStub);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void unregisterListener() {
        if (isAvailable()) {
            try {
                mBookManager.unregisterListener(onMessageStub);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
