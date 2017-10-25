package be.aquafin.excelprocessor;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Workbook;

public class PercentageColumn extends NumericColumn<Float> {

	private CellStyle style;
	
	public PercentageColumn(String name, String property, int sequence) {
		super(name, 
				property,  
				d -> d.floatValue(), 
				f -> f.doubleValue(), 
				sequence);
	}

	@Override
	public CellStyle getStyle(Workbook wb) {
		if (style==null) {
			style = wb.createCellStyle();
			DataFormat cf = wb.createDataFormat();
			style.setDataFormat(cf.getFormat("0.00%"));
		}
		return style;
	}

}
