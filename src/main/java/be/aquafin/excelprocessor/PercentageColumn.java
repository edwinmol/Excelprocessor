package be.aquafin.excelprocessor;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Workbook;

public class PercentageColumn extends NumericConverterColumn<Float> {

	private CellStyle style;
	
	public PercentageColumn(String name, String property) {
		super(name, 
				property,  
				d -> d.floatValue(), 
				f -> f.doubleValue(), 
				0);
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
