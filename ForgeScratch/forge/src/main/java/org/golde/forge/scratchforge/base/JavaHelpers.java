package org.golde.forge.scratchforge.base;

import java.lang.reflect.Field;

public class JavaHelpers {

	public static Field getField(Class<?> clazz, String fieldName) throws RuntimeException{
	    Class<?> tmpClass = clazz;
	    do {
	        try {
	            Field f = tmpClass.getDeclaredField(fieldName);
	            return f;
	        } catch (NoSuchFieldException e) {
	            tmpClass = tmpClass.getSuperclass();
	        }
	    } while (tmpClass != null);

	    throw new RuntimeException("Field '" + fieldName + "' not found on class " + clazz);
	}
	
}
