package bs.gui.components.input.fieldeditors;

import bs.commons.objects.execution.ExternalMethodExecutor;
import bs.commons.objects.execution.MethodId;
import bs.gui.components.menu.UserInput;
import bs.gui.components.menu.UserInput.Actions;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class ProtectedTextArea extends UserInput
{

	private TextField input;
	private Text status;
	private ImageView statusIcon;

	public ProtectedTextArea(Object update, String update_id, Object default_value, String name)
	{
		mainPane = new BorderPane();
		updateMethod = update;
		updateMethodId = update_id;
		defaultValue = default_value;
		this.name = name;
		initialize();
	}

	public void updateExternalMethod(Object update, String update_id)
	{
		updateMethod = update;
		updateMethodId = update_id;
	}

	private void initialize()
	{
		initializeGUI();
		initializeActions();
	}

	private void initializeGUI()
	{
		Label title = new Label(name);
		input = new TextField();

		input.setText(defaultValue.toString());
		//status = new Label("Ok");
		statusIcon = new ImageView();
		statusIcon.setImage(new Image("icons/GreenCheckMark.png", 20.0, 20.0, true, true));
		//	statusIcon.resize(10.0, 10.0);
		HBox inputBox = new HBox(input, statusIcon);
		mainPane.setLeft(title);
		mainPane.setRight(inputBox);
	}

	private void initializeActions()
	{
		input.setOnKeyReleased(new EventHandler<KeyEvent>()
		{

			public void handle(KeyEvent ke)
			{
				try
				{
					if (checkIfValidInput())
					{
						try
						{
							executeUpdate();
						} catch (Exception e)
						{

						}
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
		});
	}

	@MethodId(id = Actions.getInput)
	public Object getInputValue()
	{
		Object val = null;
		String inputText = input.getText();
		try
		{
			if (defaultValue.getClass().equals(Double.class))
			{
				val = Double.parseDouble(inputText);
			} else if (defaultValue.getClass().equals(Integer.class))
			{
				val = Integer.parseInt(inputText);
			} else if (defaultValue.getClass().equals(Boolean.class))
			{
				val = Boolean.parseBoolean(inputText);
			} else if (defaultValue.getClass().equals(String.class))
			{
				val = inputText;
			}
		} catch (Exception notValid)
		{
		}
		return val;
	}

}
