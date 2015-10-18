package org.test.launcher

import groovy.json.JsonBuilder;
import groovy.json.JsonSlurper

import org.test.reader.EntityReader
import org.test.util.FileUtil;
import org.test.util.Initiializer
import org.test.util.ReplaceUtil;
import org.test.util.TemplateManager;
import org.test.util.TemplateUtil

class MainLauncher {

	def static settings
	static TemplateManager templateManager 
	
	static main(args) {
		work()
	}

	private static work() {
		def entities = []
		TemplateUtil templateUtil = new TemplateUtil()
		templateUtil.init()
		templateManager = templateUtil.createManager()

		settings = new JsonSlurper().parseText(this.getClass().getResource('/config.json').text)
		String path = FileUtil.parent(FileUtil.parent(MainLauncher.class.getProtectionDomain().getCodeSource().getLocation().getPath()))
		path += "/src/enities/"
		FileUtil fileUtil = new FileUtil(path)
		Initiializer initiializer = new Initiializer(settings.sourcePath)
		initiializer.init()
		Thread thread = new Thread(new Runnable(){
			public void run(){
				fileUtil.withEachAllFileWithIndex { file, index ->
					EntityReader entityReader = new EntityReader()
					def entity = entityReader.work(file, index)
					entities << entity
				}

				try {
					println "\n\nresult: " + templateManager.pageList(entities[0])
				} catch (Exception e) {
					e.printStackTrace()
				}
			}
		})
		thread.start()
		while (thread.isAlive()) {

		}
	}
	
}
