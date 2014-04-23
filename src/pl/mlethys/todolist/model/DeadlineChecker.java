package pl.mlethys.todolist.model;

import org.joda.time.LocalDate;

import pl.mlethys.todolist.R;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

public class DeadlineChecker
{
	private LocalDate deadline;
	private Context context;
	private String taskName;
	private final int DEFAULT = 0;
	private final int DAY_REMIND = 1;
	private final int WEEK_REMIND = 2;
	private final int NO_REMIND = 3;
	
	
	public DeadlineChecker(LocalDate deadline, Context context, String taskName)
	{
		this.deadline = deadline;
		this.context = context;
		this.taskName = taskName;
	}
	
	public void checkDate(int choice)
	{
		if (deadline == null)
		{
			return;
		}
		switch(choice)
		{
			case DEFAULT:
				if(deadline.equals(LocalDate.now()))
				{
					createNotification(0);
				}
				break;
			case DAY_REMIND:
				if(deadline.equals(deadline.minusDays(1)))
				{
					createNotification(1);
				}
				break;
			case WEEK_REMIND:
				if(deadline.equals(deadline.minusDays(7)))
				{
					createNotification(7);
				}
				break;
			case NO_REMIND:
				//do nothing
				break;
		}
	}
	
	@SuppressLint("NewApi")
	private void createNotification(int daysToDeadline)
	{
		Notification notification = new NotificationCompat.Builder(context)
													.setContentTitle("test")
													.setContentText("test " + " za " + daysToDeadline + " dni!")
													.setSmallIcon(R.drawable.ic_launcher)
													.setAutoCancel(true)
													.build();
		notification.defaults |= Notification.DEFAULT_SOUND;
		
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(0, notification);
	}
	
}
