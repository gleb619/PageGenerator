package org.test.launcher

import org.test.util.FileUtil;
import org.test.util.ReplaceUtil;

import groovy.io.FileType
import groovy.json.JsonSlurper

class RenameLauncher {

	def static settings
	static String pPath = ''
	static String currentProjectPathCode = ''
	static String currentProjectPath = ''
	static String currentProjectPathWeb = ''
	
	static main(args) {
		settings = new JsonSlurper().parseText(this.getClass().getResource('/config.json').text)
		rename(settings.packageTo, settings.packageFrom, settings.sourcePath + "_")
	}

	private static moveToClient() {
		task_client_replaceOriginal(currentProjectPath)
	}
	
	private static moveToServer() {
		task_server_replaceOriginal(currentProjectPath)
	}
	
	static task_rename_model(def fromList, String to){
		fromList.each { from ->
			rename(from, to, '$pPath/model/')
		}
	}
	
	static rename2(def fromList, String to){
		fromList.each { from ->
			println "org.test.util.RenameUtil()#rename2:start " + "$from"
			task_rename(from, to)
		}
	}
	
	static task_rename(String from, String to) {
		println "org.test.util.RenameUtil()#task_rename " + "start"
		rename_server_full(from, to)
		rename_client_full(from, to)
		println "org.test.util.RenameUtil()#task_rename " + "end"
	}
	
	static rename_server_full(String from, String to) {
		def files = [
			  "$pPath/config/"
			, "$pPath/controller/"
			, "$pPath/model/"
			, "$pPath/util/"
		]
		
		files.each { path ->
			rename(from, to, path)
		}
	}
	
	static rename_client_full(String from, String to) {
		def files = [
			  "$pPath/resources/"
		]

		files.each { path ->
			rename(from, to, path)
		}
	}
	
	static rename(String from, String to, String path){
		println "org.test.util.RenameUtil()#rename " + "start\n\t$path"
		def dir = new File(path)
		if(!dir.exists()){
			println "ERROR::file doesn't exist"
			return
		}
		dir.eachFileRecurse (FileType.FILES) { file ->
		  String text = file.text
		  
		  if (text.indexOf(from) > -1) {
			  println "\twork with  $file.absolutePath"
			  text = text.replaceAll(from, to)
			  file.write(text, 'UTF-8')
		  }
		  else {
			  println "\tclean file $file.absolutePath"
		  }
		  
		}
	}
	
	static task_model_replaceOriginal(String currentProjectPath) {
		def filesMap = [
			"$pPath/model/": "$currentProjectPath/model/"
		]
	  
		replaceFilesInOriginalProject(filesMap)
	}
	
	static task_server_replaceOriginal(String currentProjectPath) {
		println "org.test.launcher.TestFC()#task_server_replaceOriginal " + "start"
		def filesMap = [
			  "$pPath/controller/"	: "$currentProjectPath/controller/"
			, "$pPath/config/"		: "$currentProjectPath/config/"
			, "$pPath/model/"		: "$currentProjectPath/model/"
			, "$pPath/util/"		: "$currentProjectPath/util/"
		]
		
		replaceFilesInOriginalProject(filesMap)
		println "org.test.launcher.TestFC()#task_server_replaceOriginal " + "end"
	}
	
	static task_client_replaceOriginal(String currentProjectPathWeb) {
		println "org.test.launcher.TestFC()#task_client_replaceOriginal " + "start"
		def filesMap = [
              "$pPath/resources/": 	"$currentProjectPathWeb/resources/"
        	, "$pPath/pages/"	 : 	"$currentProjectPathWeb/pages/"
		]
				
		replaceFilesInOriginalProject(filesMap)
		println "org.test.launcher.TestFC()#task_client_replaceOriginal " + "end"
	}
	
	static replaceFilesInOriginalProject(def filesMap) {
		println "replaceFilesInOriginalProject#start"
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					replaceFilesInOriginalProjectData(filesMap)
				} catch (Exception e) {
					println "ERROR: " + e .getMessage()
				}
			}
		})
		
		thread.start()
	}
	
	static replaceFilesInOriginalProjectData(def filesMap) throws Exception {
		println "replaceFilesInOriginalProjectData#start"
		FileUtil fileAdapter = null
		ReplaceUtil replaceAdapter = null
		
		filesMap.each { key, value ->
			File test = new File(key)
			if (test.exists()) {
				fileAdapter = new FileUtil(key)
				replaceAdapter = new ReplaceUtil()
				
				fileAdapter.withEachAllFile { it ->
					String path = it.path.replace(key.replace("/", "\\"), "")
					println "path: $it.path \nmove to: $value$path"
					replaceAdapter.copy(it.path, value + path, '\t')
				}
			}
			else {
				println "WARNING: file [ $key ] not found"  
			}
		}
		
		println "replaceFilesInOriginalProjectData#end"
	}
	
}
