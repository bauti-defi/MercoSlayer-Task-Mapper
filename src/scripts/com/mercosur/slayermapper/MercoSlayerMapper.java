package scripts.com.mercosur.slayermapper;

import org.tribot.api2007.Projection;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Painting;
import org.tribot.util.Util;
import scripts.com.mercosur.slayermapper.gui.GUI;

import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

@ScriptManifest(name = "MercoSlayerMapper", authors = {"Mercosur"}, category = "Tools", gameMode = 1)
public class MercoSlayerMapper extends Script implements Painting {

	private GUI gui;

	private URL fxml;

	public static RSArea monsterArea;

	@Override
	public void run() {
		try {
			fxml = new File(Util.getWorkingDirectory() + File.separator + "bin" + File.separator + "scripts" + File.separator + "com" + File.separator + "mercosur" + File.separator + "slayermapper" + File.separator + "gui" + File.separator + "mapper.fxml").toURI().toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		gui = new GUI(fxml);
		gui.show();
		do {
			sleep(500);
		} while (gui.isOpen());
		this.suspendScript(false, false);
	}

	@Override
	public void onPaint(final Graphics graphics) {
		if (monsterArea != null) {
			graphics.drawString("testing", 30, 30);
			for (RSTile tile : monsterArea.getAllTiles()) {
				graphics.drawPolygon(Projection.getTileBoundsPoly(tile, 0));
			}
		}
	}
}
