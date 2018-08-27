package scripts.com.mercosur.slayermapper.models.npcs.monster;

import org.tribot.api2007.types.RSArea;
import scripts.com.mercosur.slayermapper.models.items.ItemProperty;
import scripts.com.mercosur.slayermapper.models.npcs.AttackStyle;

public class Monster {

	private final String name;

	private final int level;

	private final ItemProperty[] requiredItemProperties;

	private final RSArea area;

	private final FinalBlowMonsterMechanic finalBlowMonsterMechanic;

	private final AttackStyle[] attackStyles;

	public Monster(final String name, final int level, final ItemProperty[] requiredItemProperties, final RSArea area, final FinalBlowMonsterMechanic finalBlowMonsterMechanic, final AttackStyle[] attackStyles) {
		this.name = name;
		this.level = level;
		this.requiredItemProperties = requiredItemProperties;
		this.area = area;
		this.finalBlowMonsterMechanic = finalBlowMonsterMechanic;
		this.attackStyles = attackStyles;
	}

	public String getName() {
		return name;
	}

	public int getLevel() {
		return level;
	}

	public ItemProperty[] getRequiredItemProperties() {
		return requiredItemProperties;
	}

	public RSArea getArea() {
		return area;
	}

	public FinalBlowMonsterMechanic getFinalBlowMonsterMechanic() {
		return finalBlowMonsterMechanic;
	}

	public AttackStyle[] getAttackStyles() {
		return attackStyles;
	}

	@Override
	public String toString() {
		return "[" + level + "]" + name;
	}
}
