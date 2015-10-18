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
	
	
	def defaultText(Object[] instances, String source, String logLevel = "", String instanceKey = "instances") {
		String output = "NULL, error found"
		String message = ""	
			
		try {
			message += "\n${logLevel}org.test.util.defaultText$L {" +
					"\n$logLevel\tinstances: $instances" +
					"\n$logLevel\tgetting data" +
					"\n$logLevel\tcreate engine" +
					"\n$logLevel\tcreate template" +
					"\n${logLevel}}"
			def args = [ : ]
			args.put(instanceKey, instances)
			args.put("templateManger", this)
			args.put("test", new Test())
			
			def template = engine.createTemplate(source).make(args)
			output = template.toString()
		} catch (Exception e) {
			e.printStackTrace()
		}
		
		
		println message
		output
	}
	
	def defaultText(Object instance, String source, String logLevel = "", String instanceKey = "instance") {
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
		} catch (Exception e) {
			e.printStackTrace()
		}
		
		println message
		output
	}
	
	def defaultText2(def instance, String source, String logLevel = "", def keys) {
		String message = "NULL, error found"
		
		message += "\n${logLevel}org.test.util.defaultText2 {" +
				"\n$logLevel\tinstance: $instance" +
				"\n$logLevel\tgetting data" +
				"\n$logLevel\tcreate engine" +
				"\n$logLevel\tcreate template" +
				"\n${logLevel}}"
		
		def args = [ : ]
		keys.eachWithIndex { it, index ->
			args.put(it, instance[index])
		}
		args.put("templateManger", this)
		args.put("test", new Test())
		
		def template = engine.createTemplate(source).make(args)
		
		println message
		template
	}
	
}
