package pl.mlethys.todolist.view;

import pl.mlethys.todolist.R;
import pl.mlethys.todolist.model.DatabaseManager;
import pl.mlethys.todolist.model.MySqliteHelper;
import android.app.Activity;
import android.os.Bundle;
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
	
	public void addProject(View view)
	{
		EditText projectTextField = (EditText) findViewById(R.id.text_field);
		dbManager.add(projectTextField.getText().toString());
		projectTextField.setText("");
		Toast.makeText(getApplicationContext(), "Stworzono nowy projekt!", Toast.LENGTH_SHORT).show();
		
	}
}
