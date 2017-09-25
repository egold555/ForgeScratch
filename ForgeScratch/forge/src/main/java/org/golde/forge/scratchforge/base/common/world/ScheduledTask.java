package org.golde.forge.scratchforge.base.common.world;

public class ScheduledTask implements Comparable<ScheduledTask>
{
	private long timeToFire;
	private boolean repeating;
	private long repeatTime;
	private Runnable taskToRun;
	
	ScheduledTask(long timeToFire, boolean repeat, long repeatTime, Runnable taskToRun)
	{
		this.timeToFire = timeToFire;
		this.repeating = repeat;
		this.repeatTime = repeatTime;
		this.taskToRun = taskToRun;
	}
	 
	long getTimeToFire() {
		return timeToFire;
	}
	
	long getRepeatTime() {
		return repeatTime;
	}
	
	boolean isRepeating() {
		return repeating;
	}
	
	void delayRepeatTime()
	{
		timeToFire += repeatTime;
	}
	
	Runnable getRunnable()
	{
		return taskToRun;
	}
	
	void run()
	{
		taskToRun.run();
	}
	
	@Override
	public int compareTo(ScheduledTask other) {
		if (other.timeToFire > this.timeToFire)
			return -1;
		else if (other.timeToFire < this.timeToFire)
			return 1;
		else
			return 0;
	}
}
