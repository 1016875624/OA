package com.oa.common.scheduled;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class ScheduledTask {
	int count=0;
	/**
	* <p>方法名称: salaryPay</p>
	* <p>描述：发放工资</p> 
	* void 返回类型
	*/
	//每个月的15日八点进行发工资
	//@Scheduled(cron="0 0 8 15 * *")
	public void salaryPay() {
		
	}
	/**
	* <p>方法名称: minuteTask</p>
	* <p>描述：分钟任务</p> void 返回类型
	*/
	@Scheduled(cron="0 0/1 * * * *")
	public void minuteTask() {
		System.out.println("分钟级任务   执行次数:"+count++);
	}
}
