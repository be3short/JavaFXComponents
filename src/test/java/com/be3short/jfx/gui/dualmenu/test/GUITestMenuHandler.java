package com.be3short.jfx.gui.dualmenu.test;

import com.be3short.ui.menu.MenuDefinition;
import com.be3short.ui.menu.MenuHandler;

public class GUITestMenuHandler implements MenuHandler
{

	@Override
	public Object[] getEventParameters(MenuDefinition arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MenuDefinition getMenu()
	{
		// TODO Auto-generated method stub
		return GUITestMenu.MENU;
	}

	@Override
	public Object handleEvent(MenuDefinition arg0, Object... arg1)
	{
		System.out.println(arg0.toString());
		return null;
	}

}
