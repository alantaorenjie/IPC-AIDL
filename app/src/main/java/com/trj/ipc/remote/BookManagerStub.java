package com.trj.ipc.remote;


import android.os.RemoteException;

import com.trj.ipc.IBookManager;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author TRJ
 * @date 2018/6/2
 * Description:
 */
public class BookManagerStub extends IBookManager.Stub{

    CopyOnWriteArrayList<String> lists = new CopyOnWriteArrayList<>();

    @Override
    public void sendMessage(String message) throws RemoteException {
        lists.add(message);
    }

    @Override
    public String getConversation() throws RemoteException {
        return lists.toString();
    }
}
