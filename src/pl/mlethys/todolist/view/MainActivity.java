package pl.mlethys.todolist.view;

import pl.mlethys.todolist.R;
import pl.mlethys.todolist.model.DateCheckService;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if (savedInstanceState == null)
		{
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		startService(new Intent(this, DateCheckService.class));
	}
	
	@Override
	public void onBackPressed()
	{
		this.finish();
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
	
	public void goToProductivity()
	{
		Intent productivityIntent = new Intent(MainActivity.this, ProductivityActivity.class);
		startActivity(productivityIntent);
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
			createSettingsDialog();
			return true;
		}
		else if (id == R.id.action_about)
		{
			createAboutDialog();
			return true;
		}
		else if (id == R.id.action_productivity)
		{
			goToProductivity();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void createAboutDialog()
	{
		Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.about_dialog);
		TextView link = (TextView) dialog.findViewById(R.id.link);
		Linkify.addLinks(link, Linkify.ALL);
		link.setMovementMethod(LinkMovementMethod.getInstance());
		dialog.show();
	}
	
	private void createSettingsDialog()
	{
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.settings_dialog);
		
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this.getApplicationContext());
		final SharedPreferences.Editor editor = sharedPreferences.edit();
		
		RadioButton button1 = (RadioButton) dialog.findViewById(R.id.radio_option_1);
		button1.setOnClickListener(new View.OnClickListener()
		{		
			@Override
			public void onClick(View v) 
			{
				boolean checked = ((RadioButton) v).isChecked();
				if(checked)
				{
					
					editor.putInt("option", 0);
					editor.commit();
					dialog.dismiss();
					Log.d("preferences", "put 2");
				}
			}
		});
		
		RadioButton button2 = (RadioButton) dialog.findViewById(R.id.radio_option_2);
		button2.setOnClickListener(new View.OnClickListener()
		{		
			@Override
			public void onClick(View v) 
			{
				boolean checked = ((RadioButton) v).isChecked();
				if(checked)
				{
					editor.putInt("option", 1);
					editor.commit();
					dialog.dismiss();
					Log.d("preferences", "put 1");
				}
			}
		});
		
		RadioButton button3 = (RadioButton) dialog.findViewById(R.id.radio_option_3);
		button3.setOnClickListener(new View.OnClickListener()
		{		
			@Override
			public void onClick(View v) 
			{
				boolean checked = ((RadioButton) v).isChecked();
				if(checked)
				{
					editor.putInt("option", 2);
					editor.commit();
					dialog.dismiss();
					Log.d("preferences", "put 2");
				}
			}
		});
		
		RadioButton button4 = (RadioButton) dialog.findViewById(R.id.radio_option_4);
		button4.setOnClickListener(new View.OnClickListener()
		{		
			@Override
			public void onClick(View v) 
			{
				boolean checked = ((RadioButton) v).isChecked();
				if(checked)
				{
					editor.putInt("option", 3);
					editor.commit();
					dialog.dismiss();
					Log.d("preferences", "put 3");
				}
			}
		});
		
		switch(sharedPreferences.getInt("option", 0))
		{
			case 0:
				button1.setChecked(true);
				break;
			case 1:
				button2.setChecked(true);
				break;
			case 2:
				button3.setChecked(true);
				break;
			case 3:
				button4.setChecked(true);
				break;
		}
		
		dialog.show();
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
