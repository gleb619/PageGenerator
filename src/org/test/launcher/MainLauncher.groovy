package org.test.launcher

import groovy.json.JsonSlurper

import org.test.model.Settings;
import org.test.util.FileUtil;
import org.test.util.Initiializer
import org.test.util.ReplaceUtil;

class MainLauncher {

	def static settings
	
	static main(args) {
		settings = new JsonSlurper().parseText(this.getClass().getResource('/config.json').text)
		FileUtil fileUtil = new FileUtil(settings.sourcePath)
		Initiializer initiializer = new Initiializer(fileUtil, settings.sourcePath)
		initiializer.init()
		Thread thread = new Thread(new Runnable(){
			public void run(){
				
			}
		})
		thread.start()
		while (thread.isAlive()) {
			
		}
	}

}
