package core.facts.equation;

import java.util.HashSet;
import java.util.LinkedList;

import core.objects.GeometryObject;
import core.objects.expression.Monomial;
import core.objects.expression.Polynomial;
import core.objects.expression.GeometryNumber;

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
        Monomial elementWithVariable = null;

        for (GeometryObject term : equationSideToDeconstruct.getAllSubObjects()) {
            Monomial monomialTerm = (Monomial) term;

            if (monomialsWithVariable.contains(monomialTerm)) {

                assert (elementWithVariable == null);
                elementWithVariable = monomialTerm;
                continue;
            }

            monomialsForNewOppositeSide.add(new Monomial(monomialTerm, GeometryNumber.createNumber(-1)));
        }
        leftoversOfDeconstructable = elementWithVariable;
        if (monomialsForNewOppositeSide.size() == 1) {
            oppositeSide = monomialsForNewOppositeSide.getFirst();
        } else {
            oppositeSide = new Polynomial(monomialsForNewOppositeSide);
        }
    }
}
