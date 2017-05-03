import com.westat.CustomContentParent;
import com.westat.DropPosition;
import com.westat.GDSection;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Screen;


/**
 * Created by Owner on 11/16/2016.
 */
public class DDUI {

    private Double pageWidth = 850.;
    private Double pageHeight = 1100.;

    private void makeRulers(Group group) {
        double inch = Screen.getPrimary().getDpi();
        double half = inch / 2;
        Line line;
        Line halfMark;
        Line inchMark;
        // add row lines
        for (int i = 1; i < 5; i++) {
            line = new javafx.scene.shape.Line(1, (i*100), pageWidth, (i*100));
            line.setStroke(Color.BLACK);
            line.setStrokeWidth(2);
            Circle vInd = new Circle(5, 5, 5);
            vInd.setFill(Color.BLACK);
            DropPosition.setVInd(vInd);
            group.getChildren().addAll(
                    line,
                    new Text(1, (i*100)+12, String.valueOf(i*100)),
                    vInd
            );
        };
        // add column lines
        for (int i = 1; i < 8; i++) {
            line = new javafx.scene.shape.Line((i*100), 1, (i*100), pageHeight);
            line.setStrokeWidth(2);
            halfMark = new Line((i*100), 1, (i*100), group.getLayoutBounds().getHeight());
            Circle hInd = new Circle(5, 5, 5);
            hInd.setFill(Color.BLACK);
            DropPosition.setHInd(hInd);
            group.getChildren().addAll(
                    line,
                    new Text((i*100), 12, String.valueOf(i*100)),
                    hInd
            );
        };
    }

    public void setUpDragDrop(Group root) {
        makeRulers(root);
        final Rectangle target = new Rectangle(1, 1, pageWidth, pageHeight);
        target.setFill(Color.BLANCHEDALMOND);
        target.setOpacity(0.7);
        target.setStroke(Color.BLUE);
        GDSection.setupTargetEvents(target);

        root.getChildren().add(target);

        GDSection.setRoot(root);
/*        root.getChildren().add( GDSection.makeSection(100, 100, 80, 40, Color.AZURE, "move me somewhere").group());
        root.getChildren().add( GDSection.makeExtSection(50, 250, "Extra", "Some descriptive text...", "a control or something else").group()  );
        root.getChildren().add( GDSection.makeCheckboxSection(300, 200, 120, 50, "Annual figures").group() );
*/
        loadLayout();

    }

    private void loadLayout() {
        GDSection.makeSection(100, 100, 80, 40, Color.AZURE, "move me somewhere");
        GDSection.makeExtSection(50, 250, "Extra", "Some descriptive text...", "a control or something else");
        GDSection.makeCheckboxSection(300, 200, 120, 50, "Annual figures");
        // make an item with x, y, id, text, label,checkbox, respName
    }

    public void autoformatContent(Double x, Double y, CustomContentParent cc) {
        GDSection.autoformat(x.intValue(), y.intValue(), cc);

    }

/*
this should be run after autoformat content
    public void setUpContextMenu(Tab tab) {
        // make a context menu for the table
        final ContextMenu cm = new ContextMenu();
        MenuItem cmCut = new MenuItem("Cut");
        cmCut.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                //        root.getChildren().add(
                GDSection.makeExtSection(50, 50, "New Cut", "This is a new Cut", "not much");
            } });
        cm.getItems().add(cmCut);
        MenuItem cmCopy = new MenuItem("Copy");
        cmCopy.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                //        root.getChildren().add( GDSection.makeExtSection(50, 50, "New Cucopyt", "This is a new Copy", "not much").group()  );
            } });
        cm.getItems().add(cmCopy);
        MenuItem cmPaste = new MenuItem("Paste");
        cmPaste.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                //      root.getChildren().add( GDSection.makeExtSection(50, 50, "New Paste", "This is a new Paste", "not much").group()  );
            } });
        cm.getItems().add(cmPaste);
        tab.setContextMenu(cm);

    }

*/
}
