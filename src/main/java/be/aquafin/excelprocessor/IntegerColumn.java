package be.aquafin.excelprocessor;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

public class IntegerColumn extends NumericColumn<Integer> {

	public IntegerColumn(String name, String property, int sequence) {
		super(name, 
				property,  
				d -> (int)Math.round(d), //TODO: add a check if the value exceeds the MAX_INT value 
				i -> i.doubleValue(), 
				sequence);
	}

	@Override
	public CellStyle getStyle(Workbook wb) {
		return null;
	}

}
