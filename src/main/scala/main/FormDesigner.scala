package main

import com.westat.{CustomContent, CustomContentItem}
import scalafx.Includes._
import scalafx.geometry.Orientation
import scalafx.scene.Group
import scalafx.scene.control.{ScrollPane, SplitPane, TreeItem, TreeView}
import scalafx.scene.input._
import scalafx.scene.layout.AnchorPane

/**
  * Created by lee on 5/10/17.
  */
class FormDesigner {

  // drag and drop needs a group to setup the pane
  val ddgrp = new Group
  val ddui = new DDUI(ddgrp)

  private val splitPane = new SplitPane {
    orientation = Orientation.Horizontal
    dividerPositions = 0.734
    prefWidth = 400
    prefHeight = 400
  //  maxWidth_= control.SplitPane.)spane.setMaxSize(SplitPane.USE_COMPUTED_SIZE, SplitPane.USE_COMPUTED_SIZE)

    val designPane = new AnchorPane {
      prefWidth = 300
      val designRoot = new ScrollPane {
        pannable = true
        prefWidth = 250
        prefHeight = 400
        content = ddgrp
      }
      children = List(
         designRoot
      )
      AnchorPane.setLeftAnchor(designRoot, 0.0)
      AnchorPane.setRightAnchor(designRoot, 0.0)
      AnchorPane.setTopAnchor(designRoot, 0.0)
      AnchorPane.setBottomAnchor(designRoot, 0.0)

    }

    val treeView = new TreeView[CustomContentItem] {
      id = "contentTree"
      val troot = new TreeItem[CustomContentItem]
      CustomContent.fillTree(troot)
      root = troot
      showRoot = false
      troot.expanded = true
    }
    setDragHandlers(treeView, ddui)
    val contentPane = new AnchorPane {
      prefWidth = 100
      children = treeView
    }
    items.addAll(designPane, contentPane)

    AnchorPane.setLeftAnchor(treeView, 0.0)
    AnchorPane.setRightAnchor(treeView, 0.0)
    AnchorPane.setTopAnchor(treeView, 0.0)
    AnchorPane.setBottomAnchor(treeView, 0.0)
  }

  def node: SplitPane = { splitPane }

  private def setDragHandlers(tv : TreeView[CustomContentItem], ddui : DDUI) = {
    tv.onMousePressed = (event : MouseEvent) => { println(event) }

    tv.onDragDetected = (event : MouseEvent) => {
        val db = tv.startDragAndDrop(TransferMode.Move)
        setDragContent(db, tv)
        event.consume()
      }

    tv.onDragDone = (event : DragEvent) => {
        //    Double left = DropPosition.dropX; // - offX //dropX
        //    Double top  = DropPosition.dropY; //- offY //dropY
        //       println(s"dragdone drop/Screen/scene/x $left/$top "+event.getScreenX+"/"+event.getScreenY+" "+event.getSceneX+"/"+event.getSceneY+" "+event.getX+"/"+event.getY)
        // println(s"setOnDragDone drops $left $top  - "+event.getDragboard.getDragViewOffsetX+"  "+event.getDragboard.getDragViewOffsetY)
        if (event.transferMode == TransferMode.Move) {
          //   group.relocate(left, top)
          //        layout.relocate(left, top)
          //        println("dragdone "+upperleft+"/"+topright)
          //    group.setTranslateX(left-upperleft)
          //    group.setTranslateY(top-topright)
          ddui.autoformatContent(event.x, event.y, CustomContent.currentContent)
        }
        event.consume()
      }
  }

  // central way of telling drabBoard what is being dragged
  // also lets you set global var to hold the dragged Object if you want
  private def setDragContent(db : Dragboard, tv : TreeView[CustomContentItem]) : Unit = {
    val ti = tv.selectionModel.value.getSelectedItem //.getTreeItem(tv.getSelectionModel().getSelectedIndex());
    //     db.setDragView(ti.getValue().toString());
    val content = new ClipboardContent()
    val name = ti.toString
    println("drag for "+name)
    content.putString(name)
    db.setContent(content)
  }


}
