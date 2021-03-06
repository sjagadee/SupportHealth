package app.nichepro.fragmenttabsponsor.request;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import app.nichepro.activities.healthcare.R;
import app.nichepro.model.SponsorShipResponseObject;
import app.nichepro.util.UpdateUiFromAdapterListener;

public class SponsorRequestAdapter extends
		ArrayAdapter<SponsorShipResponseObject> {
	private Button shareEHR;
	private Button sendMSG;
	private LayoutInflater minflater;
	private Context mContext;
	private String screenName;
	private UpdateUiFromAdapterListener mUpdateUi;

	static class ViewHolder {

		/** The note view. */
		TextView patientName;

		/** The date name. */
		TextView requestDate;

		/** The date name. */
		TextView cancerType;

		TextView contactNumber;

		Button shareEHRBtn;
		Button viewRequestBtn;
		Button approveBtn;
		Button rejectBtn;

		/**
		 * Reset the content of cell to remove the flickering effect
		 */
		public void reset() {

		}
	}

	public SponsorRequestAdapter(Context context, LayoutInflater inflater,
			int textViewResourceId, UpdateUiFromAdapterListener updateUi,
			String screenname) {
		super(context, textViewResourceId);
		minflater = inflater;
		mContext = context;
		mUpdateUi = updateUi;
		screenName = screenname;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;

		if (convertView == null) {

			convertView = minflater.inflate(R.layout.sponsor_request_cell_view,
					null);
			holder = new ViewHolder();
			holder.patientName = ((TextView) convertView
					.findViewById(R.id.tvName));
			holder.requestDate = ((TextView) convertView
					.findViewById(R.id.tvDate));
			holder.cancerType = ((TextView) convertView
					.findViewById(R.id.tvCancerType));
			holder.contactNumber = ((TextView) convertView
					.findViewById(R.id.tvMobileNo));
			holder.shareEHRBtn = (Button) convertView
					.findViewById(R.id.viewEhrbtn);
			holder.viewRequestBtn = (Button) convertView
					.findViewById(R.id.viewRequestbtn);
			holder.approveBtn = (Button) convertView
					.findViewById(R.id.approvedbtn);
			holder.rejectBtn = (Button) convertView
					.findViewById(R.id.rejectbtn);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		SponsorShipResponseObject pro = getItem(position);
		holder.patientName.setText(pro.partyName);
		holder.requestDate.setText(pro.createdDate);

		holder.shareEHRBtn.setTag(position);
		holder.viewRequestBtn.setTag(position);
		
		if (this.screenName != null
				&& screenName.length() > 0
				&& (this.screenName.compareTo("Approved") == 0 || this.screenName
						.compareTo("Rejected") == 0)) {

			holder.approveBtn.setVisibility(View.GONE);
			holder.rejectBtn.setVisibility(View.GONE);

		} else {
			holder.approveBtn.setTag(position);
			holder.rejectBtn.setTag(position);
			holder.approveBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int pos = (Integer) holder.approveBtn.getTag();
					mUpdateUi.updateUI("Approve",pos);
				}
			});

			holder.rejectBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int pos = (Integer) holder.rejectBtn.getTag();
					mUpdateUi.updateUI("Rejected",pos);
				}
			});
		}

		holder.shareEHRBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int pos = (Integer) holder.shareEHRBtn.getTag();
				mUpdateUi.updateUI("EHR", pos);
			}
		});

		holder.viewRequestBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int pos = (Integer) holder.viewRequestBtn.getTag();
				mUpdateUi.updateUI("ViewRequest", pos);
			}
		});

		return convertView;
	}
}
