import com.westat.DropPosition;
import com.westat.LayoutSection;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Screen;


/**
 * Created by Owner on 11/16/2016.
 */
public class LayoutUI {

    private Double pageWidth = 850.;
    private Double pageHeight = 1100.;

    private void makeRulers(Group group) {
        double inch = Screen.getPrimary().getDpi();
        System.out.println("primary screen dpi "+inch);
        double half = inch / 2;
        Line line;
        Line halfMark;
        Line inchMark;
        // add row lines
        for (int i = 1; i < 5; i++) {
            line = new javafx.scene.shape.Line(1, (i*100), pageWidth, (i*100));
    //        line = new javafx.scene.shape.Line(1, (i*100), group.getLayoutBounds().getWidth(), (i*100));
            //    line.setStyle("-fx-stroke: CHARTREUSE;");
            line.setStroke(Color.BLACK);
            line.setStrokeWidth(2);
         //   LayoutSection.setupTargetEvents(line);
            Circle vInd = new Circle(5, 5, 5);
          //  vInd.setOpacity(0.8);
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
  //          line = new javafx.scene.shape.Line((i*100), 1, (i*100), group.getLayoutBounds().getHeight());
            //  line.setStyle("-fx-stroke: CHARTREUSE;");
            line.setStrokeWidth(2);
//            line.setScaleX(3.0);
            halfMark = new Line((i*100), 1, (i*100), group.getLayoutBounds().getHeight());
         //   LayoutSection.setupTargetEvents(line);
            Circle hInd = new Circle(5, 5, 5);
          //  hInd.setOpacity(0.8);
            hInd.setFill(Color.BLACK);
            DropPosition.setHInd(hInd);
            group.getChildren().addAll(
                    line,
                    new Text((i*100), 12, String.valueOf(i*100)),
                    hInd
            );
        };

      //  no, even windows uses dpi for inches and its less than actual inches
      //  so just accept the convention that 100 = inch and leave it at that
      //  if you want to have it magnified, just adjust scale
        // add column inches
        for (double i = inch; i < 800; i+= inch) {
            halfMark = new Line(i-half, 18, i-half, 22);
            inchMark = new Line(i, 18, i, 26);
            group.getChildren().addAll(halfMark, inchMark,
                    new Text(i-5, 36, String.valueOf(Math.round(i/inch)+"\""))
            );
        };
    }
/*
    private void setupTargetEvents(Shape shape) {
        shape.setOnDragOver(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                if (event.getGestureSource() != shape && event.getDragboard().hasString()) {
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            }
        });

        shape.setOnDragEntered(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                event.consume();
            }
        });

        shape.setOnDragExited(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) { event.consume(); }
        });

        shape.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                DropPosition.setDropX( event.getSceneX() );
                DropPosition.setDropY( event.getY() ); //event.getSceneY();
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    success = true;
                }
                event.setDropCompleted(success);
                event.consume();
            }
        });
    }
*/

    public void setUpDragDrop(Group root) {
        makeRulers(root);
        final Rectangle target = new Rectangle(1, 1, pageWidth, pageHeight);
        target.setFill(Color.BEIGE);
        target.setOpacity(0.7);
        target.setStroke(Color.BLUE);
        LayoutSection.setupTargetEvents(target);

        root.getChildren().add(target);

        LayoutSection.setRoot(root);
    //    makeSection(root);
 //       root.getChildren().add( makeExtSection(50, 250, "Extra", "Some descriptive text...", "a control or something else")  );
        root.getChildren().add(LayoutSection.makeSection(100, 100, 80, 40, Color.AZURE, "move me somewhere").group());
        root.getChildren().add( LayoutSection.makeExtSection(50, 250, "Extra", "Some descriptive text...", "a control or something else").group()  );
        root.getChildren().add( LayoutSection.makeCheckboxSection(300, 200, 100, 50, "Annual figures").group() );
    }

}
