package app.nichepro.fragmenttabpatient.ehr;

import java.util.Calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import app.nichepro.activities.healthcare.R;
import app.nichepro.model.db.AlarmData;
import app.nichepro.util.UpdateUiFromAdapterListener;

/**
 * A simple implementation of list adapter.
 */
public class AlarmListAdapter extends ArrayAdapter<AlarmData> {
	LayoutInflater minflater;

	static class ViewHolder {
		TextView nameTxt;
		TextView dateTxt;
		CheckBox chkBox;

		/**
		 * Reset the content of cell to remove the flickering effect
		 */
		public void reset() {

		}
	}

	public AlarmListAdapter(Context context, LayoutInflater inflater,
			int textViewResourceId) {
		super(context, textViewResourceId);
		minflater = inflater;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		if (convertView == null) {

		}

		final ViewHolder holder;
		if (convertView == null) {

			convertView = minflater.inflate(R.layout.alarm_list_cell, null);

			holder = new ViewHolder();

			holder.dateTxt = ((TextView) convertView.findViewById(R.id.time));
			holder.nameTxt = ((TextView) convertView.findViewById(R.id.name));
			holder.chkBox = ((CheckBox) convertView.findViewById(R.id.chkBox));

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		AlarmData ad = getItem(position);

		holder.nameTxt.setText(ad.getMessage());

		holder.dateTxt.setText(setDate(ad.getTime()));
		holder.chkBox.setTag(position);
		holder.chkBox
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						int pos = (Integer) holder.chkBox.getTag();
						AlarmData ad = getItem(pos);

						if (ad.isDeleteAlarm == false) {
							ad.isDeleteAlarm = true;
						} else
							ad.isDeleteAlarm = false;

					}
				});
		return convertView;
	}

	private String setDate(long millis) {
		String time = "";

		Calendar currCal = Calendar.getInstance();
		Calendar setCal = Calendar.getInstance();
		setCal.setTimeInMillis(millis);
		String am_pm = "";
		if (setCal.get(Calendar.AM_PM) == 0) {
			am_pm = "AM";
		} else {
			am_pm = "PM";
		}
		String min = "";
		if (setCal.get(Calendar.MINUTE) < 10) {
			min = "0" + setCal.get(Calendar.MINUTE);
		} else
			min = "" + setCal.get(Calendar.MINUTE);
		time = "" + setCal.get(Calendar.HOUR_OF_DAY) + ":" + min + " " + am_pm;
		setCal.set(Calendar.HOUR_OF_DAY, currCal.get(Calendar.HOUR_OF_DAY));
		setCal.set(Calendar.MINUTE, currCal.get(Calendar.MINUTE));
		return time;
	}
}