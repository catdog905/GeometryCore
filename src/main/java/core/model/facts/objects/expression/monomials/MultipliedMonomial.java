package core.model.facts.objects.expression.monomials;

import java.util.Arrays;
import java.util.LinkedList;

public class MultipliedMonomial extends Monomial {
    private static Monomial multiplyManyFactors(LinkedList<Monomial> monomials) {
        Monomial res = monomials.get(0);
        for (int i = 1; i < monomials.size(); i++) {
            res = res.multiplyWith(monomials.get(i));
        }
        return res;
    }
    private static Monomial multiplyManyFactors(Monomial... monomials) {
        return multiplyManyFactors(new LinkedList<>(Arrays.asList(monomials)));
    }
}
