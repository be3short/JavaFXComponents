package com.be3short.jfx.gui.dualmenu.test;

import com.be3short.ui.menu.MenuDefinition;

public enum GUITestMenuA implements MenuDefinition
{

	SUBSUB_A1(
		"SubSub A 1"),
	SUBSUB_A2(
		"SubSub A 2"),
	SUBSUB_B1(
		"SubSub B 1"),
	SUBSUB_B2(
		"SubSub B 2"),
	SUB_A1(
		"Sub A 1",
		SUBSUB_A1,
		SUBSUB_A2),
	SUB_A2(
		"Sub A 2"),
	SUB_B1(
		"Sub B 1",
		SUBSUB_B1,
		SUBSUB_B2),
	SUB_B2(
		"Sub B 2"),
	ROOT_A(
		"Root A",
		SUB_A1,
		SUB_A2),
	ROOT_B(
		"Root B",
		SUB_B1,
		SUB_B2),
	MENU(
		"Menu",
		ROOT_A,
		ROOT_B);

	private MenuDefinition[] subMenuItems;
	private String label;

	private GUITestMenuA(String label, MenuDefinition... sub_menus)
	{
		this.label = label;
		if (sub_menus.length > 0)
		{
			this.subMenuItems = sub_menus;
		}
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
		return subMenuItems;
	}

}
