package com.xs.jt.base.module.job;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class HighFrequencyJob {
	
	@Resource(name="minHF")
	private Map<String,Integer> minHF;
	
	@Resource(name="hourHF")
	private Map<String,Integer> hourHF;
	
	@Resource(name="dayHF")
	private Map<String,Integer> dayHF;
	
	
	@Scheduled(cron = "0 */1 * * * ? ")
	private void minJob() throws Exception {
		minHF.clear();
	}
	
	@Scheduled(cron = "0 0 */1 * * ? ")
	private void hourJob() throws Exception {
		hourHF.clear();
	}
	
	@Scheduled(cron = "0 0 0 */1 * ? ")
	private void dayHFJob() throws Exception {
		dayHF.clear();
	}

}
