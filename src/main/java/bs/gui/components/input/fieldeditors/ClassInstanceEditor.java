package bs.gui.components.input.fieldeditors;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import bs.commons.objects.access.FieldAccessor;
import bs.commons.objects.execution.ExternalFieldUpdate;
import bs.commons.objects.execution.MethodId;
import bs.commons.objects.labeling.Label;
import bs.commons.objects.labeling.LabelReader;
import bs.commons.objects.manipulation.ObjectCloner;
import bs.gui.components.actions.ActionButton;
import bs.gui.components.menu.UserInput;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ClassInstanceEditor extends UserInput
{

	public Object initialObject;
	public BorderPane mainPane;
	public ScrollPane fieldScroll;
	public ArrayList<ExternalFieldUpdate> fieldUpdaters;
	public Object editedObject;

	@Override
	public boolean checkIfValidInput()
	{
		return true;
	}

	@Override
	public boolean executeUpdate()
	{
		return true;
	}

	public ClassInstanceEditor(Object edit_object)
	{
		editedObject = edit_object;
		initialObject = ObjectCloner.xmlClone(editedObject);
		initializeGUI();

	}

	private void initializeGUI()
	{
		fieldUpdaters = new ArrayList<ExternalFieldUpdate>();
		mainPane = new BorderPane();
		fieldScroll = new ScrollPane();
		mainPane.setCenter(fieldScroll);
		generateInputs();
		setupButtons();

	}

	@MethodId(id = Actions.clearChanges)
	private void generateInputs()
	{
		// editedObject = ObjectCloner.xmlClone(initialObject);
		VBox inputs = getInputBox(getInputs());
		fieldScroll.setContent(inputs);
		fieldScroll.setFitToWidth(false);
	}

	private HashMap<String, UserInput> getInputs()
	{
		HashMap<String, UserInput> inputAreas = new HashMap<String, UserInput>();
		HashMap<String, Field> fields = FieldAccessor.getObjectFields(editedObject, true);
		for (String fieldName : fields.keySet())
		{
			Field field = fields.get(fieldName);
			Object initialVal;
			try
			{
				initialVal = field.get(initialObject);
				// field.set(editedObject, initialVal);
			} catch (IllegalArgumentException | IllegalAccessException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String name = LabelReader.getLabel(Label.class, field);
			ExternalFieldUpdate update = new ExternalFieldUpdate(field, editedObject, name);
			fieldUpdaters.add(update);
			UserInput input = InputSorter.getAppropriateInput(update);
			inputAreas.put(fieldName, input);
		}
		return inputAreas;
	}

	private VBox getInzputBox(HashMap<String, UserInput> inputs)
	{
		VBox inputBox = new VBox();
		ArrayList<String> fieldNames = new ArrayList<String>(inputs.keySet());
		Collections.sort(fieldNames);
		HashMap<Class, ArrayList<UserInput>> sortedByClass = new HashMap<Class, ArrayList<UserInput>>();
		sortedByClass.put(BooleanInput.class, new ArrayList<UserInput>());
		sortedByClass.put(ChoiceInput.class, new ArrayList<UserInput>());
		sortedByClass.put(ProtectedTextArea.class, new ArrayList<UserInput>());
		for (String fieldName : fieldNames)
		{
			try
			{
				System.out.println(fieldName);
				sortedByClass.get(inputs.get(fieldName).getClass()).add(inputs.get(fieldName));
			} catch (Exception badField)
			{
				badField.printStackTrace();
			}
		}
		Class[] classOrder =
		{ BooleanInput.class, ChoiceInput.class, ProtectedTextArea.class };
		for (Class inputClass : classOrder)
		{
			for (UserInput ui : sortedByClass.get(inputClass))
			{
				inputBox.getChildren().add(ui.mainPane);
			}
		}
		return inputBox;
	}

	private VBox getInputBox(HashMap<String, UserInput> inputs)
	{
		VBox inputBox = new VBox();
		ArrayList<String> fieldNames = new ArrayList<String>(inputs.keySet());
		Collections.sort(fieldNames);

		for (String fieldName : fieldNames)
		{

			System.out.println(fieldName);
			// (inputs.get(fieldName).getClass()).add(inputs.get(fieldName));
			try
			{
				inputBox.getChildren().add(inputs.get(fieldName).mainPane);
			} catch (Exception e)
			{

			}
		}
		return inputBox;
	}

	private void setupButtons()
	{
		ActionButton clear = new ActionButton(Actions.clearChanges, this, Actions.clearChanges);
		BorderPane buttons = new BorderPane();
		buttons.setLeft(clear.getButton());
		mainPane.setBottom(buttons);
	}

	public static class Actions
	{

		public static final String clearChanges = "Clear";
		public static final String save = "Save";
		public static final String load = "Load";
	}

}
