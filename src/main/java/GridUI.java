import com.westat.CellObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.VBox;

import javax.swing.text.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Owner on 11/16/2016.
 */
public class GridUI {
    private final TableView gidsTable = new TableView();
    final ObservableList<Question> data = FXCollections.observableArrayList(
            new Question("1", "Mailing Address", "", "name and address"),
            new Question("2", "Sales and stuff", "All the revenue you can ever have.", "rev sources"),
            new Question("3", "Expenses and stuff", "Lots of expenses", "Utilities, COS, Manufacturing exp, etc"),
            new Question("4", "Certification", "Fill this in when submitting to Census", "name and address")
    );


    public void setUpGrid(VBox vbox) {
        gidsTable.setEditable(true);
        gidsTable.setPrefSize(500, 350);
        TableColumn idCol = new TableColumn("ID");
        TableColumn titleCol = new TableColumn("Title");
        TableColumn descrCol = new TableColumn("Description");
        TableColumn doodadCol = new TableColumn("DooDads");
        idCol.setCellValueFactory( new PropertyValueFactory<>("id") );
        titleCol.setCellValueFactory( new PropertyValueFactory<>("title") );
        descrCol.setCellValueFactory( new PropertyValueFactory<>("descr") );
        doodadCol.setCellValueFactory( new PropertyValueFactory<>("doodad") );
        gidsTable.setItems(data);
        gidsTable.getColumns().addAll(idCol, titleCol, descrCol, doodadCol);
        gidsTable.getSelectionModel().setCellSelectionEnabled(true);
        gidsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        vbox.getChildren().add(gidsTable);

        // make a context menu for the table
        final ContextMenu cm = new ContextMenu();
        MenuItem cmCut = new MenuItem("Cut");
        cmCut.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) { handleClipboardCut(event); } });
        cm.getItems().add(cmCut);
        MenuItem cmCopy = new MenuItem("Copy");
        cmCopy.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) { handleClipboardCopy(event); } });
        cm.getItems().add(cmCopy);
        MenuItem cmPaste = new MenuItem("Paste");
        cmPaste.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) { handleClipboardPaste(event); } });
        cm.getItems().add(cmPaste);
        gidsTable.setContextMenu(cm);
    }

    private String gridSelectionAsClipboardString(TableView table) {
        final StringBuffer cdata = new StringBuffer();
        ObservableList<TablePosition> positions = table.getSelectionModel().getSelectedCells();

        // the purpose of the following messy lines is to correct the fact that the selected cells are not sorted by row and col
        final ArrayList sdata = new ArrayList<String>(positions.size());
        positions.forEach((position) -> {
            sdata.add(String.format("%04d %04d -- %s\n", position.getRow(), position.getColumn(), position.getTableColumn().getCellObservableValue(position.getRow()).getValue().toString()));
        });
        Collections.sort(sdata);
        sdata.forEach((line) -> {cdata.append(line);});
        // now turn the result into one line for each row, with tabs separating each column value
        return CellObject.rawToCFString(cdata.toString());
    }

    private void handleClipboardCut(ActionEvent e) {
        handleClipboardCopy(e);
        ObservableList<TablePosition> positions = gidsTable.getSelectionModel().getSelectedCells();
        positions.forEach((position) -> {
            int row = position.getRow();
            int col = position.getColumn();
            data.get(row).setColumnValue(col, "");
        });
        gidsTable.refresh();
    }

    private void handleClipboardCopy(ActionEvent e) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        // get the result as list of one line for each row, with tabs separating each column value
        String result = gridSelectionAsClipboardString(gidsTable);

        content.putString(result);
        clipboard.setContent(content);
    }

    private void handleClipboardPaste(ActionEvent e) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        if (!clipboard.hasString())
            return;
        int row = gidsTable.getSelectionModel().getSelectedIndex();
        TablePosition pos = (TablePosition)gidsTable.getSelectionModel().getSelectedCells().get(0);
        int startCol = pos.getColumn();

        String[] lines = clipboard.getString().split("\n");
        for (int i = 0; i < lines.length; i++) {
            String cell = lines[i];
            String[] words = cell.split("\t");
            int col = 0;
            for (String word : words) {
                data.get(row+i).setColumnValue(startCol+col, word);
                col++;
            }
        }
        gidsTable.refresh();

    }

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


}
