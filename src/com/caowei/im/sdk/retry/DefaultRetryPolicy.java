package com.caowei.im.sdk.retry;

import com.caowei.im.sdk.netty.NettyClient;
import com.caowei.im.sdk.utils.LogUtils;

import java.lang.ref.WeakReference;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 重连策略的默认实现
 * Retry policy that retries a set
 * number of times with increasing sleep time between retries
 */
public class DefaultRetryPolicy implements IRetryPolicy {
    private static final int MAX_RETRIES_LIMIT = 29;//最大重试次数
    private static final int DEFAULT_MAX_SLEEP_MS = Integer.MAX_VALUE;//最大休眠时间

    private final Random random = new Random();

    private volatile int mCurRetries = 0;
    private volatile boolean isCancel = false;
    private WeakReference<NettyClient> weakReference;

    public DefaultRetryPolicy(WeakReference<NettyClient> weakReference) {
        this.weakReference = weakReference;
    }

    public void stopRetry() {
        mCurRetries = 0;
        isCancel = true;
    }

    public void startRetry() {
        if (mCurRetries > MAX_RETRIES_LIMIT) {
            LogUtils.log("重试次数已到最大：" + mCurRetries);
            return;
        }

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (weakReference.get() != null && !isCancel) {
                    mCurRetries++;
                    weakReference.get().reconnect(false);
                }
            }
        }, 60 * 1000);
    }

    @Override
    public boolean allowRetry(int retryCount) {
        return false;
    }

    @Override
    public long getSleepTimeMs(int retryCount) {
        return 0;
    }
}
