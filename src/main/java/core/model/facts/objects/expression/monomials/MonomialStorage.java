package core.model.facts.objects.expression.monomials;

import java.util.HashSet;

public class MonomialStorage {
    public HashSet<MonomialEnveloper> monomials = new HashSet<>();
    private static MonomialStorage singleton = null;

    private MonomialStorage() {
    }

    public static MonomialStorage getInstance() {
        if (singleton == null) {
            singleton = new MonomialStorage();
        }
        return singleton;
    }
}
