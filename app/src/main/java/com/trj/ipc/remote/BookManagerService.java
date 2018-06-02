package com.trj.ipc.remote;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * @author TRJ
 * @date 2018/6/2
 * Description:
 */
public class BookManagerService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new BookManagerStub();
    }
}
