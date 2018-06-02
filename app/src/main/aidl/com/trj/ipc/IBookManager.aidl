// IBookManager.aidl
package com.trj.ipc;

// Declare any non-default types here with import statements

interface IBookManager {

    void sendMessage(in String message);

    String getConversation();

}
