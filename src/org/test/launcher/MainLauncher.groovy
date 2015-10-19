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

	
	
	static main(args) {
		work()
	}

	private static work() {
		def entities = []
		def settings
		TemplateManager templateManager
		TemplateUtil templateUtil
		
		settings = new JsonSlurper().parseText(this.getClass().getResource('/config.json').text)
		templateUtil = new TemplateUtil(settings)
		templateUtil.init()
		templateManager = templateUtil.createManager()

		String path = FileUtil.parent(FileUtil.parent(MainLauncher.class.getProtectionDomain().getCodeSource().getLocation().getPath()))
		path += "/src/enities/"
		FileUtil fileUtil = new FileUtil(path)
		Initiializer initiializer = new Initiializer(settings.sourcePath)
		initiializer.init()
		Thread thread = new Thread(new Runnable(){
			public void run(){
				fileUtil.withEachAllFileWithIndex { file, index ->
					EntityReader entityReader = new EntityReader(templateManager)
					def entity = entityReader.work(file, index)
					entities << entity
				}

				try {
					println "\n\nresult: " + templateManager.entityList(entities[0])
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
