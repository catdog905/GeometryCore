package core.model.facts.equation;

import java.util.HashSet;
import java.util.LinkedList;

import core.model.facts.objects.expression.monomials.GeometryNumber;
import core.model.facts.objects.GeometryObject;
import core.model.facts.objects.expression.monomials.Monomial;
import core.model.facts.objects.expression.monomials.Polynomial;
import core.model.facts.objects.expression.monomials.RaisedInThePower;

public class PolynomialDeconstructor {
    private LinkedList<Monomial> monomialsForNewOppositeSide;
    private Polynomial equationSideToDeconstruct;
    private HashSet<Monomial> monomialsWithVariable;
    private Monomial oppositeSide = null;
    private Monomial leftoversOfDeconstructable = null;

    public PolynomialDeconstructor(Polynomial equationSideToDeconstruct, Monomial initialOppositeSide, HashSet<Monomial> monomialsWithVariable) {
        this.equationSideToDeconstruct = equationSideToDeconstruct;
        monomialsForNewOppositeSide = new LinkedList<>();
        this.monomialsWithVariable = monomialsWithVariable;
        if (initialOppositeSide instanceof Polynomial) {
            for (GeometryObject term : initialOppositeSide.getAllSubObjects()) {
                monomialsForNewOppositeSide.add((Monomial) term);
            }
        } else {
            monomialsForNewOppositeSide.add(initialOppositeSide);
        }
    }

    public Monomial getLeftoversOfDeconstructable() {
        if (leftoversOfDeconstructable == null)
            deconstruct();
        return leftoversOfDeconstructable;
    }

    public Monomial getOppositeSide() {
        if (oppositeSide == null)
            deconstruct();
        return oppositeSide;
    }

    private void deconstruct() {
        LinkedList<Monomial> elementsWithVariable = new LinkedList<Monomial>();
        for (GeometryObject term : equationSideToDeconstruct.getAllSubObjects()) {
            Monomial monomialTerm = (Monomial) term;

            if (monomialsWithVariable.contains(monomialTerm)) {
                if (monomialTerm instanceof Polynomial){
                    throw new RuntimeException("Unexpected polynomial in simplified expression! " +
                            "Expressor cannot deal with that.");
                }
                elementsWithVariable.add( monomialTerm);
                continue;
            }

            monomialsForNewOppositeSide.add(new Monomial(monomialTerm, GeometryNumber.get(-1)));
        }
        Monomial realElementWithVariable = null;
        Monomial toDivideBy = null;
        if (elementsWithVariable.size() == 1){
            realElementWithVariable = elementsWithVariable.getFirst();
        }else {
            LinkedList<Monomial> futurePolynomial = new LinkedList<>();
            for (Monomial monomialToDestruct : elementsWithVariable) {
                LinkedList<Monomial> futureMonomial = new LinkedList<>();
                for (Monomial subObject : monomialToDestruct.getAllSubObjects()) {
                    if (monomialsWithVariable.contains(subObject)){
                        realElementWithVariable = subObject;
                    }else{
                        futureMonomial.add(subObject);
                    }
                }
                if (futureMonomial.isEmpty()){
                    futurePolynomial.add(GeometryNumber.get(1));
                }
                else if (futureMonomial.size() == 1){
                    futurePolynomial.add(futureMonomial.getFirst());
                }else{
                    futurePolynomial.add(new Monomial(futureMonomial));
                }
            }

            if (futurePolynomial.size() == 1){
                toDivideBy =  futurePolynomial.getFirst();
            }else if (futurePolynomial.size() > 1){
                toDivideBy = new Polynomial(futurePolynomial);
            }
        }
        leftoversOfDeconstructable = realElementWithVariable;
        if (monomialsForNewOppositeSide.size() == 1) {
            oppositeSide = monomialsForNewOppositeSide.getFirst();
        } else {
            oppositeSide = new Polynomial(monomialsForNewOppositeSide);
        }
        if (toDivideBy != null){
            toDivideBy = new RaisedInThePower(toDivideBy,GeometryNumber.get(-1));
            oppositeSide = new Monomial(oppositeSide,toDivideBy);
        }
    }
}
