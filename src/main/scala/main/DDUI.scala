package main

import com.westat.{CustomContentParent, DropPosition, GDSection}

import scalafx.scene.Group
import scalafx.scene.paint.Color
import scalafx.scene.shape.{Circle, Line, Rectangle}
import scalafx.scene.text.Text
import scalafx.stage.Screen

/**
  * Created by lee on 5/11/17.
  */
class DDUI(root : Group) {

  private val pageWidth = 850.0
  private val pageHeight = 1100.0

  init(root)

  private def makeRulers(group : Group) = {
    val inch = Screen.primary.dpi
    val half = inch / 2
    var line : Line = null
    var halfMark : Line = null
    var inchMark : Line = null
    // add row lines
    for (y <- (1 to 10)) {
      line = new Line {
        startX = 1
        startY = y*100
        endX = pageWidth
        endY = y*100
        stroke = Color.Black
        strokeWidth = 2
      }
      val vInd = new Circle {
        centerX = 5
        centerY = 5
        radius = 5
        fill = Color.Black
      }
      DropPosition.setVInd(vInd);
      group.children.addAll(
        line,
        new Text(1, (y*100)+12, (y*100).toString),
        vInd
      )
    }
    // add column lines
    for (x <- (1 to 8)) {
      line = new Line {
        startX = x*100
        startY = 1
        endX = x*100
        endY = pageHeight
        stroke = Color.Black
        strokeWidth = 2
      }
      halfMark = new Line {
        startX = x*100
        startY = 1
        endX = x*100
        endY = group.layoutBounds.get().getHeight
        stroke = Color.Black
        strokeWidth = 2
      }
      val hInd = new Circle {
        centerX = 5
        centerY = 5
        radius = 5
        fill = Color.Black
      }
      DropPosition.setHInd(hInd)
      group.children.addAll(
        line,
        new Text((x*100), 12, (x*100).toString),
        hInd
      )
    }
  }

  private def init(root : Group) = {
    makeRulers(root);
    val target = new Rectangle {
    layoutX = 1
    layoutY = 1
    width = pageWidth
    height = pageHeight
    fill = Color.BlanchedAlmond
    opacity = 0.7
    stroke = Color.Blue
    }
    GDSection.setupTargetEvents(target)

    root.children.add(target)

    GDSection.setRoot(root)
    loadLayout

  }

  private def loadLayout = {
    GDSection.makeSection(100, 100, 80, 40, Color.Azure, "move me somewhere")
    GDSection.makeExtSection(50, 250, "Extra", "Some descriptive text...", "a control or something else")
    GDSection.makeCheckboxSection(300, 200, 120, 50, "Annual figures")
    // make an item with x, y, id, text, label,checkbox, respName
  }

  def autoformatContent(x : Double, y : Double, cc : CustomContentParent) = {
    GDSection.autoformat(x.toInt, y.toInt, cc)

  }

}
