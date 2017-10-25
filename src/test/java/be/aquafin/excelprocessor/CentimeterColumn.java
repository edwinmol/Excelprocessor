package be.aquafin.excelprocessor;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Workbook;

public class CentimeterColumn extends IntegerColumn {

	private CellStyle style;
	
	public CentimeterColumn(String name, String property, int sequence) {
		super(name, property, sequence);
	}
	
	@Override
	public CellStyle getStyle(Workbook wb) {
		if (style==null) {
			style = wb.createCellStyle();
			DataFormat cf = wb.createDataFormat();
			style.setDataFormat(cf.getFormat("#0 \"cm\""));
		}
		return style;
	}

}
