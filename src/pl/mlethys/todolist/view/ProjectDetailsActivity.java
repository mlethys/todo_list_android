package pl.mlethys.todolist.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class ProjectDetailsActivity extends Activity
{
	private TextView activityTitle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.project_details_activity);
		
		LinearLayout mainLayout = new LinearLayout(this);
		mainLayout.setGravity(Gravity.CENTER);
		mainLayout.setOrientation(LinearLayout.VERTICAL);
		
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		
		activityTitle = new TextView(this);
		activityTitle.append("test");
		
		mainLayout.addView(activityTitle);
		
		setContentView(mainLayout);
	}
}
