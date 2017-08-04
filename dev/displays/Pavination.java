package com.be3short.jfx.displays;

import java.io.File;
import java.util.ArrayList;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class Pavination
{

	private Pagination pagination;
	public BorderPane resultStage;
	public BorderPane screen;
	ArrayList<ImageView> plots;// graphs=new
	Integer items = 0;

	public Pavination(File plot_directory)
	{
		plots = new ArrayList<ImageView>();
		loadPlots(plot_directory);
	}

	public Pavination(String plot_directory)
	{
		plots = new ArrayList<ImageView>();
		loadPlots(plot_directory);
	}

	private void loadPlots(String plot_directory)
	{
		Integer i = 1;
		try
		{

			while (1 > 0)
			{
				ImageView newView = new ImageView(
				new Image(plot_directory + "/" + i.toString() + ".png", 400.0, 400.0, true, true));
				plots.add(newView);
				i++;
			}
		} catch (Exception e)
		{

		}
		items = plots.size();
	}

	private void loadPlots(File plot_directory)
	{
		for (File file : plot_directory.listFiles())
		{
			try
			{
				System.out.println(file.getPath());
				ImageView image = new ImageView(file.toURL().toString());

				plots.add(image);
			} catch (Exception notImage)
			{
				notImage.printStackTrace();
			}
		}
		items = plots.size();
	}

	public int itemsPerPage()
	{
		return items;
	}

	public BorderPane createPage(int pageIndex)
	{

		BorderPane box = new BorderPane();

		box.setCenter(plots.get(pageIndex));
		// int page = pageIndex * itemsPerPage();
		// for (int i = page; i < page + itemsPerPage(); i++)
		// {
		// BorderPane element = new BorderPane();
		//
		// DataView dv = graphs.get(i);
		// dv = new DataView(environment);
		// element.setCenter(dv);
		// box.setCenter(element);
		// }
		return box;

	}

	public void start(final BorderPane stage) throws Exception
	{
		resultStage = stage;
		pagination = new Pagination(items, 0);
		pagination.setStyle("-fx-border-color:red;");
		pagination.setPageFactory((Integer pageIndex) -> createPage(pageIndex));

		AnchorPane anchor = new AnchorPane();
		AnchorPane.setTopAnchor(pagination, 10.0);
		AnchorPane.setRightAnchor(pagination, 10.0);
		AnchorPane.setBottomAnchor(pagination, 10.0);
		AnchorPane.setLeftAnchor(pagination, 10.0);
		anchor.getChildren().addAll(pagination);
		resultStage.setCenter(anchor);

	}

}
