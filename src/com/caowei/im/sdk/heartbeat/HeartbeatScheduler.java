package com.caowei.im.sdk.heartbeat;

import com.caowei.im.sdk.face.IMSClientInterface;

import java.util.Timer;
import java.util.TimerTask;

public class HeartbeatScheduler extends Timer {
    public static final int HEARTBEAT_INTERVAL = 270; //默认4.5分钟
    private IMSClientInterface imsClient;
    private HeartBeatTask mHeartBeatTask;

    public HeartbeatScheduler(IMSClientInterface imsClient) {
        this.imsClient = imsClient;
    }

    public void start() {
        if (mHeartBeatTask != null) {
            mHeartBeatTask.cancel();
            mHeartBeatTask = null;
        }
        mHeartBeatTask = new HeartBeatTask(imsClient);
        schedule(mHeartBeatTask, HEARTBEAT_INTERVAL);
    }

    private static class HeartBeatTask extends TimerTask{
        private IMSClientInterface imsClientInterface;
        public HeartBeatTask(IMSClientInterface imsClientInterface) {
            this.imsClientInterface = imsClientInterface;
        }

        @Override
        public void run() {

        }
    }
}
