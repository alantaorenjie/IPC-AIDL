// IBookManager.aidl
package com.trj.ipc;

import com.trj.ipc.IMessageListener;
// Declare any non-default types here with import statements

interface IBookManager {

    void sendMessage(in String message);

    String getConversation();

    void registerListener(IMessageListener listener);

    void unregisterListener(IMessageListener listener);

}
