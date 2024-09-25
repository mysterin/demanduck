package com.nib.demanduck.util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.concurrent.*;

/**
 * @author linxiaobin
 * @Description 线程池工具类
 * @date 2024/9/25 18:04
 */
@Slf4j
@Component
public class ThreadPoolUtils {

    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(10, 20, 60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1000), new ThreadFactoryBuilder().setNameFormat("thread-pool-%d").build(),
            new ThreadPoolExecutor.AbortPolicy());

    private static final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = Executors.newScheduledThreadPool(1,
            new ThreadFactoryBuilder().setNameFormat("monitor-thread-pool-%d").build());

    static {
        monitor();
    }

    @PreDestroy
    public void shutdownGracefully() {
        // 不再接收新任务，但是会继续执行队列中已提交的任务
        THREAD_POOL_EXECUTOR.shutdown();
        try {
            // 等待一段时间让已提交的任务完成
            if (!THREAD_POOL_EXECUTOR.awaitTermination(10, TimeUnit.SECONDS)) {
                // 打印待执行任务数和执行中的任务数
                log.error("线程池任务未能正常结束，待执行任务数：{}，已执行任务数：{}，活跃线程数：{}", getQueueSize(), getCompletedTaskCount(), getActiveCount());
                // 如果在等待时间内任务仍未完成，则尝试停止当前正在执行的任务
                THREAD_POOL_EXECUTOR.shutdownNow();
                // 再次等待一段时间让任务响应中断
                if (!THREAD_POOL_EXECUTOR.awaitTermination(5, TimeUnit.SECONDS)) {
                    log.error("线程池任务未能正常结束");
                }
            } else {
                log.info("线程池任务正常结束");
            }
        } catch (InterruptedException ie) {
            // 如果等待过程中发生中断，则重新尝试停止当前正在执行的任务
            THREAD_POOL_EXECUTOR.shutdownNow();
        }
    }

    public static void execute(Runnable runnable) {
        THREAD_POOL_EXECUTOR.execute(runnable);
    }

    public static void shutdown() {
        THREAD_POOL_EXECUTOR.shutdown();
    }

    public static void shutdownNow() {
        THREAD_POOL_EXECUTOR.shutdownNow();
    }

    public static int getActiveCount() {
        return THREAD_POOL_EXECUTOR.getActiveCount();
    }

    public static int getCorePoolSize() {
        return THREAD_POOL_EXECUTOR.getCorePoolSize();
    }

    public static int getMaximumPoolSize() {
        return THREAD_POOL_EXECUTOR.getMaximumPoolSize();
    }

    public static int getPoolSize() {
        return THREAD_POOL_EXECUTOR.getPoolSize();
    }

    public static int getQueueSize() {
        return THREAD_POOL_EXECUTOR.getQueue().size();
    }

    public static long getTaskCount() {
        return THREAD_POOL_EXECUTOR.getTaskCount();
    }

    public static long getCompletedTaskCount() {
        return THREAD_POOL_EXECUTOR.getCompletedTaskCount();
    }

    public static void monitor() {
        SCHEDULED_EXECUTOR_SERVICE.scheduleAtFixedRate(() -> {
            int queueSize = getQueueSize();
            if (queueSize > 50) {
                log.warn("线程池监控：待执行任务数：{}，已执行任务数：{}，活跃线程数：{}", getQueueSize(), getCompletedTaskCount(), getActiveCount());
            } else {
                log.debug("线程池监控：待执行任务数：{}，已执行任务数：{}，活跃线程数：{}", getQueueSize(), getCompletedTaskCount(), getActiveCount());
            }
        }, 0, 1, TimeUnit.MINUTES);
    }

}
