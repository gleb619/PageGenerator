package org.test.util

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;

import groovy.io.FileType

class TemplateUtil {

	def templates = [:]
	OtherUtil otherUtil = new OtherUtil()
	
	def init(){
		ClassLoader loader = this.class.getClassLoader();
		InputStream inputStream = loader.getResourceAsStream("templates/");
		BufferedReader rdr = new BufferedReader(new InputStreamReader(inputStream));
		String line;
		while ((line = rdr.readLine()) != null) {
			def file = this.getClass().getResource("/templates/$line").getFile()
			templates.put(line, new File(file).text)
		}
		rdr.close();
	}
	
	def createManager(){
		templates.each { key, value ->
			TemplateManager.metaClass."${otherUtil.toMethodName(key)}" = { data ->
				String template = ""
				try {
					template = defaultText(data, value)
				} catch (Exception e) {
					e.printStackTrace()
				}
				println "template: $template"
				
				return template
			}
		}
		
		new TemplateManager()
	}
	
}
