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

	private static RSArea areaToPaint;

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
	}

	public static void paintArea(RSArea rsArea) {
		areaToPaint = rsArea;
	}

	@Override
	public void onPaint(final Graphics graphics) {
		if (areaToPaint != null) {
			graphics.drawString("Painting Area", 30, 30);
			for (RSTile tile : areaToPaint.getAllTiles()) {
				graphics.drawPolygon(Projection.getTileBoundsPoly(tile, 0));
			}
		}
	}
}
