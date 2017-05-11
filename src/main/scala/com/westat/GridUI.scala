package com.westat

import scalafx.Includes._
import scalafx.beans.property.StringProperty
import scalafx.collections.{ObservableBuffer}
import scalafx.scene.control._
import scalafx.scene.control.TableColumn._
import scalafx.event.ActionEvent
import scalafx.event.EventIncludes
import scalafx.scene.input.{Clipboard, ClipboardContent}


/**
 * Created by Owner on 11/16/2016.
 */

class Question(anID : String, aTitle : String, aDescr : String, aDoodad : String) {
    val id     = new StringProperty(this, "id", anID)
    val title  = new StringProperty(this, "title", aTitle)
    val descr  = new StringProperty(this, "descr", aDescr)
    val doodad = new StringProperty(this, "doodad", aDoodad)
    def setColumnValue(col : Int, value : String) = {
       col match {
           case 0 => id.set(value)
           case 1 => title.set(value)
           case 2 => descr.value = value
           case 3 => doodad.value = value
       }
    }
}


class GridUI {
    var data = ObservableBuffer(
        new Question("1", "Mailing Address", "", "name and address"),
        new Question("2", "Sales and stuff", "All the revenue you can ever have.", "rev sources"),
        new Question("3", "Expenses and stuff", "Lots of expenses", "Utilities, COS, Manufacturing exp, etc"),
        new Question("4", "Certification", "Fill this in when submitting to Census", "name and address")
    )

    private val gidsTable = new TableView[Question](data) {
        editable = true
        prefWidth = 500
        prefHeight = 350
        val idCol = new TableColumn[Question, String]("ID")
        idCol.cellValueFactory = cdf => { cdf.value.id }
        val titleCol = new TableColumn[Question, String]("Title") {
            cellValueFactory = {_.value.title }
        }
        val descrCol = new TableColumn[Question, String]("Description") {
            cellValueFactory = {_.value.descr }
        }
        val doodadCol = new TableColumn[Question, String] {
            text = "DooDads"
            cellValueFactory = {_.value.doodad }
        }

        columns ++= List(
            idCol,
            titleCol, descrCol, doodadCol
        )
        selectionModel().setSelectionMode(SelectionMode.Single)
        selectionModel().setCellSelectionEnabled(true)
        val miCut = new MenuItem("Cut")
        miCut.onAction = (event : ActionEvent) => { handleClipboardCut(event) }
        val miCopy = new MenuItem("Copy")
        miCopy.onAction =  (event : ActionEvent) => { handleClipboardCopy(event) }
//        miCopy.onAction = perform { handleClipboardCopy(null) }
//        miCopy.onAction_=((event : ActionEvent) => { handleClipboardCopy(event) })
        val miPaste = new MenuItem("Paste")
        miPaste.onAction = (event : ActionEvent) => { handleClipboardPaste(event) }
        contextMenu = new ContextMenu {
            items ++= List(
                miCut, miCopy, miPaste)
           //     new MenuItem("Cut")  {
                 // text = "Cut"
            //      onAction = (event : ActionEvent) => { handleClipboardCut(event) }
                      //( event : ActionEvent ) => {
             //            handleClipboardCut(event)
            //             event.consume()
            //         }
            //      }
              //  },
        /*
                     new MenuItem("Functional") {
                        onAction = (e : ActionEvent) => {handleFunctional(e)}
                      },

             new MenuItem {
                    text = "Copy"
                    onAction = { handleClipboardCopy(null) }
                },
                new MenuItem {
                    text = "Paste"
                    onAction = { handleClipboardPaste(null) }
                    }
                }
            )*/
        }
    }

    def tableView : TableView[Question] = gidsTable


    private def gridSelectionAsClipboardString : String = {
      /*  final StringBuffer cdata = new StringBuffer();
        ObservableList<TablePosition> positions = table.getSelectionModel().getSelectedCells();

        // the purpose of the following messy lines is to correct the fact that the selected cells are not sorted by row and col
        final ArrayList sdata = new ArrayList<String>(positions.size());
        positions.forEach((position) -> {
            sdata.add(String.format("%04d %04d -- %s\n", position.getRow(), position.getColumn(), position.getTableColumn().getCellObservableValue(position.getRow()).getValue().toString()));
        });
        Collections.sort(sdata);
        sdata.forEach((line) -> {cdata.append(line);});
        // now turn the result into one line for each row, with tabs separating each column value
        return CellObject.rawToCFString(cdata.toString());*/
        val positions = gidsTable.getSelectionModel.getSelectedCells
 //       positions.sorted((first, second) => first.asInstanceOf[TablePosition].getRow < second.asInstanceOf[TablePosition].getRow)
        val apos = positions.get(0)
        apos.getTableColumn.getCellObservableValue(apos.getRow).getValue.toString
    }

    def handleClipboardCut(e : ActionEvent) : Unit = {
        handleClipboardCopy(e);
        val positions = gidsTable.getSelectionModel.getSelectedCells
        positions.foreach(position => {
            val row = position.getRow()
            val col = position.getColumn()
            data.get(row).setColumnValue(col, "")
        })
        gidsTable.refresh()
    }

    def handleClipboardCopy(e : ActionEvent) : Unit = {
        val clipboard = Clipboard.systemClipboard
        val content = new ClipboardContent()
        // get the result as list of one line for each row, with tabs separating each column value
        val result = gridSelectionAsClipboardString

        content.putString(result)
        clipboard.setContent(content)
        e.consume()
    }

    def handleClipboardPaste(e : ActionEvent) : Unit = {
        val clipboard = Clipboard.systemClipboard
        if (clipboard.hasString()) {
            val row = gidsTable.getSelectionModel.getFocusedIndex
            val pos = gidsTable.getSelectionModel().getSelectedCells().get(0)
            val startCol = pos.getColumn()
            val lines = clipboard.string.split("\n")
            for (i <- 0 to lines.length-1) {
                val cell = lines(i)
                val words = cell.split("\t")
                var col = 0
                for (word <- words) {
                    data.get(row+i).setColumnValue(startCol+col, word)
                    col += 1
                }
            }
            gidsTable.refresh()
        }
       e.consume()
    }

    /*
    //------------------------------------ Question class ----------------------------------
    public static class Question {
        private final SimpleStringProperty id;
        private final SimpleStringProperty title;
        private final SimpleStringProperty descr;
        private final SimpleStringProperty doodad;

        private Question(String id, String title, String descr, String doodad) {
            this.id     = new SimpleStringProperty(id);
            this.title  = new SimpleStringProperty(title);
            this.descr  = new SimpleStringProperty(descr);
            this.doodad = new SimpleStringProperty(doodad);
        }

        public String getId() {
            return id.get();
        }
        public void setId(String anId) {
            id.set(anId);
        }

        public String getTitle() {
            return title.get();
        }
        public void setTitle(String aTitle) {
            title.set(aTitle);
        }

        public String getDescr() {
            return descr.get();
        }
        public void setDescr(String aDescr) {
            descr.set(aDescr);
        }

        public String getDoodad() {
            return doodad.get();
        }
        public void setDoodad(String aDoodad) {
            doodad.set(aDoodad);
        }

        public void setColumnValue(int index, String value) {
            switch(index) {
                case 0 : setId(value); break;
                case 1 : setTitle(value); break;
                case 2 : setDescr(value); break;
                case 3 : setDoodad(value); break;
                default: break;
            }
        }

    }

*/
}
