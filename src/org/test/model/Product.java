package org.test.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public class Product {

	private Integer id;
	private String name;
	private String description;
	private Integer count;
	private BigDecimal emplyees;
	private BigInteger companies;
	private Details details;
	private List<Details> list;

	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Product(Integer id, String name, String description, Integer count,
			BigDecimal emplyees, BigInteger companies, Details details,
			List<Details> list) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.count = count;
		this.emplyees = emplyees;
		this.companies = companies;
		this.details = details;
		this.list = list;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public BigDecimal getEmplyees() {
		return emplyees;
	}

	public void setEmplyees(BigDecimal emplyees) {
		this.emplyees = emplyees;
	}

	public BigInteger getCompanies() {
		return companies;
	}

	public void setCompanies(BigInteger companies) {
		this.companies = companies;
	}

	public Details getDetails() {
		return details;
	}

	public void setDetails(Details details) {
		this.details = details;
	}

	public List<Details> getList() {
		return list;
	}

	public void setList(List<Details> list) {
		this.list = list;
	}

}
