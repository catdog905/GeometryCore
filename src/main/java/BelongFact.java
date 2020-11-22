import java.util.ArrayList;

public class BelongFact extends GeometryFact {
    public GeometryObject object, subject;

    public BelongFact(GeometryObject object, GeometryObject subject) {
        this.object = object;
        this.subject = subject;
    }

    @Override
    public boolean checkFact(GeometryFact geometryFact) {
        return (((BelongFact)geometryFact).object == this.object || ((BelongFact)geometryFact).object.isAnyObject())
                && (((BelongFact)geometryFact).subject == this.subject || ((BelongFact)geometryFact).subject.isAnyObject());
    }

    @Override
    public void fillInUnknowns(GeometryFact ruleFact) {
        ((BelongFact) ruleFact).object = this.object;
        ((BelongFact) ruleFact).subject = this.subject;
    }
}
