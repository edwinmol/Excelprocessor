package be.siteware.excelprocessor;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

public class StringConverterColumn<T> extends Column<T, String> {

	public StringConverterColumn(String name, String property, PropertyConverter<T, String> read, PropertyConverter<String, T> write, int sequence) {
		super(name, property, read, write, sequence);
	}


	public StringConverterColumn(String name, String property, PropertyConverter<T, String> read, PropertyConverter<String, T> write) {
		super(name, property, read, write, 0);
	}

	@Override
	public CellStyle getStyle(Workbook wb) {
		return null;
	}

}
