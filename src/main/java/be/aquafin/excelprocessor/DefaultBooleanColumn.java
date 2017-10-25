package be.aquafin.excelprocessor;

public class DefaultBooleanColumn extends BooleanColumn<Boolean> {

	public DefaultBooleanColumn(String name, String property, int sequence) {
		super(name, 
				property, 
				a -> a, 
				b -> b, 
				sequence);
	}

}
