package gui;
import javafx.collections.FXCollections;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;


public class DungeonGUI<toReturn> extends Application {

    /**
     * Controller for connecting level to GUI.
     */
    private Controller controller;

    /**
     * Text area that displays space description.
     */
    private TextArea area = new TextArea("");

    /**
     * Shows description of a door.
     */
    private Popup descriptionPop;

    /**
     * ComboBox for doors in space.
     */
    private ComboBox<String> doorBox = new ComboBox<>();

    /**
     * Contains edit button.
     */
    private HBox bottom = new HBox();

    /**
     * Main border pane.
     */
    private BorderPane root;

    /**
     * confirm edit button.
     */
    private Button confirmButton;

    /**
     * edit button.
     */
    private Button editButton;

    /**
     * ListView of all monster types which can be used to add any selected monster.
     */
    private ListView<String> addMonsterList;

    /**
     * ListView of all treasure types which can be used to add any selected treasure.
     */
    private ListView<String> addTreasureList;

    /**
     * ListView of all monsters in space.
     */
    private ListView<String> removeMonsterList;

    /**
     * ListView of all treasures in space.
     */
    private ListView<String> removeTreasureList;

    /**
     * ListView of all spaces in level generated.
     */
    private ListView<String> spaceList;

    /**
     * Used to save or load files.
     */
    private FileChooser fileChooser;

    /**
     * Determines if changes are to be saved or not.
     */
    private boolean alteredMonster = false;

    /**
     * Determines if changes are to be saved or not.
     */
    private boolean alteredTreasure = false;

    /**
     * Label in edit popup.
     */
    private Label addMLabel;

    /**
     * Label in edit popup.
     */
    private Label addTLabel;

    /**
     * Label in edit popup.
     */
    private Label remMLabel;

    /**
     * Label in edit popup.
     */
    private Label remTLabel;

    /**
     * editing popup.
     */
    private Popup editWindow;

    /**
     * editing pane in edit popup.
     */
    private BorderPane editPane;

    /**
     * main stage of GUI.
     */
    private Stage primaryStage;

    /**
     * string rep of currently selected space.
     */
    private String selectedSpace;

    /**
     * Menu bar which contains save and loading options.
     */
    private MenuBar menuBar = new MenuBar();

    /**
     * Runs on start up of GUI, sets up all base elements and creates a random level.
     * @param assignedStage stage to show GUI.
     */
    @Override
    public void start(Stage assignedStage) {
        controller = new Controller(this);
        primaryStage = assignedStage;
        root = setUpRoot();
        Scene scene = new Scene(root, 650, 500);
        primaryStage.setTitle("Dungeon Generation");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * sets up main border pane for GUI.
     * @return root border pane.
     */
    private BorderPane setUpRoot() {
        BorderPane temp = new BorderPane();
        setUpMenuBar();
        temp.setTop(menuBar);
        setLeftSide(temp);
        setCentre(temp);
        setRightSide(temp);
        setBottom(temp);
        setUpDescriptionPopup();
        setUpEditWindow();
        return temp;
    }

    /**
     * sets up space list view.
     * @param b root border pane.
     */
    private void setLeftSide(BorderPane b) {
        ObservableList<String> spacesList = FXCollections.observableArrayList(controller.getNameList());
        spaceList = new ListView<>(spacesList);
        spaceList.setMaxWidth(100);
        spaceList.setMaxHeight(250);

        b.setLeft(spaceList);
        spaceList.setOnMouseClicked((MouseEvent event) -> {
            setDoorBox(spaceList.getSelectionModel().getSelectedItem());
            setDescriptionBox(spaceList.getSelectionModel().getSelectedItem());
            editButton.setVisible(true);
        });
    }

    /**
     * sets up space description text area.
     * @param b root border pane.
     */
    private void setCentre(BorderPane b) {
        area.setEditable(false);
        area.setStyle("-fx-padding: 10;"
                + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;"
                + "-fx-border-insets: 1;"
                + "-fx-border-radius: 1;"
                + "-fx-border-color: blue;");
        b.setCenter(area);
    }

    /**
     * sets up door combo box.
     * @param b root border pane.
     */
    private void setRightSide(BorderPane b) {
        doorBox.setVisibleRowCount(5);
        b.setRight(doorBox);
    }

    /**
     * sets up edit button on bottom.
     * @param b root border pane.
     */
    private void setBottom(BorderPane b) {
        editButton = createButton("Edit selected Space", "");
        bottom.getChildren().add(editButton);
        HBox.setMargin(editButton, new Insets(10, 10, 10, 280));
        editButton.setVisible(false);
        b.setBottom(bottom);

        editButton.setOnAction((ActionEvent event) -> {
            confirmButton.setVisible(false);
            removalLists(editPane);
            editWindow.show(primaryStage);
        });

    }

    /**
     * Sets up door description popup.
     */
    private void setUpDescriptionPopup() {
        descriptionPop = createPopUp();
        TextArea textA = new TextArea();
        Button hide = createButton("Hide", "");
        hide.setMinWidth(500);
        VBox box = new VBox();
        box.getChildren().addAll(textA, hide);
        textA.setEditable(false);
        textA.setStyle(" -fx-background-color: white;");
        textA.setMinWidth(100);
        textA.setMaxHeight(50);
        descriptionPop.getContent().add(box);
        hide.setOnAction((ActionEvent event) -> {
            descriptionPop.hide();
        });
    }

    /**
     * set up editing popup.
     */
    private void setUpEditWindow() {
        editWindow = createPopUp();
        editPane = new BorderPane();
        setUpEditTop(editPane);
        setUpEditCentre(editPane);
        addLists(editPane);
        editWindow.getContent().add(editPane);
    }

    /**
     * set up top of edit pane.
     * @param b edit pane.
     */
    private void setUpEditTop(BorderPane b) {
        HBox h = new HBox();
        Button addMonsterButton = createButton("Add Monster", "");
        Button removeMonsterButton = createButton("Remove Monster", "");
        Button addTreasureButton = createButton("Add Treasure", "");
        Button removeTreasureButton = createButton("Remove Treasure", "");
        h.getChildren().addAll(addMonsterButton, removeMonsterButton);
        h.getChildren().addAll(addTreasureButton, removeTreasureButton);
        b.setTop(h);

        addMonsterButton.setOnAction((ActionEvent event) -> {
            addMonsterList.setVisible(true);
            addMLabel.setVisible(true);
        });

        removeMonsterButton.setOnAction((ActionEvent) -> {
            removeMonsterList.setVisible(true);
            remMLabel.setVisible(true);
        });

        addTreasureButton.setOnAction((ActionEvent event) -> {
            addTreasureList.setVisible(true);
            addTLabel.setVisible(true);
        });

        removeTreasureButton.setOnAction((ActionEvent event) -> {
            removeTreasureList.setVisible(true);
            remTLabel.setVisible(true);
        });
    }

    /**
     * set up centre of edit popup.
     * @param b edit pane.
     */
    private void setUpEditCentre(BorderPane b) {
        HBox h = new HBox();
        confirmButton = createButton("Confirm/Save Edits", "");
        Button cancelButton = createButton("Cancel/Exit Edit", "");
        confirmButton.setVisible(false);
        h.getChildren().addAll(confirmButton, cancelButton);
        b.setCenter(h);

        confirmButton.setOnAction((ActionEvent event) -> {
            confirmAndCancel(false);
        });

        cancelButton.setOnAction((ActionEvent event) -> {
            confirmAndCancel(true);
            editWindow.hide();
        });
    }

    /**
     * used to determine confirmation or cancellation of editing space.
     * @param cancel true if do not save changes.
     */
    private void confirmAndCancel(boolean cancel) {
        if (cancel) {
            addMonsterList.setVisible(false);
            addMonsterList.setDisable(false);
            addTreasureList.setVisible(false);
            addTreasureList.setDisable(false);
            addMLabel.setVisible(false);
            addTLabel.setVisible(false);
            controller.finishEditMonster(false, selectedSpace);
            controller.finishEditTreasure(false, selectedSpace);
            alteredTreasure = false;
            alteredMonster = false;
            removeMonsterList.setVisible(false);
            removeMonsterList.setDisable(false);
            removeTreasureList.setVisible(false);
            removeTreasureList.setDisable(false);
            remMLabel.setVisible(false);
            remTLabel.setVisible(false);
        } else {
            addTreasureList.setDisable(false);
            addMonsterList.setDisable(false);
            removeMonsterList.setDisable(false);
            removeTreasureList.setDisable(false);
            controller.finishEditMonster(alteredMonster, selectedSpace);
            controller.finishEditTreasure(alteredTreasure, selectedSpace);
            setDescriptionBox(selectedSpace);
            removalLists(editPane);
            alteredMonster = false;
            alteredTreasure = false;
        }
    }

    /**
     * set up list of treasures and monsters for editing.
     * @param b edit pane.
     */
    private void addLists(BorderPane b) {
        ObservableList<String> mList = FXCollections.observableArrayList(controller.allMonsterTypes());
        ObservableList<String> tList = FXCollections.observableArrayList(controller.allTreasureTypes());
        addMonsterList = new ListView<String>(mList);
        addTreasureList = new ListView<String>(tList);
        VBox box = new VBox();
        addMLabel = new Label("Add Monsters:");
        addTLabel = new Label("Add Treasures:");

        addMLabel.setTextFill(Color.web("#0076a3"));
        addTLabel.setTextFill(Color.web("#0076a3"));
        addMLabel.setStyle("-fx-background-color:WHITE");
        addTLabel.setStyle("-fx-background-color:WHITE");
        addMLabel.setVisible(false);
        addTLabel.setVisible(false);

        addMonsterList.setMaxWidth(200);
        addMonsterList.setMaxHeight(250);
        addMonsterList.setVisible(false);
        addTreasureList.setMaxWidth(200);
        addTreasureList.setMaxHeight(150);
        addTreasureList.setVisible(false);

        box.getChildren().addAll(addMLabel, addMonsterList, addTLabel, addTreasureList);
        b.setLeft(box);
        addMonsterList.setOnMouseClicked((MouseEvent event) -> {
            addMonsterList.setDisable(true);
            controller.addMonster(addMonsterList.getSelectionModel().getSelectedItem(), selectedSpace);
            confirmButton.setVisible(true);
            alteredMonster = true;
        });

        addTreasureList.setOnMouseClicked((MouseEvent event) -> {
            confirmButton.setVisible(true);
            addTreasureList.setDisable(true);
            controller.addTreasure(addTreasureList.getSelectionModel().getSelectedItem(), selectedSpace);
            alteredTreasure = true;
        });
    }

    /**
     * set up list of treasures and monsters in space.
     * @param b edit pane.
     */
    private void removalLists(BorderPane b) {
        ObservableList<String> mList = FXCollections.observableArrayList(controller.allSpaceMonsters(selectedSpace));
        ObservableList<String> tList = FXCollections.observableArrayList(controller.allSpaceTreasures(selectedSpace));
        removeMonsterList = new ListView<String>(mList);
        removeTreasureList = new ListView<String>(tList);
        VBox box = new VBox();
        remMLabel = new Label("Remove Monsters:");
        remTLabel = new Label("Remove Treasures:");

        remMLabel.setTextFill(Color.web("#0076a3"));
        remTLabel.setTextFill(Color.web("#0076a3"));
        remMLabel.setStyle("-fx-background-color:WHITE");
        remTLabel.setStyle("-fx-background-color:WHITE");
        remMLabel.setVisible(false);
        remTLabel.setVisible(false);

        removeMonsterList.setMaxWidth(200);
        removeMonsterList.setMaxHeight(250);
        removeMonsterList.setVisible(false);
        removeTreasureList.setMaxWidth(200);
        removeTreasureList.setMaxHeight(150);
        removeTreasureList.setVisible(false);

        box.getChildren().addAll(remMLabel, removeMonsterList, remTLabel, removeTreasureList);
        b.setRight(box);

        removeMonsterList.setOnMouseClicked((MouseEvent event) -> {
            removeMonsterList.setDisable(true);
            confirmButton.setVisible(true);
            controller.removeMonster(removeMonsterList.getFocusModel().getFocusedIndex(), selectedSpace);
            alteredMonster = true;
        });

        removeTreasureList.setOnMouseClicked((MouseEvent event) -> {
            confirmButton.setVisible(true);
            removeTreasureList.setDisable(true);
            controller.removeTreasure(removeTreasureList.getFocusModel().getFocusedIndex(), selectedSpace);
            alteredTreasure = true;
        });
    }

    /**
     * Create a simple popup.
     * @return a simple popup.
     */
    private Popup createPopUp() {
        Popup popup = new Popup();
        popup.setX(200);
        popup.setY(300);

        return popup;
    }

    /**
     * setup the door combo box items.
     * @param currentSpace string rep of selected space.
     */
    private void setDoorBox(String currentSpace) {
        ArrayList<String> doors = controller.getDoorList(currentSpace);
        doorBox.getItems().clear();

        for (String d : doors) {
            doorBox.getItems().add(d);
        }

        doorComboAction();
    }

    /**
     * set the description text area to description of current space.
     * @param currentSpace string rep of selected space.
     */
    private void setDescriptionBox(String currentSpace) {
        String description;
        selectedSpace = currentSpace;
        description = controller.getSpaceDescription(currentSpace);
        area.setText(description);
    }

    /**
     * checks for selected door and shows popup if a door is clicked.
     */
    private void doorComboAction() {

        doorBox.setOnAction((ActionEvent ev) -> {
            if (doorBox.getItems().size() > 0) {
                descriptionPop.hide();
                changeDescriptionText(controller.getDoorDescription(doorBox.getSelectionModel().getSelectedItem(),
                        selectedSpace));
                descriptionPop.show(primaryStage);
            }
        });
    }

    /**
     * changes door description in popup to selected door.
     * @param text description of door.
     */
    private void changeDescriptionText(String text) {
        VBox temp = (VBox) descriptionPop.getContent().get(0);
        ObservableList<Node> list2 = temp.getChildren();
        for (Node f : list2) {
            if (f instanceof TextArea) {
                TextArea temp2 = (TextArea) f;
                temp2.setText(text);
            }
        }
    }

    /**
     * creates a simple button.
     * @param text text on button.
     * @param format format of button.
     * @return simple button created.
     */
    private Button createButton(String text, String format) {
        Button btn = new Button();
        btn.setText(text);
        btn.setStyle(format);
        return btn;
    }

    /**
     * sets up menu bar at top of main page.
     */
    private void setUpMenuBar() {
        Menu menu = new Menu("File");
        MenuItem save = new MenuItem("Save");
        MenuItem load = new MenuItem("Load"); //Add menu items to menu
        menu.getItems().add(save);
        menu.getItems().add(load);

        save.setOnAction((ActionEvent ev) -> {
            fileChooser = new FileChooser();
            File selectedFile = fileChooser.showSaveDialog(primaryStage);
            if (selectedFile != null) {
                String filePath = selectedFile.getAbsolutePath();
                controller.serialOut(filePath);
            }
        });

        load.setOnAction((ActionEvent event) -> {
            fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                String filePath = selectedFile.getAbsolutePath();
                controller.serialIn(filePath);
                refreshSpaceList();
                changeDescriptionText("");
                area.setText("");
            }
        });

        menuBar.getMenus().add(menu); //Add menu to menu bar
    }

    /**
     * refreshes space list info.
     */
    private void refreshSpaceList() {
        ObservableList<String> list = FXCollections.observableArrayList(controller.getNameList());
        spaceList.getItems().clear();
        spaceList.getItems().addAll(list);
    }

    /**
     * Used to launch application.
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
