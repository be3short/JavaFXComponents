package com.be3short.jfx.image;

public enum ImageIOFormat
{
	BMP(
		"bmp"),
	JPG(
		"jpg"),
	WBMP(
		"wbmp"),
	GIF(
		"gif"),
	PNG(
		"png");

	public final String formatName;

	private ImageIOFormat(String format_name)
	{
		formatName = format_name;
	}
}
