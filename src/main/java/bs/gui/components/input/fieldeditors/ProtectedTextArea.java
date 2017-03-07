package bs.gui.components.input.fieldeditors;

import bs.commons.objects.execution.MethodId;
import bs.commons.objects.expansions.InitialValue;
import bs.gui.components.menu.UserInput;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
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
	private InitialValue random;

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
		HBox titleBox = new HBox(title);
		titleBox.setPadding(new Insets(0, 10, 0, 0));
		titleBox.setAlignment(Pos.CENTER_LEFT);
		input = new TextField();

		input.setText(defaultValue.toString());
		//status = new Label("Ok");
		statusIcon = new ImageView();
		statusIcon.setImage(new Image("icons/GreenCheckMark.png", 20.0, 20.0, true, true));
		//	statusIcon.resize(10.0, 10.0);

		//inputBox.setCenter(input);
		//inputBox.setRight(statusIcon);
		if (name.length() > 0)
		{
			mainPane.setLeft(titleBox);
			HBox inputBox = new HBox(input, statusIcon);
			inputBox.setAlignment(Pos.CENTER_RIGHT);
			mainPane.setRight(inputBox);
		} else
		{
			mainPane.setCenter(input);
			mainPane.setRight(statusIcon);
		}

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
				if (inputText.substring(0, 1).equals("r"))
				{
					Double minVal = Double.parseDouble(inputText.substring(1).split(",")[0]);
					Double maxVal = Double.parseDouble(inputText.substring(1).split(",")[1]);
					val = new InitialValue(minVal, maxVal);

				} else
				{
					val = Double.parseDouble(inputText);
				}
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
