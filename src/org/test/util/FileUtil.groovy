package org.test.util

import groovy.io.FileType

class FileUtil {

	def filename = ""
	LogUtil logUtil = new LogUtil(FileUtil.class)

	public FileUtil(Object pPath) {
		super();
		this.filename = pPath
	}

	def withEachAllDir(Closure closure) {
		new File(filename).eachDir{ closure.call(it) }
	}

	def withEachAllFile(Closure closure) {
		new File(filename).eachFileRecurse(FileType.FILES) { closure.call(it) }
	}

	def withEachTestFile(Closure closure) {
		new File(filename).eachFileRecurse(FileType.FILES) {
			if (it.name =~ ~/.*\.xml/) {
				closure.call(it)
			}
		}
	}

	def withEachDaoFile(Closure closure) {
		new File(filename).eachFileRecurse(FileType.FILES) {
			if (it.name =~ ~/.*\.java/) {
				if(it.name.contains('Home')){
					closure.call(it)
				}
			}
		}
	}

	def withEachDomainFile(Closure closure) {
		new File(filename).eachFileRecurse(FileType.FILES) {
			if (it.name =~ ~/.*\.java/) {
				if(!it.name.contains('Home')){
					if(!it.name.contains('View')){
						closure.call(it)
					}
				}
			}
		}
	}

	def withEachDomainFile2(Closure closure) {
		new File(filename).eachFileRecurse(FileType.FILES) {
			if (it.name =~ ~/.*\.java/) {
				if(!it.name.contains('_')){
					closure.call(it)
				}
			}
		}
	}

	def withEachXmlFile(Closure closure) {
		new File(filename).eachFileRecurse(FileType.FILES) {
			if (it.name =~ ~/.*\.xml/) {
				if(!it.name.contains('.cfg.')){
					closure.call(it)
				}
			}
		}
	}
	
	def withEachCustomlFile(def regular, Closure closure) {
		new File(filename).eachFileRecurse(FileType.FILES) {
			if (it.name =~ regular) {
				closure.call(it)
			}
		}
	}
	
	def withEachHtmlFile(Closure closure) {
		new File(filename).eachFileRecurse(FileType.FILES) {
			if (it.name =~ ~/.*\.html/) {
				closure.call(it)
			}
		}
	}

	def parent(String path){
		new File(path).parentFile
	}
	
	def rename(String from, String to, def files){
		files.each { it ->
			println "scriptingtool.org.test.adapter.FileAdapter()#rename " + "$it"
			renameByPath(from, to, it)
		}
	}
	
	def renameByPath(String from, String to, String pPath){
		println "scriptingtool.org.test.adapter.FileAdapter()#renameByPath " + "start"
		def dir = new File(pPath)
		dir.eachFileRecurse (FileType.FILES) { file ->
		  String text = file.text
		  
		  text = text.replaceAll(from, to)
		  println "work with $file.absolutePath"
		  file.write(text, 'UTF-8')
		}
		println "scriptingtool.org.test.adapter.FileAdapter()#renameByPath " + "end"
	}
	
	def recreateDir(String path, String logLevel = "") {
		deleteDir(path, logLevel)
		mkDirs(path, logLevel)
	}

	def deleteDir(String path, String logLevel = "") {
		new File(path).deleteDir()
	}

	def deleteFile(String path, String logLevel = "") {
		println "$logLevel$path {\n$logLevel\tdelete file$logLevel\n$logLevel}"
		new File(path).delete()
	}

	def mkDirs(String path, String logLevel = "") {
		new File(path).mkdirs()
	}
	
	def mkDirIfNeeded(String path, String logLevel = "") {
		File file = new File(path)
		if (!file.exists()) {
			println "$logLevel$path {\n$logLevel\tcreate new folder\n$logLevel}"
			file.mkdirs()
		}
	}
	
	def mkDirsInParent(String path) {
		println "$path {\n\tcreate new folder\n}"
		new File(path).mkdirs()
	}

	def createFile2(String package_, String name, String text) {
		new File(package_.replace('.', '/')).mkdirs()
		new File(package_.replace('.', '/') + '/' + name).write(text)
	}

	def createFile(String name, String text) {
		new File(name).write(text, "UTF-8")
	}

	def createFile(String parentFolder, String path, String text) {
		new File(parentFolder).mkdirs()
		new File(path).write(text)
	}

	public openFile(String pPath, String name, Boolean isJava){
		new File(pPath + '/' + name + isJava ? '.java' : '')
	}

	def openFile(String name, Boolean isJava){
		File file_ = null
		if(isJava){
			file_ = new File(filename + '/' + name + '.java')
		}
		else{
			file_ = new File(filename + '/' + name)
		}

		file_
	}
	
	def copy(def fileNameFrom, def fileNameTo, String logLevel = "\t"){
		String message = "copy#"
		message += "\n$logLevel$fileNameTo {"
		File newFile = new File(fileNameTo)
		File oldFile = new File(fileNameFrom)
		
		new File(newFile.getParent()).mkdirs()
		newFile.text = oldFile.text
		
		message += "\n\t\tcreate new file:\n\t\tfrom: ${oldFile.path}\n\t\tto: ${newFile.path}"
		message += "\n${logLevel}\tall done \n${logLevel}}"
		
		logUtil.log(message)
	}
}
