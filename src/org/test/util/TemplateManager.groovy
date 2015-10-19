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
	def propertiesMethods = []
//	def engine = new GStringTemplateEngine()
//	def engine = new MarkupTemplateEngine()
	
	public TemplateManager(Object propertiesMethods) {
		super();
		this.propertiesMethods = propertiesMethods;
	}

	def eachPropertyTemplate(Object instance) {
		def output = [:]
		propertiesMethods.each { method ->
			output.put(method, this."$method"(instance))
		}
		
		output
	}
	
	def defaultText(Object instance, String source, def callFrom, def relation, String instanceKey = "instance", String logLevel = "") {
		String output = "NULL, error found"
		String message = ""
		
		SuspiciousUtil suspiciousUtil = new SuspiciousUtil()
		
		if (relation == null) {
			
		}
		else if (instance == null) {
			println "WTF, instance: is null"
		}
		else if(suspiciousUtil.work(relation, instance)){
			println "Catch override rule: ${relation.name}\n\trelation: $relation"
//			return this."$relation.method"(instance, source, null, null);
			source = relation.source
//			return this."$relation.method"(instance);
		}
		
		return defaultTextData(instance, source)
//		try {
//			message += "\n${logLevel}org.test.util.defaultText {" +
//				"\n$logLevel\tinstance: $instance" +
//				"\n$logLevel\tgetting data" +
//				"\n$logLevel\tcreate engine" +
//				"\n$logLevel\tcreate template" +
//				"\n${logLevel}}"
//			def args = [ : ]
//			args.put(instanceKey, instance)
//			args.put("templateManger", this)
//			args.put("test", new Test())
//			
//			def template = engine.createTemplate(source).make(args)
//			output = template.toString()
//			
//			if(output.contains("#SECOND_LEVEL#")){
//				engine = new SimpleTemplateEngine()
//				String newTemplate = output.replace("#SECOND_LEVEL#", '\\$')
//				output = engine.createTemplate(newTemplate).make(args).toString()
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace()
//		}
//		
//		println message
//		output
	}
	
	def defaultTextData(Object instance, String source) {
		String output = "NULL, error found"
		String message = ""
				
		try {
			message += "\norg.test.util.defaultText {" +
					"\n\tinstance: $instance" +
					"\n\tgetting data" +
					"\n\tcreate engine" +
					"\n\tcreate template" +
					"\n}"
			def args = [ : ]
			args.put("instance", instance)
			args.put("templateManger", this)
			args.put("test", new Test())
			
			def template = engine.createTemplate(source).make(args)
			output = template.toString()
			
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
