package UML.ObjectFactories;

import Models.*;
import UML.Objects.*;

public class ObjectFactory {

    public ObjectFactory(){
    }
    public UMLObject createUMLObject(Model model){
        UMLObject umlObject = null;
        if(model!=null) {
            if (model instanceof ClassModel) {
                umlObject = new ClassObject();//do like this
            } else if (model instanceof InterfaceModel) {
                umlObject = new InterfaceObject();
            } else if (model instanceof UseCaseModel) {
                umlObject =  new UseCaseObject();
            } else if (model instanceof ActorModel) {
                umlObject = new ActorObject();
            }
            assert umlObject != null;
            umlObject.setModel(model);
        }
        return umlObject;
    }
    public UMLObject createClassObject(){
        return new ClassObject();
    }
    public UMLObject createInterfaceObject(){
        return new InterfaceObject();
    }
    public UMLObject createUseCaseObject(){return  new UseCaseObject();}
    public UMLObject createActorObject(){return  new ActorObject();}

    public UMLObject copyUMLObject(Model model) {
        UMLObject copiedUMLObject = null;
        if (model != null) {
            Model copiedModel = null;
            if (model instanceof ClassModel classModel) {
                copiedModel = new ClassModel(classModel);
            } else if (model instanceof InterfaceModel interfaceModel) {
                copiedModel = new InterfaceModel(interfaceModel);
            } else if(model instanceof UseCaseModel useCaseModel){
                copiedModel = new UseCaseModel(useCaseModel);
            } else if (model instanceof ActorModel actorModel){
                copiedModel = new ActorModel(actorModel);
            }
            if (copiedModel != null) {
                copiedUMLObject = createUMLObject(copiedModel);
            }
        }
        return copiedUMLObject;
    }
}
