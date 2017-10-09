package com.be3short.jfx.properties.layout;

import java.util.HashMap;

public class ContainerLayoutProperties
{

	private HashMap<Integer, LayoutProperties> childProperties;

	public ContainerLayoutProperties()
	{
		childProperties = new HashMap<Integer, LayoutProperties>();
	}

	public void addProperties(LayoutProperties props)
	{
		childProperties.put(childProperties.size(), props);
	}

	public LayoutProperties getProperties(Integer index)
	{
		LayoutProperties properties = null;
		if (childProperties.containsKey(index))
		{
			properties = childProperties.get(index);
		}
		return properties;
	}
}
