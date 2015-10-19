package org.test.reader

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.persistence.Table

import org.test.model.Entity
import org.test.model.Property
import org.test.util.TemplateManager;


class EntityReader {

	private String log = ""
	private TemplateManager templateManager;
	
	public EntityReader(TemplateManager templateManager) {
		super();
		this.templateManager = templateManager;
	}

	public Entity work(File file, Integer index){
		readEntity(readClass(file.text), index)
	}
	
	private Entity readEntity(Class<?> clazz, Integer index){
		Entity entity = new Entity()
		entity.setName(clazz.getSimpleName())
		entity.setTableName(readTableName(clazz))
		entity.setSchemaName(readSchemaName(clazz))
		entity.setSuperClassName(clazz.getSuperclass().getSimpleName())
		entity.setLocalization(clazz.getSimpleName())
		entity.setIndex(index)
		entity.setListCount(numberOfLists(clazz))
		entity.setComplexCount(numberOfComplex(clazz))
		
		PropertyReader convertor = new PropertyReader(clazz, templateManager)
		List<Property> list = Collections.synchronizedList(new ArrayList<Property>());
		
		try {
			List<Field> fields = new ArrayList<Field>()
			fields = Arrays.asList(clazz.getDeclaredFields())
			
			ExecutorService executor = Executors.newWorkStealingPool(10);
			fields.each {
				executor.execute(new Runnable() {
					@Override
					public void run() {
						try {
							if (!suspiciousField(it)) {
								list << convertor.convert(it)
//								templateManager.eachPropertyTemplate(it)
							}
						} catch (Exception e) {
							StringWriter sw = new StringWriter();
							PrintWriter pw = new PrintWriter(sw);
							e.printStackTrace(pw);
							
							log += sw.toString(); 
						}
					}
				})
			}
			executor.shutdown();
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
		
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			
			log += sw.toString(); 
		}
		
		entity.setColumns(list)
		entity.setLog(log)
		entity
	}
	
	public Class<?> readClass(String text){
		GroovyClassLoader gcl = new GroovyClassLoader();
		Class<?> clazz = gcl.parseClass(text);
		
		clazz
	}
	
	private String readTableName(Class<?> clazz){
		String result = ""
		if(clazz.isAnnotationPresent(Table.class)){
			Table table = clazz.getAnnotation(Table.class)
			result = table.name()
		}
		
		result
	}
	
	private String readSchemaName(Class<?> clazz){
		String result = "public"
		if(clazz.isAnnotationPresent(Table.class)){
			Table table = clazz.getAnnotation(Table.class)
			result = table.schema()
		}
		
		result
	}
	
	private Integer numberOfLists(Class<?> clazz){
		Integer list = 0
		PropertyReader convertor = new PropertyReader(clazz)
		Arrays.asList(clazz.getDeclaredFields()).eachWithIndex { it, index ->
			if (convertor.isList(it.type)) {
				list++
			}
		}
		
		list
	}
	
	private Integer numberOfComplex(Class<?> clazz){
		Integer complex = 0
		PropertyReader convertor = new PropertyReader(clazz)
		Arrays.asList(clazz.getDeclaredFields()).eachWithIndex { it, index ->
			if (convertor.isComplex(clazz)) {
				complex++
			}
		}
		
		complex
	}
	
	private Boolean suspiciousField(Field field){
		Boolean result = false
		if(field.getName().contains("\$")){
			result = true
		}
		else if(field.getName().contains("__timeStamp")){
			result = true
		}
		else if(field.getName().contains("metaClass")){
			result = true
		}
		
		result
	}
}
