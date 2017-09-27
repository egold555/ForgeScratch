package org.golde.java.scratchforge.helpers.codeparser;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.golde.java.scratchforge.helpers.PLog;

public class CodeParser {
	private List<CodeComponent> components;
	
	// Parse the code into a list of CodeComponents. 
	public void parseCode(String code)
	{
		Pattern patStart = Pattern.compile("\\/\\*BEGIN:(.*?)\\*\\/");
		Matcher matcherStart = patStart.matcher(code); 
		Pattern patEnd = Pattern.compile("\\/\\*END:(.*?)\\*\\/");
		Matcher matcherEnd = patEnd.matcher(code); 
		
		components = new ArrayList<CodeComponent>();
		
		while (matcherStart.find()) {
			String name = matcherStart.group(1);
			int startIndex = matcherStart.end();
			if (matcherEnd.find(startIndex)) {
				String endName = matcherEnd.group(1);
				if (! endName.equals(name)) {
					PLog.error("END for component: " + name + " has wrong name.");
				}
				int endIndex = matcherEnd.start();
				String componentCode = code.substring(startIndex, endIndex);
				CodeComponent component = new CodeComponent(name, componentCode);
				components.add(component);
			}
			else {
				PLog.error("No matching END for component: " + name);
			}
		}
	}
	
	// Get all of the CodeComponents.
	public List<CodeComponent> getComponents()
	{
		return components;
	}
	
	// Get the code components of a particular type.
	public List<CodeComponent> getComponentsOfType(String type) 
	{
		List<CodeComponent> result = new ArrayList<CodeComponent>();
		
		for (CodeComponent c: components) {
			if (c.getType().equals(type)) {
				result.add(c);
			}
		}
		
		return result;
	}
}
