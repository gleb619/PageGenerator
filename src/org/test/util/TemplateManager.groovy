package org.test.util

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.test.reader.Test

import groovy.io.FileType
import groovy.text.GStringTemplateEngine
import groovy.text.SimpleTemplateEngine
import groovy.text.markup.MarkupTemplateEngine

class TemplateManager {

	def engine = new SimpleTemplateEngine()
//	def engine = new GStringTemplateEngine()
//	def engine = new MarkupTemplateEngine()
	
	
	def defaultText(Object instance, String source, String instanceKey = "instance", String logLevel = "") {
		String output = "NULL, error found"
		String message = ""
		
		try {
			message += "\n${logLevel}org.test.util.defaultText {" +
				"\n$logLevel\tinstance: $instance" +
				"\n$logLevel\tgetting data" +
				"\n$logLevel\tcreate engine" +
				"\n$logLevel\tcreate template" +
				"\n${logLevel}}"
			def args = [ : ]
			args.put(instanceKey, instance)
			args.put("templateManger", this)
			args.put("test", new Test())
			
			def template = engine.createTemplate(source).make(args)
			output = template.toString()
			
			println "before: $output\n\n-----------\n"
			
			if(output.contains("#SECOND_LEVEL#")){
				engine = new SimpleTemplateEngine()
				String newTemplate = output.replace("#SECOND_LEVEL#", '\\$')
				output = engine.createTemplate(newTemplate).make(args).toString()
			}
			
		} catch (Exception e) {
			e.printStackTrace()
		}
		
		println message
		output
	}
	
}
