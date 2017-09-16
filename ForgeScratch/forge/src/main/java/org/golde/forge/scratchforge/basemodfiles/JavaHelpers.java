package org.golde.forge.scratchforge.basemodfiles;

import java.lang.reflect.Field;
import java.util.List;

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
	
	public static String joinStrings(List<String> list, String conjunction, int iequals)
	{
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for(int i = iequals; i < list.size(); ++i)
		{
			String item = list.get(i);

			if (first)
				first = false;
			else
				sb.append(conjunction);
			sb.append(item);
		}
		return sb.toString();
	}
	
	public static String makeJavaId(String name) {
		String result = "";
		for (int i = 0; i < name.length(); ++i) {
			char c = name.charAt(i);
			if (isJavaId(c)) {
				result = result + c;
			}
			else {
				result = result + "_";
			}
		}

		return result.toLowerCase();
	}

	public static boolean isJavaId(char c) {
		if (c >= 'A' && c <= 'Z')
			return true;
		else if (c >= 'a' && c <= 'z') 
			return true;
		else if (c >= '0' && c <= '9') 
			return true;

		return false;
	}
	
}
