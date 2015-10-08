package app.nichepro.fragmenttabpatient.info;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import app.nichepro.activities.healthcare.R;
import app.nichepro.model.SymtomResponseObject;
import app.nichepro.util.UpdateUiFromAdapterListener;

public class SymptomListAdapter extends ArrayAdapter<SymtomResponseObject> {
	LayoutInflater minflater;
	Context mContext;
	boolean isCheckBoxOff;
	UpdateUiFromAdapterListener mUpdate;

	/** Holds child views for one row. */
	private static class ViewHolder {
		private CheckBox checkBox;
		private TextView textView;
		private TextView dateView;

		public TextView getDateView() {
			return dateView;
		}

		public TextView getDescView() {
			return descView;
		}

		public TextView getSeverityView() {
			return severityView;
		}

		public LinearLayout getDescLayout() {
			return descLayout;
		}

		public LinearLayout getSeverityLayout() {
			return severityLayout;
		}

		public void setTextView(TextView textView) {
			this.textView = textView;
		}

		private TextView descView;
		private TextView severityView;
		private LinearLayout descLayout;
		private LinearLayout severityLayout;

		public ViewHolder(TextView textView, CheckBox checkBox, TextView date,
				TextView desc, TextView severity, LinearLayout descL,
				LinearLayout seveL) {
			this.checkBox = checkBox;
			this.textView = textView;
			this.dateView = date;
			this.descView = desc;
			this.severityView = severity;
			this.descLayout = descL;
			this.severityLayout = seveL;
		}

		public CheckBox getCheckBox() {
			return checkBox;
		}

		public TextView getTextView() {
			return textView;
		}

	}

	public SymptomListAdapter(Context context, LayoutInflater inflater,
			int textViewResourceId, boolean off,
			UpdateUiFromAdapterListener update) {
		super(context, textViewResourceId);
		minflater = inflater;
		mContext = context;
		isCheckBoxOff = off;
		mUpdate = update;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		SymtomResponseObject value = getItem(position);

		// The child views in each row.
		CheckBox checkBox;
		TextView textView;
		TextView dateView;
		TextView descView;
		TextView severityView;
		LinearLayout descLayout;
		LinearLayout severityLayout;
		// Create a new row view
		if (convertView == null) {
			convertView = minflater.inflate(R.layout.symptom_list_cell, null);

			// Find the child views.
			textView = (TextView) convertView.findViewById(R.id.txSymtom);
			checkBox = (CheckBox) convertView.findViewById(R.id.chkBox);
			dateView = (TextView) convertView.findViewById(R.id.txSymtomDate);
			descView = (TextView) convertView.findViewById(R.id.trackDesc);
			severityView = (TextView) convertView
					.findViewById(R.id.severitylevel);
			descLayout = (LinearLayout) convertView
					.findViewById(R.id.severitylayout);
			severityLayout = (LinearLayout) convertView
					.findViewById(R.id.desclayout);

			// Optimization: Tag the row with it's child views, so we don't have
			// to
			// call findViewById() later when we reuse the row.
			convertView.setTag(new ViewHolder(textView, checkBox, dateView,
					descView, severityView, descLayout, severityLayout));

			// If CheckBox is toggled, update the planet it is tagged with.
			checkBox.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					CheckBox cb = (CheckBox) v;

					int pos = (Integer) cb.getTag();
					SymtomResponseObject planet = (SymtomResponseObject) getItem(pos);
					planet.setSelected(cb.isChecked());
					if (cb.isChecked()) {
						mUpdate.updateUI(pos);
					}
				}
			});
		}
		// Reuse existing row view
		else {
			// Because we use a ViewHolder, we avoid having to call
			// findViewById().
			ViewHolder viewHolder = (ViewHolder) convertView.getTag();
			checkBox = viewHolder.getCheckBox();
			textView = viewHolder.getTextView();
			dateView = viewHolder.getDateView();
			descView = viewHolder.getDescView();
			severityView = viewHolder.getSeverityView();
			descLayout = viewHolder.getDescLayout();
			severityLayout = viewHolder.getSeverityLayout();
		}

		// Tag the CheckBox with the Planet it is displaying, so that we can
		// access the planet in onClick() when the CheckBox is toggled.
		if (isCheckBoxOff) {
			checkBox.setVisibility(View.GONE);
			dateView.setVisibility(View.VISIBLE);
			String date = value.createdStamp;
			String d[] = date.split("T");
			if (d != null && d.length > 0) {
				dateView.setText(d[0]);
			}

			descView.setText(value.trackDescription);
			severityView.setText(value.severityLevel);

		} else {
			checkBox.setTag(position);
			descLayout.setVisibility(View.GONE);
			severityLayout.setVisibility(View.GONE);
		}

		// Display planet data
		checkBox.setChecked(value.isSelected());
		textView.setText(value.description);

		return convertView;
	}
}
