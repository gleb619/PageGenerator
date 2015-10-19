package org.test.util

import org.test.model.Entity;
import org.test.model.Property;

class SuspiciousUtil {

	def work(def source, def info){
		Boolean result = false

		if (info instanceof Entity) {
		}
		else if (info instanceof Property) {
			if(source["classContains"] != null){
				if(info.dataType.simpleName.toLowerCase().contains(source["classContains"].toLowerCase())){
					result = true
				}
			}
		}

		result
	}
	
}
