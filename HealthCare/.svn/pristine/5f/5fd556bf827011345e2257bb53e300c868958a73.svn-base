package app.nichepro.fragmenttabpatient.community;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import app.nichepro.activities.healthcare.R;
import app.nichepro.model.PatientUpEventsResponseObject;

public class UpcommingEventsListAdapter extends
		ArrayAdapter<PatientUpEventsResponseObject> {
	LayoutInflater minflater;

	static class ViewHolder {

		/** The note view. */
		TextView name;

		/** The date name. */
		TextView startDate;
		
		TextView endDate;
		
		TextView status;

		TextView location;
		TextView city;
		TextView state;
		TextView webLink;

		/**
		 * Reset the content of cell to remove the flickering effect
		 */
		public void reset() {

		}
	}

	public UpcommingEventsListAdapter(Context context, LayoutInflater inflater,
			int textViewResourceId) {
		super(context, textViewResourceId);
		minflater = inflater;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		ViewHolder holder;

		if (convertView == null) {

			convertView = minflater.inflate(R.layout.upcoming_events_list_cell,
					null);
			holder = new ViewHolder();
			holder.name = ((TextView) convertView.findViewById(R.id.name));
			holder.startDate = ((TextView) convertView.findViewById(R.id.startdate));
			holder.endDate = ((TextView) convertView.findViewById(R.id.enddate));
			holder.status = ((TextView) convertView.findViewById(R.id.status));
			holder.location = ((TextView) convertView.findViewById(R.id.location));
			holder.city = ((TextView) convertView.findViewById(R.id.city));
			holder.state = ((TextView) convertView.findViewById(R.id.state));
			//holder.webLink = ((TextView) convertView.findViewById(R.id.weblink));
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		PatientUpEventsResponseObject puero = getItem(position);
		
		String[] d = null;
		if (puero.startDate != null && !puero.startDate.isEmpty()) {
			d = puero.startDate.split("T");
		}

		if (d != null && d.length > 0) {
			holder.startDate.setText(d[0]);
		}
		
		String[] ed = null;
		if (puero.endDate != null && !puero.endDate.isEmpty()) {
			ed = puero.endDate.split("T");
		}

		if (ed != null && ed.length > 0) {
			holder.endDate.setText(ed[0]);
		}

		holder.name.setText(puero.eventNote);
		holder.status.setText(puero.status);
		holder.location.setText(puero.location);
		holder.city.setText(puero.city);
		holder.state.setText(puero.state);
		//holder.webLink.setText(puero.webLink);
		
		
		return convertView;
	}

}
