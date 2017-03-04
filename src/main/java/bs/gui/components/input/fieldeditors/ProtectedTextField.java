package bs.gui.components.input.fieldeditors;

import java.util.ArrayList;
import java.util.regex.Pattern;

import bs.commons.dimvars.values.InitialValue;
import bs.commons.objects.expansions.Range;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

public class ProtectedTextField<T>
{

	private ObjectProperty<T> parsedObject;

	public ObjectProperty<T> getParsedObject()
	{
		return parsedObject;
	}

	private boolean inputIsRange;
	private T initialValue;

	public TextField getInput()
	{
		return input;
	}

	public ImageView getStatusIcon()
	{
		return statusIcon;
	}

	private String prompt;
	private TextField input;
	private ImageView statusIcon;
	private InitialValue random;

	public ProtectedTextField(T initial_value, String prompt)//, Class<T> range_class)
	{
		parsedObject = new SimpleObjectProperty<T>(initial_value);
		initialValue = initial_value;
		this.prompt = prompt;
		//inputClass = range_class;
		initialize();
	}

	//	public ProtectedTextField(T initial_value, String prompt, Class<T> input_class)
	//	{
	//		parsedObject = new SimpleObjectProperty<T>(initial_value);
	//		initialValue = initial_value;
	//		this.prompt = prompt;
	//		inputClass = input_class;
	//		initialize();
	//	}
	private void initialize()
	{
		initializeTextField();
		initializeStatusIcon();
		initializeActions();
	}

	private void initializeStatusIcon()
	{
		statusIcon = new ImageView();
		statusIcon.setImage(new Image("icons/GreenCheckMark.png", 20.0, 20.0, true, true));
	}

	private void initializeTextField()
	{
		input = new TextField();
		if (initialValue != null)
		{
			input.setText(initialValue.toString());
		}
		if (prompt != null)
		{
			input.setPromptText(prompt);
		}
	}

	private void initializeActions()
	{
		input.setOnKeyReleased(new EventHandler<KeyEvent>()
		{

			public void handle(KeyEvent ke)
			{
				checkEntry();
			}
		});
	}

	private void checkEntry()
	{
		try
		{
			if (updateValue())
			{
				statusIcon.setImage(new Image("icons/GreenCheckMark.png", 20.0, 20.0, true, true));
			} else
			{
				statusIcon.setImage(new Image("icons/RedX.png", 20.0, 20.0, true, true));
			}
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private <S> boolean updateValue()
	{
		boolean validValue = true;
		try
		{
			if (initialValue.getClass().equals(Range.class))
			{
				@SuppressWarnings("unchecked")
				ArrayList<S> vals = parseValueSeries(input.getText(), ((Range) parsedObject.get()).getItemClass(), ",");
				if (vals.size() == 2)
				{
					parsedObject
					.set((T) new Range<S>(vals.get(0), vals.get(1), ((Range) parsedObject.get()).getItemClass()));
				} else if (vals.size() == 1)
				{
					parsedObject.set((T) new Range<S>(vals.get(0), null, ((Range) parsedObject.get()).getItemClass()));
				} else
				{
					throw new Exception();
				}
			} else
			{
				T val = (T) parseValue(input.getText(), initialValue.getClass());
				parsedObject.set(val);
			}
		} catch (Exception invalidInput)
		{
			validValue = false;
		}
		return validValue;
	}
	//	@MethodId(id = Actions.getInput)
	//	public <T> T getInputValue()
	//	{
	//		T val = null;
	//		String inputText = input.getText();
	//		
	//	}catch(
	//
	//	Exception notValid)
	//	{
	//	}return val;
	//	}

	public static <S> ArrayList<S> parseValueSeries(String text, Class<S> object_class, String separation_char)
	throws IllegalArgumentException
	{
		ArrayList<S> returnVals = new ArrayList<S>();
		String[] sections = text.split(Pattern.quote(separation_char));
		for (String section : sections)
		{
			returnVals.add(parseValue(section, object_class));
		}
		return returnVals;
	}

	public static <S> S parseValue(String text, Class<S> object_class) throws IllegalArgumentException
	{
		Object returnObject = null;

		if (object_class.equals(Boolean.class))
		{
			returnObject = Boolean.parseBoolean(text);
		} else if (object_class.equals(Double.class))
		{
			returnObject = Double.parseDouble(text);
		} else if (object_class.equals(Integer.class))
		{
			returnObject = Integer.parseInt(text);
		} else
		{
			throw new IllegalArgumentException("Unable to parse " + text + " to " + object_class.getName());
		}
		try
		{
			S returnCasted = (S) object_class.cast(returnObject);
			return returnCasted;
		} catch (Exception e)
		{
			throw new IllegalArgumentException("Error casting " + text + " to " + object_class.getName());
		}
	}

}
