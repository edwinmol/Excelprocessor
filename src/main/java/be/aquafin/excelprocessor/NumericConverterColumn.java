package be.aquafin.excelprocessor;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

public class NumericConverterColumn<T> extends Column<T, Double> {

	public NumericConverterColumn(String name, String property, PropertyConverter<T, Double> read, PropertyConverter<Double, T> write, int sequence) {
		super(name, property, read, write, sequence);
	}

	public NumericConverterColumn(String name, String property, PropertyConverter<T, Double> read, PropertyConverter<Double, T> write) {
		super(name, property, read, write, 0);
	}

	@Override
	public CellStyle getStyle(Workbook wb) {
		return null;
	}

}
