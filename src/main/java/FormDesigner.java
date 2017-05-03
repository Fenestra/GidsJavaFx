import com.westat.QItem;
import com.westat.CustomContent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
  * Created by lee on 3/2/17.
  */



public class FormDesigner {

  private TreeItem getQItem(String id, String title, String num, String descr, String ctype) {
    TreeItem item = new TreeItem(id);
    //    ArrayList<TreeItem> stuff = new ArrayList<TreeItem>();
    TreeItem qtitle = new TreeItem(title);
    TreeItem qnum   = new TreeItem(num);
    TreeItem qdescr = new TreeItem(descr);
    TreeItem qtype  = new TreeItem(ctype);
    item.getChildren().addAll(qtitle, qnum, qdescr, qtype);
    return item;
  }
  private TreeItem getQnrItem(QItem qitem) {
    TreeItem item = new TreeItem(qitem.id());
    //    ArrayList<TreeItem> stuff = new ArrayList<TreeItem>();
    TreeItem qtitle = new TreeItem(qitem.name());
    TreeItem object = new TreeItem(qitem);
    item.getChildren().addAll(qtitle, object);
    return item;
  }

 // final Group ddgrp = new Group();  // drag and drop needs a group to setup the pane
 // DDUI ddui = new DDUI();
 // ddui.setUpDragDrop(ddgrp);
 // ddui.setUpContextMenu(tab3);

  private void setDragHandlers(final TreeView tv, final DDUI ddui) {
    tv.setOnMousePressed(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        System.out.println(event);
      }
    });
    tv.setOnDragDetected(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        Dragboard db = tv.startDragAndDrop(TransferMode.MOVE);
        setDragContent(db, tv);
        event.consume();
      }
    });

    tv.setOnDragDone(new EventHandler<DragEvent>() {
      @Override
      public void handle(DragEvent event) {
        //    Double left = DropPosition.dropX; // - offX //dropX
        //    Double top  = DropPosition.dropY; //- offY //dropY
        //       println(s"dragdone drop/Screen/scene/x $left/$top "+event.getScreenX+"/"+event.getScreenY+" "+event.getSceneX+"/"+event.getSceneY+" "+event.getX+"/"+event.getY)
        // println(s"setOnDragDone drops $left $top  - "+event.getDragboard.getDragViewOffsetX+"  "+event.getDragboard.getDragViewOffsetY)
        if (event.getTransferMode() == TransferMode.MOVE) {
          //   group.relocate(left, top)
          //        layout.relocate(left, top)
          //        println("dragdone "+upperleft+"/"+topright)
          //    group.setTranslateX(left-upperleft)
          //    group.setTranslateY(top-topright)
          ddui.autoformatContent(event.getX(), event.getY(), CustomContent.currentContent());
        };
        event.consume();
      }
    });
  }

  // central way of telling drabBoard what is being dragged
  // also lets you set global var to hold the dragged Object if you want
  private void setDragContent(Dragboard db, TreeView tv) {
    TreeItem ti = tv.getTreeItem(tv.getSelectionModel().getSelectedIndex());
    //     db.setDragView(ti.getValue().toString());
    ClipboardContent content = new ClipboardContent();
    String name = ti.toString();
    System.out.println("drag for "+name);
    content.putString(name);
    db.setContent(content);
  }


  public SplitPane node() {
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

    Group ddgrp = new Group();  // drag and drop needs a group to setup the pane
    final DDUI ddui = new DDUI();
    ddui.setUpDragDrop(ddgrp);
    designRoot.setContent(ddgrp);
//    designRoot.setContent(target); //designRoot.getChildren().add(target);
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
    CustomContent.fillTree(root);
/*
    root.getChildren().addAll(
//      getQItem("1a", "Name", "1a", "Name of individual or establishment", "label"),
//      getQItem("1b", "Street Address", "1b", "address number and name of street", "label"));
      getQnrItem(new QItem("first", 20, 100, "first item")),
      getQnrItem(new QItem("second", 40, 200, "second item")));
*/
    treeView.setRoot(root);
    treeView.setShowRoot(false);
    setDragHandlers(treeView, ddui);
    contentPane.getChildren().add( treeView );
    contentPane.setLeftAnchor(treeView, 0.0);
    contentPane.setRightAnchor(treeView, 0.0);
    contentPane.setTopAnchor(treeView, 0.0);
    contentPane.setBottomAnchor(treeView, 0.0);
    return spane;
  }
}
