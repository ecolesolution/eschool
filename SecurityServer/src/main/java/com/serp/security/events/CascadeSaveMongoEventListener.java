/**
 * 
 */
package com.serp.security.events;

import java.lang.reflect.Field;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mapping.model.MappingException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.util.ReflectionUtils;
import com.serp.security.annotaions.CascadeSave;
/**
 * @author sanjeet choudhary
 *
 */
public class CascadeSaveMongoEventListener extends AbstractMongoEventListener<Object> {

	@Autowired
	MongoOperations mongoOperations;

	@Override
	public void onBeforeConvert(BeforeConvertEvent<Object> event) {
		Object source = event.getSource();
		ReflectionUtils.doWithFields(source.getClass(), new ReflectionUtils.FieldCallback() {

			@Override
			public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
				 ReflectionUtils.makeAccessible(field);
				 
				    if (field.isAnnotationPresent(DBRef.class) && 
				      field.isAnnotationPresent(CascadeSave.class)) {
				     
				        Object fieldValue = field.get(source);
				        if (fieldValue != null) {
				            FieldCallback callback = new FieldCallback();
				            ReflectionUtils.doWithFields(fieldValue.getClass(), callback);
				            mongoOperations.save(fieldValue);
				        }
				    }
				
			}
			
		});
	}
}
