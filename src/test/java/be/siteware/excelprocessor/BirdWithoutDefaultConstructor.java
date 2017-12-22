package be.siteware.excelprocessor;

import javax.annotation.Generated;

public class BirdWithoutDefaultConstructor {

	String name;
	String color;
	Integer size;

	public BirdWithoutDefaultConstructor(String name, String color, Integer size) {
		super();
		this.name = name;
		this.color = color;
		this.size = size;
	}

	@Generated("SparkTools")
	private BirdWithoutDefaultConstructor(Builder builder) {
		this.name = builder.name;
		this.color = builder.color;
		this.size = builder.size;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	/**
	 * Creates builder to build {@link BirdWithoutDefaultConstructor}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link BirdWithoutDefaultConstructor}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private String name;
		private String color;
		private Integer size;

		private Builder() {
		}

		public Builder withName(String name) {
			this.name = name;
			return this;
		}

		public Builder withColor(String color) {
			this.color = color;
			return this;
		}

		public Builder withSize(Integer size) {
			this.size = size;
			return this;
		}

		public BirdWithoutDefaultConstructor build() {
			return new BirdWithoutDefaultConstructor(this);
		}
	}

	
	
	
}
