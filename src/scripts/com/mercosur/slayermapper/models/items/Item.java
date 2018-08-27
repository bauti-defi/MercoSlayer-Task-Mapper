package scripts.com.mercosur.slayermapper.models.items;

import java.util.stream.Stream;

public class Item extends AbstractItem {

	private ItemProperty[] properties;

	private boolean canEquip;

	public Item(final String name, final boolean stackable, final boolean canEquip, final ItemProperty... properties) {
		super(name, stackable);
		this.canEquip = canEquip;
		this.properties = properties;
	}

	public boolean isCanEquip() {
		return canEquip;
	}

	public boolean hasProperty(ItemProperty property) {
		return Stream.of(properties).anyMatch(prop -> prop == property);
	}

	public ItemProperty[] getProperties() {
		return properties;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder(super.toString());
		stringBuilder.append(isCanEquip() ? ":Equipable" : ":Not-Equipable");
		for (ItemProperty property : properties) {
			stringBuilder.append(":" + property);
		}
		return stringBuilder.toString();
	}

}
