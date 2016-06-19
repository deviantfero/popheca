package gui.proc;

import java.io.File;
import javafx.scene.Scene;

public class Loader {
	public static void loadCss( String filename, Scene applyScene ) {
		File f = new File("bin/gui/styles/" + filename );
		File f2 = new File("src/gui/styles/" + filename );
		/*when it compiles it copies it to the bin folder
		 *so it's necessary to have them on both dirs
		 *make changes on src dir */
		if( f2.exists() )
			applyScene.getStylesheets().add( "file:///" + f.getAbsolutePath() );
		else if( f.exists() )
			applyScene.getStylesheets().add( "file:///" + f.getAbsolutePath() );
		else
			applyScene.getStylesheets().add("/gui/styles/" + filename );
	}
}
