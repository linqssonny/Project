package com.library.base.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by admin on 2016/11/16.
 */

public class ThreadManager {

    private ThreadPoolExecutor mThreadPoolExecutor;

    private PriorityBlockingQueue<Runnable> mPriorityBlockingQueue;

    //private final int mCorePoolSize = 3;
    private final int mCorePoolSize = Runtime.getRuntime().availableProcessors();
    //private final int mMaximumPoolSize = 4;
    private final int mMaximumPoolSize = Runtime.getRuntime().availableProcessors() * 2 + 1;

    private ThreadManager() {
        mPriorityBlockingQueue = new PriorityBlockingQueue<>(10, new PriorityComparator());
        mThreadPoolExecutor = new ThreadPoolExecutor(
                mCorePoolSize,
                mMaximumPoolSize,
                0,
                TimeUnit.SECONDS,
                mPriorityBlockingQueue,
                Executors.defaultThreadFactory(),
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

                    }
                }
        );
    }

    private static class ThreadManagerInstances {
        private static ThreadManager sThreadManager = new ThreadManager();
    }

    public static ThreadManager getInstances() {
        return ThreadManagerInstances.sThreadManager;
    }

    public <T extends PriorityRunnable> void execute(T t) {
        if (null == t || null == mThreadPoolExecutor) {
            return;
        }
        synchronized (mThreadPoolExecutor) {
            mThreadPoolExecutor.execute(t);
        }
    }

    public <T extends PriorityRunnable> void remove(T t) {
        if (null == t || null == mThreadPoolExecutor) {
            return;
        }
        synchronized (mThreadPoolExecutor) {
            if (mThreadPoolExecutor.getQueue().contains(t)) {
                mThreadPoolExecutor.remove(t);
            }
        }
    }
}
