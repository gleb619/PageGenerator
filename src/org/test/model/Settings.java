package org.test.model;

import java.io.Serializable;

public class Settings implements Serializable {

	private static final long serialVersionUID = -5823955454091760356L;
	
	private String sourcePath;

	public Settings(String sourcePath) {
		super();
		this.sourcePath = sourcePath;
	}

	public String getSourcePath() {
		return sourcePath;
	}

	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}

}
