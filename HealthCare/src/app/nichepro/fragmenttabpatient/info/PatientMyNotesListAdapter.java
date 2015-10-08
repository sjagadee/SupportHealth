package app.nichepro.fragmenttabpatient.info;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import app.nichepro.activities.healthcare.R;
import app.nichepro.model.PatientNoteResponseObject;

public class PatientMyNotesListAdapter extends
		ArrayAdapter<PatientNoteResponseObject> {
	LayoutInflater minflater;

	/**
	 * The Class ViewHolder. Using ViewHolder pattern for optimizing custom cell
	 * in list activity This is done for optimizing and reusing the row, when
	 * user does scrolling and on the list view we don't want to call
	 * findviewbyid for every cell again and again
	 */

	private static class ViewHolder {
		private CheckBox checkBox;

		public CheckBox getCheckBox() {
			return checkBox;
		}

		public TextView getNoteText() {
			return noteText;
		}

		public TextView getDateText() {
			return dateText;
		}

		private TextView noteText;
		private TextView dateText;

		public ViewHolder(TextView textView, CheckBox checkBox, TextView date) {
			this.checkBox = checkBox;
			this.noteText = textView;
			this.dateText = date;
		}

	}

	public PatientMyNotesListAdapter(Context context, LayoutInflater inflater,
			int textViewResourceId) {
		super(context, textViewResourceId);
		minflater = inflater;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		CheckBox checkBox;
		TextView note;
		TextView date;

		// Create a new row view
		if (convertView == null) {
			convertView = minflater.inflate(R.layout.patient_home_list_cell,
					null);

			// Find the child views.
			note = (TextView) convertView.findViewById(R.id.note);
			checkBox = (CheckBox) convertView.findViewById(R.id.chkBox);
			date = (TextView) convertView.findViewById(R.id.date);

			// Optimization: Tag the row with it's child views, so we don't have
			// to
			// call findViewById() later when we reuse the row.
			convertView.setTag(new ViewHolder(note, checkBox, date));

			// If CheckBox is toggled, update the planet it is tagged with.
			checkBox.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					CheckBox cb = (CheckBox) v;
					PatientNoteResponseObject planet = (PatientNoteResponseObject) cb
							.getTag();
					planet.setSelected(cb.isChecked());
				}
			});
		}
		// Reuse existing row view
		else {
			// Because we use a ViewHolder, we avoid having to call
			// findViewById().
			ViewHolder viewHolder = (ViewHolder) convertView.getTag();
			checkBox = viewHolder.getCheckBox();
			note = viewHolder.getNoteText();
			date = viewHolder.getDateText();
		}
		PatientNoteResponseObject pnro = getItem(position);
		String[] d = null;
		if (pnro.noteDateTime != null && !pnro.noteDateTime.isEmpty()) {
			d = pnro.noteDateTime.split("T");
		}

		note.setText(pnro.noteName);
		if (d != null && d.length > 0) {
			date.setText(d[0]);
		}

		checkBox.setTag(pnro);
		checkBox.setChecked(pnro.isSelected());

		return convertView;
	}
}