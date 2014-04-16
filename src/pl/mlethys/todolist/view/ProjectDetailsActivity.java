package pl.mlethys.todolist.view;

import org.joda.time.LocalDate;

import pl.mlethys.todolist.R;
import pl.mlethys.todolist.model.DatabaseManager;
import pl.mlethys.todolist.model.MySqliteHelper;
import pl.mlethys.todolist.model.Task;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class ProjectDetailsActivity extends Activity
{
	private final String LOG_TAG = "Project_details_activity";
	
	private ScrollView scrollView;
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
		
		params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		
		dateDialog = new Dialog(this);
		LinearLayout parentLayout = new LinearLayout(this);
		parentLayout.setOrientation(LinearLayout.VERTICAL);
		
		
		LinearLayout mainLayout = new LinearLayout(this);
		mainLayout.setGravity(Gravity.CENTER|Gravity.TOP);
		mainLayout.setOrientation(LinearLayout.VERTICAL);	
		
		
		title = getIntent().getStringExtra("title");
		
		mainLayout.addView(createTitle(title), params);
		childLayout = createChildLayout();
		mainLayout.addView(childLayout, params);
		setTasks();
		mainLayout.addView(setAddTaskPanel(), params);
		scrollView = new ScrollView(this);
		scrollView.addView(mainLayout);
		parentLayout.addView(scrollView, params);
		setContentView(parentLayout);
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
				LocalDate tmpDate;
				if(tmpDatePicker == null)
				{
					dbManager.add(taskNameEditText.getText().toString(), dbManager.getProjectId(title));
					Log.d(LOG_TAG, "add task, null date");
				}
				else
				{
					tmpDate = new LocalDate(tmpDatePicker.getYear() + "-" + tmpDatePicker.getMonth() + "-" + tmpDatePicker.getDayOfMonth());
					dbManager.add(taskNameEditText.getText().toString(), tmpDate, dbManager.getProjectId(title));
					Log.d(LOG_TAG, "add task, not null date");
				}				
				Toast.makeText(ProjectDetailsActivity.this, R.string.added_task_message, Toast.LENGTH_SHORT).show();
		
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
		for(final Task task : dbManager.getTasks(dbManager.getProjectId(title)))
		{	
			RelativeLayout layout = new RelativeLayout(this);
			layout.setGravity(Gravity.TOP);
			CheckBox checkbox = new CheckBox(this);
			checkbox.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v) 
				{
					dbManager.deleteTask(dbManager.getTaskId(task.getName()));
					finish();
					startActivity(getIntent());
				}
			});
			layout.addView(checkbox);
			
			TextView taskName = new TextView(this);
			taskName.append(task.getName());

			taskName.setMaxWidth(300);
			taskName.setPadding(50, 0, 0, 0);
			layout.addView(taskName);
			
			Log.d(LOG_TAG, "getting date");
			TextView taskDeadline = new TextView(this);
			//taskDeadline.setHint(R.string.deadline_hint);
			taskDeadline.setPadding(350, 0, 0, 0);
			if(task.getDeadline() != null)
			{		
				//taskDeadline.setClickable(true);
				taskDeadline.append(task.getDeadline().toString());	
			}
			else
			{
				taskDeadline.append(this.getResources().getText(R.string.no_deadline));
			}
			layout.addView(taskDeadline, params);
			childLayout.addView(layout);
		}	
	}
	
	private LinearLayout setAddTaskPanel()
	{
		Log.d(LOG_TAG, "setAddTaskPanel is called");
		LinearLayout layout = new LinearLayout(this);
		layout.setGravity(Gravity.CENTER);
		layout.setOrientation(LinearLayout.HORIZONTAL);
		taskNameEditText = new EditText(this);
		taskNameEditText.setMaxWidth(300);
		layout.addView(taskNameEditText, params);
		taskNameEditText.setHint(R.string.add_task_hint);
		layout.addView(createDatePickerButton(), params);
		layout.addView(createAddTaskButton(), params);
		return layout;
	}
}
