package be.siteware.excelprocessor;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

public class LongColumn extends NumericConverterColumn<Long> {

	public LongColumn(String name, String property) {
		super(name, property,  d -> {return Math.round(d);}, l -> {return l.doubleValue();}, 0);
	}

	@Override
	public CellStyle getStyle(Workbook wb) {
		return null;
	}

}
