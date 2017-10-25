package be.aquafin.excelprocessor;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

public class DefaultStringColumn extends StringColumn<String> {

	public DefaultStringColumn(String name, String property, int sequence) {
		super(name, property, a -> a, a -> a, sequence);
	}

	@Override
	public CellStyle getStyle(Workbook wb) {
		return null;
	}

}
