package com.btcdata.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.btcdata.task.LtcTaskGetPrice;
import com.btcdata.task.TaskGetPrice;

@Configuration
@EnableScheduling // 启用定时任务
public class SchedulingConfig {
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private TaskGetPrice task;
	
	@Autowired
	private LtcTaskGetPrice ltcTask;
	
	// 每5秒执行一次  上一次执行完毕时间点  == @Scheduled(fixedDelay=3000)
	//@Scheduled(cron = "0/5 * * * * ?")  
	// 上一次开始执行时间点后5秒再次执行
	//@Scheduled(fixedRate=5000)
    public void scheduler() {
		//log.info(">>>>>>>>>>>>> scheduled ... ");
		//task.taskPrice();
    }
	
	//@Scheduled(fixedRate=7000)
    public void schedulerLtc() {
		//log.info(">>>>>>>>>>>>> scheduled ... ");
		//ltcTask.taskPrice();
    }
}
