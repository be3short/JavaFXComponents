package com.be3short.jfx.connectors;

import java.util.HashMap;

public class Linked<T>
{

	private static HashMap<String, Object> linkedItems = new HashMap<String, Object>();
	private String itemKey;

	public Linked(T item)
	{
		itemKey = item.toString();
		if (!linkedItems.containsKey(itemKey))
		{
			linkedItems.put(itemKey, item);
		}
	}

	@SuppressWarnings("unchecked")
	public T access()
	{
		return (T) linkedItems.get(itemKey);
	}
}
