package app.nichepro.fragmenttabdoctor.queries;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewAnimator;
import app.nichepro.HealthCareApp;
import app.nichepro.activities.healthcare.BaseFragment;
import app.nichepro.activities.healthcare.R;
import app.nichepro.animation.AnimationFactory;
import app.nichepro.animation.AnimationFactory.FlipDirection;
import app.nichepro.model.BaseResponseObject;
import app.nichepro.model.CommunicationListResponseObject;
import app.nichepro.model.CommunicationResponseObject;
import app.nichepro.model.ErrorResponseObject;
import app.nichepro.model.KeyValue;
import app.nichepro.model.MessageListResponseObject;
import app.nichepro.model.PartyListResponseObject;
import app.nichepro.model.PartyResponseObject;
import app.nichepro.model.PartySharingListResponseObject;
import app.nichepro.model.PartySharingResponseObject;
import app.nichepro.model.db.EmailMessage;
import app.nichepro.responsehandler.ResponseParserListener;
import app.nichepro.util.Constants;
import app.nichepro.util.EnumFactory.CommunicationTabs;
import app.nichepro.util.EnumFactory.ResponseMesssagType;
import app.nichepro.util.UIUtilities;
import app.nichepro.util.UpdateUiFromAdapterListener;
import app.nichepro.util.WebLinks;
import app.nichepro.util.WebRequestTask;

public class DoctorQueryFragment extends BaseFragment implements
		OnClickListener, ResponseParserListener, OnTouchListener,
		UpdateUiFromAdapterListener {

	private ArrayList<KeyValue> listOfDoctor;
	private ArrayList<String> doctorName;
	public PartyListResponseObject doctorListObject;
	protected ArrayList<CharSequence> selectedDoctors = new ArrayList<CharSequence>();

	private ArrayList<KeyValue> listOfPatient;
	private ArrayList<String> patientName;
	public PartySharingListResponseObject patientListObject;
	protected ArrayList<CharSequence> selectedPatient = new ArrayList<CharSequence>();
	Button btnInbox;
	Button btnCompose;
	Button btnOutBox;

	Boolean isToggle;
	String tDoctorName, tSubject, tMessage;
	Dialog progress;
	TextView toPatientName;
	TextView toDoctorName;
	LinearLayout inboxView;
	LinearLayout composeView;
	LinearLayout outboxView;
	EditText etToPatientName;
	EditText selectDoctorName;
	EditText etSubject;
	EditText etMessage;
	ViewAnimator viewAnimator;
	ArrayList<MessageListResponseObject> inboxMsgArray;
	ArrayList<MessageListResponseObject> outboxMsgArray;
	WebRequestTask ehrRequestTask;
	WebRequestTask patientRequestTask;

	private ResponseMesssagType msgType;
	private LayoutInflater mInflater;
	private ExpandableListView mMessageList;
	private ExpandableListView mOutBoxMessageList;

	HealthCareApp myApp;
	boolean isReply;
	private EmailMessage selectedMessage;
	DoctorEmailListAdapter listAdapter;
	private CommunicationTabs commTabs;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.patient_query_screen, container,
				false);
		commTabs = CommunicationTabs.Inbox;

		mInflater = inflater;
		viewAnimator = (ViewAnimator) view.findViewById(R.id.viewFlipper);
		myApp = (HealthCareApp) getActivity().getApplication();
		btnInbox = (Button) view.findViewById(R.id.msg_list);
		btnCompose = (Button) view.findViewById(R.id.send_msg);
		btnOutBox = (Button) view.findViewById(R.id.outbox);
		btnInbox.setSelected(true);
		btnInbox.setOnClickListener(this);
		btnCompose.setOnClickListener(this);
		btnOutBox.setOnClickListener(this);
		isToggle = true;
		btnInbox = (Button) view.findViewById(R.id.msg_list);
		btnCompose = (Button) view.findViewById(R.id.send_msg);
		btnOutBox = (Button) view.findViewById(R.id.outbox);

		inboxView = (LinearLayout) view.findViewById(R.id.patient_message_list);
		composeView = (LinearLayout) view.findViewById(R.id.patient_new_note);
		outboxView = (LinearLayout) view.findViewById(R.id.outbox_message_list);
		inboxView.setTag(0);
		composeView.setTag(1);
		outboxView.setTag(2);
		mOutBoxMessageList = (ExpandableListView) view
				.findViewById(R.id.outbox_communication);

		etSubject = (EditText) view.findViewById(R.id.etSubjectMessage);
		etMessage = (EditText) view.findViewById(R.id.etMessageCommunicate);
		Button send = (Button) view.findViewById(R.id.bSendCommunicate);

		mMessageList = (ExpandableListView) view
				.findViewById(R.id.patient_communication);

		send.setOnClickListener(this);
		selectDoctorName = (EditText) view.findViewById(R.id.bSelect);
		selectDoctorName.setVisibility(view.GONE);
		// selectDoctorName.setOnTouchListener(this);
		// selectDoctorName.setInputType(InputType.TYPE_NULL); // disable soft

		etToPatientName = (EditText) view.findViewById(R.id.bSelectPatient);
		etToPatientName.setOnTouchListener(this);
		etToPatientName.setInputType(InputType.TYPE_NULL); // disable soft//
															// input
		etToPatientName.setVisibility(View.VISIBLE);
		toPatientName = (TextView) view.findViewById(R.id.tSelectPatient);
		toPatientName.setVisibility(View.VISIBLE);

		toDoctorName = (TextView) view.findViewById(R.id.tSelectDoctor);
		toDoctorName.setVisibility(View.GONE);
		etSubject = (EditText) view.findViewById(R.id.etSubjectMessage);
		etMessage = (EditText) view.findViewById(R.id.etMessageCommunicate);

		requestForListMsg();
		// listener for child row click
		mMessageList.setOnChildClickListener(myListItemClicked);
		// listener for group heading click
		mMessageList.setOnGroupClickListener(myListGroupClicked);
		return view;
	}

	void createMessageList() {
		// if (inboxMsgArray != null && inboxMsgArray.size() > 0)
		{

			listAdapter = new DoctorEmailListAdapter(mInflater.getContext(),
					inboxMsgArray, this, true);

			mMessageList.setAdapter(listAdapter);
		}
	}

	void createOutBoxList() {

		// Creating the list adapter and populating the list
		// if (outboxMsgArray != null && outboxMsgArray.size() > 0)
		{

			listAdapter = new DoctorEmailListAdapter(mInflater.getContext(),
					outboxMsgArray, this, false);

			mOutBoxMessageList.setAdapter(listAdapter);
		}
	}

	public void sendMessage() {

		commTabs = CommunicationTabs.Compose;

		AnimationFactory.flipTransition(viewAnimator, FlipDirection.LEFT_RIGHT,
				composeView);
		btnInbox.setTextColor(Color.WHITE);
		btnCompose.setTextColor(Color.BLACK);
		btnOutBox.setTextColor(Color.WHITE);
		btnInbox.setSelected(false);
		btnCompose.setSelected(true);
		btnOutBox.setSelected(false);
		refreshEditfields();
		requestForPatient();

	}

	public void onChangeSelectedDoctors() {
		StringBuilder stringBuilder = new StringBuilder();

		for (CharSequence colour : selectedDoctors)
			stringBuilder.append(colour + ";");

		selectDoctorName.setText(stringBuilder.toString());
	}

	public void onChangeSelectedPatient() {
		StringBuilder stringBuilder = new StringBuilder();

		int count = listOfPatient.size();

		for (int i = 0; i < count; i++) {
			KeyValue kv = listOfPatient.get(i);
			if (selectedPatient.contains(kv.getKey())) {
				stringBuilder.append(kv.getValue() + ";");

			}
		}
		etToPatientName.setText(stringBuilder.toString());

	}

	public void showSelectDoctorDialog() {
		boolean[] checkedColours = new boolean[listOfDoctor.size()];
		int count = listOfDoctor.size();

		for (int i = 0; i < count; i++) {
			KeyValue kv = listOfDoctor.get(i);
			checkedColours[i] = selectedDoctors.contains(kv.getKey());
		}

		DialogInterface.OnMultiChoiceClickListener coloursDialogListener = new DialogInterface.OnMultiChoiceClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which,
					boolean isChecked) {
				KeyValue kv = listOfDoctor.get(which);
				if (isChecked) {

					selectedDoctors.add(kv.getKey());
				} else {
					selectedDoctors.remove(kv.getKey());
				}

				onChangeSelectedDoctors();
			}
		};

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Select Doctor");
		CharSequence s[] = doctorName.toArray(new CharSequence[doctorName
				.size()]);
		builder.setMultiChoiceItems(s, checkedColours, coloursDialogListener);
		builder.setPositiveButton("Select",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {

					}
				});

		AlertDialog dialog = builder.create();
		dialog.show();
	}

	public void showSelectPatientDialog() {
		boolean[] checkedColours = new boolean[listOfPatient.size()];
		int count = listOfPatient.size();

		for (int i = 0; i < count; i++) {
			KeyValue kv = listOfPatient.get(i);
			checkedColours[i] = selectedPatient.contains(kv.getKey());
		}

		DialogInterface.OnMultiChoiceClickListener coloursDialogListener = new DialogInterface.OnMultiChoiceClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which,
					boolean isChecked) {
				KeyValue kv = listOfPatient.get(which);
				if (isChecked) {

					selectedPatient.add(kv.getKey());
				} else {
					selectedPatient.remove(kv.getKey());
				}

				onChangeSelectedPatient();
			}
		};

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Select Patient");
		CharSequence s[] = patientName.toArray(new CharSequence[patientName
				.size()]);
		builder.setMultiChoiceItems(s, checkedColours, coloursDialogListener);
		builder.setPositiveButton("Select",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {

					}
				});

		AlertDialog dialog = builder.create();
		dialog.show();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.msg_list:
			if (commTabs != CommunicationTabs.Inbox) {
				commTabs = CommunicationTabs.Inbox;
				AnimationFactory.flipTransition(viewAnimator,
						FlipDirection.LEFT_RIGHT, inboxView);
				btnInbox.setTextColor(Color.BLACK);
				btnCompose.setTextColor(Color.WHITE);
				btnOutBox.setTextColor(Color.WHITE);
				btnInbox.setSelected(true);
				btnCompose.setSelected(false);
				btnOutBox.setSelected(false);
				requestForListMsg();
			}
			break;
		case R.id.send_msg:
			if (commTabs != CommunicationTabs.Compose) {

				sendMessage();
			}
			break;
		case R.id.outbox:
			if (commTabs != CommunicationTabs.Outbox) {

				commTabs = CommunicationTabs.Outbox;

				AnimationFactory.flipTransition(viewAnimator,
						FlipDirection.LEFT_RIGHT, outboxView);
				btnInbox.setTextColor(Color.WHITE);
				btnCompose.setTextColor(Color.WHITE);
				btnOutBox.setTextColor(Color.BLACK);
				btnInbox.setSelected(false);
				btnCompose.setSelected(false);
				btnOutBox.setSelected(true);
				requestForListMsg();
			}
			break;

		case R.id.bSendCommunicate: {
			requestForSendMsg();

		}
			break;
		}

	}

	@Override
	public void onEndParsingMsgType(ResponseMesssagType msgType) {
		// TODO Auto-generated method stub
		this.msgType = msgType;
	}

	@Override
	public void onEndParsingResponse(ArrayList<BaseResponseObject> items) {
		// TODO Auto-generated method stub
		if (this.msgType == ResponseMesssagType.DeleteCommunicationMessageType) {
			UIUtilities.showConfirmationAlert(getActivity(),
					R.string.dialog_title_information,
					"Your message has deleted successfully",
					R.string.dialog_button_Ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							requestForListMsg();
						}
					});
		} else if (this.msgType == ResponseMesssagType.CommunicationEventCreatedMessageType) {
			UIUtilities.showConfirmationAlert(getActivity(),
					R.string.dialog_title_information,
					"Your message has sent successfully",
					R.string.dialog_button_Ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							refreshEditfields();
						}
					});
		} else if (this.msgType == ResponseMesssagType.DoctorListMessageType) {
			listOfDoctor = new ArrayList<KeyValue>();
			doctorName = new ArrayList<String>();
			if (items != null && !items.isEmpty() && items.size() > 0) {
				this.doctorListObject = (PartyListResponseObject) items.get(0);
				for (PartyResponseObject msg : this.doctorListObject.party) {
					KeyValue kv = new KeyValue();
					kv.setKey(msg.partyId);
					if (msg.firstName != null) {
						kv.setValue(msg.firstName);

					} else
						kv.setValue("Name not found");
					doctorName.add(kv.getValue());

					listOfDoctor.add(kv);

				}

			}
		} else if (this.msgType == ResponseMesssagType.listCCRSharingMessageType) {
			if (items != null && !items.isEmpty() && items.size() > 0) {
				listOfPatient = new ArrayList<KeyValue>();
				patientName = new ArrayList<String>();
				this.patientListObject = (PartySharingListResponseObject) items
						.get(0);
				for (PartySharingResponseObject msg : this.patientListObject.CCRSharingList) {
					KeyValue kv = new KeyValue();
					kv.setKey(msg.partyId);
					if (msg.patientName != null) {
						kv.setValue(msg.patientName);

					} else
						kv.setValue("Name not found");
					patientName.add(kv.getValue());

					listOfPatient.add(kv);

					if (selectedMessage != null
							&& selectedMessage.PartyIdFrom != null
							&& !selectedMessage.PartyIdFrom.isEmpty()
							&& msg.partyId
									.compareTo(selectedMessage.PartyIdFrom) == 0) {

						if (isReply == true) {
							isReply = false;
							selectedPatient.add(kv.getKey());
							etSubject.setText(selectedMessage.SUBJECT);
							etToPatientName.setText(kv.getValue());
						}
					}

				}

			} else {
				// No patient found
				UIUtilities.showConfirmationAlert(getActivity(),
						R.string.dialog_title_information, "No Patient found",
						R.string.dialog_button_Ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

							}
						});
			}
		} else if (this.msgType == ResponseMesssagType.CommunicationEventListMessageType) {

			CommunicationListResponseObject clro = (CommunicationListResponseObject) items
					.get(0);
			if (items != null && !items.isEmpty() && items.size() > 0
					&& clro.CommunicationEventList.size() > 0) {
				boolean isInboxEmpty = true;
				boolean isOutboxEmpty = true;
				inboxMsgArray = new ArrayList<MessageListResponseObject>();
				outboxMsgArray = new ArrayList<MessageListResponseObject>();

				for (CommunicationResponseObject element : clro.CommunicationEventList) {
					if (element.sentFrom != null
							&& element.sentFrom.toString().compareTo("Patient") == 0)

					{
						isInboxEmpty = false;
						EmailMessage em = new EmailMessage();
						em.USERNAME = element.patientName;
						em.MESSAGE = element.content;
						em.SUBJECT = element.subject;
						em.PartyIdFrom = element.partyIdFrom;
						em.PartyIdTo = element.partyIdTo;
						em.communicationEventId = element.communicationEventId;

						if (inboxMsgArray.size() == 0) {
							MessageListResponseObject mlro = new MessageListResponseObject();
							mlro.msgList = new ArrayList<EmailMessage>();
							mlro.msgList.add(em);
							inboxMsgArray.add(mlro);
						} else {
							MessageListResponseObject mlro = inboxMsgArray
									.get(inboxMsgArray.size() - 1);
							EmailMessage msg = mlro.msgList.get(mlro.msgList
									.size() - 1);
							if (msg.getSUBJECT().toString()
									.compareTo(em.getSUBJECT().toString()) == 0) {
								mlro.msgList.add(em);
							} else {
								MessageListResponseObject mlro1 = new MessageListResponseObject();
								mlro1.msgList = new ArrayList<EmailMessage>();
								mlro1.msgList.add(em);
								inboxMsgArray.add(mlro1);
							}
						}
					} else {
						isOutboxEmpty = false;

						EmailMessage em = new EmailMessage();
						em.communicationEventId = element.communicationEventId;

						em.USERNAME = element.doctorName;
						em.MESSAGE = element.content;
						em.SUBJECT = element.subject;
						em.TIME = element.createdStamp;
						em.PartyIdFrom = element.partyIdFrom;
						em.PartyIdTo = element.partyIdTo;
						if (outboxMsgArray.size() == 0) {
							MessageListResponseObject mlro = new MessageListResponseObject();
							mlro.msgList = new ArrayList<EmailMessage>();
							mlro.msgList.add(em);
							outboxMsgArray.add(mlro);
						} else {
							MessageListResponseObject mlro = outboxMsgArray
									.get(outboxMsgArray.size() - 1);
							EmailMessage msg = mlro.msgList.get(mlro.msgList
									.size() - 1);
							if (msg.getSUBJECT().toString()
									.compareTo(em.getSUBJECT().toString()) == 0) {
								mlro.msgList.add(em);
							} else {
								MessageListResponseObject mlro1 = new MessageListResponseObject();
								mlro1.msgList = new ArrayList<EmailMessage>();
								mlro1.msgList.add(em);
								outboxMsgArray.add(mlro1);
							}
						}

					}

				}

				if (commTabs == CommunicationTabs.Inbox) {
					if (isInboxEmpty) {
						UIUtilities.showConfirmationAlert(getActivity(),
								R.string.dialog_title_information,
								R.string.empty_inbox,
								R.string.dialog_button_Ok,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										createMessageList();

									}
								});
					} else
						createMessageList();
				} else if (commTabs == CommunicationTabs.Outbox) {
					if (isOutboxEmpty) {
						UIUtilities.showConfirmationAlert(getActivity(),
								R.string.dialog_title_information,
								R.string.empty_outbox,
								R.string.dialog_button_Ok,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										createOutBoxList();

									}
								});
					} else
						createOutBoxList();
				}

			} else {
				if (commTabs == CommunicationTabs.Inbox) {
					UIUtilities.showConfirmationAlert(getActivity(),
							R.string.dialog_title_information,
							R.string.empty_inbox, R.string.dialog_button_Ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									createMessageList();

								}
							});
				} else if (commTabs == CommunicationTabs.Outbox) {
					UIUtilities.showConfirmationAlert(getActivity(),
							R.string.dialog_title_information,
							R.string.empty_outbox, R.string.dialog_button_Ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									createOutBoxList();

								}
							});
				}
			}
		} else if (this.msgType == ResponseMesssagType.ErrorResponseMessageType) {
			if (items != null && !items.isEmpty() && items.size() > 0) {
				ErrorResponseObject ero = (ErrorResponseObject) items.get(0);
				UIUtilities.showErrorWithOkButton(getActivity(),
						ero.getErrorText());
			} else
				UIUtilities.showServerError(getActivity());

		} else if (this.msgType == ResponseMesssagType.UnknownResponseMessageType) {
			UIUtilities.showServerError(getActivity());
		} else {
			UIUtilities.showServerError(getActivity());
		}

	}

	public void requestForSendMsg() {

		if (selectedPatient.size() == 0) {
			UIUtilities.showConfirmationAlert(getActivity(),
					R.string.dialog_title_information,
					"Please select a Patient", R.string.dialog_button_Ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {

						}
					});
		} else if (etSubject.getText().toString().length() == 0) {
			UIUtilities.showConfirmationAlert(getActivity(),
					R.string.dialog_title_information, "Please add Subject",
					R.string.dialog_button_Ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {

						}
					});
		} else if (etMessage.getText().toString().length() == 0) {
			UIUtilities.showConfirmationAlert(getActivity(),
					R.string.dialog_title_information, "Please add Message",
					R.string.dialog_button_Ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {

						}
					});
		} else {

			UIUtilities.hideKeyboard(getActivity());

			String ccString = null;
			int size = selectedPatient.size();
			if (size > 1) {

				for (int i = 1; i < size; i++) {
					if (i == 1) {
						ccString = selectedPatient.get(1).toString();
					} else
						ccString += ";" + selectedPatient.get(i).toString();
				}
			}

			ehrRequestTask = new WebRequestTask(
					WebLinks.getLink(WebLinks.CreateCommunication),
					getActivity(), this);

			HealthCareApp myApp = (HealthCareApp) getActivity()
					.getApplication();

			ehrRequestTask.AddParam(Constants.PARAM_partyIdFrom, myApp
					.getLoggedInUser().getPartyId());
			ehrRequestTask.AddParam(Constants.PARAM_partyIdTo, selectedPatient
					.get(0).toString());
			ehrRequestTask.AddParam(Constants.PARAM_subject, etSubject
					.getText().toString());
			ehrRequestTask.AddParam(Constants.PARAM_content, etMessage
					.getText().toString());

			if (ccString != null) {
				ehrRequestTask.AddParam(Constants.PARAM_ccString, ccString);

			}
			ehrRequestTask.execute();
		}
	}

	public void requestForListMsg() {
		ehrRequestTask = new WebRequestTask(
				WebLinks.getLink(WebLinks.ListCommunication), getActivity(),
				this);

		ehrRequestTask.AddParam(Constants.PARAM_partyIdTo, myApp
				.getLoggedInUser().getPartyId());

		ehrRequestTask.execute();
	}

	public void requestForPatient() {
		ehrRequestTask = new WebRequestTask(
				WebLinks.getLink(WebLinks.ListOfCCRSharing), getActivity(),
				this);
		ehrRequestTask.AddParam(Constants.PARAM_partyIdTo, myApp
				.getLoggedInUser().getPartyId());

		ehrRequestTask.execute();
	}

	@Override
	public void onErrorResponse(ArrayList<BaseResponseObject> items) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onError(Exception ex) {
		// TODO Auto-generated method stub
		UIUtilities.showErrorWithOkButton(getActivity(), ex.getMessage());

	}

	public ResponseMesssagType getMsgType() {
		return msgType;
	}

	public void setMsgType(ResponseMesssagType msgType) {
		this.msgType = msgType;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bSelect: {
			if (MotionEvent.ACTION_UP == event.getAction()) {
				showSelectDoctorDialog();
			}
			break;
		}
		case R.id.bSelectPatient: {
			if (MotionEvent.ACTION_UP == event.getAction()) {
				showSelectPatientDialog();
			}
			break;
		}
		default:
			break;
		}

		return false;
	}

	// method to expand all groups
	private void expandAll() {
		int count = listAdapter.getGroupCount();
		for (int i = 0; i < count; i++) {
			mMessageList.expandGroup(i);
		}
	}

	// method to collapse all groups
	private void collapseAll() {
		int count = listAdapter.getGroupCount();
		for (int i = 0; i < count; i++) {
			mMessageList.collapseGroup(i);
		}
	}

	// our child listener
	private OnChildClickListener myListItemClicked = new OnChildClickListener() {

		public boolean onChildClick(ExpandableListView parent, View v,
				int groupPosition, int childPosition, long id) {

			return false;
		}

	};

	// our group listener
	private OnGroupClickListener myListGroupClicked = new OnGroupClickListener() {

		public boolean onGroupClick(ExpandableListView parent, View v,
				int groupPosition, long id) {

			// get the group header

			return false;
		}

	};

	void refreshEditfields() {

		if (selectedPatient != null && selectedPatient.size() > 0) {
			selectedPatient.clear();
		}

		etSubject.setText("");
		etMessage.setText("");
		etToPatientName.setText("");
	}

	@Override
	public void updateUI(String message) {
		// TODO Auto-generated method stub
		int group = -1;
		int child = -1;
		if (message != null && !message.isEmpty()) {
			isReply = true;
			String token[] = message.split(",");
			group = Integer.parseInt(token[0]);
			child = Integer.parseInt(token[1]);
			MessageListResponseObject mlro = inboxMsgArray.get(group);
			EmailMessage msg = mlro.msgList.get(child);
			selectedMessage = msg;
		}
		sendMessage();
	}

	@Override
	public void updateUI(String message, int position) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateUI(int position) {
		// TODO Auto-generated method stub

	}

	public void deleteMessages() {
		AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

		alert.setTitle("Remove messages!");
		alert.setMessage("Press delete to remove selected messages. This is undo action & can not be un done after selection");
		alert.setPositiveButton("Delete",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						requestToDeleteMessages();
					}
				});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {

					}
				});

		alert.show();

	}

	public void requestToDeleteMessages() {
		if (this.commTabs == CommunicationTabs.Inbox) {
			String commId = null;
			for (MessageListResponseObject elementList : this.inboxMsgArray) {
				for (EmailMessage msg : elementList.msgList) {
					if (msg.isSelected) {
						if (commId == null) {
							commId = msg.communicationEventId;
						} else
							commId = commId + "," + msg.communicationEventId;
					}
				}
			}

			initiateDeleteRequest(commId);

		} else if (this.commTabs == CommunicationTabs.Outbox) {
			String commId = null;
			for (MessageListResponseObject elementList : this.outboxMsgArray) {
				for (EmailMessage msg : elementList.msgList) {
					if (msg.isSelected) {
						if (commId == null) {
							commId = msg.communicationEventId;
						} else
							commId = commId + "," + msg.communicationEventId;
					}
				}
			}
			initiateDeleteRequest(commId);
		}
	}

	public void initiateDeleteRequest(String commId) {

		if (commId != null) {
			ehrRequestTask = new WebRequestTask(
					WebLinks.getLink(WebLinks.DeleteCommunication),
					getActivity(), this);
			ehrRequestTask.AddParam(Constants.PARAM_communicationEventId,
					commId);
			ehrRequestTask.execute();
		} else {
			UIUtilities.showConfirmationAlert(getActivity(),
					R.string.dialog_title_information,
					"You have not selected any message!",
					R.string.dialog_button_Ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {

						}
					});
		}
	}

	public void myOnKeyDown(int key_code) {
		if (key_code == KeyEvent.KEYCODE_MENU) {
			deleteMessages();
		}
	}

}
