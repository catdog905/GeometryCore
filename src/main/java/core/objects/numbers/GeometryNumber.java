package core.objects.numbers;

import core.objects.GeometryObject;

public class GeometryNumber extends NumberEnveloper {
    private GeometryNumber(Number number) {
        super(number);
    }
    @Override
    public boolean isEquivalentTo(GeometryObject object) {
        if (!getClass().equals(object.getClass()))
            return false;
        return ((NumberEnveloper)object).number.equals(super.number);
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
