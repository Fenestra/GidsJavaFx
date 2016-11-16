import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

/**
 * Created by Owner on 11/16/2016.
 */
public class LayoutUI {
    private double dropX = 0;
    private double dropY = 0;

    private void makeRulers(Group group) {
        Line line;
        // add row lines
        for (int i = 1; i < 5; i++) {
            line = new javafx.scene.shape.Line(1, (i*100), group.getLayoutBounds().getWidth(), (i*100));
            line.setStyle("-fx-stroke: CHARTREUSE;");
            setupTargetEvents(line);
            group.getChildren().addAll(
                    line,
                    new Text(1, (i*100)+12, String.valueOf(i*100))
            );
        };
        // add column lines
        for (int i = 1; i < 8; i++) {
            line = new javafx.scene.shape.Line((i*100), 1, (i*100), group.getLayoutBounds().getHeight());
            line.setStyle("-fx-stroke: CHARTREUSE;");
            setupTargetEvents(line);
            group.getChildren().addAll(
                    line,
                    new Text((i*100), 12, String.valueOf(i*100))
            );
        };
    }

    private void makeSection(Group group) {
        final Group section = new Group();
        final Rectangle source = new Rectangle(50, 100, 120, 50);
        source.setFill(Color.AQUAMARINE);
        final double textOffsetX = 10;
        final double textOffsetY = 10;
        final Text text = new Text(50+textOffsetX, 100+textOffsetY, "Try to Drag me anywhere.");
        text.setWrappingWidth(110);
        text.setBoundsType(TextBoundsType.VISUAL);

        section.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                Dragboard db = section.startDragAndDrop(TransferMode.ANY);
                db.setDragView(section.snapshot(null, null));
                ClipboardContent content = new ClipboardContent();
                content.putString(section.toString());
                db.setContent(content);
                event.consume();
            }
        });

        section.setOnDragDone(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                double left = dropX;
                double top  = dropY;
                if (event.getTransferMode() == TransferMode.MOVE) {
                    source.relocate(left, top);
                    text.setX(left+textOffsetX);
                    text.setY(top+textOffsetY);
                }
                event.consume();
            }
        });

        section.getChildren().addAll(source, text);
        group.getChildren().add(section);
    }


    private Group makeExtSection(int startLeft, int startTop, String title, String descr, String doodad) {
        final Group section = new Group();
        final Rectangle source = new Rectangle(startLeft, startTop, 180, 80);
        source.setFill(Color.GREEN);
        section.setId("ExtSection");
        final double textOffsetX = 10;
        final double textOffsetY = 10;
        final Text ttitle = new Text(startLeft+textOffsetX, startTop+30+textOffsetY, title);
        final Text tdescr = new Text(startLeft+textOffsetX, startTop+45+textOffsetY, descr);
        final Text tdood  = new Text(startLeft+textOffsetX, startTop+60+textOffsetY, doodad);
        section.getChildren().addAll(source, ttitle, tdescr, tdood);

        section.setOnDragDetected(new EventHandler <MouseEvent>() {
            public void handle(MouseEvent event) {
                Dragboard db = section.startDragAndDrop(TransferMode.ANY);
                db.setDragView(section.snapshot(null, null));
                ClipboardContent content = new ClipboardContent();
                content.putString(section.toString());
                db.setContent(content);
                event.consume();
            }
        });

        section.setOnDragDone(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                double left = dropX;
                double top  = dropY;
                if (event.getTransferMode() == TransferMode.MOVE) {
                    source.setX(left);
                    source.setY(top);
                    ttitle.setX(left+textOffsetX);
                    ttitle.setY(top+30+textOffsetY);
                    tdescr.setX(left+textOffsetX);
                    tdescr.setY(top+45+textOffsetY);
                    tdood.setX(left+textOffsetX);
                    tdood.setY(top+60+textOffsetY);
                }
                event.consume();
            }
        });

        return section;
    }

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
                dropX = event.getSceneX();
                dropY = event.getY(); //event.getSceneY();
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

    public void setUpDragDrop(Group root) {
        final Rectangle target = new Rectangle(1, 1, 600, 600);
        target.setFill(Color.BEIGE);
        setupTargetEvents(target);

        root.getChildren().add(target);
        makeRulers(root);
        makeSection(root);
        root.getChildren().add( makeExtSection(50, 250, "Extra", "Some descriptive text...", "a control or something else")  );
    }

}
