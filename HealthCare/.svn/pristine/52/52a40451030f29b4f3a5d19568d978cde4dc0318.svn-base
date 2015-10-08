
package app.nichepro.util;


public class ServerErrorException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5262486634724919901L;
	public static int code = -1;

	/**
	 * 
	 */
	public ServerErrorException() {
	}
	
	public ServerErrorException(String msg){
		super(msg);
		code = 100;
	}
	
	public ServerErrorException(String msg, int code){
		super(msg);
		ServerErrorException.code = code;
	}

}
