package org.test.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Entity implements Serializable {

	private static final long serialVersionUID = 1971524256565667105L;

	private String name;
	private String tableName;
	private List<Property> columns = new ArrayList<Property>();
	/* ================== 18 окт. 2015 г. ================== */
	private String log;
	/* ================== 18 окт. 2015 г. ================== */
	private String schemaName;
	private String superClassName;
	private String localization;
	private Integer index;
	private Integer listCount;
	private Integer complexCount;

	public Entity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public Entity setName(String name) {
		this.name = name;
		return this;
	}

	public String getTableName() {
		return tableName;
	}

	public Entity setTableName(String tableName) {
		this.tableName = tableName;
		return this;
	}

	public List<Property> getColumns() {
		return columns;
	}

	public Entity setColumns(List<Property> columns) {
		this.columns = columns;
		return this;
	}

	public String getSchemaName() {
		return schemaName;
	}

	public Entity setSchemaName(String schemaName) {
		this.schemaName = schemaName;
		return this;
	}

	public String getSuperClassName() {
		return superClassName;
	}

	public Entity setSuperClassName(String superClassName) {
		this.superClassName = superClassName;
		return this;
	}

	public String getLocalization() {
		return localization;
	}

	public Entity setLocalization(String localization) {
		this.localization = localization;
		return this;
	}

	public Integer getIndex() {
		return index;
	}

	public Entity setIndex(Integer index) {
		this.index = index;
		return this;
	}

	public Integer getListCount() {
		return listCount;
	}

	public Entity setListCount(Integer listCount) {
		this.listCount = listCount;
		return this;
	}

	public Integer getComplexCount() {
		return complexCount;
	}

	public Entity setComplexCount(Integer complexCount) {
		this.complexCount = complexCount;
		return this;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}
	
}
