package scripts.com.mercosur.slayermapper.util;

import org.tribot.api.General;
import org.tribot.api.types.generic.Filter;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MonsterAreaGenerator implements Runnable {

	private final String name;

	private final List<RSTile> monsterAreaTiles = new ArrayList<>();

	private RSArea monsterToAddArea;

	public AtomicBoolean SCAN = new AtomicBoolean(true);

	public MonsterAreaGenerator(final String name) {
		this.name = name;
		monsterToAddArea = new RSArea(Player.getPosition(), Player.getPosition());
	}

	@Override
	public void run() {
		General.println("Starting scan...");
		int tilesAreaSize = 0;
		while (SCAN.get()) {
			List<RSTile> newMonsterTiles = Stream.of(NPCs.getAll(new Filter<RSNPC>() {
				@Override
				public boolean accept(final RSNPC rsnpc) {
					return rsnpc.getName().equalsIgnoreCase(name) && rsnpc.isValid() && PathFinding.canReach(rsnpc.getPosition(), false);
				}
			})).map(rsnpc -> rsnpc.getPosition())
					.distinct()
					.filter(position -> !monsterToAddArea.contains(position) && !monsterAreaTiles.contains(position))
					.collect(Collectors.toList());//get all good new monster tiles

			if (!newMonsterTiles.isEmpty()) {
				monsterAreaTiles.addAll(newMonsterTiles); //save new tiles
				if (tilesAreaSize < monsterAreaTiles.size()) {
					tilesAreaSize = monsterAreaTiles.size();
					monsterToAddArea = generateArea();//generate new area based on tile bounds
					General.println("Tile count: " + tilesAreaSize);
				}
			}

			General.sleep(50);
		}
		General.println("Stopping scan...");
	}

	public RSArea getMonsterToAddArea() {
		return monsterToAddArea;
	}

	private RSArea generateArea() {
		int upperBound = 0;
		int lowerBound = 0;
		int rightBound = 0;
		int leftBound = 0;
		for (RSTile tile : monsterAreaTiles) {
			if (tile.getY() > upperBound) {
				upperBound = tile.getY();
			}
			if (tile.getY() < lowerBound) {
				lowerBound = tile.getY();
			}
			if (tile.getX() > leftBound) {
				leftBound = tile.getX();
			}
			if (tile.getX() < rightBound) {
				rightBound = tile.getX();
			}
		}
		return new RSArea(new RSTile(leftBound, upperBound), new RSTile(rightBound, lowerBound));
	}

}
