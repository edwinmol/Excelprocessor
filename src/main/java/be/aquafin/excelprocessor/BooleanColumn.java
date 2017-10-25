package be.aquafin.excelprocessor;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

public class BooleanColumn<T> extends Column<T, Boolean> {


	public BooleanColumn(String name, String property, PropertyConverter<T, Boolean> read,  PropertyConverter<Boolean, T> write, int sequence) {
		super(name, property, read, write, sequence);
	}

	@Override
	public CellStyle getStyle(Workbook wb) {
		return null;
	}

}
