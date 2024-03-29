package core.model.facts.objects.expression.monomials;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

import core.model.facts.objects.GeometryObject;
import core.model.facts.objects.expression.AbleToAddSimilarTerms;
import core.model.facts.objects.expression.Expandable;
import core.model.facts.objects.expression.Multipliable;
import core.model.facts.objects.expression.Substitutable;

public class Monomial extends GeometryObject implements Substitutable, Multipliable, Expandable,
        AbleToAddSimilarTerms {
    LinkedList<Monomial> subObjects;

    public Monomial(LinkedList<Monomial> subObjects) {
        this.subObjects = subObjects;
    }
    public Monomial(Monomial... subObjects) {
        this.subObjects = new LinkedList<>(Arrays.asList(subObjects));
    }
    public Monomial() {
        subObjects = new LinkedList<>();
    }
    public Monomial(Monomial factor1, Monomial factor2) {
        subObjects = new LinkedList<>(Arrays.asList(factor1, factor2));
    }

    public static <T, E extends MonomialEnveloper> E buildOf(
            T toEnvelop, Class<T> tClass, Class<E> eClass) {
        E search = (E) MonomialStorage.getInstance().monomials.stream()
                .filter(x -> x.getClass() == eClass && x.toEnvelop().equals(toEnvelop))
                .findAny().orElse(null);
        if (search == null) {
            E obj = null;
            try {
                obj = eClass.getDeclaredConstructor((Class<T>) tClass).newInstance(toEnvelop);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                obj = (E)MonomialEnveloper.returnNewObject(toEnvelop);
            }
            MonomialStorage.getInstance().monomials.add(obj);
            return obj;
        }
        return search;
    }
    @Override
    public LinkedList<Monomial> getAllSubObjects() {
        return new LinkedList<>(subObjects);
    }
    @Override
    public GeometryObject createNewSimilarObject(Map<GeometryObject, GeometryObject> correspondence) {
        LinkedList<Monomial> newObjects = new LinkedList<>();
        for (Monomial obj : subObjects) {
            newObjects.add((Monomial) correspondence.get(obj));
        }
        return new Monomial(newObjects);
    }
    @Override
    public Monomial substitute(HashMap<Monomial, Monomial> substituteTable) {
        if (substituteTable.containsKey(this)) {
            return substituteTable.get(this);
        }
        LinkedList<Monomial> newMonomialContents = new LinkedList<>();
        for (GeometryObject term : getAllSubObjects()) {
            Monomial monomialTerm = (Monomial) term;
            newMonomialContents.add(monomialTerm.substitute(substituteTable));
        }
        return new Monomial(newMonomialContents);
    }
    @Override
    public Monomial getMonomial() {
        return this;
    }
    @Override
    public Monomial expandAllBrackets() {
        if (subObjects.size() == 0)
            return this;
        Monomial acc = subObjects.get(0).expandAllBrackets();
        for (int i = 1; i < subObjects.size(); i++) {
            Monomial expanded = subObjects.get(i).expandAllBrackets();
            acc = acc.multiplyWith(expanded);
        }
        return acc;
    }
    @Override
    public Monomial multiplyWith(Monomial monomial) {
        if (monomial instanceof Polynomial)
            return multiplyWithPolynomial((Polynomial) monomial);
        LinkedList<Monomial> factors = new LinkedList<>();
        if (subObjects.size() > 0)
            factors.addAll(subObjects);
        else
            factors.add(this);
        if (monomial.subObjects.size() > 0)
            factors.addAll(monomial.subObjects);
        else
            factors.add(monomial);
        return new Monomial(factors);
    }
    private Polynomial multiplyWithPolynomial(Polynomial polynomial) {
        return new Polynomial(polynomial.getAllSubObjects()
                .stream().map(this::multiplyWith)
                .collect(Collectors.toCollection(LinkedList::new)));
    }
    @Override
    public Monomial addSimilarTerms() {
        if (subObjects.isEmpty())
            return this;
        LinkedList<Monomial> newMonomial = new LinkedList<>();
        for (Monomial subObject : subObjects){
            newMonomial.add(subObject.addSimilarTerms());
        }
        return new Monomial(newMonomial);
    }
}