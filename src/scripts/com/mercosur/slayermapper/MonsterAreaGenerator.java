package scripts.com.mercosur.slayermapper;

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
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MonsterAreaGenerator implements Runnable {

	private final String name;
	public AtomicReference<RSArea> monsterToAddArea = new AtomicReference<>(new RSArea(Player.getPosition(), Player.getPosition()));
	private final List<RSTile> monsterAreaTiles = new ArrayList<>();

	public AtomicBoolean SCAN = new AtomicBoolean(true);

	public MonsterAreaGenerator(final String name) {
		this.name = name;
	}

	@Override
	public void run() {
		int oldSize = 0;
		while (SCAN.get()) {
			List<RSTile> newTiles = Stream.of(NPCs.getAll(new Filter<RSNPC>() {
				@Override
				public boolean accept(final RSNPC rsnpc) {
					return rsnpc.getName().equalsIgnoreCase(name) && rsnpc.isValid() && PathFinding.canReach(rsnpc.getPosition(), false);
				}
			})).map(rsnpc -> rsnpc.getPosition())
					.distinct()
					.filter(position -> !monsterToAddArea.get().contains(position) && !monsterAreaTiles.contains(position))
					.collect(Collectors.toList());

			//could delete useless ones
			//updateArea(newTiles);

			monsterToAddArea.set(new RSArea(monsterAreaTiles.stream().toArray(RSTile[]::new)));
			if (monsterAreaTiles.size() > oldSize) {
				oldSize = monsterAreaTiles.size();
				General.println(monsterAreaTiles.size());
			}
			General.sleep(100);
		}
		General.println("Stopping.");
	}


}
