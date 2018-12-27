package com.demo.controller;

import com.demo.model.Task;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ScheduledFuture;

@Log4j2
@RestController
@Api(description = "定时任务")
@RequestMapping("/quartz/task")
public class DynamicTaskController {
    @Autowired
    private Task task;
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private ScheduledFuture<?> future1;
    private ScheduledFuture<?> future2;

    @Bean
    public ThreadPoolTaskScheduler springTaskConfiguration() {
        return new ThreadPoolTaskScheduler();
    }

    @PostMapping("/startCron1")
    @ApiOperation("开始定时任务1")
    public ResponseEntity startCron1() {
        if (null != future1 && future1.isDone()) {
            ResponseEntity.ok("定时任务1已启动");
        }
        future1 = threadPoolTaskScheduler.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("first DynamicTask，" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            }
        }, new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                return new CronTrigger(task.getCorn1()).nextExecutionTime(triggerContext);
            }
        });

        System.out.println("DynamicTask.startCron1()");
        return ResponseEntity.ok("开始定时任务1");
    }

    @PostMapping("/stopCron1")
    @ApiOperation("关闭定时任务1")
    public ResponseEntity stopCron1() {
        if (future1 != null) {
            future1.cancel(true);
        }
        System.out.println("DynamicTask.stopCron1()");
        return ResponseEntity.ok("关闭定时任务1");
    }

    @PostMapping("/startCron2")
    @ApiOperation("开始定时任务2")
    public ResponseEntity startCron2() {

        if (null != future2 && future2.isDone()) {
            ResponseEntity.ok("定时任务2已启动");
        }

        future2 = threadPoolTaskScheduler.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("second DynamicTask，" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            }
        }, new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                return new CronTrigger(task.getCorn2()).nextExecutionTime(triggerContext);
            }
        });

        System.out.println("DynamicTask.startCron2()");

        return ResponseEntity.ok("开始定时任务2");
    }

    @PostMapping("/stopCron2")
    @ApiOperation("关闭定时任务2")
    public ResponseEntity stopCron2() {
        if (future2 != null) {
            future2.cancel(true);
        }
        System.out.println("DynamicTask.stopCron2()");
        return ResponseEntity.ok("关闭定时任务2");
    }
}
