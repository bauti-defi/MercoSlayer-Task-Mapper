package scripts.com.mercosur.slayermapper;

import org.tribot.api.input.Mouse;
import org.tribot.api2007.Player;
import org.tribot.api2007.Projection;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.EventBlockingOverride;
import org.tribot.script.interfaces.Painting;
import org.tribot.script.interfaces.PreEnding;
import org.tribot.util.Util;
import scripts.com.mercosur.slayermapper.gui.GUI;
import scripts.com.mercosur.slayermapper.util.MonsterAreaGenerator;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

@ScriptManifest(name = "MercoSlayerMapper", authors = {"Mercosur"}, category = "Tools", gameMode = 1)
public class MercoSlayerMapper extends Script implements Painting, PreEnding, EventBlockingOverride {

	public static MonsterAreaGenerator monsterAreaGenerator;

	private static RSArea generalArea;

	private GUI gui;

	private URL fxml;

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
		} while (gui.isShowing());
	}

	@Override
	public void onPaint(final Graphics graphics) {
		if (monsterAreaGenerator != null) {
			generalArea = new RSArea(Player.getPosition(), 30);
			graphics.setColor(Color.WHITE);
			for (RSTile generalTile : generalArea.getAllTiles()) {
				graphics.drawPolygon(Projection.getTileBoundsPoly(generalTile, 0));
			}
			monsterAreaGenerator.onPaint(graphics);
		}
	}

	@Override
	public void onPreEnd() {
		if (gui != null) {
			gui.close();
		}
	}

	@Override
	public OVERRIDE_RETURN overrideKeyEvent(final KeyEvent keyEvent) {
		return OVERRIDE_RETURN.PROCESS;
	}

	@Override
	public OVERRIDE_RETURN overrideMouseEvent(final MouseEvent mouseEvent) {
		if (mouseEvent.getButton() == 2 && monsterAreaGenerator != null) {
			final Point mousePoint = Mouse.getPos();
			RSTile closestTile = null;
			for (RSTile tile : generalArea.getAllTiles()) {
				if (closestTile == null) {
					closestTile = tile;

				} else if (tile.getHumanHoverPoint().distance(mousePoint) < closestTile.getHumanHoverPoint().distance(mousePoint)) {
					closestTile = tile;
				}
			}
			monsterAreaGenerator.tileClicked(closestTile);
		}
		return OVERRIDE_RETURN.PROCESS;
	}
}
