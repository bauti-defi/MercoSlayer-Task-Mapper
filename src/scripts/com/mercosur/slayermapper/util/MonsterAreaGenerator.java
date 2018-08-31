package scripts.com.mercosur.slayermapper.util;

import org.tribot.api2007.Player;
import org.tribot.api2007.Projection;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.interfaces.Painting;
import org.tribot.script.interfaces.TileClicking;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MonsterAreaGenerator implements TileClicking, Painting {

	private final String name;

	private final List<RSTile> monsterAreaTiles = new ArrayList<>();

	private RSArea monsterToAddArea;

	public MonsterAreaGenerator(final String name) {
		this.name = name;
		monsterToAddArea = new RSArea(Player.getPosition(), Player.getPosition());
	}

	public RSArea getMonsterToAddArea() {
		return monsterToAddArea;
	}

	@Override
	public void tileClicked(final RSTile rsTile) {
		monsterAreaTiles.add(rsTile);
		if (monsterAreaTiles.size() >= 2) {
			monsterToAddArea = new RSArea(monsterAreaTiles.stream().toArray(RSTile[]::new));
		}
	}

	@Override
	public void onPaint(final Graphics graphics) {
		if (monsterToAddArea != null) {
			final Graphics2D painter = (Graphics2D) graphics;
			painter.setColor(Color.RED);
			for (RSTile tile : monsterToAddArea.getAllTiles()) {
				painter.drawPolygon(Projection.getTileBoundsPoly(tile, 0));
			}
		}
	}
}
