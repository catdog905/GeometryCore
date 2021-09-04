package GeometryCore.GeometryObjects;

public class Degree extends NumberEnveloper {

    private Degree(Number number) {
        super(number);
    }

    public static Degree createNumber(Number number) {
        Degree search = (Degree) NumberStorage.getInstance().numbers.stream()
                .filter(x -> x.getClass() == Degree.class && x.number.equals(number)).findAny().orElse(null);
        if (search == null) {
            Degree obj = new Degree(number);
            NumberStorage.getInstance().numbers.add(obj);
            return obj;
        }
        return search;
    }
}
