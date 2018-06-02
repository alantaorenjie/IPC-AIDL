package com.trj.ipc.remote;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.trj.ipc.IBookManager;

/**
 * @author TRJ
 * @date 2018/6/2
 * Description:
 */
public class BookManagerServiceConnection implements ServiceConnection {

    private static BookManagerServiceConnection bookManagerServiceConnection = new BookManagerServiceConnection();

    private IBookManager mBookManager;

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        mBookManager = IBookManager.Stub.asInterface(service);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        mBookManager = null;
    }

    public static BookManagerServiceConnection getInstance(){
        return bookManagerServiceConnection;
    }

    public void init(Context context){
        Intent intent = new Intent(context, BookManagerService.class);
        context.bindService(intent,this, Service.BIND_AUTO_CREATE);
    }

    private boolean isAvailable(){
        return mBookManager!=null;
    }

    public void sendMessage(String message){
        if (isAvailable()){
            try {
                mBookManager.sendMessage(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public String getConversation() {
        if (isAvailable()){
            try {
                return mBookManager.getConversation();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
