package app.nichepro.fragmenttabpatient.ehr;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewAnimator;
import app.nichepro.activities.healthcare.BaseFragment;
import app.nichepro.activities.healthcare.R;
import app.nichepro.animation.AnimationFactory;
import app.nichepro.animation.AnimationFactory.FlipDirection;
import app.nichepro.model.PatientMedicationListResponseObject;
import app.nichepro.model.PatientMedicationResponseObject;
import app.nichepro.util.UpdateUiFromAdapterListener;

public class PatientEhrMedicationFragment extends BaseFragment implements
		OnClickListener, UpdateUiFromAdapterListener {
	Boolean isToggle;
	Button btn_current_medication, btn_previous_medication;
	public boolean isFromDoctor;
	public String patientId;
	ViewAnimator viewAnimator;
	PatientMedicationListResponseObject medicationListObject;
	private ArrayAdapter<PatientMedicationResponseObject> mCurrentlistAdapter;
	private ArrayAdapter<PatientMedicationResponseObject> mPastlistAdapter;
	private ListView mCurrentMedicationListView;
	private ListView mPastMedicationListView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.patient_ehr_medication,
				container, false);

		if (medicationListObject != null && medicationListObject.MEDICATION_LIST.size() > 0) {
			PatientMedicationResponseObject paro = medicationListObject.MEDICATION_LIST.get(0);
			TextView nameTxt = (TextView)view.findViewById(R.id.patientName);
			nameTxt.setText(paro.partyName);
		}
		
		mCurrentMedicationListView = (ListView) view
				.findViewById(R.id.currentmedicationlist);
		mPastMedicationListView = (ListView) view
				.findViewById(R.id.previousmedicationlist);

		// Creating the list adapter and populating the list
		mCurrentlistAdapter = new PatientMedicationListAdapter(
				inflater.getContext(), inflater, R.layout.medication_list_cell,
				this, isFromDoctor);

		// Creating the list adapter and populating the list
		mPastlistAdapter = new PatientMedicationListAdapter(
				inflater.getContext(), inflater,
				R.layout.previous_medication_list_cell, this, true);

		isToggle = true;

		viewAnimator = (ViewAnimator) view.findViewById(R.id.viewFlipper);

		btn_current_medication = (Button) view
				.findViewById(R.id.bCurrentMedication);
		btn_previous_medication = (Button) view
				.findViewById(R.id.bPreviousMedication);
		btn_current_medication.setSelected(true);
		btn_current_medication.setOnClickListener(this);
		btn_previous_medication.setOnClickListener(this);
		initializeCurrentMedicationList();

		return view;
	}

	void initializeCurrentMedicationList() {

		for (PatientMedicationResponseObject element : medicationListObject.MEDICATION_LIST) {
			if (element.status.compareTo("Active") == 0) {
				mCurrentlistAdapter.add(element);	
			}else{
				mPastlistAdapter.add(element);
			}
		}
		mCurrentMedicationListView.setAdapter(mCurrentlistAdapter);
		mPastMedicationListView.setAdapter(mPastlistAdapter);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.bCurrentMedication:
			if (isToggle == false) {
				AnimationFactory.flipTransition(viewAnimator,
						FlipDirection.LEFT_RIGHT);
				btn_current_medication.setTextColor(Color.BLACK);
				btn_previous_medication.setTextColor(Color.WHITE);
				btn_current_medication.setSelected(true);
				btn_previous_medication.setSelected(false);
			}
			isToggle = true;
			break;

		case R.id.bPreviousMedication:
			if (isToggle == true) {
				AnimationFactory.flipTransition(viewAnimator,
						FlipDirection.LEFT_RIGHT);
				btn_previous_medication.setTextColor(Color.BLACK);
				btn_current_medication.setTextColor(Color.WHITE);
				btn_previous_medication.setSelected(true);
				btn_current_medication.setSelected(false);
			}
			isToggle = false;
			break;
		}

	}

	@Override
	public void updateUI(String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateUI(int position) {

		// TODO Auto-generated method stub
		if (isFromDoctor) {
			// Do nothing

		} else {
			mActivity.pushFragments(app.nichepro.util.Constants.TAB_EHR,
					new CreateAlarmFragment(), true, true);

		}
	}

	@Override
	public void updateUI(String message, int position) {
		// TODO Auto-generated method stub
		
	}

}
