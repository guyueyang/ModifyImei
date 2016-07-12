package com.andcup.android.app.info.changer.manager;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.andcup.android.app.info.changer.action.WriteFilesAction;
import com.andcup.android.app.info.changer.rxfileobserver.FileEvent;
import com.andcup.android.app.info.changer.rxfileobserver.MultiFileObserver;
import com.andcup.android.app.info.changer.rxfileobserver.RxFileObserver;
import com.andcup.android.app.info.changer.tools.AndroidUtils;

import java.io.File;

import rx.Observable;

/**
 * Created by Administrator on 2016/3/1.
 */
public class ListeningService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        new Thread(new Runnable(){
            @Override
            public void run() {
                // TODO Auto-generated method stub
                //处理具体的逻辑
                Log.i("Service", "启动监听");
                File sdCard = Environment.getExternalStorageDirectory();
                Observable<FileEvent> sdCardFileEvents = RxFileObserver.create(sdCard);
                sdCardFileEvents.subscribe(fileEvent -> {
                    Log.i("用户操作", fileEvent.toString());
                });
            }

        }).start();
        return super.onStartCommand(intent, flags, startId);

    }
}
