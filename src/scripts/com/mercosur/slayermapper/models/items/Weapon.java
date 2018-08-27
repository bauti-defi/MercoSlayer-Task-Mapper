package scripts.com.mercosur.slayermapper.models.items;

import scripts.com.mercosur.slayermapper.models.npcs.AttackStyle;

public class Weapon extends Item {

	private final AttackStyle attackStyle;

	public Weapon(final String name, final boolean stackable, final AttackStyle attackStyle, final ItemProperty... properties) {
		super(name, stackable, true, properties);
		this.attackStyle = attackStyle;
	}

	public AttackStyle getAttackStyle() {
		return attackStyle;
	}
}
