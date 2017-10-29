package com.be3short.jfx.textarea;

import com.be3short.data.cloning.ObjectCloner;
import com.be3short.io.xml.XMLParser;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class XMLEditor<T>
{

	private ObjectProperty<T> object;
	private ObjectProperty<T> draft;
	private BorderPane display;
	private ScrollFreeTextArea text;

	public XMLEditor(ObjectProperty<T> obj)
	{
		initialize(obj);
	}

	private void initialize(ObjectProperty<T> obj)
	{
		display = new BorderPane();
		text = new ScrollFreeTextArea();
		display.setCenter(text);
		draft = new SimpleObjectProperty<T>();
		initializeTextListener();
		loadObject(obj);
		text.getTextArea().setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
	}

	public void loadObject(ObjectProperty<T> obj)
	{
		object = obj;
		draft.set((T) ObjectCloner.xmlClone(obj.getValue()));
		text.getTextArea().setText(XMLParser.serializeObject(object.getValue()));

	}

	private void initializeTextListener()
	{
		text.getTextArea().setOnKeyReleased(new EventHandler<KeyEvent>()
		{

			public void handle(KeyEvent event)
			{
				Thread thread = new Thread(new Runnable()
				{

					public void run()
					{
						try
						{
							T parsed = (T) XMLParser.getObjectFromString(text.getTextArea().getText());
							draft.set(parsed);
							text.getTextArea()
							.setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
						} catch (Exception e)
						{
							text.getTextArea().setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
						}
					}
				});

				thread.start();
			}
		});

	}

	public ObjectProperty<T> getObject()
	{
		return object;
	}

	public ObjectProperty<T> getDraft()
	{
		return draft;
	}

	public BorderPane getDisplay()
	{
		return display;
	}

	public TextArea getText()
	{
		return text.getTextArea();
	}
}
