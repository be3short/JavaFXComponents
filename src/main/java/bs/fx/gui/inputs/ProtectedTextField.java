package bs.fx.gui.inputs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

public class ProtectedTextField<T> extends UserInput<T>
{

	private boolean inputIsRange;
	private T initialValue;

	public ImageView getStatusIcon()
	{
		return statusIcon;
	}

	private String prompt;
	private TextField inputField;
	private ImageView statusIcon;
	// private InitialValue random;

	public ProtectedTextField(T... initial_value)// , Class<T>
													// range_class)
	{
		selection = new SimpleObjectProperty<ArrayList<T>>();
		ArrayList<T> selections = new ArrayList<T>();
		selections.addAll(Arrays.asList(initial_value));
		selection.set(selections);
		this.prompt = prompt;
		initialValue = initial_value[0];
		// inputClass = range_class;
		initialize();
	}

	// public ProtectedTextField(T initial_value, String prompt, Class<T>
	// input_class)
	// {
	// selection = new SimpleObjectProperty<T>(initial_value);
	// initialValue = initial_value;
	// this.prompt = prompt;
	// inputClass = input_class;
	// initialize();
	// }
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
		status = statusIcon;
	}

	private void initializeTextField()
	{
		inputField = new TextField();
		String text = "";
		try
		{
			text += selection.get().get(0);
			for (int i = 1; i < selection.get().size(); i++)
			{
				text += "," + selection.get().get(i);
			}
		} catch (Exception e)
		{

		}
		inputField.setText(text);
		input = inputField;
	}

	private void initializeRangeText()
	{

	}

	private void initializeActions()
	{
		inputField.setOnKeyReleased(new EventHandler<KeyEvent>()
		{

			public void handle(KeyEvent ke)
			{
				checkEntry();
			}
		});
		inputField.setOnKeyPressed(new EventHandler<KeyEvent>()
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
			// try// (selection.get().size())
			// {
			@SuppressWarnings("unchecked")
			ArrayList<S> vals = (ArrayList<S>) parseValueSeries(inputField.getText(), initialValue.getClass(), ",");
			// selection.get().addAll((Collection<? extends T>) vals);
			selection.set((ArrayList<T>) vals);
			// if (vals.size() == 2)
			// {
			// Range<S> inputVal = new Range<S>(vals.get(0), vals.get(1),
			// ((Range) selection.get()).getItemClass());
			// if (!(inputVal.getLower().equals(((Range)
			// selection.get()).getLower())
			// && (inputVal.getUpper().equals(((Range)
			// selection.get()).getLower()))))
			// {
			// selection
			// .set((T) new Range<S>(vals.get(0), vals.get(1), ((Range)
			// selection.get()).getItemClass()));
			// }
			// } else if (vals.size() == 1)
			// {
			// Range<S> inputVal = new Range<S>(vals.get(0), null, ((Range)
			// selection.get()).getItemClass());
			// if (!(inputVal.getLower().equals(((Range)
			// selection.get()).getLower())))
			// {
			// selection.set((T) new Range<S>(vals.get(0), null, ((Range)
			// selection.get()).getItemClass()));
			// }
			// } else
			// {
			// throw new Exception();
			// }
			// } else
			// {
			// T val = (T) parseValue(inputField.getText(),
			// initialValue.getClass());
			// selection.set(val);
			// }
		} catch (

		Exception invalidInput)
		{
			invalidInput.printStackTrace();
			validValue = false;
		}
		return validValue;
	}
	// @MethodId(id = Actions.getInput)
	// public <T> T getInputValue()
	// {
	// T val = null;
	// String inputText = input.getText();
	//
	// }catch(
	//
	// Exception notValid)
	// {
	// }return val;
	// }

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
		} else if (object_class.equals(String.class))
		{
			returnObject = text;
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
