package org.test.util

class LogUtil {

	private Class<?> clazz;
	private Boolean isRoot = false;
	private String text = "";
	private static LogUtil instance;

	public LogUtil(Class<?> clazz) {
		super();
		this.clazz = clazz;
	}

	public static LogUtil BUILD() {
		if (instance == null) {
			instance = new LogUtil(LogUtil.class)
			instance.setIsRoot(true)
		}
		return instance;
	}
	
	public LogUtil log(String message, String logLevel = LEVEL.INFO){
		String logMessage = "[$logLevel][${clazz.getName()}]: $message"
		
		text += logMessage;
		
		if (logLevel == LEVEL.INFO) {
			System.out.println(logMessage)
		}
		else {
			System.err.println(logMessage)
		}
		
		if (isRoot) {
			return instance;
		}
		
		return this;
	}

	/*=============================================*/
	
	public Boolean getIsRoot() {
		return isRoot;
	}

	public void setIsRoot(Boolean isRoot) {
		this.isRoot = isRoot;
	}

	public java.lang.Object getSepaisRoot() {
		return sepaisRoot;
	}

	public void setSepaisRoot(java.lang.Object sepaisRoot) {
		this.sepaisRoot = sepaisRoot;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	/*=============================================*/
		
	class LEVEL {
		
		public static String INFO = "INFO";
		public static String ERROR = "ERROR";
		
	}
	
}
