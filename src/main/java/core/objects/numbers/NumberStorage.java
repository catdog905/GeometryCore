package core.objects.numbers;

import java.util.HashSet;

public class NumberStorage {
    public HashSet<NumberEnveloper> numbers = new HashSet<>();
    private static NumberStorage singleton = null;

    private NumberStorage() {
    }

    public static NumberStorage getInstance() {
        if (singleton == null) {
            singleton = new NumberStorage();
        }
        return singleton;
    }
}
