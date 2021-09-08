package GeometryCore;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;

import GeometryCore.GeometryObjects.Monomial;
import GeometryCore.GeometryObjects.NumberEnveloper;
import GeometryCore.GeometryObjects.NumberValue;
import GeometryCore.GeometryObjects.Polynomial;
import GeometryCore.GeometryObjects.RaisedInThePower;

public class AlgebraProcessor {
    public static Monomial simplifyExpression(Monomial expression) {
        if (expression instanceof Polynomial) {
            LinkedList<Monomial> newSubObjects = new LinkedList<>();
            for (int i = 0; i < expression.getAllSubObjects().size(); i++) {
                Monomial simplified = simplifyExpression((Monomial) expression.getAllSubObjects().get(i));
                if (simplified instanceof Polynomial)
                    newSubObjects.addAll((LinkedList<Monomial>)simplified.getAllSubObjects());
                else
                    newSubObjects.add(simplified);
            }
            return new Polynomial(newSubObjects);
        } else if (expression instanceof RaisedInThePower) {
            LinkedList<LinkedList<? extends Monomial>> newFactors = ((RaisedInThePower) expression).getAllSubObjects().stream()
                    .map(x -> {
                        if (x instanceof RaisedInThePower) {
                            Monomial power = new Monomial(
                                    ((RaisedInThePower) x).power, ((RaisedInThePower) expression).power);
                            return  x.getAllSubObjects().stream()
                                    .map(y -> new RaisedInThePower(simplifyExpression((Monomial) y), simplifyExpression(power)))
                                    .collect(Collectors.toCollection(LinkedList::new));
                        } else if (x instanceof NumberValue) {
                            return new LinkedList<>(Arrays.asList(new RaisedInThePower((NumberValue) x,
                                    simplifyExpression(((RaisedInThePower) expression).power))));
                        } else {
                            return x.getAllSubObjects().stream()
                                    .map(y -> new RaisedInThePower(simplifyExpression((Monomial) y),
                                            ((RaisedInThePower) expression).power))
                                    .collect(Collectors.toCollection(LinkedList::new));
                        }
                    }).collect(Collectors.toCollection(LinkedList::new));
            LinkedList<Monomial> lineListFactors = new LinkedList<>();
            for (LinkedList<? extends Monomial> list : newFactors) {
                lineListFactors.addAll(list);
            }
            return new Monomial(lineListFactors);
        }
        if (expression instanceof NumberEnveloper || expression instanceof NumberValue)
            return expression;
        return multiplyFactors((LinkedList<Monomial>) expression.getAllSubObjects());
    }

    private static Monomial multiplyFactors(LinkedList<Monomial> factors) {
        if (factors.size() == 0)
            throw new RuntimeException("Monomial must contain at least 1 subObject");
        Monomial result = simplifyExpression(factors.get(0));
        for (int i = 1; i < factors.size(); i++) {
            result = multiplyFactors(result, simplifyExpression(factors.get(i)));
        }
        return result;
    }

    private static Monomial multiplyFactors(Monomial factor1, Monomial factor2) {
        if (!(factor1 instanceof Polynomial) && !(factor2 instanceof Polynomial)) {
            LinkedList<Monomial> toCreate = new LinkedList<>();
            if (factor1 instanceof NumberEnveloper || factor1 instanceof NumberValue) {
                toCreate.add(factor1);
            } else {
                toCreate.addAll((LinkedList<Monomial>)factor1.getAllSubObjects());
            }
            if (factor2 instanceof NumberEnveloper || factor2 instanceof NumberValue) {
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

    private static LinkedList<Monomial> multiplyMonomialWithPolynomial(Monomial monomial, Polynomial polynomial) {
        LinkedList<Monomial> subObjAddTo = monomial instanceof NumberEnveloper || monomial instanceof NumberValue
                ? new LinkedList<>(Arrays.asList(monomial)) : (LinkedList<Monomial>) monomial.getAllSubObjects();
        return polynomial.getAllSubObjects().stream().map((Monomial x) -> {
            if (x instanceof NumberEnveloper || x instanceof NumberValue)
                return new Monomial(x, subObjAddTo);
            if (x.getAllSubObjects().size() == 1)
                return new Monomial(((LinkedList<Monomial>) x.getAllSubObjects()).get(0), subObjAddTo);
            LinkedList<Monomial> list = (LinkedList<Monomial>) x.getAllSubObjects();
            list.addAll(subObjAddTo);
            return new Monomial(list);
        }).collect(Collectors.toCollection(LinkedList::new));
    }
}
