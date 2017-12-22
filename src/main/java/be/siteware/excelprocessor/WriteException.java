package be.siteware.excelprocessor;

public class WriteException extends Exception {

	public WriteException(String string, Exception e) {
		super(string, e);
	}

}
