import java.util.ArrayList;

public class BelongFact extends GeometryFact {
    public GeometryObject object, subject;

    public BelongFact(GeometryObject object, GeometryObject subject) {
        this.object = object;
        this.subject = subject;
    }

    @Override
    public boolean checkFact(GeometryFact geometryFact) {
        return (((BelongFact)geometryFact).object == object || ((BelongFact)geometryFact).object instanceof AnyObject)
                && (((BelongFact)geometryFact).subject == subject || ((BelongFact)geometryFact).subject instanceof AnyObject);
    }
}
