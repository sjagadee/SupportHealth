package app.nichepro.fragmenttabpatient.ehr;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import app.nichepro.activities.healthcare.R;
import app.nichepro.model.PartyResponseObject;
import app.nichepro.util.UpdateUiFromAdapterListener;

public class DoctorListAdapter extends ArrayAdapter<PartyResponseObject> {
	Button shareEHR;
	Button sendMSG;
	LayoutInflater minflater;
	Context mContext;
	Activity mActivity;
	UpdateUiFromAdapterListener mUpdateUi;

	static class ViewHolder {

		/** The note view. */
		TextView lastNameText;

		/** The date name. */
		TextView firstNameText;

		TextView specializationText;

		Button shareBtn;

		/**
		 * Reset the content of cell to remove the flickering effect
		 */
		public void reset() {

		}
	}

	public DoctorListAdapter(Context context, LayoutInflater inflater,
			int textViewResourceId, UpdateUiFromAdapterListener updateUi) {
		super(context, textViewResourceId);
		minflater = inflater;
		mContext = context;
		mUpdateUi = updateUi;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {

			convertView = minflater.inflate(R.layout.doctor_list_view, null);
			holder = new ViewHolder();
			holder.lastNameText = ((TextView) convertView
					.findViewById(R.id.tvDoctorLastName));
			holder.firstNameText = ((TextView) convertView
					.findViewById(R.id.tvDoctorFirstName));

			holder.specializationText = ((TextView) convertView
					.findViewById(R.id.tvSpecialization));
			holder.shareBtn = (Button) convertView.findViewById(R.id.sharebtn);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		PartyResponseObject pro = getItem(position);

		holder.lastNameText.setText(pro.lastName);
		holder.firstNameText.setText(pro.firstName);

		holder.specializationText.setText(pro.specialization);
		if (pro.specialization != null && pro.specialization.isEmpty()) {
			holder.specializationText.setVisibility(View.GONE);
		}

		holder.shareBtn.setTag(pro.partyId);
		holder.shareBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String partyId = (String) v.getTag();
				mUpdateUi.updateUI(partyId);

			}
		});

		return convertView;
	}
}