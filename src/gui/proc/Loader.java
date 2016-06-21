package gui.proc;

import java.io.File;
import javafx.scene.Scene;

public class Loader {
	public static void loadCss( String filename, Scene applyScene ) {
		applyScene.getStylesheets().add("/gui/styles/" + filename );
		System.out.println( "CSS::/gui/styles/" + filename + " LOADED");
	}
}
