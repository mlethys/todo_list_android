package pl.mlethys.todolist.model;

import java.util.TimerTask;

import org.joda.time.LocalDate;

import android.content.Context;
import android.util.Log;

public class DateCheckTask extends TimerTask
{
	private Context context;
	private LocalDate date;
	private String taskName;
	
	public DateCheckTask(Context context, LocalDate date, String taskName)
	{
		super();
		this.context = context;
		this.date = date;
		this.taskName = taskName;
	}
	
	@Override
	public void run() 
	{
		DeadlineChecker checker = new DeadlineChecker(date, context, taskName);
		checker.checkDate(0);
		Log.d("test", "run");
	}

}
