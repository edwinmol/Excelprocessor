package be.aquafin.excelprocessor;

public class BooleanColumn extends BooleanConverterColumn<Boolean> {

	public BooleanColumn(String name, String property) {
		super(name, 
				property, 
				a -> a, 
				b -> b, 
				0);
	}

}
