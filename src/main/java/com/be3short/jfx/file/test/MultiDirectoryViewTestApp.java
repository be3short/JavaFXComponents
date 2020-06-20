
package com.be3short.jfx.file.test;

import com.be3short.jfx.file.FileBrowser;
import com.be3short.jfx.file.MultiFileBrowser;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MultiDirectoryViewTestApp extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		MultiFileBrowser mb = new MultiFileBrowser();

		// FileBrowser fb = new FileBrowser(ta, st, null);
		Scene s = new Scene(mb.getBrowseWindow(), 500, 500);
		primaryStage.setScene(s);
		primaryStage.show();
	}

	public static void main(String[] args) {

		FileBrowser.addAllowedExtension(".xml");
		Application.launch(args);

	}
}