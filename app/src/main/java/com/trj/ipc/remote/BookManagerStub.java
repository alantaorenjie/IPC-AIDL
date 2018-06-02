package com.trj.ipc.remote;


import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemClock;

import com.trj.ipc.IBookManager;
import com.trj.ipc.IMessageListener;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author TRJ
 * @date 2018/6/2
 * Description:
 */
public class BookManagerStub extends IBookManager.Stub {

    public BookManagerStub() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    SystemClock.sleep(5000);
                    final int count = mListenerList.beginBroadcast();
                    for (int i = 0; i < count; i++) {
                        try {
                            mListenerList.getBroadcastItem(i).onMessage("消息消息" + i);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    //必须成对出现
                    mListenerList.finishBroadcast();
                }
            }
        }).start();
    }

    CopyOnWriteArrayList<String> lists = new CopyOnWriteArrayList<>();

    RemoteCallbackList<IMessageListener> mListenerList = new RemoteCallbackList<>();

    @Override
    public void sendMessage(String message) throws RemoteException {
        lists.add(message);
    }

    @Override
    public String getConversation() throws RemoteException {
        return lists.toString();
    }

    @Override
    public void registerListener(IMessageListener listener) throws RemoteException {
        mListenerList.unregister(listener);
        mListenerList.register(listener);
    }

    @Override
    public void unregisterListener(IMessageListener listener) throws RemoteException {
        mListenerList.unregister(listener);
    }
}
