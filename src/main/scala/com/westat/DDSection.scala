package com.westat

import java.awt.{GridBagLayout, Checkbox}
import javafx.application.Application
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.Group
import javafx.scene.control.CheckBox
import javafx.scene.input.{DragEvent, MouseEvent, ClipboardContent, TransferMode, Dragboard}
import javafx.scene.layout.{Pane, BackgroundFill, VBox}
import javafx.scene.paint.Color
import javafx.scene.shape.{Circle, Shape, Rectangle}
import javafx.scene.text.Text
import javafx.stage.Stage

/**
  * Created by lee on 12/6/16.

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
*/
case class DDSection(name : String) {
  private val agroup = new Group  // new Pane()  // new Group
  private val textOffsetX: Double = 5
  private val textOffsetY: Double = 5
  def group : Group = agroup
//  val layout = new VBox(10)
//  layout.setPadding(new Insets(5, 5, 5, 5))
  private var offX : Double = 30
  private var offY : Double = 40
  private var upperleft : Double = 1
  private var topright : Double = 1

  def makeLayout = {
//    val layout = new VBox(10)
//    layout.setPadding(new Insets(5, 5, 5, 5))
//    group.getChildren.add(layout)
  }

  def setDragHandlers : DDSection = {
    group.setOnDragDetected(new EventHandler[MouseEvent]() {
      def handle(event: MouseEvent) {
        val db: Dragboard = group.startDragAndDrop(TransferMode.MOVE)
//        println("dragdetected Screen/scene/x "+event.getScreenX+"/"+event.getSceneX+"/"+event.getX)
//        println("dragdetected Screen/scene/y "+event.getScreenY+"/"+event.getSceneY+"/"+event.getY)
        //     println("  root / group "+LayoutSection.root.getLayoutX+"/"+ group.getLayoutX)
        //      db.setDragView(group.snapshot(null, null), offX, offY)
    //    db.setDragView(group.snapshot(null, null))
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
         println(s"dragdone drop/Screen/scene/x $left/$top "+event.getScreenX+"/"+event.getScreenY+" "+event.getSceneX+"/"+event.getSceneY+" "+event.getX+"/"+event.getY)
        // println(s"setOnDragDone drops $left $top  - "+event.getDragboard.getDragViewOffsetX+"  "+event.getDragboard.getDragViewOffsetY)
        if (event.getTransferMode eq TransferMode.MOVE) {
          group.relocate(left, top)
  //        layout.relocate(left, top)
          println("dragdone "+upperleft+"/"+topright)
      //    group.setTranslateX(left-upperleft)
      //    group.setTranslateY(top-topright)
        }
        event.consume
      }
    })
    this
  }

  def setBounds(startLeft: Int, startTop: Int, width : Int, height : Int, color : Color = Color.LIGHTBLUE) : DDSection = {
    makeLayout
    upperleft = startLeft
    topright = startTop
  //  group.setPrefSize(width, height)
    val source: Rectangle = new Rectangle(startLeft, startTop, width, height)
    source.setFill(color)
//       group.getChildren.add(source)
    val ttitle: Text = new Text(startLeft + textOffsetX, startTop + 10 + textOffsetY, name)
    ttitle.setWrappingWidth(width - (2 * textOffsetX))
    group.getChildren.addAll(source, ttitle)
//    layout.getChildren.add(ttitle)
    setDragHandlers
  }

  def setText(startLeft: Int, startTop: Int, title : String, descr : String, doodad : String) : DDSection = {
    makeLayout
    val source: Rectangle = new Rectangle(startLeft, startTop, 180, 80)
    source.setFill(Color.GREEN)
    source.setOpacity(0.7)
    //    group.getChildren.add(source)
    val ttitle: Text = new Text(startLeft + textOffsetX, startTop + 30 + textOffsetY, title)
    val tdescr: Text = new Text(startLeft + textOffsetX, startTop + 45 + textOffsetY, descr)
    val tdood: Text = new Text(startLeft + textOffsetX, startTop + 60 + textOffsetY, doodad)
    group.getChildren.addAll(source, ttitle, tdescr, tdood)
//    layout.getChildren.addAll(ttitle, tdescr, tdood)
//    group.getChildren.add(new Rectangle(0,0,0,0))
    setDragHandlers
  }

  def addCheckbox(text : String) : DDSection = {
    val cb = new CheckBox(text)
    cb.setOpacity(0.8)
    cb.setLayoutX(upperleft+10)
    cb.setLayoutY(topright+30)
    group.getChildren.add(cb)
    this
  }

}

object DDSection {
  var root : Group = null

  def setRoot(value : Group) = {
    root = value
  }

  def makeSection(startLeft: Int, startTop: Int, width : Int, height : Int, color : Color = Color.YELLOW, id : String): DDSection = {
    val section = DDSection(id)
    section.setBounds(startLeft, startTop, width, height, color)
  }

  def makeExtSection(startLeft: Int, startTop: Int, title: String, descr: String, doodad: String): DDSection = {
    val section = DDSection(title)
//    section.setId("ExtSection")

    section.setText(startLeft, startTop, title, descr, doodad)
  }

  def makeCheckboxSection(startLeft: Int, startTop: Int, width : Int, height : Int, text: String) : DDSection = {
    val section = DDSection("CB"+text)
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
          val scrX = event.getScreenX
          val scrY = event.getScreenY
          val tX = 30
          val tY = 40
          var left = sX
          var top = sY-(sY-y)
          println(s"dragOver left,top $left,$top screen $scrX/$scrY scene $sX, $sY raw $x/$y")

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
