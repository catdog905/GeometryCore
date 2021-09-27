package core.objects.numbers;

public class GeometryNumber extends NumberEnveloper {
    private GeometryNumber(Number number) {
        super(number);
    }
    public static GeometryNumber createNumber(Number number) {
        GeometryNumber search = (GeometryNumber) NumberStorage.getInstance().numbers.stream()
                .filter(x -> x.getClass() == GeometryNumber.class && x.number.equals(number)).findAny().orElse(null);
        if (search == null) {
            GeometryNumber obj = new GeometryNumber(number);
            NumberStorage.getInstance().numbers.add(obj);
            return obj;
        }
        return search;
    }
}
