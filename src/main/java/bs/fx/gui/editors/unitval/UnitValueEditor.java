package bs.fx.gui.editors.unitval;

import java.util.ArrayList;

import bs.commons.dimvars.core.UnitData;
import bs.commons.dimvars.core.UnitValue;
import bs.commons.dimvars.exceptions.UnitException;
import bs.commons.dimvars.units.TimeUnit;
import bs.commons.objects.access.MethodAccessor;
import bs.commons.objects.execution.ContinuousFieldLoader;
import bs.commons.objects.execution.ContinuousFieldLoader.MethodLabel;
import bs.gui.components.input.fieldeditors.ChoiceInput;
import bs.gui.components.input.fieldeditors.ProtectedTextArea;
import bs.gui.components.menu.UserInput;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UnitValueEditor extends UserInput
{

	private UnitValue unitVal;
	private String variableName;
	public ArrayList<ContinuousFieldLoader> fieldLoaders;

	public UnitValueEditor(String variable_name, UnitValue val)
	{
		mainPane = new BorderPane();
		variableName = variable_name;
		unitVal = val;
		System.out.println("and here");
		fieldLoaders = new ArrayList<ContinuousFieldLoader>();
		initialize();
	}

	private void initialize()
	{
		Separator sepp = new Separator();
		sepp.setPrefHeight(10.0);
		//setTop(sepp);
		ContinuousFieldLoader valLoader;
		try
		{
			valLoader = new ContinuousFieldLoader(unitVal.getClass().getField("value"), unitVal, "value");
			ProtectedTextArea inputArea = new ProtectedTextArea(valLoader, MethodLabel.update, unitVal.value,
			variableName);
			//HBox input = (HBox) inputArea.mainPane.getCenter();
			System.out.println(unitVal.toString());
			ContinuousFieldLoader unitLoader = new ContinuousFieldLoader(unitVal.getClass().getField("unit"), unitVal,
			"unit");
			ChoiceInput unitInput = new ChoiceInput(unitLoader, MethodLabel.update, unitVal.unit, "Unit",
			unitVal.unit.getClass().getEnumConstants());
			HBox input = new HBox(new Label("Units: "));
			input.getChildren().add(unitInput.mainPane.getRight());
			if (unitVal.unit.getRate() != null)
			{
				System.out.println(unitVal.getClass());
				unitVal.getClass().getField("rate").setAccessible(true);
				ContinuousFieldLoader rateLoader = new ContinuousFieldLoader(unitVal.getClass().getField("rate"),
				unitVal, "rate");
				ChoiceInput rateInput = new ChoiceInput(unitLoader, MethodLabel.update, unitVal.unit.getRate(), "Unit",
				TimeUnit.values());
				input.getChildren().add(new Label(" per "));
				input.getChildren().add(rateInput.mainPane.getRight());

				fieldLoaders.add(rateLoader);
			}
			fieldLoaders.add(valLoader);
			fieldLoaders.add(unitLoader);
			input.setAlignment(Pos.CENTER_LEFT);
			input.setPadding(new Insets(5, 0, 3, 0));
			String unitStr = "";
			try
			{
				unitStr = UnitData.getUnitData(unitVal.unit).unitAbbreviation;
				if (unitVal.rate != null)
				{
					unitStr += "/" + UnitData.getUnitData(unitVal.unit).unitAbbreviation;
				}
				unitStr = variableName + "(" + unitStr + ")";
			} catch (UnitException e)
			{

				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ToggleButton showUnits = new ToggleButton(unitStr);
			input.setAlignment(Pos.CENTER_RIGHT);
			HBox textInput = (HBox) inputArea.mainPane.getRight();
			//	textInput.setPrefWidth(280.0);
			showUnits.setOnAction(new EventHandler<ActionEvent>()
			{

				public void handle(ActionEvent event)
				{
					if (showUnits.isSelected())
					{
						showUnits.setText(variableName);
						//inputArea.mainPane.setRight(input);
						mainPane.setBottom(input);
					} else
					{
						mainPane.setBottom(null);
						//inputArea.mainPane.setRight(textInput);

						try
						{
							String unitStr = UnitData.getUnitData(unitVal.unit).unitAbbreviation;
							if (unitVal.rate != null)
							{
								unitStr += "/" + UnitData.getUnitData(unitVal.unit).unitAbbreviation;
							}
							showUnits.setText(variableName + "(" + unitStr + ")");

						} catch (UnitException e)
						{

							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
			//setBottom(input);
			showUnits.setSelected(false);

			//setTop(inputArea.mainPane);
			mainPane.setCenter(inputArea.mainPane);
			//Separator sep = new Separator();
			//HBox boxx = (HBox) inputArea.mainPane.getRight();
			//inputArea.mainPane.setCenter(null);
			//boxx.getChildren().add(0, showUnits);
			//inputArea.mainPane.setLeft(null);
			//mainPane.setCenter(inputArea.mainPane);
			mainPane.setLeft(showUnits);

			//			Stage s = new Stage();
			//			s.setScene(new Scene(mainPane));
			//			s.show();

		} catch (NoSuchFieldException | SecurityException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
