package GeometryCore;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;

import GeometryCore.GeometryObjects.GeometryNumber;
import GeometryCore.GeometryObjects.GeometryObject;
import GeometryCore.GeometryObjects.Monomial;
import GeometryCore.GeometryObjects.RaisedInThePower;

public class MonomialDeconstructor {

    private LinkedList<Monomial> monomialsForNewOppositeSide;
    private Monomial equationSideToDeconstruct;
    private HashSet<Monomial> monomialsWithVariable;
    private Monomial oppositeSide = null;
    private Monomial initialOppositeSide = null;
    private Monomial leftoversOfDeconstructable = null;

    public MonomialDeconstructor(Monomial equationSideToDeconstruct, Monomial initialOppositeSide, HashSet<Monomial> monomialsWithVariable) {
        this.equationSideToDeconstruct = equationSideToDeconstruct;
        monomialsForNewOppositeSide = new LinkedList<>();
        this.monomialsWithVariable = monomialsWithVariable;
        this.initialOppositeSide = initialOppositeSide;
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

    public void deconstruct() {
        LinkedList<GeometryObject> monomialsOnDeconstructableSide = equationSideToDeconstruct.getAllSubObjects();
        int iterationsToMake = monomialsOnDeconstructableSide.size();

        if (equationSideToDeconstruct instanceof RaisedInThePower) {
            Monomial reverseRITP_power = new RaisedInThePower(
                    new LinkedList<>(Collections.singletonList((Monomial) monomialsOnDeconstructableSide.getLast()))
                    , GeometryNumber.createNumber(-1)
            );
            initialOppositeSide = new RaisedInThePower(
                    new LinkedList<>(Collections.singletonList(initialOppositeSide)), reverseRITP_power
            );
            // We do not want to iterate through the last subObject of RaisedInThePower [the power]
            iterationsToMake--;
        }

        monomialsForNewOppositeSide.add(initialOppositeSide);
        Monomial elementWithVariable = null;
        for (GeometryObject term : equationSideToDeconstruct.getAllSubObjects()) {
            if (iterationsToMake == 0)
                break;
            Monomial monomialTerm = (Monomial) term;
            if (monomialsWithVariable.contains(monomialTerm)) {
                assert (elementWithVariable == null);
                elementWithVariable = monomialTerm;
            } else {
                monomialsForNewOppositeSide.add(new RaisedInThePower(
                        new LinkedList<>(Collections.singletonList(monomialTerm)),
                        GeometryNumber.createNumber(-1))
                );
            }
            iterationsToMake--;
        }

        leftoversOfDeconstructable = elementWithVariable;
        if (monomialsForNewOppositeSide.size() == 1)
            oppositeSide = monomialsForNewOppositeSide.getFirst();
        else
            oppositeSide = new Monomial(monomialsForNewOppositeSide);
    }

}