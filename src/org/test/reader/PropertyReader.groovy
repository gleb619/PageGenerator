package org.test.reader

import java.io.File;
import java.lang.annotation.Annotation
import java.lang.reflect.Field;
import java.lang.reflect.Method
import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import org.test.model.Property

class PropertyReader {
	
	private Class<?> ownerClass
	
	public PropertyReader(Class<?> ownerClass) {
		super();
		this.ownerClass = ownerClass;
	}

	public Property convert(Field field) {
		Property property = new Property()
			
		property.list = isList(field.getType()) 
		property.complex = isComplex(field.getType())
		property.fetchLazy = isFetchLazy(ownerClass, field.getName()) 
		property.name = field.getName() 
		property.columnName = readColumnName(ownerClass, field.getName())
		property.joinColumn = readJoinColumnName(ownerClass, field.getName(), field)
		property.type = field.getType().getSimpleName() 
		property.mappedBy = readMappedBy(ownerClass, field.getName())
		property.simpleType = field.getType().getSimpleName()
		property.sortType = field.getType().getSimpleName()
		property.localization = field.getName().toLowerCase()
		property.dataType = field.getType()
		property.caption = field.getName()
		
		property;
	}

	public List<Property> convert(List<Field> from) {
		List<Property> list = new ArrayList<Property>()
		from.eachWithIndex { it, index ->
			list << convert(it)
		}
		
		list;
	}
	
	public Boolean isComplex(Class<?> clazz){
		Boolean isComplex = true
		
		def listOfNonComplex = [
			  "Boolean"
			, "boolean"
			, "String"
			, "Integer"
			, "int"
			, "Double"
			, "double"
			, "BigDecimal"
			, "BigInteger"
			, "Date"
		]

		isComplex = !listOfNonComplex.contains(clazz.getSimpleName())
	}
	
	public Boolean isList(Class<?> clazz){
		Boolean isList = Collection.class.isAssignableFrom(clazz)
	}
	
	private Boolean isFetchLazy(Class<?> clazz, String name){
		String getterName = "get${name.capitalize()}"
		Method method = loadGetter(clazz, name)
		Boolean result = false;
		
		if (method != null && method.isAnnotationPresent(OneToMany.class)) {
			OneToMany annotInstance = method.getAnnotation(OneToMany.class)
			FetchType fetchType = annotInstance.fetch()
			
			if(fetchType.equals(FetchType.LAZY)){
				result = true;
			}
		}
		
		result
	}
	
	private String readColumnName(Class<?> clazz, String name){
		String getterName = "get${name.capitalize()}"
		Method method = loadGetter(clazz, name)
		String result = ""
		
		if (method != null && method.isAnnotationPresent(Column.class)) {
			Column annotInstance = method.getAnnotation(Column.class)
			result = annotInstance.name()
		}
		
		result
	}
	
	private String readMappedBy(Class<?> clazz, String name){
		String getterName = "get${name.capitalize()}"
		Method method = loadGetter(clazz, name)
		String result = ""
		
		if (method != null && method.isAnnotationPresent(OneToMany.class)) {
			OneToMany annotInstance = method.getAnnotation(OneToMany.class)
			result = annotInstance.mappedBy()
		}
		
		result
	}
	
	private String readJoinColumnName(Class<?> clazz, String name, Field field){
		String getterName = "get${name.capitalize()}"
		Method method = loadGetter(clazz, name)
		String result = ""
		
		if (method != null && method.isAnnotationPresent(JoinColumn.class)) {
			JoinColumn annotInstance = method.getAnnotation(JoinColumn.class)
			result = annotInstance.name()
		}
		else if (method != null && method.isAnnotationPresent(OneToMany.class)){
			OneToMany annotInstance = method.getAnnotation(OneToMany.class)
			String mappedBy = annotInstance.mappedBy()
			String classToOpen = field.getType().toString()
			
			EntityReader classReader = new EntityReader()
			File fileToOpen = FileManager.loadClass(classToOpen + ".java")
			if (fileToOpen != null) {
				Class<?> clazzToOpen = classReader.readClass(fileToOpen.text)
				PropertyReader convertor = new PropertyReader(clazzToOpen)
				Method invertedMethod = convertor.loadGetter(clazzToOpen, mappedBy)
				
				if (invertedMethod.isAnnotationPresent(JoinColumn.class)){
					JoinColumn ivertedAnnotInstance = invertedMethod.getAnnotation(JoinColumn.class)
					result = ivertedAnnotInstance.name()
				}
			}
		}
		
		result
	}
	
	public Method loadGetter(Class<?> clazz, String name){
		String getterName = "get${name.capitalize()}"
		Method result = null
		
		List<Method> allMethods = new ArrayList<Method>(Arrays.asList(clazz.getDeclaredMethods()))
		for (Method method : allMethods) {
			
			if (method.getName().equals(getterName)) {
				result = method
			}
		}
		
		result
	}

}
