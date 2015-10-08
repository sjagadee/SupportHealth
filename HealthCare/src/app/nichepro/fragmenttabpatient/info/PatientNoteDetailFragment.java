package app.nichepro.fragmenttabpatient.info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import app.nichepro.activities.healthcare.BaseFragment;
import app.nichepro.activities.healthcare.R;
import app.nichepro.model.PatientNoteResponseObject;

public class PatientNoteDetailFragment extends BaseFragment  {
	
	public PatientNoteResponseObject mNoteObject;


	@Override
	public View onCreateView(final LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.patient_note_detail_screen,
				container, false);
		

		if (mNoteObject != null) {
			TextView noteTitle = (TextView)view.findViewById(R.id.noteTitle);
			TextView noteDetail = (TextView)view.findViewById(R.id.noteDetail);
			noteTitle.setText(mNoteObject.noteName);
			noteDetail.setText(mNoteObject.noteInfo);
		}


		return view;

	}


}
