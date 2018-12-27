package com.demo.Schedule;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 基于Timer实现的定时调度（不推荐，用该方式不如用 ScheduledExecutorService ）
 */
public class TimerDemo {
    public static void main(String[] args) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("执行任务：" + LocalDateTime.now());
            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask, 5000, 3000);
    }
}
