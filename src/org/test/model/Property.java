package org.test.model;

import java.io.Serializable;

public class Property implements Serializable {

	private static final long serialVersionUID = 7453223074019943051L;
	
	private String name;
	private String caption;
	private Class<?> dataType;
	/* ================== 18 окт. 2015 г. ================== */
	private Boolean list;
	private Boolean complex;
	private Boolean fetchLazy;
	private String columnName;
	private String joinColumn;
	private String type;
	private String mappedBy;
	private String simpleType;
	private String sortType;
	private String localization;

	public Property() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public Property setName(String name) {
		this.name = name;
		return this;
	}

	public String getCaption() {
		return caption;
	}

	public Property setCaption(String caption) {
		this.caption = caption;
		return this;
	}

	public Class<?> getDataType() {
		return dataType;
	}

	public Property setDataType(Class<?> dataType) {
		this.dataType = dataType;
		return this;
	}

	public Boolean getList() {
		return list;
	}

	public Property setList(Boolean list) {
		this.list = list;
		return this;
	}

	public Boolean getComplex() {
		return complex;
	}

	public Property setComplex(Boolean complex) {
		this.complex = complex;
		return this;
	}

	public Boolean getFetchLazy() {
		return fetchLazy;
	}

	public Property setFetchLazy(Boolean fetchLazy) {
		this.fetchLazy = fetchLazy;
		return this;
	}

	public String getColumnName() {
		return columnName;
	}

	public Property setColumnName(String columnName) {
		this.columnName = columnName;
		return this;
	}

	public String getJoinColumn() {
		return joinColumn;
	}

	public Property setJoinColumn(String joinColumn) {
		this.joinColumn = joinColumn;
		return this;
	}

	public String getType() {
		return type;
	}

	public Property setType(String type) {
		this.type = type;
		return this;
	}

	public String getMappedBy() {
		return mappedBy;
	}

	public Property setMappedBy(String mappedBy) {
		this.mappedBy = mappedBy;
		return this;
	}

	public String getSimpleType() {
		return simpleType;
	}

	public Property setSimpleType(String simpleType) {
		this.simpleType = simpleType;
		return this;
	}

	public String getSortType() {
		return sortType;
	}

	public Property setSortType(String sortType) {
		this.sortType = sortType;
		return this;
	}

	public String getLocalization() {
		return localization;
	}

	public Property setLocalization(String localization) {
		this.localization = localization;
		return this;
	}

}
