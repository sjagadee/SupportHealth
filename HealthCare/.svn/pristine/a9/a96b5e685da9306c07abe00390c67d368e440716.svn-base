package app.nichepro.fragmenttabdoctor.queries;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import app.nichepro.activities.healthcare.R;
import app.nichepro.model.MessageListResponseObject;
import app.nichepro.model.db.EmailMessage;
import app.nichepro.util.UpdateUiFromAdapterListener;

public class DoctorEmailListAdapter extends BaseExpandableListAdapter {

	Context mContext;
	ArrayList<MessageListResponseObject> eMessageList;
	UpdateUiFromAdapterListener mUpdateUI;
	boolean isInbox;

	private static class ChildViewHolder {
		private CheckBox cBox;

		public CheckBox getcBox() {
			return cBox;
		}

		public TextView getUserName() {
			return userName;
		}

		public TextView getMessage() {
			return message;
		}

		public Button getReplyBtn() {
			return replyBtn;
		}

		private TextView userName;
		private TextView message;
		private Button replyBtn;
		private TextView fromView;

		public TextView getFromView() {
			return fromView;
		}

		public void setFromView(TextView fromView) {
			this.fromView = fromView;
		}

		public ChildViewHolder(TextView user, CheckBox checkBox, TextView msg,
				Button btn, TextView from) {
			this.cBox = checkBox;
			this.userName = user;
			this.message = msg;
			this.replyBtn = btn;
			this.fromView = from;
		}

	}

	static class ParentViewHolder {

		/** The note view. */
		TextView userName;

		TextView subject;

		TextView quantity;
		TextView from;

		public void reset() {

		}
	}

	public DoctorEmailListAdapter(Context context,
			ArrayList<MessageListResponseObject> message,
			UpdateUiFromAdapterListener updateUI, boolean inbox) {
		eMessageList = message;
		mContext = context;
		mUpdateUI = updateUI;
		isInbox = inbox;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		MessageListResponseObject msgList = eMessageList.get(groupPosition);
		return msgList.msgList.get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		EmailMessage emsg = (EmailMessage) getChild(groupPosition,
				childPosition);

		TextView user;
		CheckBox cBox;
		TextView msg;
		Button rBtn;
		TextView from;

		ChildViewHolder holder;

		if (convertView == null) {

			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(
					R.layout.email_message_list_child_cell, null);

			cBox = ((CheckBox) convertView.findViewById(R.id.chkBox));
			user = ((TextView) convertView.findViewById(R.id.userName));
			msg = (TextView) convertView.findViewById(R.id.emailTxt);
			rBtn = (Button) convertView.findViewById(R.id.bSendEmail);
			from = (TextView) convertView.findViewById(R.id.from);

			convertView
					.setTag(new ChildViewHolder(user, cBox, msg, rBtn, from));
			cBox.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					CheckBox cb = (CheckBox) v;
					EmailMessage planet = (EmailMessage) cb.getTag();
					planet.setSelected(cb.isChecked());
				}
			});
		} else {
			holder = (ChildViewHolder) convertView.getTag();
			user = holder.getUserName();
			cBox = holder.getcBox();
			msg = holder.getMessage();
			rBtn = holder.getReplyBtn();
			from = holder.getFromView();
		}

		if (emsg.getUSERNAME() != null && emsg.getUSERNAME().length() > 0) {
			user.setText(emsg.getUSERNAME().toString());
		}

		if (emsg.getMESSAGE() != null && emsg.getMESSAGE().length() > 0) {
			msg.setText(emsg.getMESSAGE().toString());
		}

		if (isInbox) {
			rBtn.setTag(groupPosition + "," + childPosition);
			rBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String str = (String) v.getTag();
					mUpdateUI.updateUI(str);

				}
			});
		} else {
			// Outbox
			rBtn.setVisibility(View.GONE);
			from.setText("To");
		}
		cBox.setTag(emsg);
		cBox.setChecked(emsg.isSelected());

		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {

		MessageListResponseObject em = eMessageList.get(groupPosition);
		int count = em.msgList.size();
		return count;

	}

	@Override
	public Object getGroup(int groupPosition) {
		return eMessageList.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		if (eMessageList != null) {
			return eMessageList.size();
		} else
			return 0;

	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isLastChild,
			View convertView, ViewGroup parent) {

		ParentViewHolder holder;

		if (convertView == null) {

			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.email_message_list_cell,
					null);
			holder = new ParentViewHolder();
			holder.userName = ((TextView) convertView.findViewById(R.id.user));
			holder.subject = ((TextView) convertView.findViewById(R.id.subject));
			holder.quantity = ((TextView) convertView.findViewById(R.id.qty));
			holder.from = ((TextView) convertView.findViewById(R.id.from));

			convertView.setTag(holder);
		} else {
			holder = (ParentViewHolder) convertView.getTag();
		}

		MessageListResponseObject msg = eMessageList.get(groupPosition);
		EmailMessage emsg = msg.msgList.get(0);

		if (emsg.getUSERNAME() != null && emsg.getUSERNAME().length() > 0) {
			holder.userName.setText(emsg.getUSERNAME().toString());
		}

		if (emsg.getSUBJECT() != null && emsg.getSUBJECT().length() > 0) {
			holder.subject.setText(emsg.getSUBJECT().toString());
		}

		holder.quantity.setText(msg.msgList.size() + "");
		if (msg.msgList.size() > 0) {
			holder.quantity.setVisibility(View.VISIBLE);
			holder.quantity.setText(msg.msgList.size() + "");

		}

		if (isInbox == false) {
			holder.from.setText("To");
		}

		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
}