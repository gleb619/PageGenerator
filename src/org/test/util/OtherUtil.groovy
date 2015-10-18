package org.test.util

class OtherUtil {

	def toMethodName(String fileName){
		String method = toCamelCase(fileName.replaceFirst(~/\.[^\.]+$/, ''))
		method.substring(0, 1).toLowerCase() + method.substring(1)
	}
	
	def toCamelCase(String s){
		String[] parts = s.split("_");
		String camelCaseString = "";
		for (String part : parts){
		   camelCaseString = camelCaseString + toProperCase(part);
		}
		
		camelCaseString;
	 }
	 
	 def toProperCase(String s) {
		 s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
	 }
	
}
