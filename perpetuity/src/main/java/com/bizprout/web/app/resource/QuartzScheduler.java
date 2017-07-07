package com.bizprout.web.app.resource;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuartzScheduler implements ServletContextListener {

	Scheduler scheduler = null;
	
	Logger logger=LoggerFactory.getLogger(this.getClass());

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		logger.debug("Context Destroyed "+this.getClass());

		try {
			scheduler.shutdown();
		} catch (SchedulerException e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {

		logger.debug("Context initialized" + this.getClass());

		// Setup the Job class and the Job group
		JobDetail job = newJob(CronEmailJob.class).withIdentity(
				"CronQuartzJob", "Group").build();

		// Create a Trigger
		Trigger trigger = newTrigger()
				.withIdentity("TriggerName", "Group")
				.withSchedule(
						CronScheduleBuilder.cronSchedule("0 * * ? * *"))
				.build();

		// Setup the Job and Trigger with Scheduler & schedule jobs
		try {
			scheduler = new StdSchedulerFactory().getScheduler();
			scheduler.start();
			scheduler.scheduleJob(job, trigger);
		} catch (SchedulerException e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}

	}

}
