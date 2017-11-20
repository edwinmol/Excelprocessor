package be.aquafin.excelprocessor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelProcessor<T> {
	
	// the column meta data
	private Map<String,Column<?,?>> columns = new HashMap<>(); 
	// the name of the worksheet
	private String worksheet;
	// the class of the beans that will be written or read
	private Class<T> beanClass;

	// the list of errors (after import)
	private List<ImportError> errors;
	// mapping of column numbers to columns
	private Map<Integer, Column<?,?>> columnNumbers;

	private static Logger log = Logger.getLogger(ExcelProcessor.class.getName());
	
	public ExcelProcessor(Class<T> beanClass) {
		this.beanClass = beanClass;
	}

	private void readinit() {
		errors = new ArrayList<>();
		columnNumbers = new HashMap<>();
	}

	public List<T> read(File file) throws IOException {
		if (file==null || !file.exists()) {
			throw new IllegalArgumentException("Invalid file!");
		}
		try(InputStream in = new FileInputStream(file)) {
			return read(in);
		} catch (FileNotFoundException e) {
			log.log(Level.SEVERE, "Cannot open input file", e);
		} 
		return null;
	}
	
	public List<T> read(InputStream stream) throws IOException {
		if (stream == null) {
			throw new IllegalArgumentException("InputStream cannot be null!");
		}
		readinit();
		List<T> result = new ArrayList<T>();
		try (Workbook wb = new XSSFWorkbook(stream)) {
			Sheet sheet = wb.getSheetAt(0);
			if (worksheet!=null) {
				sheet = wb.getSheet(worksheet);
			}
			if (sheet==null) {
				errors.add(new ImportError(ImportError.Type.WORKSHEET_NOT_FOUND,null,null,null));
			} else {
				Iterator<Row> rows = sheet.iterator();
				while (rows.hasNext()) {
					Row row = rows.next();
					T bean = readRow(row);
					if (bean!=null) {
						result.add(bean);
					}
				}			
				if (columnNumbers.size()==0) {
					errors.add(new ImportError(ImportError.Type.HEADER_NOT_FOUND, null, null,null));
				}	
			}					
		}
		return result;
	}

	private T readRow(Row row) {
		T result = null;
		if (columnNumbers.size() == 0) {
			readHeader(row);
		} else {
			result = readBean(row);
		}
		return result;
	}

	private T readBean(Row row) {
		T result = null;
		if (!isEmptyRow(row)) {
			try {
				result = beanClass.newInstance();				
			} catch (Exception e) {
				errors.add(new ImportError(ImportError.Type.ERROR_CREATING_BEAN, null, row.getRowNum(), null));
				log.log(Level.WARNING, "Could not create bean "+beanClass.getCanonicalName()+" : does it have a default constructor?");
			}
			if (result != null) {
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					Column<?,?> c = columnNumbers.get(cell.getColumnIndex());
					if (c != null) {
						readProperty(result, c, cell);
					}
				}
			}
		}
		return result;
	}

	private boolean isEmptyRow(Row row) {
		Iterator<Cell> cellIterator = row.cellIterator();
		while (cellIterator.hasNext()) {
			Cell cell = cellIterator.next();
	        if (cell != null && cell.getCellTypeEnum() != CellType.BLANK)
	            return false;
	    }
	    return true;
	}

	private void readProperty(T bean, Column<?,?> c, Cell cell) {
		// read the cells value and set the property of the bean after converting it to the proper type
		Object value = null;
		try {
			if (c instanceof NumericColumn<?>) {
				value = getNumericValue(cell);
			} else if (c instanceof StringColumn<?>) {
				value = getStringValue(cell);
			} else if (c instanceof BooleanColumn<?>) {
				value = getBooleanValue(cell);
			}
			// convert the value to the correct destination value and 
			// set the bean value
			if (value!=null) {
				PropertyUtils.setProperty(bean, c.getProperty(), c.readConvert(value));
			}
		} catch (Exception e) {
			errors.add(new ImportError(ImportError.Type.GET_PROPERTY_FAILED,c,cell.getRowIndex()+1,e.getLocalizedMessage()));
			log.log(Level.WARNING, "Could not get property "+c.getProperty()+" : "+ e.getMessage());
		}	

		
	}

	private void readHeader(Row row) {
		try {
			Iterator<Cell> cellIterator = row.cellIterator();		
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				String value = getStringValue(cell);
				if (value!=null) {
					Column<?,?> c = columns.get(value.toLowerCase().trim());
					if (c!=null) {
						// we found a matching column, save its column index 
						columnNumbers.put(cell.getColumnIndex(),c);
					}				
				}			
			}
		} catch (Exception e) {
			//ignore
		}		
		if (columnNumbers.size()>0) {
			//check if all columns were found
			if (columnNumbers.size()<columns.size()) {
				for (Column<?, ?> c : columns.values()) {
					if (!columnNumbers.values().contains(c)) {
						errors.add(new ImportError(ImportError.Type.COLUMN_NOT_FOUND,c,row.getRowNum(),null));
					}
				}
			}
		}
	}

	public void write(List<T> beans, File file) throws WriteException, IOException {
		OutputStream out = new FileOutputStream(file);
		write(beans, out);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void write(List<T> beans, OutputStream out) throws WriteException, IOException {
		XSSFWorkbook wb = null;
		try {
			wb = new XSSFWorkbook();
			Sheet sheet = null;
			if (worksheet!=null) {
				sheet = wb.createSheet(worksheet);
			} else {
				sheet = wb.createSheet();
			}
			//write the header
			Row header = sheet.createRow(0);
			List<Column> list = new ArrayList<>(columns.values());
			list.sort(Column::compareTo);
			for (int i = 0; i < list.size(); i++) {
				Column<?,?> c = list.get(i);
				Cell cell = header.createCell(i);
				cell.setCellType(CellType.STRING);
				cell.setCellValue(c.getName());			
			}
			//empty row
			sheet.createRow(1);
			int rowindex = 2;
			//add rows for beans
			for (T bean : beans) {
				writeRow(bean, sheet.createRow(rowindex));
				rowindex++;
			}
			wb.write(out);
		} catch (Exception e) {
			throw new WriteException("Writing excel failed",e);
		} finally {
			wb.close();
			out.close();
		}
		
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void writeRow(T bean, Row row) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		List<Column> list = new ArrayList<>(columns.values());
		list.sort(Column::compareTo);
		for (int i = 0; i < list.size(); i++) {
			Column<?,?> c = list.get(i);
			Cell cell = row.createCell(i);
			writeCell(bean,c,cell);			
		}
		
	}

	private void writeCell(T bean, Column<?,?> c, Cell cell) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Object value = c.writeConvert(PropertyUtils.getProperty(bean, c.getProperty()));
		if (c instanceof NumericColumn<?>) {
			cell.setCellType(CellType.NUMERIC);
			cell.setCellValue((Double)value);
		} else if (c instanceof StringColumn<?>) {
			cell.setCellType(CellType.STRING);
			cell.setCellValue((String)value);
		} else if (c instanceof BooleanColumn<?>) {
			cell.setCellType(CellType.BOOLEAN);
			cell.setCellValue((Boolean)value);
		}
		CellStyle style = c.getStyle(cell.getSheet().getWorkbook());
		if (style!=null) {
			cell.setCellStyle(style);
		}
	}

	public ExcelProcessor<T> column(Column<?, ?>c) {
		if (c.getProperty()==null) {
			c.setProperty(c.getName());
		}
		columns.put(c.getName().toLowerCase(), c);
		return this;
	}
	
	public ExcelProcessor<T> worksheet(String name) {
		worksheet = name;
		return this;
	}
	
	private String getStringValue(Cell cell) {
		String result = null;
		if (cell.getCellTypeEnum() != CellType.BLANK) {
			result = cell.getStringCellValue();
		}
		return result;
	}
	
	private Double getNumericValue(Cell cell) {
		Double result = null;
		if (cell.getCellTypeEnum() != CellType.BLANK) {
			result = cell.getNumericCellValue();
		}
		return result;
	}
	
	private Boolean getBooleanValue(Cell cell) {
		Boolean result = null;
		if (cell.getCellTypeEnum() != CellType.BLANK) {
			result = cell.getBooleanCellValue();			
		}
		return result;
	}

	public boolean hasErrors() {
		return errors!=null && !errors.isEmpty();
	}

	public List<ImportError> getErrors() {
		return errors;
	}

}
