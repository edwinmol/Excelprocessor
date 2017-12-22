package be.siteware.excelprocessor;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Workbook;

public class CentimeterColumn extends IntegerColumn {

	private CellStyle style;
	
	public CentimeterColumn(String name, String property) {
		super(name, property);
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
