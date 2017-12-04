package be.aquafin.excelprocessor;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

public class ExcelProcessorTest {

	private static SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	
	@Test
	public void testExcelProcessor() {
		ExcelProcessor<Person> processor = new ExcelProcessor<>(Person.class);
	}

	@Test
	public void readValidFile() throws Exception {
		ExcelProcessor<Person> processor = createPersonProcessor();
		File file = new File("src/test/resources/valid.xlsx");
		List<Person> persons = processor.read(file);
		assertThat(processor.hasErrors(), is(false));
		assertThat(processor.getErrors().size(), is(0));
		assertThat(persons.size(), is(4));
		assertThat(processor.getBeanInfo().size(), is(4));
		assertThat(persons.get(0).getId(), is(1l));
		assertThat(persons.get(0).getFirstName(), is("Harry"));
		assertThat(persons.get(0).getBirthDate(), is(format.parse("13/07/1973")));
		assertThat(persons.get(0).getActive(), is(0.5f));
		assertThat(persons.get(0).getSalary(), is(BigDecimal.valueOf(20000.00).setScale(2)));
		assertThat(persons.get(0).getProgrammer(), is(true));		
		assertThat(persons.get(1).getProgrammer(), is(false));
	}

	@Test
	public void readSpecificWorksheet() throws Exception {
		ExcelProcessor<Bird> processor = createBirdProcessor();
		try (InputStream in = getClass().getClassLoader().getResourceAsStream("worksheets.xlsx")) {
			List<Bird> birds = processor.read(in);
			assertThat(processor.hasErrors(), is(false));
			assertThat(birds.size(), is(2));
			assertThat(birds.get(0).getName(), is("Colibri"));
			assertThat(birds.get(0).getSize(), is(10));
		}
	}
	
	@Test
	public void columnNotFound() throws Exception {
		ExcelProcessor<Person> processor = createPersonProcessor();
		File file = new File("src/test/resources/column not found.xlsx");
		List<Person> persons = processor.read(file);
		List<ImportError> errors = processor.getErrors();
		assertThat(errors.size(), is(1));
		assertThat(errors.get(0).getType(), is(ImportError.Type.COLUMN_NOT_FOUND));
		assertThat(errors.get(0).getColumn().getName(), is("Date of Birth"));

	}
	
	@Test
	public void headerNotFound() throws Exception {
		ExcelProcessor<Person> processor = createPersonProcessor();
		File file = new File("src/test/resources/no header.xlsx");
		List<Person> persons = processor.read(file);
		List<ImportError> errors = processor.getErrors();
		assertThat(errors.size(), is(1));
		assertThat(persons.size(), is(0));
		assertThat(errors.get(0).getType(), is(ImportError.Type.HEADER_NOT_FOUND));
		
	}
	
	@Test
	public void worksheetNotFound() throws Exception {
		ExcelProcessor<Person> processor = createPersonProcessor();
		processor.worksheet("not found");
		File file = new File("src/test/resources/worksheets.xlsx");
		List<Person> persons = processor.read(file);
		List<ImportError> errors = processor.getErrors();
		assertThat(errors.size(), is(1));
		assertThat(persons.size(), is(0));
		assertThat(errors.get(0).getType(), is(ImportError.Type.WORKSHEET_NOT_FOUND));
		
	}
	
	@Test
	public void invalidProperties() throws Exception {
		ExcelProcessor<Person> processor = createPersonProcessor();
		File file = new File("src/test/resources/invalid properties.xlsx");
		List<Person> persons = processor.read(file);
		List<ImportError> errors = processor.getErrors();
		assertThat(errors.size(), is(3));
		assertThat(persons.size(), is(5));
		assertThat(errors.get(0).getType(), is(ImportError.Type.GET_PROPERTY_FAILED));
		assertThat(errors.get(0).getLineNumber(), is(6));
		assertThat(errors.get(0).getColumn().getName(), is("Date of Birth"));
		assertThat(errors.get(1).getType(), is(ImportError.Type.GET_PROPERTY_FAILED));
		assertThat(errors.get(1).getLineNumber(), is(7));
		assertThat(errors.get(1).getColumn().getName(), is("id"));
		assertThat(errors.get(2).getType(), is(ImportError.Type.GET_PROPERTY_FAILED));
		assertThat(errors.get(2).getLineNumber(), is(7));
		assertThat(errors.get(2).getColumn().getName(), is("Programmer"));

	}

	@Test
	public void writeAndReadRoundtrip() throws Exception {
		File file = new File("test.xlsx");
		try {
			List<Person> people = Arrays.asList(
					Person.builder().withFirstName("Edwin").withLastName("Mol").withBirthDate(format.parse("13/07/1973"))
					.withId(10l).withProgrammer(true).withSalary(BigDecimal.valueOf(1000).setScale(2)).withActive(1.0f).build()
					);
			ExcelProcessor<Person> processor = createPersonProcessor();
			processor.worksheet("people");
			processor.write(people, file);
			//now read the file
			List<Person> people2 = processor.read(file);
			assertThat(people2.size(), is(people.size()));
			assertTrue(people.get(0).equals(people2.get(0)));
		} finally {
			file.delete();			
		}
	}
	
	@Test
	public void checkSequenceOfWrittenColumns() throws Exception {
		File file = new File("test.xlsx");
		XSSFWorkbook wb = null;
		try {
			List<Person> people = Arrays.asList(
					Person.builder().withFirstName("Edwin").withLastName("Mol").withBirthDate(format.parse("13/07/1973"))
					.withId(10l).withProgrammer(true).withSalary(BigDecimal.valueOf(1000)).withActive(1.0f).build()
					);
			ExcelProcessor<Person> processor = createPersonProcessor();
			processor.worksheet("people");
			processor.write(people, file);
			
			wb = new XSSFWorkbook(file);
			Sheet sheet = wb.getSheet("people");
			Row header = sheet.getRow(0);
			assertThat(header.getCell(0).getStringCellValue(), is(("id")));
			assertThat(header.getCell(1).getStringCellValue(), is(("First Name")));
			assertThat(header.getCell(2).getStringCellValue(), is(("Last Name")));
			assertThat(header.getCell(3).getStringCellValue(), is(("Date of Birth")));
			assertThat(header.getCell(4).getStringCellValue(), is(("Active percentage")));
			assertThat(header.getCell(5).getStringCellValue(), is(("Salary")));
			assertThat(header.getCell(6).getStringCellValue(), is(("Programmer")));
		} finally {
			wb.close();
			file.delete();			
		}
	}
	
	@Test
	public void beanCreationError() throws Exception {
		File file = new File("src/test/resources/worksheets.xlsx");
		ExcelProcessor<BirdWithoutDefaultConstructor> processor = new ExcelProcessor<>(BirdWithoutDefaultConstructor.class)
				.column(new StringColumn("name", "name"))
				.column(new StringColumn("color", "color"))
				.column(new CentimeterColumn("size", "size"))
				.worksheet("birds");
		List<BirdWithoutDefaultConstructor> birds = processor.read(file);
		List<ImportError> errors = processor.getErrors();
		assertThat(errors.size(), is(2));
		assertThat(birds.size(), is(0));
		assertThat(errors.get(0).getType(), is(ImportError.Type.ERROR_CREATING_BEAN));
		assertThat(errors.get(0).getLineNumber(), is(3));
	}
	
	private ExcelProcessor<Bird> createBirdProcessor() {
		ExcelProcessor<Bird> processor = new ExcelProcessor<>(Bird.class)
				.column(new StringColumn("name", "name" ))
				.column(new StringColumn("color", "color"))
				.column(new CentimeterColumn("size", "size"))
				.worksheet("birds");
		return processor;
	}
	

	private ExcelProcessor<Person> createPersonProcessor() {
		ExcelProcessor<Person> processor = new ExcelProcessor<>(Person.class)
				.column(new LongColumn("id", "id" ))
				.column(new StringColumn("First Name", "firstName"))
				.column(new StringColumn("Last Name", "lastName"))
				.column(new DateColumn("Date of Birth", "birthDate", "DD/MM/YYYY"))
				.column(new FloatColumn("Active percentage", "active"))
				.column(new CurrencyColumn("Salary", "salary"))
				.column(new BooleanColumn("Programmer", "programmer"));
		return processor;
	}

}
