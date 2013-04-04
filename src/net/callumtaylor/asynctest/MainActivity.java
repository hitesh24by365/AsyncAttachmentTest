package net.callumtaylor.asynctest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener
{
	@Override protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		findViewById(R.id.begin).setOnClickListener(this);

		// re-attach the activity if the task is still available
		TaskHelper.getInstance().attach("task", this);
	}

	@Override protected void onDestroy()
	{
		super.onDestroy();

		// detach the activity so we dont leak
		TaskHelper.getInstance().detach("task");
	}

	public void updateUI()
	{
		((Button)findViewById(R.id.begin)).setText("It's finished!");
	}

	@Override public void onClick(View arg0)
	{
		if (arg0.getId() == R.id.begin)
		{
			// start the task
			Task t = new Task();
			TaskHelper.getInstance().addTask("task", t, this);
			t.execute();
		}
	}
}