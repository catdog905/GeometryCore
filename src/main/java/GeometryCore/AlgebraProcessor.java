package GeometryCore;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;

import GeometryCore.GeometryObjects.GeometryNumber;
import GeometryCore.GeometryObjects.GeometryObject;
import GeometryCore.GeometryObjects.Monomial;
import GeometryCore.GeometryObjects.Polynomial;

public class AlgebraProcessor {
    public static Monomial simplifyExpression(Monomial expression) {
        if (expression instanceof Polynomial) {
            return null;
        } else {
            Monomial expandedComplexBrackets = multiplyFactors(expression.getAllSubObjects()
                    .stream().map(x -> (Monomial)x).collect(Collectors.toCollection(LinkedList::new)));
            return expandedComplexBrackets;
        }
    }

    private static Monomial multiplyFactors(LinkedList<Monomial> factors) {
        if (factors.size() == 0)
            return GeometryNumber.createNumber(1);
        Monomial result = factors.get(0);
        for (int i = 1; i < factors.size(); i++) {
            result = multiplyFactors(result, factors.get(i));
        }
        return result;
    }

    private static Monomial multiplyFactors(Monomial factor1, Monomial factor2) {
        LinkedList<Monomial> factorSet1;
        LinkedList<Monomial> factorSet2;
        if (factor1 instanceof Polynomial)
            factorSet1 = factor1.getAllSubObjects().stream().map(x -> (Monomial) x)
                    .collect(Collectors.toCollection(LinkedList::new));
        else
            factorSet1 = new LinkedList<>(Arrays.asList(factor1));
        if (factor2 instanceof Polynomial)
            factorSet2 = factor2.getAllSubObjects().stream().map(x -> (Monomial) x)
                    .collect(Collectors.toCollection(LinkedList::new));
        else
            factorSet2 = new LinkedList<>(Arrays.asList(factor1));
        LinkedList<Monomial> monomials = new LinkedList<>();
        for (GeometryObject mono1 : factorSet1) {
            for (GeometryObject mono2 : factorSet2) {
                monomials.add(multiplySimpleFactors((Monomial) mono1, (Monomial)mono2));
            }
        }
        if (monomials.size() == 1)
            return monomials.get(0);
        return new Polynomial(monomials);
    }

    private static Monomial multiplySimpleFactors(Monomial factor1, Monomial factor2) {
        LinkedList<Monomial> list1 = factor1.getAllSubObjects().stream().filter(x -> x instanceof Monomial)
                .map(x -> (Monomial) x).collect(Collectors.toCollection(LinkedList::new));
        LinkedList<Monomial> list2 = factor2.getAllSubObjects().stream().filter(x -> x instanceof Monomial)
                .map(x -> (Monomial) x).collect(Collectors.toCollection(LinkedList::new));
        if (list1.size() == 0)
            list1.add(factor1);
        if (list2.size() == 0)
            list2.add(factor2);
        list1.addAll(list2);
        return new Monomial(list1);
    }
}
