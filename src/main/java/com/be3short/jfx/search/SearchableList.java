package com.be3short.jfx.search;

import com.be3short.jfx.search.SearchInput.SearchEvent;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

public class SearchableList<T> extends BorderPane implements EventResponder
{

	private HashMap<String, T> listItems;
	private ListView<String> list;
	private ArrayList<String> filteredList;
	private SearchInput search;

	public SearchInput getSearch()
	{
		return search;
	}

	public SearchableList(HashMap<String, T> list_items)
	{
		listItems = list_items;
		initialize();
	}

	public ListView<String> getList()
	{
		return list;
	}

	private void initialize()
	{
		list = new ListView<String>();
		filteredList = new ArrayList<String>();
		list.getItems().addAll(listItems.keySet());
		search = new SearchInput(this, Actions.filterList, Actions.clearFilter);
		setCenter(list);
		setBottom(search);
	}

	public void applyFilter(String filter)
	{
		list.getItems().clear();
		for (String name : listItems.keySet())
		{
			if (name.contains(filter))
			{
				list.getItems().add(name);
			}
		}
		// list.getItems().clear();
		// list.getItems().addAll(filteredList);
	}

	public T getSelectedObject()
	{
		T selected = null;
		try
		{
			String selectedName = list.getSelectionModel().getSelectedItem();
			selected = (T) listItems.get(selectedName);
		} catch (Exception e)
		{

		}
		return selected;
	}

	public void addToSearchableList(HashMap<String, T> list_items)
	{
		for (String key : list_items.keySet())
		{
			listItems.put(key, list_items.get(key));
		}
	}

	public static class Actions
	{

		public static final String filterList = "Filter List";
		public static final String clearFilter = "Clear filter";
	}

	@Override
	public void newEvent(Object location)
	{
		if (location.equals(SearchEvent.SEARCH_CLEARED))
		{
			applyFilter("");
		}

	}

	@Override
	public void newEvent(Object source, Object selection)
	{
		// TODO Auto-generated method stub
		if (source.equals(SearchEvent.NEW_CHARACTERS))
		{
			applyFilter(selection.toString());
		}
	}
}
