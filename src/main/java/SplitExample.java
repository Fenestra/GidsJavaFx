import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.ArrayList;


public class SplitExample extends Application {


    private TreeItem getQItem(String id, String title, String num, String descr, String ctype)
    {
        TreeItem item = new TreeItem(id);
    //    ArrayList<TreeItem> stuff = new ArrayList<TreeItem>();
        TreeItem qtitle = new TreeItem(title);
        TreeItem qnum   = new TreeItem(num);
        TreeItem qdescr = new TreeItem(descr);
        TreeItem qtype  = new TreeItem(ctype);
        item.getChildren().addAll(qtitle, qnum, qdescr, qtype);
  //      stuff.add(qtitle);
  //      stuff.add(scania);
  //      stuff.add(gm);
  //      stuff.add(ford);
  //      item.getChildren().addAll(stuff);
        return item;
    }

    public Parent createContent() {

        final SplitPane spane = new SplitPane();
        spane.setOrientation(Orientation.HORIZONTAL);
        spane.setDividerPositions(0.734);
        spane.setPrefSize(400, 400);
        spane.setMaxSize(SplitPane.USE_COMPUTED_SIZE, SplitPane.USE_COMPUTED_SIZE);
        AnchorPane designPane = new AnchorPane();
        designPane.setPrefWidth(300);

        ScrollPane designRoot = new ScrollPane();
        final Rectangle target = new Rectangle(1, 1, 850, 1100);
        target.setFill(Color.BLANCHEDALMOND);
        target.setOpacity(0.7);
        target.setStroke(Color.BLUE);
        designRoot.setPannable(true);
        designRoot.setPrefSize(250, 400);
        designRoot.setContent(target); //designRoot.getChildren().add(target);
        designPane.getChildren().add(designRoot);
        designPane.setLeftAnchor(designRoot, 0.0);
        designPane.setRightAnchor(designRoot, 0.0);
        designPane.setTopAnchor(designRoot, 0.0);
        designPane.setBottomAnchor(designRoot, 0.0);

        AnchorPane contentPane = new AnchorPane();
        contentPane.setPrefWidth(100);
        spane.getItems().addAll( designPane, contentPane );
        TreeView treeView = new TreeView();
        treeView.setId("contentTree");
     //   treeView.setRoot(getQItem("1a", "Street Address", "1a", "address number and name of street", "label"));
        TreeItem root = new TreeItem();
        root.getChildren().addAll(
                getQItem("1a", "Name", "1a", "Name of individual or establishment", "label"),
                getQItem("1b", "Street Address", "1b", "address number and name of street", "label"));
        treeView.setRoot(root);
        treeView.setShowRoot(false);
        contentPane.getChildren().add( treeView );
        contentPane.setLeftAnchor(treeView, 0.0);
        contentPane.setRightAnchor(treeView, 0.0);
        contentPane.setTopAnchor(treeView, 0.0);
        contentPane.setBottomAnchor(treeView, 0.0);
   //     treeView.autosize();
        //    <TreeView id="contentTree" layoutX="-44.0" prefHeight="398.0" prefWidth="156.0"
        //            AnchorPane.rightAnchor="0.0" />


        Button btnLeft = new Button("Left");
        Button btnRight = new Button("Right");
        TextField edSize = new TextField(".25in");
        edSize.setPromptText("Distance (eg 1.0mm)");
        ToolBar toolBar1 = new ToolBar();
        toolBar1.getItems().addAll(
                new Separator(),
                btnLeft,
                btnRight,
                new Separator(),
                edSize,
                new Separator()
        );

        TabPane tabPane = new TabPane();
        tabPane.setPrefSize(600, 500);
        tabPane.setMinSize(TabPane.USE_PREF_SIZE, TabPane.USE_PREF_SIZE);
        tabPane.setMaxSize( Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight() );
        Tab tab1 = new Tab("split", spane);
        Tab tab2 = new Tab("nothing");

        tabPane.setRotateGraphic(false);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
        tabPane.setSide(Side.TOP);
        tabPane.getTabs().addAll(tab1, tab2);

        BorderPane bp = new BorderPane(tabPane);
        bp.setTop(toolBar1);
        return bp;
    }

    @Override public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("JavaFx SplitPane Example");
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

/*
    private TabPane tabPane;
    private Tab tab1;
    private Tab tab2;
    private Tab tab3;

    public Parent createContent() {
        tabPane = new TabPane();
        tabPane.setPrefSize(600, 500);
        tabPane.setMinSize(TabPane.USE_PREF_SIZE, TabPane.USE_PREF_SIZE);
        //    tabPane.setMaxSize(TabPane.USE_PREF_SIZE, TabPane.USE_PREF_SIZE);
        tabPane.setMaxSize( Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight() );
        tab1 = new Tab();
        tab2 = new Tab();
        tab3 = new Tab();

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

        // new Drag and Drop tab
        tab3.setText("new Drag and Drop");
        tab3.setTooltip(new Tooltip("New Drag and Drop example"));

        //   <SplitPane dividerPositions="0.7341137123745819"
        //   maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity"
        //   prefHeight="400.0" prefWidth="600.0" xmlns:fx="http://javafx.com/fxml/1" xmlns="http://javafx.com/javafx/8">
        final SplitPane spane = new SplitPane();
        spane.setDividerPositions(0.734);
        spane.setPrefSize(400, 400);
        spane.setMaxSize(Sc);
        AnchorPane designPane = new AnchorPane();
        designPane.setPrefWidth(300);
        AnchorPane contentPane = new AnchorPane();
        contentPane.setPrefWidth(100);
        spane.getItems().addAll( designPane, contentPane );
        TreeView treeView = new TreeView();
        treeView.setId("contentTree");
        contentPane.getChildren().add( treeView );
        contentPane.setRightAnchor(treeView, 0.0);
        //   contentPane.setRightAnchor(treeView, );
        treeView.autosize();
        //    <TreeView id="contentTree" layoutX="-44.0" prefHeight="398.0" prefWidth="156.0"
        //            AnchorPane.rightAnchor="0.0" />

        final Group ddgrp = new Group();  // drag and drop needs a group to setup the pane
        DDUI ddui = new DDUI();
        ddui.setUpDragDrop(ddgrp);

        designPane.getChildren().add(ddgrp);
        spane.setOrientation(Orientation.VERTICAL);
        tab3.setContent(spane);

        //    tab3.setContent(ddgrp);
        tabPane.getTabs().add(tab3);

        Button btnLeft = new Button("Left");
        Button btnRight = new Button("Right");
        TextField edSize = new TextField(".25in");
        edSize.setPromptText("Distance (eg 1.0mm)");
    *     CheckBox chkSound = new CheckBox("Sound");
         CheckBox chkMusic = new CheckBox("Music");
         RadioButton rdoEasy = new RadioButton("Easy");
         RadioButton rdoMedium = new RadioButton("Medium");
         RadioButton rdoHard = new RadioButton("Hard");
         ToggleGroup groupDifficulty = new ToggleGroup();
         groupDifficulty.getToggles().addAll(
                 rdoEasy,
                 rdoMedium,
                 rdoHard
         );*
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
        //       return tabPane;
    }




    @Override public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("GIDS JavaFx Demo");
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
        System.out.println("dpi is "+Screen.getPrimary().getDpi());

        Length.test();

    }

    **
     * Java main for when running without JavaFX launcher
     *
    public static void main(String[] args) {
        launch(args);
    }

}
*/