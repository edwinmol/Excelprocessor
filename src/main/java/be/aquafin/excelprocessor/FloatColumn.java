package be.aquafin.excelprocessor;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

public class FloatColumn extends NumericColumn<Float> {

	public FloatColumn(String name, String property, int sequence) {
		super(name, 
				property,  
				d -> d.floatValue(), 
				f -> f.doubleValue(), 
				sequence);
	}

	@Override
	public CellStyle getStyle(Workbook wb) {
		return null;
	}

}
