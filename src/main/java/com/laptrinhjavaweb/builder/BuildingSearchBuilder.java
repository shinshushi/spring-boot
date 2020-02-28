package com.laptrinhjavaweb.builder;

public class BuildingSearchBuilder {
	
	private String name;
	private String district;
	private Integer numberOfBasement;
	private Integer floorArea;
	private String rentAreaFrom;
	private String rentAreaTo;
	private String[] types = new String[] {};
	private String rentCostFrom;
	private String rentCostTo;
	private Long staffId;
	
	private BuildingSearchBuilder(Builder builder) {
		this.name = builder.name;
		this.district = builder.district;
		this.numberOfBasement = builder.numberOfBasement;
		this.floorArea = builder.floorArea;
		this.rentAreaFrom = builder.rentAreaFrom;
		this.rentAreaTo = builder.rentAreaTo;
		this.types = builder.types;
		this.rentCostFrom = builder.rentCostFrom;
		this.rentCostTo = builder.rentCostTo;
		this.staffId = builder.staffId;
	}
	
	public String getName() {
		return name;
	}
	public String getDistrict() {
		return district;
	}
	public Integer getNumberOfBasement() {
		return numberOfBasement;
	}
	public Integer getFloorArea() {
		return floorArea;
	}
	public String getRentAreaFrom() {
		return rentAreaFrom;
	}
	public String getRentAreaTo() {
		return rentAreaTo;
	}

	/**
	 * @return the types
	 */
	public String[] getTypes() {
		return types;
	}
	public String getRentCostFrom() {
		return rentCostFrom;
	}

	public String getRentCostTo() {
		return rentCostTo;
	}

	public Long getStaffId() {
		return staffId;
	}

	public static class Builder{
		private String name;
		private String district;
		private Integer numberOfBasement;
		private Integer floorArea;
		private String rentAreaFrom;
		private String rentAreaTo;
		private String[] types = new String[] {};
		private String rentCostFrom;
		private String rentCostTo;
		private Long staffId;
		
		public Builder setName(String name) {
			this.name = name;
			return this;
		}
		public Builder setDistrict(String district) {
			this.district = district;
			return this;
		}
		public Builder setNumberOfBasement(Integer numberOfBasement) {
			this.numberOfBasement = numberOfBasement;
			return this;
		}
		public Builder setFloorArea(Integer floorArea) {
			this.floorArea = floorArea;
			return this;
		}
		public Builder setRentAreaFrom(String rentAreaFrom) {
			this.rentAreaFrom = rentAreaFrom;
			return this;
		}
		public Builder setRentAreaTo(String rentAreaTo) {
			this.rentAreaTo = rentAreaTo;
			return this;
		}
		/**
		 * @param types the types to set
		 */
		public Builder setTypes(String[] types) {
			this.types = types;
			return this;
		}
		
		public Builder setRentCostFrom(String rentCostFrom) {
			this.rentCostFrom = rentCostFrom;
			return this;
		}
		public Builder setRentCostTo(String rentCostTo) {
			this.rentCostTo = rentCostTo;
			return this;
		}
		public Builder setStaffId(Long staffId) {
			this.staffId = staffId;
			return this;
		}
		public BuildingSearchBuilder build() {
			return new BuildingSearchBuilder(this);
		}
		
	}
	
}
