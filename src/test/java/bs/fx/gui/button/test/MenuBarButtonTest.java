package bs.fx.gui.button.test;

import bs.fx.gui.button.MenuBarButton;
import bs.fx.organization.identifier.MenuIdentifier;
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuBarButtonTest extends Application
{

	public static void main(String args[])
	{
		launch(args);
	}

	private static enum TestViewType implements MenuIdentifier
	{
		A(
			"A"),
		B(
			"B");

		private final String labelName;

		private TestViewType(String name)
		{
			labelName = name;
		}

		@Override
		public String getLabel()
		{
			// TODO Auto-generated method stub
			return labelName;
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		ObjectProperty<MenuIdentifier> prop = new SimpleObjectProperty<MenuIdentifier>(TestViewType.A);
		MenuBarButton a = new MenuBarButton(TestViewType.A, prop);
		MenuBarButton b = new MenuBarButton(TestViewType.B, prop);
		TextField testOutput = new TextField();
		testOutput.setEditable(false);
		prop.addListener(new ChangeListener<MenuIdentifier>()
		{

			@Override
			public void changed(ObservableValue<? extends MenuIdentifier> observable, MenuIdentifier oldValue,
			MenuIdentifier newValue)
			{
				testOutput.setText(newValue.getLabel() + " selected");
			}
		});
		primaryStage.setScene(new Scene(new VBox(a.getMainPane(), b.getMainPane(), testOutput)));
		primaryStage.show();
	}
}
