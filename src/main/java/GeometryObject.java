import java.util.HashMap;
import java.util.HashSet;

public abstract class GeometryObject {
    private boolean isAnyObject = false;
    public boolean isAnyObject(){
        return isAnyObject;
    }
    public void setAnyObject() {
        isAnyObject = true;
    }
    public abstract HashSet<GeometryObject> getSubObjects();
}
