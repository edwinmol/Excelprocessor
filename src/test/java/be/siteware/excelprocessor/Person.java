package be.siteware.excelprocessor;

import java.math.BigDecimal;
import java.util.Date;

public class Person {

	Long id;
	String firstName;
	String lastName;
	Date birthDate;
	Float active;
	Boolean programmer;
	BigDecimal salary;

	private Person(Builder builder) {
		this.id = builder.id;
		this.firstName = builder.firstName;
		this.lastName = builder.lastName;
		this.birthDate = builder.birthDate;
		this.active = builder.active;
		this.programmer = builder.programmer;
		this.salary = builder.salary;
	}
	
	public Person() {
		super();
		// TODO Auto-generated constructor stub
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
	public Float getActive() {
		return active;
	}
	public void setActive(Float active) {
		this.active = active;
	}
	public Boolean getProgrammer() {
		return programmer;
	}
	public void setProgrammer(Boolean programmer) {
		this.programmer = programmer;
	}
	public BigDecimal getSalary() {
		return salary;
	}
	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}
	/**
	 * Creates builder to build {@link Person}.
	 * @return created builder
	 */
	public static Builder builder() {
		return new Builder();
	}
	/**
	 * Builder to build {@link Person}.
	 */
	public static final class Builder {
		private Long id;
		private String firstName;
		private String lastName;
		private Date birthDate;
		private Float active;
		private Boolean programmer;
		private BigDecimal salary;

		private Builder() {
		}

		public Builder withId(Long id) {
			this.id = id;
			return this;
		}

		public Builder withFirstName(String firstName) {
			this.firstName = firstName;
			return this;
		}

		public Builder withLastName(String lastName) {
			this.lastName = lastName;
			return this;
		}

		public Builder withBirthDate(Date birthDate) {
			this.birthDate = birthDate;
			return this;
		}

		public Builder withActive(Float active) {
			this.active = active;
			return this;
		}

		public Builder withProgrammer(Boolean programmer) {
			this.programmer = programmer;
			return this;
		}

		public Builder withSalary(BigDecimal salary) {
			this.salary = salary;
			return this;
		}

		public Person build() {
			return new Person(this);
		}
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((active == null) ? 0 : active.hashCode());
		result = prime * result + ((birthDate == null) ? 0 : birthDate.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((programmer == null) ? 0 : programmer.hashCode());
		result = prime * result + ((salary == null) ? 0 : salary.hashCode());
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
		Person other = (Person) obj;
		if (active == null) {
			if (other.active != null)
				return false;
		} else if (!active.equals(other.active))
			return false;
		if (birthDate == null) {
			if (other.birthDate != null)
				return false;
		} else if (!birthDate.equals(other.birthDate))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (programmer == null) {
			if (other.programmer != null)
				return false;
		} else if (!programmer.equals(other.programmer))
			return false;
		if (salary == null) {
			if (other.salary != null)
				return false;
		} else if (!salary.equals(other.salary))
			return false;
		return true;
	}
	
	
	
	
}
