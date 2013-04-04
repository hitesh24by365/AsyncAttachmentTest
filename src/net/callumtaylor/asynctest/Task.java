package net.callumtaylor.asynctest;

import android.app.Activity;
import android.os.AsyncTask;

public class Task extends AsyncTask<Void, Integer, Void>
{
	private MainActivity activity;
	private ProgressDialogFragment progressDialog;

	@Override protected void onPreExecute()
	{
		super.onPreExecute();

		// create the dialog and attach it to the fragment manager
		progressDialog = new ProgressDialogFragment.Builder()
			.setMessage("Working...")
			.build();
		progressDialog.show(activity.getFragmentManager(), "task_progress");
	}

	@Override protected Void doInBackground(Void... params)
	{
		try
		{
			// simulate processing
			for (int progress = 0; progress <= 100; progress++)
			{
				publishProgress(progress);
				Thread.sleep(50);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	@Override protected void onProgressUpdate(Integer... values)
	{
		super.onProgressUpdate(values);

		// update the progress
		progressDialog.setProgress(activity.getFragmentManager(), values[0]);
	}

	@Override protected void onPostExecute(Void result)
	{
		super.onPostExecute(result);

		if (activity != null)
		{
			// use the new activity reference just incase it was re-attached
			progressDialog.dismiss(activity.getFragmentManager());

			// reference UI updates using activity
			activity.updateUI();
		}

		// remove the reference
		TaskHelper.getInstance().removeTask("task");
	}

	/**
	 * Attaches an activity to the task
	 * @param a The activity to attach
	 */
	public void attach(Activity a)
	{
		this.activity = (MainActivity)a;
	}

	/**
	 * Removes the activity from the task
	 */
	public void detach()
	{
		this.activity = null;
	}
}