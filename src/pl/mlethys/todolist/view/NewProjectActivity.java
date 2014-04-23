package pl.mlethys.todolist.view;

import pl.mlethys.todolist.R;
import pl.mlethys.todolist.model.DatabaseManager;
import pl.mlethys.todolist.model.MySqliteHelper;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NewProjectActivity extends Activity
{
	private DatabaseManager dbManager;
	private MySqliteHelper databaseHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_project_activity);
		
		databaseHelper = new MySqliteHelper(this);
		dbManager = new DatabaseManager(databaseHelper);
	}
	
	@SuppressLint({ "NewApi", "ResourceAsColor" })
	public void addProject(View view)
	{
		final EditText projectTextField = (EditText) findViewById(R.id.text_field);
		final Drawable originalDrawable = projectTextField.getBackground();
		final ColorStateList color = projectTextField.getHintTextColors();
		if (projectTextField.getText().toString().isEmpty())
		{
			new CountDownTimer(200, 100)
			{

				@SuppressWarnings("deprecation")
				@Override
				public void onFinish() 
				{
					projectTextField.setBackgroundDrawable(originalDrawable);
					projectTextField.setHintTextColor(color.getDefaultColor());
				}

				@Override
				public void onTick(long arg0) 
				{
					projectTextField.setBackgroundColor(getResources().getColor(R.color.red));
					projectTextField.setHintTextColor(getResources().getColor(R.color.white));
					
				}
				
			}.start();
		}
		else
		{
			dbManager.add(projectTextField.getText().toString());
			projectTextField.setText("");
			Toast.makeText(getApplicationContext(), "Stworzono nowy projekt!", Toast.LENGTH_SHORT).show();
		}
	}
}
