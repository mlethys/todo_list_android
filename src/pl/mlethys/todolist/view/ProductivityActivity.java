package pl.mlethys.todolist.view;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.joda.time.LocalDate;

import pl.mlethys.todolist.R;
import pl.mlethys.todolist.model.DatabaseManager;
import pl.mlethys.todolist.model.MySqliteHelper;
import pl.mlethys.todolist.model.Point;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;

import com.androidplot.Plot;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

public class ProductivityActivity extends Activity
{
	private DatabaseManager dbManager;
	private MySqliteHelper databaseHelper;
	private XYPlot plot;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.productivity_activity);
		
		databaseHelper = new MySqliteHelper(this);
		dbManager = new DatabaseManager(databaseHelper);
		createGraph();
		addData();
	}
	
	private void createGraph()
	{
		plot = (XYPlot) findViewById(R.id.productivity_plot);
		
		//configuration of look and feel of plot
        plot.getGraphWidget().getGridBackgroundPaint().setColor(Color.BLACK);
        plot.getGraphWidget().getGridBackgroundPaint().setAlpha(0);
        plot.getGraphWidget().getBackgroundPaint().setAlpha(0);
        plot.getBackgroundPaint().setAlpha(0);
        plot.getGraphWidget().getDomainOriginLinePaint().setColor(Color.BLACK);
        plot.getGraphWidget().getRangeOriginLinePaint().setColor(Color.BLACK);
        plot.getLayoutManager().remove(plot.getLegendWidget());
        plot.getGraphWidget().setPadding(0, 0, 8, 0);
        plot.setBorderStyle(Plot.BorderStyle.SQUARE, null, null);
        plot.getBorderPaint().setAlpha(0);
        plot.getBorderPaint().setAntiAlias(true);
        Paint lineFill = new Paint();
        lineFill.setAlpha(200);
        lineFill.setShader(new LinearGradient(0, 0, 0, 250, Color.WHITE, Color.RED, Shader.TileMode.MIRROR));
        //end of configuration
	}
	
	private void addData()
	{
		List<Number> dates = new LinkedList<Number>();
		List<Number> values = new LinkedList<Number>();
		int date = 200;
		for (Point point : dbManager.getPoints(new LocalDate(2014, 5, 5), LocalDate.now()))
		{
			dates.add(date);
			date += 20;
			values.add(point.getValue());
		}
		Number[] arrayDates = dates.toArray(new Number[dates.size()]);
		Number[] arrayValues = values.toArray(new Number[values.size()]);
		
		 Number[] numSightings = {5, 8, 9, 2, 5};
	        Number[] years = {
	                2001,  // 2001
	                2002, // 2002
	                2003, // 2003
	                2004, // 2004
	                2005  // 2005
	        };
	        XYSeries series2 = new SimpleXYSeries(
	                Arrays.asList(arrayDates),
	                Arrays.asList(arrayValues),
	                "helloandroid");
	        LineAndPointFormatter formatter  = new LineAndPointFormatter(Color.rgb(0, 0,0), Color.BLUE, Color.RED, null);
	        plot.addSeries(series2, formatter);
	}
}
