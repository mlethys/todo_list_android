package pl.mlethys.todolist.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class ProjectDetailsActivity extends Activity
{
	private LinearLayout childLayout;
	private final String BUTTON_TEXT = "Dodaj";
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		LinearLayout mainLayout = new LinearLayout(this);
		mainLayout.setGravity(Gravity.CENTER|Gravity.TOP);
		mainLayout.setOrientation(LinearLayout.VERTICAL);	
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		
		String title = getIntent().getStringExtra("title");
		
		mainLayout.addView(createTitle(title), params);
		childLayout = createChildLayout();
		childLayout.addView(createAddTastButton());
		mainLayout.addView(childLayout, params);

		setContentView(mainLayout);
	}
	
	private TextView createTitle(String title)
	{
		TextView activityTitle = new TextView(this);
		activityTitle.append(title);
		activityTitle.setTextSize(30f);
		activityTitle.setGravity(Gravity.CENTER);
		return activityTitle;
	}
	
	private Button createAddTastButton()
	{
		Button button = new Button(this);
		button.setText(BUTTON_TEXT);
		
		button.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				
			}
		});
		return button;
	}
	
	private LinearLayout createChildLayout()
	{
		LinearLayout layout = new LinearLayout(this);
		layout.setGravity(Gravity.TOP);
		layout.setOrientation(LinearLayout.VERTICAL);	
		return layout;
	}
}
