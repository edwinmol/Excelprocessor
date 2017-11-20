# README #

The Excelprocessor is a library based on Apache POI that makes it easy to read and write excel files.

## Design Goals ##

* Offer a simple API to the developer without all the bells and whistles of the Apache POI library.

## How do I get set up? ##

Add the following dependency in your pom.xml:

    <dependency>
        <groupId>be.aquafin</groupId>
        <artifactId>excelprocessor</artifactId>
        <version>1.0.0</version>
    </dependency>

## Quick start ##

### Reading objects from an excel file ###

Given and excel file with contents:

id      | First Name      | Last Name      | Date of Birth
:-------|:----------------|:---------------|:-------------
1       | Edwin           | Mol            | 13/07/1973
2       | Gert            | Verbiest       | 11/10/1988
3       | Monique         | Verstappen     | 10/06/1978

We could read the file as follows.

* Define a Java Bean that corresponds to a row in the excel file.

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

* Create the ExcelProcessor with the column mappings.

    ExcelProcessor<Person> processor = new ExcelProcessor<>(Person.class)
        .column(new LongColumn("Identifier", "id" ))
		.column(new StringColumn("First Name", "firstName"))
		.column(new StringColumn("Last Name", "lastName"))
		.column(new DateColumn("Date of Birth", "birthDate", "DD/MM/YYYY"));

* Now read a list of people

    List<Person> people = processor.read(new File("people.xlsx");

* Rows that could not be read will be listed in the errors

    if (processor.hasErrors()) {
        List<ImportError> errors = processor.getErrors()
        //handle errors
    }

### Writing an excel file ###

* The first two steps are the same as reading from an excel file
    1. Define a Java Bean.
    2. Create an ExcelProcessor with column definitions.

* Now we create a list of beans

    List<Person> people = new ArrayList<>();
    // add objects to the list
    // people.add(...
    processor.worksheet("people"); //select the name of the worksheet to write to (optional)
    processor.write(people, new File("people.xlsx"));

### Questions? ###

* edwin.mol@aquafin.be
