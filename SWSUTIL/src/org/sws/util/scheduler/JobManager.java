package org.sws.util.scheduler;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.sws.util.entity.Entity;

public class JobManager{
	
	private static JobManager instance;
	private static Entity jobs;
	
	public static JobManager getInstance() throws SchedulerException
	{
		if(instance == null) {
			return new JobManager();
		}
		return instance;
	}
	
	/**
	 * 배치잡을 등록한다.
	 * @param jobName 작업 이름
	 * @param group 그룹 명
	 * @param clasz Job클래스를 상속받은 실행 클래스
	 * @param schedule clon 형식의 배치 시케쥴
	 * @throws SchedulerException 
	 */
	public static void addJob(String jobName, String group, Class clasz, String schedule) throws SchedulerException
	{
		//Quartz 1.6.3
    	//JobDetail job = new JobDetail();
    	//job.setName("dummyJobName");
    	//job.setJobClass(HelloJob.class); 
		JobDetail job = JobBuilder.newJob(clasz)
				.withIdentity("job_" + jobName, group)
				.build();
		
		//Quartz 1.6.3
    	//CronTrigger trigger = new CronTrigger();
    	//trigger.setName("dummyTriggerName");
    	//trigger.setCronExpression("0/5 * * * * ?");
		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity("trigger_" + jobName, group)
				.withSchedule(CronScheduleBuilder.cronSchedule(schedule))
				.build();
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		scheduler.start();
    	scheduler.scheduleJob(job, trigger);
    	
    	jobs.setValue(jobName, scheduler);
	}
	
	/**
	 * 작업을 삭제한다.
	 * @param jobName
	 * @throws SchedulerException
	 */
	public static void removeJob(String jobName) throws SchedulerException
	{
		Scheduler scheduler = (Scheduler)jobs.getObject(jobName);
		scheduler.clear(); // TODO 이렇게 하면 작업이 해제 되는지 확인해봐야함
	}
}
