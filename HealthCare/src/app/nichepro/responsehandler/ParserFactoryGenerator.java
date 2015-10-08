package app.nichepro.responsehandler;

import app.nichepro.util.EnumFactory.ResponseMesssagType;

public class ParserFactoryGenerator {
	public static BaseParser createParser(ResponseMesssagType msgType) {
		switch (msgType) {
		case LoginMessageType:
			return new LoginParser();
		case UserRegistrationMessageType:
			return new RegistrationParser();
		case ErrorResponseMessageType:
			return new ErrorParser();
		case PatientLoginMessageType:
			return new AddPatientNoteParser();
		case PatientNoteListMessageType:
			return new ListPatientNoteParser();
		case MedicationListMessageType:
			return new PatientMedicationListParser();
		case PatientNoteMessageType:
			return new PatientNoteParser();
		case DoctorListMessageType:
			return new PartyListPareser();
		case HospitalListMessageType:
			return new HospitalListPareser();
		case DiagnosisListMessageType:
			return new PatientDiagnosisListParser();
		case VitalStatisticsMessageType:
			return new PatientVitalStatListParser();
		case UpdateSponsorRequestListMessageType:
		case PatientSymtomsCreatedMessageType:
		case PatientSymtomsUpdatedMessageType:
		case PatientAppointmentListMessageType:
		case AppointmentProcedureListMessageType:
		case AppointmentMedicationListMessageType:
		case AppointmentDiagnosisListMessageType:
		case AppointmentVitatStatListMessageType:
		case AppointmentDoctorListMessageType:
			return new AppointmentListParser();
		case CommunicationEventListMessageType:
			return new CommunicationListParser();
		case PatientListMessageType:
			return new PatientListPareser();
		case PasswordSentMessageType:
		case DeleteNoteMessageType:
		case DeleteCommunicationMessageType:
		case ProfileUpdateMessageType:
		case CommunicationEventCreatedMessageType:
		case PushCCREventListMessageType:
		case JoinUsRequestMessageType:
		case RequstForSponsor:
			return new CommunicationSendParser();
		case UpcomingEventsMessageType:
		case PastEventsMessageType:
			return new PatientUpEventsListParser();
		case listCCRSharingMessageType:
			return new PartySharingListParser();
		case AllergiesListMessageType:
			return new AllergiesListParser();
		case ListOfHtmlPageMessageType:
			return new HtmlLinkParser();
		case SponsorshipListMessageType:
			return new SponsorRequestListParser();
		case SymtomsListMessageType:
			return new SymtomsRequestListParser();
		case PatientSymtomsListMessageType:
			return new SymtomsRequestPatientListParser();
		default:
			return new UnknownParser();
		}
	}
}
