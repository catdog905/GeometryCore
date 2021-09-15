package GeometryCore;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import GeometryCore.GeometryObjects.GeometryNumber;
import GeometryCore.GeometryObjects.GeometryObject;
import GeometryCore.GeometryObjects.Monomial;
import GeometryCore.GeometryObjects.NumberValue;
import GeometryCore.GeometryObjects.Polynomial;
import GeometryCore.GeometryObjects.RaisedInThePower;

public class Substitutor {
    Monomial expressionToSubstituteIn;
    HashMap<Monomial, Monomial> substituteTable;

    public Substitutor(Monomial expressionToSubstituteIn, HashMap<Monomial, Monomial> substituteTable) {
        this.expressionToSubstituteIn = expressionToSubstituteIn;
        this.substituteTable = substituteTable;
    }

    Monomial substitute(Monomial expression) {
        if (substituteTable.containsKey(expression)) {
            return substituteTable.get(expression);
        }
        if (expression instanceof Polynomial) {
            //Polynomial
            Polynomial polynomialEquation = (Polynomial) expression;
            LinkedList<Monomial> newPolynomialContents = new LinkedList<>();
            for (GeometryObject term : polynomialEquation.getAllSubObjects()) {
                Monomial monomialTerm = (Monomial) term;
                newPolynomialContents.add(substitute(monomialTerm));
            }
            return (Monomial) new Polynomial(newPolynomialContents);
        } else if (expression instanceof NumberValue || expression instanceof GeometryNumber) {
            //A number, finally
            return expression;
        } else if (expression instanceof RaisedInThePower) {
            RaisedInThePower ritp = (RaisedInThePower) expression;
            var subObjects = ritp.getAllSubObjects();
            int iterationsToDo = subObjects.size() - 1;
            var newSubObjects = new LinkedList<Monomial>();
            Monomial newPower = substitute((Monomial) subObjects.getLast());
            for (var subObject : subObjects) {
                if (iterationsToDo == 0)
                    break;
                newSubObjects.add(substitute((Monomial) subObject));
                iterationsToDo--;

            }
            return new RaisedInThePower(newSubObjects, newPower);
        } else {
            //Monomial
            LinkedList<Monomial> newMonomialContents = new LinkedList<>();
            for (GeometryObject term : expression.getAllSubObjects()) {
                Monomial monomialTerm = (Monomial) term;
                newMonomialContents.add(substitute(monomialTerm));
            }
            return new Monomial(newMonomialContents);
        }

    }

    Monomial substitutedExpression = null;

    public Monomial getSubstituted() {
        if (substitutedExpression == null)
            substitutedExpression = substitute(expressionToSubstituteIn);
        return substitutedExpression;
    }
}
