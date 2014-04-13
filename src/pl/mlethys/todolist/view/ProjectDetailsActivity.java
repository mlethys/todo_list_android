package pl.mlethys.todolist.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import pl.mlethys.todolist.R;
import pl.mlethys.todolist.model.DatabaseManager;
import pl.mlethys.todolist.model.MySqliteHelper;
import pl.mlethys.todolist.model.Task;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class ProjectDetailsActivity extends Activity
{
	private final String LOG_TAG = "Project_details_activity";
	
	private LinearLayout childLayout;
	private Dialog dateDialog;
	private String title;
	private DatabaseManager dbManager;
	private MySqliteHelper databaseHelper;
	private LayoutParams params;
	private EditText taskNameEditText;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		databaseHelper = new MySqliteHelper(this);
		dbManager = new DatabaseManager(databaseHelper);
		
		dateDialog = new Dialog(this);
		
		LinearLayout mainLayout = new LinearLayout(this);
		mainLayout.setGravity(Gravity.CENTER|Gravity.TOP);
		mainLayout.setOrientation(LinearLayout.VERTICAL);	
		params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		
		title = getIntent().getStringExtra("title");
		
		mainLayout.addView(createTitle(title), params);
		childLayout = createChildLayout();
		mainLayout.addView(childLayout, params);
		setTasks();
		mainLayout.addView(setAddTaskPanel(), params);
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
	
	@SuppressLint({ "SimpleDateFormat", "ShowToast" })
	private Button createAddTaskButton()
	{
		Button button = new Button(this);
		button.setText(R.string.add_task_button);
		button.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{	
				DatePicker tmpDatePicker = (DatePicker) dateDialog.findViewById(R.id.date_picker);
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date tmpDate;
				try
				{
					tmpDate = dateFormat.parse("2014-01-12");
					dbManager.add(taskNameEditText.getText().toString(), tmpDate, dbManager.getProjectId(title));
					Toast.makeText(ProjectDetailsActivity.this, dateFormat.format(tmpDate), Toast.LENGTH_SHORT).show();
				} 
				catch (ParseException e)
				{
					AlertDialog.Builder alert = new AlertDialog.Builder(ProjectDetailsActivity.this);
					alert.setTitle(R.string.date_format_error_title);
					alert.setMessage(R.string.date_format_error_msg);
					alert.show();
					e.printStackTrace();
				}
				finish();
				startActivity(getIntent());
				
			}
		});
		return button;
		
	}
	
	@SuppressLint("NewApi")
	private ImageButton createDatePickerButton()
	{
		ImageButton button = new ImageButton(this);
		button.setBackgroundResource(R.drawable.calendar);
		button.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{		
				dateDialog.setContentView(R.layout.date_picker_dialog);
				
				Button button = (Button) dateDialog.findViewById(R.id.dialog_confirm_button);
				button.setOnClickListener(new View.OnClickListener()
				{		
					@Override
					public void onClick(View v) 
					{
						dateDialog.dismiss();
						
					}
				});
				dateDialog.show();
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
	
	private void setTasks()
	{
		Log.d(LOG_TAG, "setTasks is called");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try
		{	
			for(Task task : dbManager.getTasks(dbManager.getProjectId(title)))
			{	
				LinearLayout layout = new LinearLayout(this);
				layout.setGravity(Gravity.TOP);
				layout.setOrientation(LinearLayout.HORIZONTAL);
				TextView taskName = new TextView(this);
				taskName.append(task.getName());
				layout.addView(taskName, params);
				TextView taskDeadline = new TextView(this);
				taskDeadline.append(dateFormat.format(task.getDeadline()));
				layout.addView(taskDeadline, params);
				childLayout.addView(layout);
			}
			
		} 
		catch (ParseException e)
		{
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setTitle(R.string.date_format_error_title);
			alert.setMessage(R.string.date_format_error_msg);
			alert.show();
			e.printStackTrace();
		}
	}
	
	private LinearLayout setAddTaskPanel()
	{
		LinearLayout layout = new LinearLayout(this);
		layout.setGravity(Gravity.CENTER);
		layout.setOrientation(LinearLayout.HORIZONTAL);
		taskNameEditText = new EditText(this);
		layout.addView(taskNameEditText, params);
		taskNameEditText.setHint(R.string.add_task_hint);
		layout.addView(createDatePickerButton(), params);
		layout.addView(createAddTaskButton(), params);
		return layout;
	}
}
