package be.aquafin.excelprocessor;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

public abstract class Column<T,G> implements Comparable<Column<T, G>> {

	private String name;
	private String property;
	private PropertyConverter<T, G>readConverter;
	private PropertyConverter<G, T>writeConverter;
	private int sequence;
	
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public PropertyConverter<T, G> getReadConverter() {
		return readConverter;
	}
	public void setReadConverter(PropertyConverter<T, G> readConverter) {
		this.readConverter = readConverter;
	}
	public PropertyConverter<G, T> getWriteConverter() {
		return writeConverter;
	}
	public void setWriteConverter(PropertyConverter<G, T> writeConverter) {
		this.writeConverter = writeConverter;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public Column(String name, String property, PropertyConverter<T, G>readConverter, PropertyConverter<G, T>writeConverter, int sequence) {
		super();
		this.name = name;
		this.property = property;
		this.readConverter = readConverter;
		this.writeConverter = writeConverter;
		this.sequence = sequence;
	}

	@SuppressWarnings("unchecked")
	public T readConvert(Object in) {
		if (readConverter!=null && in!=null) {
			return readConverter.convert((G)in);
		}
		return null;
	}	
	@SuppressWarnings("unchecked")
	public G writeConvert(Object in) {
		if (writeConverter!=null && in!=null) {
			return writeConverter.convert((T)in);
		}
		return null;
	}	
	abstract public CellStyle getStyle(Workbook wb);
	
	@Override
	public int compareTo(Column<T, G> o) {
		return sequence - o.sequence;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Column other = (Column) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
