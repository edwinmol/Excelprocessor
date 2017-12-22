package be.siteware.excelprocessor;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Workbook;

public class CurrencyColumn extends NumericConverterColumn<BigDecimal> {
	
	private String dataformat = "#,##0.00 €";
	private CellStyle style;
	
	public CurrencyColumn(String name, String property) {
		this(name, property, null);
	}
	
	public CurrencyColumn(String name, String property, String format) {
		super(name, 
				property, 
				number -> {return BigDecimal.valueOf(number).setScale(2, RoundingMode.HALF_DOWN);}, 
				decimal -> {return decimal.doubleValue();},
				0);
		
		if (format!=null) {
			this.dataformat = format;
		}
	}
		
	@Override
	public CellStyle getStyle(Workbook wb) {
		if (style==null) {
			style = wb.createCellStyle();
			DataFormat cf = wb.createDataFormat();
			style.setDataFormat(cf.getFormat(dataformat));
		}
		return style;
	}

}
