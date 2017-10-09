package com.be3short.jfx.buttons;

import java.util.Arrays;
import java.util.HashMap;

import com.be3short.jfx.event.menu.MenuDefinition;
import com.be3short.jfx.event.menu.FxEventHandler;

import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;

public class SelectableQuantityButton extends FxEventHandler
{

	private HashMap<MenuDefinition, Integer> quantities;
	private SplitMenuButton button;
	private Integer quantity;
	private String prefix;

	public SelectableQuantityButton(Integer total)
	{
		quantity = 1;
		prefix = "Add";
		initializeListItems(total);
		super.loadMenuDefinitions(false, quantities.keySet().toArray(new MenuDefinition[quantities.size()]));
		initializeButton();
	}

	public SelectableQuantityButton(Integer total, String prefix)
	{
		quantity = 1;
		this.prefix = prefix;
		initializeListItems(total);
		super.loadMenuDefinitions(false, quantities.keySet().toArray(new MenuDefinition[quantities.size()]));
		initializeButton();
	}

	public SplitMenuButton getButton()
	{
		return button;
	}

	public Integer getQuantity()
	{
		return quantity;
	}

	private void initializeButton()
	{
		button = new SplitMenuButton();
		button.setText(prefix + " (1)");
		MenuDefinition[] menus = new MenuDefinition[quantities.size()];
		for (MenuDefinition info : quantities.keySet())
		{
			QuantityDefinition quant = (QuantityDefinition) info;
			menus[quant.number - 1] = info;
		}
		MenuItem[] menuz = new MenuItem[quantities.size()];
		for (MenuItem menu : super.menuDefinitionMap.keySet())
		{
			MenuDefinition quant = super.menuDefinitionMap.get(menu);
			menuz[Arrays.asList(menus).indexOf(quant)] = menu;
		}
		button.getItems().addAll(menuz);
	}

	private void initializeListItems(Integer total)
	{
		quantities = new HashMap<MenuDefinition, Integer>();
		for (Integer i = 1; i <= total; i++)
		{
			QuantityDefinition quantity = new QuantityDefinition(i);
			quantities.put(quantity, i);
		}
	}

	@Override
	public Object respondToEvent(Object event_flag)
	{
		Integer newQuantity = quantity;
		try
		{
			MenuDefinition selection = (MenuDefinition) event_flag;
			newQuantity = quantities.get(selection);
			quantity = newQuantity;
			button.setText(prefix + " (" + String.valueOf(quantity) + ")");

		} catch (Exception badFlag)
		{
			badFlag.printStackTrace();
		}
		return newQuantity;
	}

	public static class QuantityDefinition implements MenuDefinition
	{

		private String label;
		public final Integer number;

		public QuantityDefinition(Integer num)
		{
			number = num;
			label = String.valueOf(num);
		}

		@Override
		public String label()
		{
			// TODO Auto-generated method stub
			return label;
		}

		@Override
		public MenuDefinition[] subMenuItems()
		{
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public MenuDefinition[] rootItems()
		{
			// TODO Auto-generated method stub
			return null;
		}

	}

}
