package com.be3short.jfx.properties.layout;

import java.util.HashMap;

public class LayoutProperties
{

	public Double width;
	public Double height;
	public Double xPosition;
	public Double yPosition;

	public LayoutProperties(Double width, Double height, Double x, Double y)
	{
		this.width = width;
		this.height = height;
		this.xPosition = x;
		this.yPosition = y;
	}

	public LayoutProperties(Double[] layout)
	{
		this.width = layout[0];
		this.height = layout[1];
		this.xPosition = layout[2];
		this.yPosition = layout[3];
	}

	public LayoutProperties()
	{
		this.width = 400.0;
		this.height = 400.0;
		this.xPosition = 0.0;
		this.yPosition = 0.0;
	}
}
