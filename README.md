# README #

The Excelprocessor is a library based on Apache POI that makes it easy to read and write excel files.

## Design Goals ##

* Offer a simple API to the developer without all the bells and whistles of the Apache POI library.

## How do I get set up? ##

Add the following dependency in your pom.xml:

    <dependency>
        <groupId>be.siteware</groupId>
        <artifactId>excelprocessor</artifactId>
        <version>1.0.4</version>
    </dependency>

## Quick start ##

### Reading objects from an excel file ###

* Given and excel file with contents:

Identifier| First Name      | Last Name      | Date of Birth
:---------|:----------------|:---------------|:-------------
1         | Edwin           | Mol            | 13/07/1973
2         | Gert            | Verbiest       | 11/10/1988
3         | Monique         | Verstappen     | 10/06/1978

We can read the file as follows.

1. Define a Java Bean that corresponds to a row in the excel file.

        class Person {
            private Long id;
            private String firstName;
            private String lastName;
            private Date birthDate;

            public Person() {
                super();
            }
            public Long getId() {
                return id;
            }
            public void setId(Long id) {
                this.id = id;
            }
            public String getFirstName() {
                return firstName;
            }
            public void setFirstName(String firstName) {
                this.firstName = firstName;
            }
            public String getLastName() {
                return lastName;
            }
            public void setLastName(String lastName) {
                this.lastName = lastName;
            }
            public Date getBirthDate() {
                return birthDate;
            }
            public void setBirthDate(Date birthDate) {
                this.birthDate = birthDate;
            }
        }

2. Create the ExcelProcessor with the column mappings.

        ExcelProcessor<Person> processor = new ExcelProcessor<>(Person.class)
            .column(new LongColumn("Identifier", "id" ))
            .column(new StringColumn("First Name", "firstName"))
            .column(new StringColumn("Last Name", "lastName"))
            .column(new DateColumn("Date of Birth", "birthDate", "DD/MM/YYYY"));

3. Now read a list of people

        List<Person> people = processor.read(new File("people.xlsx");

4. Inspect errors

        if (processor.hasErrors()) {
            List<ImportError> errors = processor.getErrors()
            //handle errors
        }

### Writing objects to an excel file ###

* The first two steps are the same as reading from an excel file
    1. Define a Java Bean.
    2. Create an ExcelProcessor with column definitions.


* Now create a list of beans

        List<Person> people = new ArrayList<>();
        // add objects to the list
        // people.add(...

* And finally write to the excel file

        processor.worksheet("people"); //select the name of the worksheet to write to (optional)
        processor.write(people, new File("people.xlsx"));

### Using custom converters ###

Besides using the default Column classes you can implement your own by extending the Column<?,?> class or one of its subclasses. 
This allows you to implement your own conversion functions to convert cell data to a bean property and vice versa.

Have a look at the Float Column as an example on how to do this.

        public class FloatColumn extends NumericConverterColumn<Float> {

	        public FloatColumn(String name, String property) {
		        super(name, 
    				property,  
	    			d -> d.floatValue(), 
		    		f -> f.doubleValue());
	        }

	        @Override
	        public CellStyle getStyle(Workbook wb) {
		        return null;
	        }

        }

You can also implement your own column class for adding a custom cell style.
The example below illustrates the use of a custom Column for writing(and reading) Integer values as centimeters.

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
