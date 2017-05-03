package com.westat

import javafx.event.{ActionEvent, EventHandler}
import javafx.geometry.Insets
import javafx.scene.control.{ContextMenu, Menu, MenuItem}
import javafx.scene.input.ContextMenuEvent
import javafx.scene.text.{Font, FontWeight}
import javafx.scene.Group
import javafx.scene.control.CheckBox
import javafx.scene.input.{ClipboardContent, DragEvent, Dragboard, MouseEvent, TransferMode}
import javafx.scene.layout._
import javafx.scene.paint.Color
import javafx.scene.shape.{Circle, Rectangle, Shape}
import javafx.scene.text.Text

import scala.collection.mutable.ListBuffer

/**
  * Created by lee on 12/6/16.
*/

trait QnrItem {
  def id : String
  def x : Int
  def y : Int
  def name : String
}

case class QItem(anID : String, aX : Int, aY : Int, aName : String) extends QnrItem {
  def id : String = anID
  def x : Int = aX
  def y : Int = aY
  def name : String = aName
}

case class GDSection(name : String) {
  private val agroup = new Group
  private val textOffsetX: Double = 5
  private val textOffsetY: Double = 5
  def group : Group = agroup
  private val grid = new GridPane
  private var source : Rectangle = null
  //  layout.setPadding(new Insets(5, 5, 5, 5))
  private var offX : Double = 30
  private var offY : Double = 40
  private var upperleft : Double = 1
  private var topright : Double = 1
  private var currentCol = 1
  private var currentRow = 0
  private val items = new ListBuffer[javafx.scene.Node]

  private def init(startLeft: Int, startTop: Int, width : Int, height : Int, color : Color = Color.LIGHTBLUE) : GDSection = {
    upperleft = startLeft
    topright = startTop
    //  group.setPrefSize(width, height)
 //   val source: Rectangle = new Rectangle(startLeft, startTop, width, height)
    source = new Rectangle(startLeft, startTop, width, height)
    source.setFill(color)
    group.getChildren.addAll(source, grid)
    grid.setLayoutX(startLeft)
    grid.setLayoutY(startTop)
    grid.setPrefSize(width, height)
    grid.autosize()
    grid.setBackground(new Background( new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY )))
//    val layout = new VBox(10)
//    layout.setPadding(new Insets(5, 5, 5, 5))
//    group.getChildren.add(layout)
    this
  }

  private def addNode(node : javafx.scene.Node) : GDSection = {
    node.setId((grid.getChildren.size()).toString)
    grid.add(node, currentCol, currentRow)
    items += node
    source.setWidth(grid.getWidth)
    source.setHeight(grid.getHeight)
    this
  }

  private def removeNode(anID : String) : Int = {
  //  items.foreach(n => println(n))
    val res = items.indexWhere(n => n.getId.equals(anID))
 //  println(s"removeNode called for $anID and found it at $res")
    if (res >= 0)
       items.remove(res)
    res
  }

  private def addToNewRow(node : javafx.scene.Node, text : String) : GDSection = {
    currentRow += 1
    currentCol = 0
  //  node.setId((grid.getChildren.size()).toString)
    node.setAccessibleText(text)
 //   grid.add(node, currentCol, currentRow)
    addNode(node)
    currentCol += 1
    this
  }

  private def addToCurrentRow(node : javafx.scene.Node, text : String) : GDSection = {
  //  node.setId((grid.getChildren.size()).toString)
    node.setAccessibleText(text)
  //  grid.add(node, currentCol, currentRow)
    addNode(node)
    currentCol += 1
    this
  }

  // id for each menu item is sectionIndex:contentIndex
  private def addSubMenu(parentItem : Menu, name : String, id : String) = {
    val subItem = new MenuItem(name)
    subItem.setId(id)
    subItem.setOnAction(new EventHandler[ActionEvent]() {
      def handle(event : ActionEvent) {
        GDSection.handleRemove(subItem.getId)
      //  println("called Remove on "+subItem.getId)
      } })
    parentItem.getItems.add(subItem)
  }

  private def setUpContextMenu : GDSection = {
    // make a context menu for the table
    val cm = new ContextMenu()
    val sm = new Menu("Remove")
    val idx = GDSection.currentSectionIndex.toString
    cm.getItems.add(sm)
    addSubMenu(sm, "Entire Section", s"$idx:-1")
    var it = grid.getChildren.iterator()
    var nd = it.next()
    while (nd.ne(null)) {
      println(" adding to context menu from gridChildren "+nd)
      addSubMenu(sm, nd.getAccessibleText, s"$idx:"+nd.getId)
      if (it.hasNext)
        nd = it.next()
      else
        nd = null
    }
    group.setOnContextMenuRequested(new EventHandler[ContextMenuEvent]() {
      def handle(event : ContextMenuEvent): Unit = {
        cm.show(grid, event.getScreenX, event.getScreenY)
        event.consume()
      }
    })
    this
  }

  def setDragHandlers : GDSection = {
    group.setOnDragDetected(new EventHandler[MouseEvent]() {
      def handle(event: MouseEvent) {
        val db: Dragboard = group.startDragAndDrop(TransferMode.MOVE)
//        println("dragdetected Screen/scene/x "+event.getScreenX+"/"+event.getSceneX+"/"+event.getX)
//        println("dragdetected Screen/scene/y "+event.getScreenY+"/"+event.getSceneY+"/"+event.getY)
        //     println("  root / group "+LayoutSection.root.getLayoutX+"/"+ group.getLayoutX)
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
  //       println(s"dragdone drop/Screen/scene/x $left/$top "+event.getScreenX+"/"+event.getScreenY+" "+event.getSceneX+"/"+event.getSceneY+" "+event.getX+"/"+event.getY)
        // println(s"setOnDragDone drops $left $top  - "+event.getDragboard.getDragViewOffsetX+"  "+event.getDragboard.getDragViewOffsetY)
        if (event.getTransferMode eq TransferMode.MOVE) {
          group.relocate(left, top)
  //        layout.relocate(left, top)
  //        println("dragdone "+upperleft+"/"+topright)
      //    group.setTranslateX(left-upperleft)
      //    group.setTranslateY(top-topright)
        }
        event.consume
      }
    })
    this
  }

  def moveLeft(amt : Long) = {
    group.setLayoutX(group.getLayoutX - amt)
  }

  def moveRight(amt : Long) = {
    group.setLayoutX(group.getLayoutX + amt)
//    group.relocate(group.getLayoutX - amt, group.getLayoutY)
  }

  def setBounds(startLeft: Int, startTop: Int, width : Int, height : Int, color : Color = Color.LIGHTBLUE) : GDSection = {
    init(startLeft, startTop, width, height, color)
    val ttitle: Text = new Text(startLeft + textOffsetX, startTop + 10 + textOffsetY, name)
    ttitle.setWrappingWidth(width - (2 * textOffsetX))
    ttitle.setFont(Font.font(null, FontWeight.BOLD, Font.font(null).getSize))
//    group.getChildren.addAll(source, ttitle)
  //  grid.add(ttitle, 1, 1)
    addToNewRow(ttitle, name)
    setDragHandlers
  }

  def setText(startLeft: Int, startTop: Int, title : String, descr : String, doodad : String) : GDSection = {
    init(startLeft, startTop, 180, 80, Color.GREEN)
    val ttitle: Text = new Text(startLeft + textOffsetX, startTop + 30 + textOffsetY, title)
    val tdescr: Text = new Text(startLeft + textOffsetX, startTop + 45 + textOffsetY, descr)
    val tdood: Text = new Text(startLeft + textOffsetX, startTop + 60 + textOffsetY, doodad)
//    group.getChildren.addAll(source, ttitle, tdescr, tdood)
    addToNewRow(ttitle, title)
    addToNewRow(tdescr, descr)
    addToNewRow(tdood, doodad)
//    layout.getChildren.addAll(ttitle, tdescr, tdood)
//    group.getChildren.add(new Rectangle(0,0,0,0))
    addCheckbox("1")
//    addToCurrentRow(new Text("  no "))
    addCheckboxToCurrentRow("2")
    grid.getColumnConstraints.add(new ColumnConstraints(50))
    setDragHandlers
  }

  def addCheckbox(text : String) : GDSection = {
    val cb = new CheckBox(text)
 //   cb.setOpacity(0.8)
    addToNewRow(cb, "Checkbox: "+text)
  }

  def addCheckboxToCurrentRow(text : String) : GDSection = {
    val cb = new CheckBox(text)
    //  cb.setOpacity(0.8)
    addToCurrentRow(cb, "Checkbox: "+text)
  }

}

object GDSection {
  var root : Group = null
  private val sectionList = new ListBuffer[GDSection]
  private var currentSection : GDSection = null

  def setRoot(value : Group) = {
    root = value
  }

  def moveCurrentLeft(amt : String) = {
    println(s"moveCurrentLeft called for $amt")
    val x = Length.dimension(amt)
    currentSection.moveLeft(x.asDeviceUnits)
  }
  def moveCurrentRight(amt : String) = {
    println(s"moveCurrentRight called for $amt")
    val x = Length.dimension(amt)
    currentSection.moveRight(x.asDeviceUnits)
  }

  def makeSection(startLeft: Int, startTop: Int, width : Int, height : Int, color : Color = Color.YELLOW, id : String): GDSection = {
    val section = GDSection(id)
    sectionList += section
    currentSection = section
    root.getChildren.add(section.agroup)
    section.agroup.setVisible(true)
    section.setBounds(startLeft, startTop, width, height, color)
    section.setUpContextMenu
  }

  def makeExtSection(startLeft: Int, startTop: Int, title: String, descr: String, doodad: String): GDSection = {
    val section = GDSection(title)
//    section.setId("ExtSection")
    sectionList += section
    currentSection = section
    root.getChildren.add(section.agroup)
    section.setText(startLeft, startTop, title, descr, doodad)
    section.setUpContextMenu
  }

  def makeCheckboxSection(startLeft: Int, startTop: Int, width : Int, height : Int, text: String) : GDSection = {
    val section = GDSection(text)
    sectionList += section
    currentSection = section
    root.getChildren.add(section.agroup)
    section.setBounds(startLeft, startTop, width, height)
    section.addCheckbox(text)
    section.addCheckbox("Second opt")
    section.setUpContextMenu
  }

  def currentSectionIndex : Int = {
    sectionList.indexOf(currentSection)
  }

  def idAsTuple(id : String) : (Int, Int) = {
    val arr = id.split(":")
    (arr.head.toInt, arr(1).toInt)
  }

  def handleRemove(anID : String) = {
    println("gdsection.handleRemove for "+anID)
    val idx = idAsTuple(anID)
    currentSection = sectionList(idx._1)
    idx._2 match {
      case -1 => {
        println(s"removing everything for $anID and index of $idx")
        currentSection.group.setVisible(false)
      }
      case _ : Int => {
        println("now should delete portion of section "+idx)
        val nidx = currentSection.removeNode(idx._2.toString)
        currentSection.grid.getChildren.remove(nidx)
        currentSection.setUpContextMenu
      }
    }
  /*  if (idx._2.equals(-1)) {
      val idx = root.getChildren.indexOf(currentSection)
      println(s"removing everything for $anID and index of $idx")
   //   root.getChildren.remove(idx)
   //   currentSection = root.getChildren.get(root.getChildren.size()).asInstanceOf[GDSection]
      currentSection.group.setVisible(false)
    }
*/
  }

  /*
  make questiontitle bold
  make responses use responselabel,
     if paper, then keycode
     if electronicResponse, checkbox control
   */
  def autoformat(startLeft: Int, startTop: Int, cc: CustomContentParent) : GDSection = {
  //  makeExtSection(startLeft, startTop, cc.id, cc.text, cc.dataElementName)
    val wid = 300
    val section = makeSection(startLeft, startTop, wid, 10, Color.ALICEBLUE, cc.children.head.asInstanceOf[CustomContentParent].children.head.text)
    cc.children.tail.foreach(par => addItems(section, par.asInstanceOf[CustomContentParent], wid))
    section
  }

  def addItems(section : GDSection, par: CustomContentParent, aWidth : Int) : GDSection = {
    if (par.text.isEmpty)
      return section

    par.children.foreach(cc => addItem(section, cc, aWidth))
/*    par match {
      case _: CCItemParent =>  par.children.foreach(cc =>
        if (cc.text.nonEmpty) {
          val node = new Text(cc.text)
          node.setWrappingWidth(aWidth - 4)
          section.addToNewRow(node)
        })
      case _ : CCItemResponse => {
   //     section.addToNewRow(new Text(par.children(1).text)) // keycode
        section.addToNewRow(new CheckBox(par.children(0).text+" "+par.asInstanceOf[CustomContentItem].dataElementName))
        //section.addToCurrentRow(new Text(" "+par.asInstanceOf[CustomContentItem].dataElementName))
      }
    }*/
    section
  }

  def addText(section: GDSection, text : String, aWidth : Int) = {
    if (text.nonEmpty) {
      val node = new Text(text)
      node.setWrappingWidth(aWidth - 4)
      section.addToNewRow(node, text)
    }
  }

  def addItem(section : GDSection, cc: CustomContentItem, aWidth : Int) : GDSection = {
    if (cc.text.isEmpty)
      return section
    cc match {
      case _ : CCQuestionTitle => addText(section, cc.text, aWidth)
      case _ : CCQuestionWording => addText(section, cc.text, aWidth)

      case _ : CCItemNumber => addText(section, cc.text, aWidth)
      case _ : CCItemWording => addText(section, cc.text, aWidth)
      case _ : CCItemHeaderColumnRef =>
      case _ : CCItemHeaderKeycode =>

      case r : CCItemResponse => r.children.foreach(rc => rc match {
        case _ : CCResponseLabel => //addText(section, rc.text, aWidth)
        case _ : CCResponseKeycode =>
        case _ : CCElectronicResponse => section.addCheckbox(r.label)
        case _ : CCPaperResponse =>
      })
    }
    section
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
          var left = x //sX
          var top = y //sY-(sY-y)
  //        println(s"dragOver left,top $left,$top screen $scrX/$scrY scene $sX, $sY raw $x/$y")

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
        DropPosition.dropX = event.getX // event.getSceneX
        DropPosition.dropY = event.getY // event.getSceneY-(event.getSceneY-event.getY)
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
