
import javafx.application.Application;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class GidsMain extends Application {

    private TabPane tabPane;
    private Tab tab1;
    private Tab tab2;

    public Parent createContent() {
        tabPane = new TabPane();
        tabPane.setPrefSize(600, 500);
        tabPane.setMinSize(TabPane.USE_PREF_SIZE, TabPane.USE_PREF_SIZE);
        tabPane.setMaxSize(TabPane.USE_PREF_SIZE, TabPane.USE_PREF_SIZE);
        tab1 = new Tab();
        tab2 = new Tab();

        tabPane.setRotateGraphic(false);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
        tabPane.setSide(Side.TOP);

        // Drag and Drop tab
        tab1.setText("Drag and Drop");
        tab1.setTooltip(new Tooltip("Drag and Drop example"));
        final Group grp = new Group();  // drag and drop needs a group to setup the pane
        LayoutUI ui = new LayoutUI();
        ui.setUpDragDrop(grp);
        tab1.setContent(grp);
        tabPane.getTabs().add(tab1);

        // Grid clipboard tab
        tab2.setText("Grid Clipboard");
        tab2.setTooltip(new Tooltip("Grid Clipboard example"));
        final VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setTranslateX(10);
        vbox.setTranslateY(10);
        GridUI gui = new GridUI();
        gui.setUpGrid(vbox);
        tab2.setContent(vbox);
        tabPane.getTabs().add(tab2);
      return tabPane;
    }

    @Override public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("GIDS JavaFx Demo");
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    /**
     * Java main for when running without JavaFX launcher
     */
    public static void main(String[] args) {
        launch(args);
    }

}
