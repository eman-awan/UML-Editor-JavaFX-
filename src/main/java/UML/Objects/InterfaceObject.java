package UML.Objects;

import Controllers.InterfaceDiagramController;
import Models.InterfaceModel;
import Models.Model;
import UML.UI_Components.EditableField;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class InterfaceObject extends UMLObject {
    private final Group groupDiagram;
    private VBox detailsBox;
    private EditableField className;
    private List<StackPane> methods;
    private VBox methodBox;
    private InterfaceDiagramController controller;

    public InterfaceObject() {
        super();
        groupDiagram = new Group();
        model = new InterfaceModel();

        initComponents();

        groupDiagram.getChildren().add(controller);

        // Add groupDiagram to the node hierarchy
        getChildren().add(groupDiagram);

        // Set click event to highlight boundary
        this.setOnMouseClicked(event -> highlightBoundary(true));

        // Automatically adjust layout when bounds change
        this.layoutBoundsProperty().addListener((observable, oldValue, newValue) ->
                Platform.runLater(this::resizeDetailsBox));
    }

    @Override
    public Model getModel() {
        return model;
    }

    @Override
    public double getWidth() {
        return detailsBox.getWidth();
    }

    @Override
    public double getHeight() {
        return detailsBox.getHeight();
    }

    private void resizeDetailsBox() {
        Bounds boundsInScene = detailsBox.localToScene(detailsBox.getBoundsInLocal());
        Bounds boundsInGroup = groupDiagram.sceneToLocal(boundsInScene);
        setLayoutX(boundsInGroup.getMinX());
        setLayoutY(boundsInGroup.getMinY());
    }

    private void initComponents() {
        detailsBox = new VBox();
        detailsBox.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        // Top box for interface label and name
        VBox topBox = new VBox();
        Label interfaceLabel = new Label("<<interface>>");
        interfaceLabel.setAlignment(Pos.BASELINE_CENTER);
        HBox interfaceLabelWrapper = new HBox(interfaceLabel);
        interfaceLabelWrapper.setAlignment(Pos.BASELINE_CENTER);

        className = new EditableField("Interface Name");
        HBox classNameWrapper = new HBox(className);
        classNameWrapper.setAlignment(Pos.BASELINE_CENTER);

        topBox.getChildren().addAll(interfaceLabelWrapper, classNameWrapper);
        detailsBox.getChildren().add(topBox);

        // Initialize controller
        controller = new InterfaceDiagramController(this, interfaceLabelWrapper);

        // Method box for method names
        methodBox = new VBox();
        methodBox.setPadding(new Insets(5, 0, 5, 0));
        methods = new ArrayList<>();
        methodBox.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1, 0, 0, 0))));

        detailsBox.getChildren().add(methodBox);

        groupDiagram.getChildren().add(detailsBox);
    }

    public void addMethod(String temp) {
        StackPane method = new EditableField(temp);
        methods.add(method);
        methodBox.getChildren().add(method);
    }

    @Override
    public void setModel(Model model) {
        InterfaceModel interfaceModel = (InterfaceModel) model;
        setModel(interfaceModel);
    }

    public void setModel(InterfaceModel model) {
        this.model = model;

        methods.clear();

        if (model.getInterfaceName() != null && !model.getInterfaceName().isEmpty()) {
            className.setText(model.getInterfaceName());
        }

        for (String method : model.getMethods()) {
            addMethod(method);
        }

        this.setLayoutX(model.getX());
        this.setLayoutY(model.getY());
    }

    @Override
    public void reloadModel() {
        super.reloadModel();

        InterfaceModel downcastModel = (InterfaceModel) model;

        downcastModel.setInterfaceName(className.getText());

        if (downcastModel.getMethods() != null) {
            downcastModel.getMethods().clear();
        }

        for (StackPane methodStackPane : methods) {
            if (methodStackPane instanceof EditableField editableField) {
                downcastModel.addMethod(editableField.getText());
            }
        }
    }
}
