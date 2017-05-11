package main

import scalafx.Includes._
import com.westat.{GDSection, GridUI}

import scalafx.application.JFXApp
import scalafx.event.ActionEvent
import scalafx.event.EventIncludes
import scalafx.geometry.{Insets, Side}
import scalafx.scene.Scene
import scalafx.scene.control.TabPane.TabClosingPolicy
import scalafx.scene.control._
import scalafx.scene.layout.{HBox, Priority, VBox}
import scalafx.stage.Screen
/**
 * Created by Owner on 5/3/2017.
 */


object GidsMain extends JFXApp {

  stage = new JFXApp.PrimaryStage {
    //   initStyle(StageStyle.Unified)
    title = "GidsJavaFx"
    width = 1000
    height = 500

    scene = new Scene {
      root = new VBox {
        vgrow = Priority.Always
        hgrow = Priority.Always
        spacing = 10
        padding = Insets(20)
        maxWidth = Screen.primary.visualBounds.width
        maxHeight = Screen.primary.visualBounds.height
        children = List(
          new ToolBar {
            // toolbar controls
            val btnLeft = new Button("Left")
            btnLeft.onAction =  (event : ActionEvent) => {
            //  println("left button clicked")
              GDSection.moveCurrentLeft(edSize.getText)
            }
            val btnRight = new Button("Right")
            btnRight.onAction = (event : ActionEvent) => {
              //println("right button clicked")
              GDSection.moveCurrentRight(edSize.getText)
            }
            val edSize = new TextField()
            edSize.text =  "1.00in"
            edSize.setPromptText("Distance (eg 1.0mm)")

            items = List(
              new Separator(),
              btnLeft,
              btnRight,
              new Separator(),
              edSize,
              new Separator()
            )
          },

        new TabPane {
            minWidth = 400
            prefWidth = 1000
            prefHeight = 500
            minWidth = 500
            minHeight = 300

          tabs = Seq(
              new Tab {
                text = "Grid Clipboard"
                tooltip = new Tooltip("Grid Clipboard example")
                content = new VBox {
                  spacing = 10
                  children = List(
                    new GridUI().tableView
                  )
                }
              },
              new Tab {
                text = "Drag and Drop"
                tooltip = new Tooltip("Drag and Drop example")
                content = new FormDesigner().node
              }

            )
            tabClosingPolicy = TabClosingPolicy.Unavailable
            side = Side.Top
          }

        )
      }
    }
  }


}
