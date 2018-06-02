package com.trj.ipc.remote;

import android.os.RemoteException;
import android.util.Log;

import com.trj.ipc.IMessageListener;

/**
 * @author TRJ
 * @date 2018/6/2
 * Description:
 */
public class OnMessageStub extends IMessageListener.Stub {
    @Override
    public void onMessage(String message) throws RemoteException {
        Log.i("onMessage", message);
    }
}
