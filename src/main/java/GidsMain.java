import com.westat.GDSection;
import com.westat.Length;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
//import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;



public class GidsMain extends Application {

    private TabPane tabPane;
//    private Tab tab1;
    private Tab tab2;
    private Tab tab3;

    public Parent createContent() {
        tabPane = new TabPane();
        tabPane.setPrefSize(1000, 500);
        tabPane.setMinSize(TabPane.USE_PREF_SIZE, TabPane.USE_PREF_SIZE);
        tabPane.setMaxSize( Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight() );
 //       tab1 = new Tab();
        tab2 = new Tab();
        tab3 = new Tab();

        tabPane.setRotateGraphic(false);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
        tabPane.setSide(Side.TOP);
/*
        // Drag and Drop tab
        tab1.setText("Drag and Drop");
        tab1.setTooltip(new Tooltip("Drag and Drop example"));
        final Group grp = new Group();  // drag and drop needs a group to setup the pane
        LayoutUI ui = new LayoutUI();
        ui.setUpDragDrop(grp);
        tab1.setContent(grp);
        tabPane.getTabs().add(tab1);
*/
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

        // new Drag and Drop tab
        tab3.setText("Drag and Drop");
        tab3.setTooltip(new Tooltip("Drag and Drop example"));
        FormDesigner designer = new FormDesigner();
        tab3.setContent(designer.node());
/*
        final Group ddgrp = new Group();  // drag and drop needs a group to setup the pane
        DDUI ddui = new DDUI();
        ddui.setUpDragDrop(ddgrp);
        ddui.setUpContextMenu(tab3);
        tab3.setContent(ddgrp);
*/
        tabPane.getTabs().add(tab3);


        Button btnLeft = new Button("Left");
        Button btnRight = new Button("Right");
        TextField edSize = new TextField("1.00in");
        edSize.setPromptText("Distance (eg 1.0mm)");
    /*     CheckBox chkSound = new CheckBox("Sound");
         CheckBox chkMusic = new CheckBox("Music");
         RadioButton rdoEasy = new RadioButton("Easy");
         RadioButton rdoMedium = new RadioButton("Medium");
         RadioButton rdoHard = new RadioButton("Hard");
         ToggleGroup groupDifficulty = new ToggleGroup();
         groupDifficulty.getToggles().addAll(
                 rdoEasy,
                 rdoMedium,
                 rdoHard
         );*/
        btnLeft.setOnAction(new EventHandler<ActionEvent>() {
           public void handle(ActionEvent event) {
               GDSection.moveCurrentLeft(edSize.getText());
           }
        });
        btnRight.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                GDSection.moveCurrentRight(edSize.getText());
            }
        });
        ToolBar toolBar1 = new ToolBar();
        toolBar1.getItems().addAll(
                new Separator(),
                btnLeft,
                btnRight,
                new Separator(),
                edSize,
                new Separator()
        );

        BorderPane bp = new BorderPane(tabPane);
        bp.setTop(toolBar1);
        return bp;
    }




    @Override public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("GIDS JavaFx Demo");
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
        System.out.println("dpi is "+Screen.getPrimary().getDpi());

        Length.test();

    }

    /**
     * Java main for when running without JavaFX launcher
     */
    public static void main(String[] args) {
        launch(args);
    }

}
