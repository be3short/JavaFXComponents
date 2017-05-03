package bs.commons.gui.search;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

public class SearchableList<T> extends BorderPane
{

	private HashMap<String, T> listItems;
	private ListView<String> list;
	private ArrayList<String> filteredList;
	private SearchInput search;

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
		list.getItems().addAll(listItems.keySet());
		search = new SearchInput(this, Actions.filterList, Actions.clearFilter);
		setCenter(list);
		setTop(search);
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
		list.getItems().clear();
		list.getItems().addAll(filteredList);
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
}
