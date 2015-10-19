package org.test.util

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;

import groovy.io.FileType

class TemplateUtil {

	def templates = [:]
	def relationMapping = [:]
	def properties = []
	def settings
	OtherUtil otherUtil = new OtherUtil()
	
	public TemplateUtil(Object settings) {
		super();
		this.settings = settings;
	}

	def init(){
		ClassLoader loader = this.class.getClassLoader();
		InputStream inputStream = loader.getResourceAsStream("templates/");
		BufferedReader rdr = new BufferedReader(new InputStreamReader(inputStream));
		String line;
		while ((line = rdr.readLine()) != null) {
			def file = this.getClass().getResource("/templates/$line").getFile()
			templates.put(line, new File(file).text)
			
			if (line.toString().contains("property")) {
				properties << otherUtil.toMethodName(line)
			}
			
			settings.dataTypes.each { dataType ->
				println "\tWork with $dataType.name"
				def relKey = otherUtil.toMethodName(dataType.originTemplate)
				def keyToCompare = otherUtil.toMethodName(line)
				
				if (relKey.equals(keyToCompare)) {
					dataType.method = otherUtil.toMethodName(dataType.originTemplate)
					
					if(relationMapping[relKey] != null){
						relationMapping[relKey] << dataType
					}
					else {
						relationMapping.put(relKey, dataType)
					}
				}
			}
			
		}
		rdr.close();
	}
	
	def createManager(){
		relationMapping.each { key, value ->
			value.source = templates[value.template]
		}
		
		templates.each { key, value ->
			TemplateManager.metaClass."${otherUtil.toMethodName(key)}" = { data ->
				String template = ""
				try {
					template = defaultText(data, value, otherUtil.toMethodName(key), relationMapping[otherUtil.toMethodName(key)])
				} catch (Exception e) {
					e.printStackTrace()
				}
				
				return template
			}
		}
		
		new TemplateManager(properties)
	}
	
}
