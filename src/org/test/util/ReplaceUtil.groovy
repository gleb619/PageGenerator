package org.test.util

import java.nio.*

class ReplaceUtil {

	String oldPackage = ""
	String newPackage = ""
	String fileName = ""
	LogUtil logUtil = new LogUtil(ReplaceUtil.class)
	
	public ReplaceUtil(Object oldPackage, Object newPackage, Object fileName) {
		super();
		this.oldPackage = oldPackage;
		this.newPackage = newPackage;
		this.fileName = fileName;
	}
	public ReplaceUtil() {
		super();
	}

	def replace(def fileNameFrom, def fileNameTo, Boolean changePackage = false){
		String message = "replace#"
		message += "\n" + fileNameTo + "{"
		
		File newFile = new File(fileNameTo)
		File oldFile = new File(fileNameFrom)
		
		new File(newFile.getParent()).mkdir()
		newFile.text = oldFile.text
		
		message += "\n\tcreate new file:\n\tfrom${oldFile.path}\n\tfrom${newFile.path}"
		
		if(changePackage){
			message += "\n\t\tchange package"
			def sources_ = new File(fileNameTo).text
			sources_ = sources_
					   .replaceAll("^package $oldPackage", "package $newPackage")
					   .replaceFirst("(import)", "import ${oldPackage}.${fileName};\nimport")
			new File(fileNameTo).write(sources_)
			
		}
		
		message += "\n\tTry to delete old one \n\t\t" + fileNameFrom + "\n\tresult = " + new File(fileNameFrom).delete() + "\n}"
		logUtil.log(message)
	}
	
	def copy(def fileNameFrom, def fileNameTo, String logLevel = "\t"){
		String message = "replace##"
		message += "\n$logLevel $fileNameTo {"
		new File(new File(fileNameTo).getParent()).mkdir()
		new File(fileNameTo).text = new File(fileNameFrom).text
		
		message += "\n${logLevel}\tcreate new file"
			
		message += "\n${logLevel}\tall done \n${logLevel}}"
		logUtil.log(message)
	}
	
	
	
}
