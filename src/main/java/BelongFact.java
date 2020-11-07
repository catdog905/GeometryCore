public class BelongFact extends GeometryFact {
    public GeometryObject object, subject;

    public BelongFact(GeometryObject object, GeometryObject subject) {
        this.object = object;
        this.subject = subject;
    }
}
