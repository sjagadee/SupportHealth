package app.nichepro.util;

public interface UpdateUiFromAdapterListener {

	/**
	 * On end parsing msg type.
	 * 
	 * @param msgType
	 *            the msg type
	 */
	void updateUI(String message);

	void updateUI(String message, int position);

	void updateUI(int position);

}
