package be.aquafin.excelprocessor;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

public class StringColumn<T> extends Column<T, String> {

	public StringColumn(String name, String property, PropertyConverter<T, String> read, PropertyConverter<String, T> write, int sequence) {
		super(name, property, read, write, sequence);
	}

	@Override
	public CellStyle getStyle(Workbook wb) {
		return null;
	}

}
