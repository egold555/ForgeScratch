package org.golde.java.scratchforge.helpers.codeparser;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeComponent {
	private String name;
	private String code;
	private Map<String, String> values = new HashMap<String,String>();
	
	public CodeComponent(String name, String code)
	{
		this.name = name;
		this.code = code;
		parseValues();
	}
	
	public String getName() { return name; }
	
	public String getCode() { return code; }
	
	public String getType() { return getValueAsString("type", ""); }
	
	public String getValueAsString(String key, String defValue) {
		if (values.containsKey(key))
			return values.get(key);
		else
			return defValue;
	}
	
	public int getValueAsInteger(String key, int defValue) {
		if (values.containsKey(key)) {
			try {
				return Integer.parseInt(values.get(key));
			}
			catch (NumberFormatException e) {
				System.out.println("Value for key '" + key + "' is not an integer: " + values.get(key));
				return defValue;
			}
		}
		else {
			return defValue;
		}
	}


	public double getValueAsDouble(String key, double defValue) {
		if (values.containsKey(key)) {
			try {
				return Double.parseDouble(values.get(key));
			}
			catch (NumberFormatException e) {
				System.out.println("Value for key '" + key + "' is not an double: " + values.get(key));
				return defValue;
			}
		}
		else {
			return defValue;
		}
	}


	public boolean getValueAsBoolean(String key, boolean defValue) {
		if (values.containsKey(key)) {
			return Boolean.valueOf(values.get(key).toLowerCase());
		}
		else {
			return defValue;
		}
	}
	
	private void parseValues()
	{
		Pattern pat = Pattern.compile("\\/\\*(.*?):(.*?)\\*\\/");
		Matcher matcher = pat.matcher(code); 
		
		while (matcher.find()) {
			String key = matcher.group(1);
			String val = matcher.group(2);
			values.put(key, val);
		}
	}
}
