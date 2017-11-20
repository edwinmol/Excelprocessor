package be.aquafin.excelprocessor;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

public class BooleanConverterColumn<T> extends Column<T, Boolean> {


	public BooleanConverterColumn(String name, String property, PropertyConverter<T, Boolean> read, PropertyConverter<Boolean, T> write, int sequence) {
		super(name, property, read, write, sequence);
	}

	public BooleanConverterColumn(String name, String property, PropertyConverter<T, Boolean> read, PropertyConverter<Boolean, T> write) {
		super(name, property, read, write, 0);
	}

	@Override
	public CellStyle getStyle(Workbook wb) {
		return null;
	}

}
