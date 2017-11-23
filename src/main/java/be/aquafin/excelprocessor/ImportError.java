package be.aquafin.excelprocessor;

public class ImportError {

	public static enum Type {
		WORKSHEET_NOT_FOUND,
		HEADER_NOT_FOUND,
		COLUMN_NOT_FOUND,
		ERROR_CREATING_BEAN,
		GET_PROPERTY_FAILED
	}
	
	private Type type;
	private Column<?, ?> column;
	private Integer lineNumber;
	private String message;
	
	public ImportError(Type type, Column<?, ?> column, Integer lineNumber, String message) {
		super();
		this.type = type;
		this.column = column;
		this.lineNumber = lineNumber;
		this.message = message;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Column<?, ?> getColumn() {
		return column;
	}

	public void setColumn(Column<?, ?> column) {
		this.column = column;
	}

	public Integer getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(Integer lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ImportError [type=" + type + ", column=" + column + ", lineNumber=" + lineNumber + ", message="
				+ message + "]";
	}


	

}
