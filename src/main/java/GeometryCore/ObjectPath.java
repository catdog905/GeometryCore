package GeometryCore;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import GeometryCore.Facts.Fact;
import GeometryCore.GeometryObjects.GeometryObject;

public class ObjectPath {
    public Fact fact;
    public LinkedList<GeometryObject> objectPath=null;
    public ObjectPath(){}
    public ObjectPath(Fact fact,LinkedList<GeometryObject> objectPath){
        this.objectPath=objectPath;
        this.fact=fact;
    }
    public ObjectPath(Fact fact,GeometryObject objectPath){
        this.objectPath= new LinkedList<>(Collections.singletonList(objectPath));
        this.fact=fact;
    }
    public static HashMap<GeometryObject,LinkedList<ObjectPath>> extractObjectPathsFromFactSet(HashSet<Fact> facts){
        HashMap<GeometryObject,LinkedList<ObjectPath>> answer = new HashMap<>();
        for (Fact fact:facts) {
            for (var object:fact.getAllSubObjects()){
                ObjectPath curObjectPath = new ObjectPath(fact,object);
                addObjectPathsOfSubObjectToHashMap(object,curObjectPath,answer);
            }
        }
        return answer;
    }
    private static void addObjectPathsOfSubObjectToHashMap(GeometryObject fatherObject,
                                                    ObjectPath currentObjectPath,
                                                    HashMap<GeometryObject,LinkedList<ObjectPath>> objectPathMap){
        if (!objectPathMap.containsKey(fatherObject)){
            LinkedList<ObjectPath> list = new LinkedList<>();
            objectPathMap.put(fatherObject,list);
        }
        objectPathMap.get(fatherObject).add(currentObjectPath);

        for (GeometryObject subObject : fatherObject.getAllSubObjects()) {
            var newPath = new LinkedList<GeometryObject>(currentObjectPath.objectPath);
            newPath.add(subObject);
            addObjectPathsOfSubObjectToHashMap(subObject,
                    new ObjectPath(currentObjectPath.fact, newPath),
                    objectPathMap);
        }
    }
}
