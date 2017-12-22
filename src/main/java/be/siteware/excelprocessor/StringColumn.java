package be.siteware.excelprocessor;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

public class StringColumn extends StringConverterColumn<String> {

	public StringColumn(String name, String property) {
		super(name, property, a -> a, a -> a, 0);
	}

	@Override
	public CellStyle getStyle(Workbook wb) {
		return null;
	}

}
