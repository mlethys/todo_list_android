package pl.mlethys.todolist.view;

import org.joda.time.LocalDate;

import pl.mlethys.todolist.R;
import pl.mlethys.todolist.model.DatabaseManager;
import pl.mlethys.todolist.model.DateCheckService;
import pl.mlethys.todolist.model.DeadlineChecker;
import pl.mlethys.todolist.model.MySqliteHelper;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends ActionBarActivity
{
	private DatabaseManager dbManager;
	private MySqliteHelper databaseHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if (savedInstanceState == null)
		{
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
//		DateCheckService service = new DateCheckService(getDate());
//		startService(new Intent(getBaseContext(), DateCheckService.class));
	}
	
	private LocalDate getDate()
	{
		if (dbManager.getCurrentProjects() == null)
		{
			return null;
		}
		for(int i = 0; i < dbManager.getCurrentProjects().size(); i++)
		{
			for(int j = 0; j < dbManager.getTasks(dbManager.getProjectId(dbManager.getCompletedProjects().get(i))).size(); j++)
			{
				return dbManager.getTasks(dbManager.getProjectId(dbManager.getCompletedProjects().get(i))).get(j).getDeadline();
			}
		}
		return null;
	}
	
	public void goToNewProject(View view)
	{
		Intent newProjectIntent = new Intent(MainActivity.this, NewProjectActivity.class);
		startActivity(newProjectIntent);
	}
	
	public void goToCurrentProjects(View view)
	{
		Intent currentProjectsIntent = new Intent(MainActivity.this, CurrentProjectsActivity.class);
		startActivity(currentProjectsIntent);
	}
	
	public void goToCompletedProjects(View view)
	{
		Intent completedProjectsIntent = new Intent(MainActivity.this, CompletedProjectsActivity.class);
		startActivity(completedProjectsIntent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings)
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment
	{

		public PlaceholderFragment()
		{
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
