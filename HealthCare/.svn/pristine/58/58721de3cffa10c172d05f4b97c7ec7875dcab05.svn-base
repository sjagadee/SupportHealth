package app.nichepro.fragmenttabpatient.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import app.nichepro.activities.healthcare.BaseFragment;
import app.nichepro.activities.healthcare.R;

public class PatientHomeFirstScreenFragment extends BaseFragment implements
		OnClickListener {

	TextView diagnoseCancer, cancerCheckup, visitHospital;
	CheckBox yes1, yes2, no1, no2;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.patient_home_first_screen,
				container, false);

		diagnoseCancer = (TextView) view.findViewById(R.id.tvDiagnoseCancer);
		cancerCheckup = (TextView) view.findViewById(R.id.tvCancerCheckup);
		visitHospital = (TextView) view.findViewById(R.id.tvVisitHospital);
		yes1 = (CheckBox) view.findViewById(R.id.cbYes1);
		no1 = (CheckBox) view.findViewById(R.id.cbNo1);
		yes2 = (CheckBox) view.findViewById(R.id.cbYes2);
		no2 = (CheckBox) view.findViewById(R.id.cbNo2);
		yes1.setOnClickListener(this);
		no1.setOnClickListener(this);
		yes2.setOnClickListener(this);
		no2.setOnClickListener(this);

		diagnoseCancer.setVisibility(View.VISIBLE);
		yes1.setVisibility(View.VISIBLE);
		no1.setVisibility(View.VISIBLE);
		return view;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.cbYes1:

			break;
		case R.id.cbNo1:
			cancerCheckup.setVisibility(View.VISIBLE);
			yes2.setVisibility(View.VISIBLE);
			no2.setVisibility(View.VISIBLE);
			break;
		case R.id.cbYes2:

			break;
		case R.id.cbNo2:
			visitHospital.setVisibility(View.VISIBLE);
			break;
		}
	}
}
