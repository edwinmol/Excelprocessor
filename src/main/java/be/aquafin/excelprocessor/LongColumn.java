package be.aquafin.excelprocessor;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

public class LongColumn extends NumericColumn<Long> {

	public LongColumn(String name, String property, int sequence) {
		super(name, property,  d -> {return Math.round(d);}, l -> {return l.doubleValue();}, sequence);
	}

	@Override
	public CellStyle getStyle(Workbook wb) {
		return null;
	}

}
