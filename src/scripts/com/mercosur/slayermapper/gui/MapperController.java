package scripts.com.mercosur.slayermapper.gui;

import com.allatori.annotations.DoNotRename;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSArea;
import scripts.com.mercosur.slayermapper.models.Task;
import scripts.com.mercosur.slayermapper.models.items.Item;
import scripts.com.mercosur.slayermapper.models.items.ItemProperty;
import scripts.com.mercosur.slayermapper.models.items.consumable.Food;
import scripts.com.mercosur.slayermapper.models.items.consumable.Potion;
import scripts.com.mercosur.slayermapper.models.npcs.AttackStyle;
import scripts.com.mercosur.slayermapper.models.npcs.monster.FinalBlowMonsterMechanic;
import scripts.com.mercosur.slayermapper.models.npcs.monster.Monster;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@DoNotRename
public class MapperController extends AbstractGUIController {

	@FXML
	@DoNotRename
	private TextField taskToAddName;

	@FXML
	@DoNotRename
	private ListView<Task> taskList;

	@FXML
	@DoNotRename
	private ListView<Monster> taskMonsterList;

	@FXML
	@DoNotRename
	private TextField monsterToAddName;

	@FXML
	@DoNotRename
	private TextField monsterToAddLevel;

	@FXML
	@DoNotRename
	private ListView<ItemProperty> monsterToAddRequiredItemProperties;

	@FXML
	@DoNotRename
	private Label areaGeneratorLabel;

	@FXML
	@DoNotRename
	private CheckBox monsterToAddHasMechanic;

	@FXML
	@DoNotRename
	private ComboBox<FinalBlowMonsterMechanic> monsterToAddFinalBlowMechanic;

	@FXML
	@DoNotRename
	private CheckBox monsterToAddMelees;

	@FXML
	@DoNotRename
	private CheckBox monsterToAddRanges;

	@FXML
	@DoNotRename
	private CheckBox monsterToAddMages;

	@FXML
	@DoNotRename
	private ListView<Monster> monsterList;

	@FXML
	@DoNotRename
	private ListView<Food> foodList;

	@FXML
	@DoNotRename
	private TextField foodToAddName;

	@FXML
	@DoNotRename
	private ListView<Potion> potionList;

	@FXML
	@DoNotRename
	private TextField potionToAddName;

	@FXML
	@DoNotRename
	private ComboBox<Potion.Type> potionToAddType;

	@FXML
	@DoNotRename
	private ListView<Skills.SKILLS> potionToAddSkills;

	@FXML
	@DoNotRename
	private ListView<Item> itemList;

	@FXML
	@DoNotRename
	private TextField itemToAddName;

	@FXML
	@DoNotRename
	private CheckBox itemToAddIsStackable;

	@FXML
	@DoNotRename
	private CheckBox itemToAddIsEquipable;

	@FXML
	@DoNotRename
	private ListView<ItemProperty> itemToAddProperties;

	@FXML
	@DoNotRename
	public void addTask() {
		if (taskToAddName.getText().isEmpty()) {
			return;
		}
		taskList.getItems().add(new Task(taskToAddName.getText()));
	}

	@FXML
	@DoNotRename
	public void deleteTask() {
		if (taskList.getSelectionModel().isEmpty()) {
			return;
		}
		taskList.getItems().remove(taskList.getSelectionModel().getSelectedIndex());
	}

	@FXML
	@DoNotRename
	public void selectTaskMonsters() {
		if (taskList.getSelectionModel().isEmpty()) {
			return;
		}
		final Task selectedTask = taskList.getSelectionModel().getSelectedItem();
		taskMonsterList.getSelectionModel().clearSelection();
		for (Monster monster : selectedTask.getMonsters()) {
			taskMonsterList.getSelectionModel().select(monster);
		}
	}

	@FXML
	@DoNotRename
	public void updateTaskMonsterList() {
		if (taskList.getSelectionModel().isEmpty() || taskMonsterList.getSelectionModel().isEmpty()) {
			return;
		}
		taskList.getSelectionModel().getSelectedItem().setMonsters(taskMonsterList.getItems().stream().collect(Collectors.toList()));
	}

	private boolean isGeneratingArea() {
		return areaGeneratorLabel.getText().equalsIgnoreCase("On");
	}

	@FXML
	@DoNotRename
	public void toggleMonsterMechanicComboBox() {
		if (monsterToAddHasMechanic.isSelected()) {
			monsterToAddFinalBlowMechanic.setDisable(false);
		} else {
			monsterToAddFinalBlowMechanic.setDisable(true);
		}
	}

	@FXML
	@DoNotRename
	public void generateMonsterArea() {
		if (!isGeneratingArea()) {
			areaGeneratorLabel.setText("On");

		} else {
			//stop generating
		}
	}

	@FXML
	@DoNotRename
	public void addMonster() {
		final String name = monsterToAddName.getText();
		if (name.isEmpty()) {
			return;
		}

		final int level = Integer.parseInt(monsterToAddLevel.getText());
		final ItemProperty[] itemProperties = monsterToAddRequiredItemProperties.getSelectionModel().getSelectedItems().stream().toArray(ItemProperty[]::new);
		final RSArea area = null;
		final FinalBlowMonsterMechanic finalBlowMonsterMechanic;
		final AttackStyle[] attackStyles;
		if (monsterToAddHasMechanic.isSelected()) {
			finalBlowMonsterMechanic = monsterToAddFinalBlowMechanic.getValue();
		} else {
			finalBlowMonsterMechanic = null;
		}
		List<AttackStyle> styles = new ArrayList<>();
		if (monsterToAddMelees.isSelected()) {
			styles.add(AttackStyle.MELEE);
		}
		if (monsterToAddMages.isSelected()) {
			styles.add(AttackStyle.MAGIC);
		}
		if (monsterToAddRanges.isSelected()) {
			styles.add(AttackStyle.RANGE);
		}
		attackStyles = styles.stream().toArray(AttackStyle[]::new);

		monsterList.getItems().add(new Monster(name, level, itemProperties, area, finalBlowMonsterMechanic, attackStyles));
	}

	@FXML
	@DoNotRename
	public void deleteMonster() {

	}

	@FXML
	@DoNotRename
	public void addFood() {
		if (foodToAddName.getText().isEmpty() || foodList.getItems().stream().anyMatch(food -> food.getName().equalsIgnoreCase(foodToAddName.getText()))) {
			return;
		}
		foodList.getItems().add(new Food(foodToAddName.getText()));
		foodToAddName.clear();
	}

	@FXML
	@DoNotRename
	public void deleteFood() {
		if (foodList.getSelectionModel().isEmpty()) {
			return;
		}
		foodList.getItems().remove(foodList.getSelectionModel().getSelectedIndex());
	}

	@FXML
	@DoNotRename
	public void addPotion() {
		final String name = potionToAddName.getText();
		final Potion.Type type = potionToAddType.getValue();
		final Skills.SKILLS[] skills = potionToAddSkills.getSelectionModel().getSelectedItems().stream().toArray(Skills.SKILLS[]::new);
		if (name.isEmpty() || potionList.getItems().stream().anyMatch(potion -> potion.getName().equalsIgnoreCase(name)) || skills.length == 0) {
			return;
		}
		potionList.getItems().add(new Potion(name, type, skills));
		potionToAddName.clear();
		potionToAddSkills.getSelectionModel().clearSelection();
	}

	@FXML
	@DoNotRename
	public void deletePotion() {
		if (potionList.getSelectionModel().isEmpty()) {
			return;
		}
		potionList.getItems().remove(potionList.getSelectionModel().getSelectedIndex());
	}

	@FXML
	@DoNotRename
	public void addItem() {
		final String name = itemToAddName.getText();
		final boolean stackable = itemToAddIsStackable.isSelected();
		final boolean equipable = itemToAddIsEquipable.isSelected();
		final ItemProperty[] properties = itemToAddProperties.getSelectionModel().getSelectedItems().stream().toArray(ItemProperty[]::new);
		if (name.isEmpty() || itemList.getItems().stream().anyMatch(item -> item.getName().equalsIgnoreCase(name))) {
			return;
		}
		itemList.getItems().add(new Item(name, stackable, equipable, properties));
		itemToAddName.clear();
		itemToAddIsStackable.setSelected(false);
		itemToAddIsEquipable.setSelected(false);
		itemToAddProperties.getSelectionModel().clearSelection();
	}

	@FXML
	@DoNotRename
	public void deleteItem() {
		if (itemList.getSelectionModel().isEmpty()) {
			return;
		}
		itemList.getItems().remove(itemList.getSelectionModel().getSelectedIndex());
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		potionToAddType.setItems(FXCollections.observableArrayList(Potion.Type.values()));
		potionToAddSkills.setItems(FXCollections.observableArrayList(Skills.SKILLS.values()));
		potionToAddSkills.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		itemToAddProperties.setItems(FXCollections.observableArrayList(ItemProperty.values()));
		itemToAddProperties.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		//TODO: load monster and task list
		taskMonsterList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		monsterToAddRequiredItemProperties.setItems(FXCollections.observableArrayList(ItemProperty.values()));
		monsterToAddFinalBlowMechanic.setItems(FXCollections.observableArrayList(FinalBlowMonsterMechanic.values()));
	}
}
