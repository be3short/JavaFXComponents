package com.be3short.jfx.connectors.application;

public class BasicDisplayWindowSettings
{

	public String windowLabelPrefix;
	public Double width;
	public Double height;

	public BasicDisplayWindowSettings()
	{

		windowLabelPrefix = null;
		width = 400.0;
		height = 400.0;
	}

	public BasicDisplayWindowSettings(String window_prefix)
	{

		windowLabelPrefix = window_prefix;
		width = 400.0;
		height = 400.0;
	}

	public BasicDisplayWindowSettings(String window_prefix, Double width, Double height)
	{
		windowLabelPrefix = window_prefix;
		this.width = width;
		this.height = height;
	}
}