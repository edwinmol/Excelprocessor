package be.aquafin.excelprocessor;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

public class FloatColumn extends NumericConverterColumn<Float> {

	public FloatColumn(String name, String property) {
		super(name, 
				property,  
				d -> d.floatValue(), 
				f -> f.doubleValue(), 
				0);
	}

	@Override
	public CellStyle getStyle(Workbook wb) {
		return null;
	}

}
