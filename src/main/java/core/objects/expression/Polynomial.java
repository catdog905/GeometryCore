package core.objects.expression;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

import core.objects.GeometryObject;

public class Polynomial extends Monomial {
    public Polynomial(LinkedList<Monomial> subObjects) {
        super(subObjects);
        if (subObjects.size() < 2) {
            throw new RuntimeException("Illegal constructor for polynomial");
        }
    }

    public Polynomial(Monomial... subObjects) {
        this(new LinkedList<>(Arrays.asList(subObjects)));
    }

    @Override
    public LinkedList<Monomial> getAllSubObjects() {
        return new LinkedList<>(super.getAllSubObjects());
    }

    @Override
    public GeometryObject createNewSimilarObject(Map<GeometryObject, GeometryObject> correspondence) {
        LinkedList<Monomial> newObjects = new LinkedList<>();
        for (GeometryObject obj : super.getAllSubObjects()) {
            newObjects.add((Monomial) correspondence.get(obj));
        }
        return new Polynomial(newObjects);
    }

    @Override
    public Monomial substitute(HashMap<Monomial, Monomial> substituteTable) {
        if (substituteTable.containsKey(this)) {
            return substituteTable.get(this);
        }
        LinkedList<Monomial> newPolynomialContents = new LinkedList<>();
        for (GeometryObject term : getAllSubObjects()) {
            Monomial monomialTerm = (Monomial) term;
            newPolynomialContents.add(monomialTerm.substitute(substituteTable));
        }
        return new Polynomial(newPolynomialContents);
    }

    @Override
    public Monomial expandAllBrackets() {
        LinkedList<Monomial> monomials = new LinkedList<>();
        for (Monomial monomial : getAllSubObjects()) {
            Monomial expandAllBrackets = monomial.expandAllBrackets();
            if (expandAllBrackets instanceof Polynomial)
                monomials.addAll(expandAllBrackets.getAllSubObjects());
            else
                monomials.add(expandAllBrackets);
        }
        return new Polynomial(monomials);
    }

    @Override
    public Monomial multiplyWith(Monomial monomial) {
        if (monomial instanceof Polynomial)
            return multiplyWithPolynomial((Polynomial) monomial);
        return new Polynomial(getAllSubObjects()
                .stream().map(monomial::multiplyWith)
                .collect(Collectors.toCollection(LinkedList::new)));
    }

    private Polynomial multiplyWithPolynomial(Polynomial polynomial) {
        LinkedList<Monomial> monomials = new LinkedList<>();
        for (Monomial x : getAllSubObjects()) {
            Monomial monomial = x.multiplyWith(polynomial);
            monomials.addAll(monomial.getAllSubObjects());
        }
        return new Polynomial(monomials);
    }


    // Implementation of addition of several terms
    private HashMap<Integer, LinkedList<Monomial>> termsGroupedByLength;
    @Override
    public Monomial addSimilarTerms() {
        // We assume that brackets are already expanded, and that all the singular terms are in
        // canonical form (e.g. [1*2]->[2]; [x^2] -> [x*x], [2*x]^2 -> 4*[x]^2; etc), therefore
        // there is [0;1] numbers in each monomial.
        // Also, equal monomials have equal lengths.
        groupTermsByLengthAndAddOnes();
        LinkedList<Monomial> simplifiedPolynomialContent = new LinkedList<>();
        for (LinkedList<Monomial> termsOfSameLength : termsGroupedByLength.values()) {
            simplifiedPolynomialContent.addAll(addSimilarTermsAndRemoveOnes(termsOfSameLength));
        }
        if (simplifiedPolynomialContent.isEmpty())
            return GeometryNumber.get(0);
        else if (simplifiedPolynomialContent.size() == 1) {
            return simplifiedPolynomialContent.getFirst();
        }
        return new Polynomial(simplifiedPolynomialContent);
    }

    private void groupTermsByLengthAndAddOnes() {
        termsGroupedByLength = new HashMap<Integer, LinkedList<Monomial>>();
        for (Monomial term : getAllSubObjects()) {
            term = term.addSimilarTerms();
            Monomial termWithGuaranteedNumber = addOneIfNecessary(term);
            addTermInBefittingGroup(termWithGuaranteedNumber);
        }
    }

    private static Monomial addOneIfNecessary(Monomial monomial) {
        if (monomial instanceof RaisedInThePower) {
            // If we see RaisedInThePower unwrapped, it means that we should wrap it like [1*[RITP]]
            return addOneToRaisedInThePower((RaisedInThePower) monomial);
        } else {
            return addOneToMonomialIfNecessary(monomial);
        }
    }

    private static Monomial addOneToRaisedInThePower(RaisedInThePower term) {
        return new Monomial(GeometryNumber.get(1),  term);
    }

    private static Monomial addOneToMonomialIfNecessary(Monomial term) {
        if (doesMonomialContainNumber(term)) {
            return term;
        } else {
            LinkedList<Monomial> newTermContents = term.getAllSubObjects();
            if (newTermContents.isEmpty())
                newTermContents.add(term);
            newTermContents.add(GeometryNumber.get(1));
            return new Monomial(newTermContents);
        }
    }

    private static boolean doesMonomialContainNumber(Monomial monomial) {
        return getNumberInMonomial(monomial) != null;
    }

    private static boolean doMonomialSubObjectsContainNumber(LinkedList<Monomial> subObjects) {
        return getNumberInMonomialSubObjects(subObjects) != null;
    }

    private static GeometryNumber getNumberInMonomial(Monomial monomial) {
        if (monomial instanceof GeometryNumber) {
            return (GeometryNumber) monomial;
        }
        return getNumberInMonomialSubObjects(monomial.getAllSubObjects());
    }

    private static GeometryNumber getNumberInMonomialSubObjects(LinkedList<Monomial> subObjects) {
        var numbers = subObjects.stream().filter(x -> x instanceof GeometryNumber).collect(Collectors.toList());
        if (numbers.isEmpty())
            return null;
        return (GeometryNumber) numbers.get(0);
    }

    private void addTermInBefittingGroup(Monomial term) {
        int termLength = term.subObjects.size();
        if (!termsGroupedByLength.containsKey(termLength)) {
            termsGroupedByLength.put(termLength, new LinkedList<>());
        }
        termsGroupedByLength.get(termLength).add(term);
    }


    private static LinkedList<Monomial> addSimilarTermsAndRemoveOnes(LinkedList<Monomial> terms) {
        LinkedList<Monomial> newTerms = new LinkedList<>();
        while (!terms.isEmpty()) {
            Monomial mainMonomial = terms.getFirst();
            terms.removeFirst();
            mainMonomial = combineWithEquivalentTermsInTheListAndRemoveThem(mainMonomial, terms);
            if (mainMonomial != null)
                newTerms.add(mainMonomial);
        }
        return newTerms;
    }

    private static Monomial combineWithEquivalentTermsInTheListAndRemoveThem(Monomial term, LinkedList<Monomial> allTerms) {
        if (term instanceof RaisedInThePower) {
            allTerms.remove(term);
        } else {
            term = combineWithEquivalentMonomialsInTheListAndRemoveThem(term, allTerms);
        }
        return term;
    }


    private static Monomial combineWithEquivalentMonomialsInTheListAndRemoveThem(Monomial ourTerm, LinkedList<Monomial> allTerms) {
        LinkedList<Monomial> allSubObjectsExceptTheNumber = ourTerm.getAllSubObjects();
        Number currentNumber;
        if (allSubObjectsExceptTheNumber.isEmpty()) {
            currentNumber = getNumberInMonomial(ourTerm).toEnvelop();
        } else {
            currentNumber = popGeometryNumberFromMonomialList(allSubObjectsExceptTheNumber).toEnvelop();
        }
        for (var it = allTerms.listIterator(0); it.hasNext(); ) {
            Monomial comparableMonomial = it.next();
            if (comparableMonomial instanceof RaisedInThePower)
                continue;
            Number comparableNumber = null;
            boolean areTermsSimilar = true;
            for (Monomial subObject : comparableMonomial.subObjects) {
                if (subObject instanceof GeometryNumber) {
                    comparableNumber = ((GeometryNumber) subObject).toEnvelop();
                } else {
                    if (!allSubObjectsExceptTheNumber.contains(subObject)) {
                        areTermsSimilar = false;
                        break;
                    }
                }
            }
            if (comparableNumber == null)
                comparableNumber = getNumberInMonomial(comparableMonomial).toEnvelop();
            if (areTermsSimilar) {
                currentNumber = GeometryNumber.addNumbers(currentNumber, comparableNumber);
                it.remove();
            }
        }
        return constructMonomialFromNumberAndSubObjects(currentNumber,allSubObjectsExceptTheNumber);
    }
    private static Monomial constructMonomialFromNumberAndSubObjects(Number number, LinkedList<Monomial> subObjects){
        if (GeometryNumber.areNumbersEquivalent(number, 1)) {
            if (subObjects.isEmpty())
                return GeometryNumber.get(1);
            return makeAMonomialFromSubObjects(subObjects);
        } else if (GeometryNumber.areNumbersEquivalent(number, 0)) {
            return null;
        } else {
            subObjects.add(GeometryNumber.get(number));
            return makeAMonomialFromSubObjects(subObjects);
        }
    }
    private static Monomial makeAMonomialFromSubObjects(LinkedList<Monomial> subObjects){
        return subObjects.size() == 1 ?
                subObjects.getFirst()
                : new Monomial(subObjects);
    }

    private static GeometryNumber popGeometryNumberFromMonomialList(LinkedList<Monomial> monomials) {
        GeometryNumber numberAsMonomial = getNumberInMonomialSubObjects(monomials);
        monomials.remove(numberAsMonomial);
        return numberAsMonomial;
    }

}