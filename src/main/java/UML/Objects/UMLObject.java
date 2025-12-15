package UML.Objects;

import Models.Model;
import UML.CustomPoint;
import UML.Line.Line;
import UML.Moveable;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public abstract class UMLObject extends Moveable {
    protected Model model;
    private final Rectangle highlightRectangle;
    private final List<Line> associatedLines;
    transient private final CustomPoint point;
    protected Runnable redrawLine;

    public UMLObject(Runnable redrawFunc) {
        super();
        setRunnable(redrawFunc);
        point = new CustomPoint(getLayoutX(), getLayoutY());
        associatedLines = new ArrayList<>();
        highlightRectangle = createHighlightRectangle();
        addMouseEvents();
    }

    public UMLObject() {
        point = new CustomPoint(getLayoutX(), getLayoutY());
        associatedLines = new ArrayList<>();
        highlightRectangle = createHighlightRectangle();
        addMouseEvents();
    }

    private Rectangle createHighlightRectangle() {
        Rectangle rect = new Rectangle();
        rect.setFill(Color.BLUE);
        rect.setStrokeWidth(1);
        rect.setVisible(false); // Initially hidden
        return rect;
    }
    public void resetMousePressedHandlers() {
        EventHandler<? super MouseEvent> currentHandler = getOnMousePressed();
        if (currentHandler != null) {
            removeEventHandler(MouseEvent.MOUSE_PRESSED, currentHandler);
        }
        setOnMousePressed(new MousePressedHandler());
    }
    public void addAssociatedLine(Line line) {
        associatedLines.add(line);
    }

    public List<UML.Line.Line> getAssociatedLines() {
        return associatedLines;
    }

    protected void setRunnable(Runnable runnable) {
        this.redrawLine = runnable;
    }

    private void addMouseEvents() {
        setOnMousePressed(event -> highlightBoundary(true));
        //setOnMouseClicked(event -> highlightBoundary(true));

        setOnMouseDragged(new MouseDraggedHandler());
        setOnMouseExited(event -> highlightBoundary(false));
    }

    public abstract double getWidth();

    public abstract double getHeight();

    public abstract Model getModel();

    public abstract void setModel(Model model);

    public void reloadModel() {
        if (model == null || associatedLines == null) {
            return;
        }

        model.getIncomingAssociations().clear();
        model.getOutgoingAssociations().clear();

        for (UML.Line.Line line : associatedLines) {
            if (line.getStartObject() == this) {
                model.addStartAssociation(line.getAssociationModel());
            } else if (line.getEndObject() == this) {
                model.addEndAssociation(line.getAssociationModel());
            }
        }

        model.setCoordinate(this.getLayoutX(), this.getLayoutY());
    }

    public void delete() {
        while (!associatedLines.isEmpty()) {
            associatedLines.get(0).delete();
        }
        if (getParent() != null && getParent() instanceof Pane) {
            ((Pane) getParent()).getChildren().remove(this);
        }
    }

    public class MousePressedHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            point.setLocation(event.getSceneX(), event.getSceneY());
        }
    }

    public class MouseDraggedHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            double deltaX = event.getSceneX() - point.getX();
            double deltaY = event.getSceneY() - point.getY();

            double newPointX = getLayoutX() + deltaX;
            double newPointY = getLayoutY() + deltaY;

            double parentWidth = getParent().getLayoutBounds().getWidth();
            double parentHeight = getParent().getLayoutBounds().getHeight();

            if (newPointX >= 0 && newPointX + getBoundsInParent().getWidth() <= parentWidth) {
                setLayoutX(newPointX);
            }
            if (newPointY >= 0 && newPointY + getBoundsInParent().getHeight() <= parentHeight) {
                setLayoutY(newPointY);
            }
            if (!associatedLines.isEmpty())
                updateLines();

            point.setLocation(event.getSceneX(), event.getSceneY());
        }
    }

    protected void updateLines() {
        for (Line line : associatedLines) {
            if (line.getStartObject() == this)
                line.updateLinePosition(this, true);
            else if (line.getEndObject() == this)
                line.updateLinePosition(this, false);
        }
    }

    // Modified highlightBoundary to ensure visibility of the highlight rectangle
    public void highlightBoundary(boolean highlight) {
        highlightRectangle.setVisible(highlight);
        if (highlight) {
            highlightRectangle.setLayoutX(getLayoutX());
            highlightRectangle.setLayoutY(getLayoutY());
            highlightRectangle.setWidth(getWidth());
            highlightRectangle.setHeight(getHeight());
            highlightRectangle.setStroke(Color.LIGHTGREEN);  // Use the color you want
            highlightRectangle.setStrokeWidth(2.0); // Border thickness
            if (getParent() != null && getParent() instanceof Pane) {
                // Add highlightRectangle to parent pane
                Pane parent = (Pane) getParent();
                if (!parent.getChildren().contains(highlightRectangle)) {
                    parent.getChildren().add(highlightRectangle);
                }
            }
        } else {
            highlightRectangle.setVisible(false);
        }
    }
}
