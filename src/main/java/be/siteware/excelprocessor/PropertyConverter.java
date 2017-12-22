package be.siteware.excelprocessor;

/**
 * Functional interface 
 * 
 * @author edwin
 *
 * @param <T>
 * @param <G>
 */
public interface PropertyConverter<T,G> {

	T convert(G value);
	
}
