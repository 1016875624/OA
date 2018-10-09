package com.oa.common.scheduled;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask {
	/**
	* <p>方法名称: salaryPay</p>
	* <p>描述：发放工资</p> 
	* void 返回类型
	*/
	//每个月的15日八点进行发工资
	@Scheduled(cron="0 0 8 15 * *")
	public void salaryPay() {
		
	}
}
