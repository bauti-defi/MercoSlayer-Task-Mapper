package scripts.com.mercosur.slayermapper.models.items.consumable;

import org.tribot.api2007.Skills;
import scripts.com.mercosur.slayermapper.models.items.AbstractItem;

public class Potion extends AbstractItem {

	private final Type type;
	private final Skills.SKILLS[] skills;

	public enum Type {
		BUFF, RESTORATION
	}

	public Potion(final String name, Type type, Skills.SKILLS... skills) {
		super(name, true, false);
		this.type = type;
		this.skills = skills;
	}

	public Skills.SKILLS[] getSkills() {
		return skills;
	}

	public Type getType() {
		return type;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder(getName());
		stringBuilder.append(":" + type);
		for (Skills.SKILLS skill : skills) {
			stringBuilder.append(":" + skill);
		}
		return stringBuilder.toString();
	}
}
