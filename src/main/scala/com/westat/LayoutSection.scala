package com.westat

import java.awt.Checkbox
import javafx.application.Application
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.Group
import javafx.scene.control.CheckBox
import javafx.scene.input.{DragEvent, MouseEvent, ClipboardContent, TransferMode, Dragboard}
import javafx.scene.layout.{BackgroundFill, VBox}
import javafx.scene.paint.Color
import javafx.scene.shape.{Circle, Shape, Rectangle}
import javafx.scene.text.Text
import javafx.stage.Stage

/**
  * Created by lee on 12/6/16.
  */

object DropPosition {
  var dropX : Double = 0
  var dropY : Double = 0
  var hInd : Circle = null
  var vInd : Circle = null
  def setDropX(value : Double) = {
    dropX = value
  }
  def setDropY(value : Double) = {
    dropY = value
  }
  def setHInd(value : Circle) = {
    hInd = value
  }
  def setVInd(value : Circle) = {
    vInd = value
  }
  def showPosition(x : Double, y : Double) = {
    hInd.relocate(x-4, 20)
    vInd.relocate(20, y-4)
//    println(s"showPosition $x $y")
  }
}

case class Section(name : String) {
  private val agroup = new Group
  private val textOffsetX: Double = 5
  private val textOffsetY: Double = 5
  def group : Group = agroup
//  val layout = new VBox(10)
//  layout.setPadding(new Insets(5, 5, 5, 5))
  private var offX : Double = 30
  private var offY : Double = 40

  def setBounds(startLeft: Int, startTop: Int, width : Int, height : Int, color : Color = Color.GREEN) : Section = {
    val source: Rectangle = new Rectangle(startLeft, startTop, width, height)
    source.setFill(color)
    val ttitle: Text = new Text(startLeft + textOffsetX, startTop + 10 + textOffsetY, name)
    ttitle.setWrappingWidth(width - (2 * textOffsetX))
    group.getChildren.addAll(source, ttitle)

    group.setOnDragDetected(new EventHandler[MouseEvent]() {
      def handle(event: MouseEvent) {
        val db: Dragboard = group.startDragAndDrop(TransferMode.MOVE)
      //  offX = event.getSceneX - group.getLayoutX
      //  offY = event.getSceneY - group.getLayoutY

   println("dragdetected Screen/scene/x "+event.getScreenX+"/"+event.getSceneX+"/"+event.getX)
   println("dragdetected Screen/scene/y "+event.getScreenY+"/"+event.getSceneY+"/"+event.getY)
   //     println("  root / group "+LayoutSection.root.getLayoutX+"/"+ group.getLayoutX)
  //      db.setDragView(group.snapshot(null, null), offX, offY)
        db.setDragView(group.snapshot(null, null))
        val content: ClipboardContent = new ClipboardContent
        content.putString(name)
        db.setContent(content)
        event.consume
      }
    })
    group.setOnDragDone(new EventHandler[DragEvent]() {
      def handle(event: DragEvent) {
        val left: Double = DropPosition.dropX // - offX //dropX
        val top: Double = DropPosition.dropY //- offY //dropY
        // println(s"setOnDragDone drops $left $top  - "+event.getDragboard.getDragViewOffsetX+"  "+event.getDragboard.getDragViewOffsetY)
        if (event.getTransferMode eq TransferMode.MOVE) {
          group.relocate(left, top)
        }
        event.consume
      }
    })
    this
  }

  def setText(startLeft: Int, startTop: Int, title : String, descr : String, doodad : String) : Section = {
    val source: Rectangle = new Rectangle(startLeft, startTop, 180, 80)
    source.setFill(Color.GREEN)
    source.setOpacity(0.7)
    val ttitle: Text = new Text(startLeft + textOffsetX, startTop + 30 + textOffsetY, title)
    val tdescr: Text = new Text(startLeft + textOffsetX, startTop + 45 + textOffsetY, descr)
    val tdood: Text = new Text(startLeft + textOffsetX, startTop + 60 + textOffsetY, doodad)
    group.getChildren.addAll(source, ttitle, tdescr, tdood)
//    group.getChildren.add(new Rectangle(0,0,0,0))
    group.setOnDragDetected(new EventHandler[MouseEvent]() {
      def handle(event: MouseEvent) {
        val db: Dragboard = group.startDragAndDrop(TransferMode.MOVE)
        db.setDragView(group.snapshot(null, null))
        val content: ClipboardContent = new ClipboardContent
        content.putString(group.toString)
        db.setContent(content)
        event.consume
      }
    })
    group.setOnDragDone(new EventHandler[DragEvent]() {
      def handle(event: DragEvent) {
        val left: Double = DropPosition.dropX //dropX
        val top: Double = DropPosition.dropY //dropY
       // println(s"setOnDragDone drops $left $top  - "+event.getDragboard.getDragViewOffsetX+"  "+event.getDragboard.getDragViewOffsetY)
        if (event.getTransferMode eq TransferMode.MOVE) {
      /*    source.setX(left)
          source.setY(top)
          ttitle.setX(left + textOffsetX)
          ttitle.setY(top + 30 + textOffsetY)
          tdescr.setX(left + textOffsetX)
          tdescr.setY(top + 45 + textOffsetY)
          tdood.setX(left + textOffsetX)
          tdood.setY(top + 60 + textOffsetY)*/
      //    group.getChildren.remove(4)
          group.relocate(left, top)
  //        group.setOpacity(0.8)
          //       group.getChildren.add(0, new Rectangle(0,0,0,0))
     //     group.requestLayout()
     //     LayoutSection.root.requestLayout()
          //group.getParent.requestLayout()
     //     group.setVisible(false)
     //     group.setVisible(true)
        //  LayoutSection.root.setVisible(true)
        }
        event.consume
      }
    })
    this
  }

  def addCheckbox(text : String) : Section = {
    val cb = new CheckBox(text)
    cb.setOpacity(0.8)
//    val label: Text = new Text(startLeft + textOffsetX, startTop + 30 + textOffsetY, text)
    val layout = new VBox(10)
    layout.setPadding(new Insets(5, 5, 5, 5))
    layout.getChildren.add(cb)
    group.getChildren.add(layout)
    this
  }

}

object LayoutSection {
  var root : Group = null

  def setRoot(value : Group) = {
    root = value
  }

  def makeSection(startLeft: Int, startTop: Int, width : Int, height : Int, color : Color = Color.YELLOW, id : String): Section = {
    val section = Section(id)
    section.setBounds(startLeft, startTop, width, height)
  }

  def makeExtSection(startLeft: Int, startTop: Int, title: String, descr: String, doodad: String): Section = {
    val section = Section(title)
//    section.setId("ExtSection")

    section.setText(startLeft, startTop, title, descr, doodad)
  }

  def makeCheckboxSection(startLeft: Int, startTop: Int, width : Int, height : Int, text: String) : Section = {
    val section = Section("CB"+text)
    section.setBounds(startLeft, startTop, width, height)
    section.addCheckbox(text)
  }

  def setupTargetEvents(shape: Shape) {
    shape.setOnDragOver(new EventHandler[DragEvent]() {
      def handle(event: DragEvent) {
        if ((event.getGestureSource ne shape) && event.getDragboard.hasString) {
          event.acceptTransferModes(TransferMode.MOVE)
          val x = event.getX
          val y = event.getY
          val sX = event.getSceneX
          val sY = event.getSceneY
          val tX = 30
          val tY = 40
          var left = sX
          var top = sY-(sY-y)
    //      println(s"dragOver left,top $left,$top scene $sX, $sY")

          DropPosition.showPosition(left, top)
        }
        event.consume
      }
    })
    shape.setOnDragEntered(new EventHandler[DragEvent]() {
      def handle(event: DragEvent) {
        event.consume
      }
    })
    shape.setOnDragExited(new EventHandler[DragEvent]() {
      def handle(event: DragEvent) {
        event.consume
      }
    })
    shape.setOnDragDropped(new EventHandler[DragEvent]() {
      def handle(event: DragEvent) {
        DropPosition.dropX = event.getSceneX
        DropPosition.dropY = event.getSceneY-(event.getSceneY-event.getY)
  //      root.fireEvent()
        val db: Dragboard = event.getDragboard
        var success: Boolean = false
        if (db.hasString) {
          success = true
        }
        event.setDropCompleted(success)
        event.consume
      }
    })
  }
}
