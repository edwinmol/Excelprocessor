package be.aquafin.excelprocessor;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

public class NumericColumn<T> extends Column<T, Double> {

	public NumericColumn(String name, String property, PropertyConverter<T, Double> read, PropertyConverter<Double, T> write, int sequence) {
		super(name, property, read, write, sequence);
	}

	@Override
	public CellStyle getStyle(Workbook wb) {
		return null;
	}

}
