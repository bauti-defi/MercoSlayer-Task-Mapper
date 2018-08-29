package scripts.com.mercosur.slayermapper.gui;

import javafx.scene.control.CheckBox;
import scripts.com.mercosur.slayermapper.models.npcs.monster.Monster;

public class MonsterCheckBox extends CheckBox {

	private final Monster monster;

	public MonsterCheckBox(final Monster monster) {
		super(monster.toString());
		this.monster = monster;
	}

	public Monster getMonster() {
		return monster;
	}
}
