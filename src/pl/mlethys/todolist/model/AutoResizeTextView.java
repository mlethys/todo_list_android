package pl.mlethys.todolist.model;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class AutoResizeTextView extends TextView
{

	
	public AutoResizeTextView(Context context)
	{
		super(context);
	}
	
	public AutoResizeTextView(Context context, AttributeSet attrs)
	{
		super(context, attrs, 0);
	}
	
    public AutoResizeTextView(Context context, AttributeSet attrs, int defStyle) 
    {
        super(context, attrs, defStyle);
    }
    
    public void append(CharSequence text, int lineSize)
    {
    	if(text.length() > lineSize)
    	{
    		String tmpText = text.subSequence(0, lineSize - 1) + "\n" + text.subSequence(lineSize, text.length());
    		append(tmpText);
    	}
    	else
    	{
    		append(text);
    	}
    }
	
	
}