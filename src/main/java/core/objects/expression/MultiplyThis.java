package core.objects.expression;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class MultiplyThis {
    private LinkedList<Monomial> factors;
    private Monomial result = null;

    public MultiplyThis(LinkedList<Monomial> factors) {
        this.factors = factors;
    }

    public Monomial get() {
        if (result == null)
            result = multiplyFactors(factors);
        return result;
    }

    private Monomial multiplyFactors(LinkedList<Monomial> factors) {
        if (factors.size() == 0)
            throw new RuntimeException("Monomial must contain at least 1 subObject");
        Monomial result = factors.get(0);
        for (int i = 1; i < factors.size(); i++) {
            result = multiplyFactors(result, factors.get(i));
        }
        return result;
    }

    private Monomial multiplyFactors(Monomial factor1, Monomial factor2) {
        if (!(factor1 instanceof Polynomial) && !(factor2 instanceof Polynomial)) {
            LinkedList<Monomial> toCreate = new LinkedList<>();
            if (factor1 instanceof GeometryNumber || factor1 instanceof GeometryNumber) {
                toCreate.add(factor1);
            } else {
                toCreate.addAll((LinkedList<Monomial>)factor1.getAllSubObjects());
            }
            if (factor2 instanceof GeometryNumber || factor2 instanceof GeometryNumber) {
                toCreate.add(factor2);
            } else {
                toCreate.addAll((LinkedList<Monomial>)factor2.getAllSubObjects());
            }
            return new Monomial(toCreate);
        }
        if (!(factor1 instanceof Polynomial)) {
            LinkedList<Monomial> list = multiplyMonomialWithPolynomial(factor1, (Polynomial) factor2);
            return list.size() == 1 ? new Monomial(list.get(0)) : new Polynomial(list);
        }
        if (!(factor2 instanceof Polynomial)){
            LinkedList<Monomial> list = multiplyMonomialWithPolynomial(factor2, (Polynomial) factor1);
            return list.size() == 1 ? new Monomial(list.get(0)) : new Polynomial(list);
        }
        LinkedList<Monomial> summands = new LinkedList<>();
        for (Monomial mono : (LinkedList<Monomial>)factor1.getAllSubObjects()) {
            summands.addAll((LinkedList<Monomial>) multiplyFactors(mono, (Polynomial)factor2).getAllSubObjects()
            );
        }
        return new Polynomial(summands);
    }

    private LinkedList<Monomial> multiplyMonomialWithPolynomial(Monomial monomial, Polynomial polynomial) {
        LinkedList<Monomial> subObjAddTo = monomial instanceof GeometryNumber || monomial instanceof GeometryNumber
                ? new LinkedList<>(Arrays.asList(monomial)) : (LinkedList<Monomial>) monomial.getAllSubObjects();
        return polynomial.getAllSubObjects().stream().map((Monomial x) -> {
            //if (x instanceof GeometryNumber || x instanceof GeometryNumber)
            //    return new Monomial(x, subObjAddTo);
            //if (x.getAllSubObjects().size() == 1)
            //    return new Monomial(((LinkedList<Monomial>) x.getAllSubObjects()).get(0), subObjAddTo);
            LinkedList<Monomial> list = (LinkedList<Monomial>) x.getAllSubObjects();
            list.addAll(subObjAddTo);
            return new Monomial(list);
        }).collect(Collectors.toCollection(LinkedList::new));
    }
}
