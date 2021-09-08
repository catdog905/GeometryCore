package GeometryCore;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

import GeometryCore.Facts.EqualityFact;
import GeometryCore.GeometryObjects.GeometryNumber;
import GeometryCore.GeometryObjects.GeometryObject;
import GeometryCore.GeometryObjects.Monomial;
import GeometryCore.GeometryObjects.NumberValue;
import GeometryCore.GeometryObjects.Polynomial;
import GeometryCore.GeometryObjects.RaisedInThePower;

public class AlgebraProcessor {

    private static boolean tryFindVariableAndPutItInHashSet(NumberValue variable, Monomial equation, HashSet<Monomial> whereToPut) {
        if (equation instanceof Polynomial) {
            //Polynomial
            Polynomial polynomialEquation = (Polynomial) equation;
            for (GeometryObject term : polynomialEquation.getAllSubObjects()) {
                Monomial monomialTerm = (Monomial) term;
                if (tryFindVariableAndPutItInHashSet(variable, monomialTerm, whereToPut)) {
                    whereToPut.add(equation);
                    return true;
                }
            }
        } else {
            //Monomial
            for (GeometryObject term : equation.getAllSubObjects()) {
                if (term instanceof NumberValue) {
                    //A number, finally
                    NumberValue numberValueOfTerm = (NumberValue) term;
                    if (numberValueOfTerm.equals(variable)) {
                        whereToPut.add(numberValueOfTerm);
                        whereToPut.add(equation);
                        return true;
                    }

                } else {
                    //Monomial inside of monomial
                    if (tryFindVariableAndPutItInHashSet(variable, (Monomial) term, whereToPut)) {
                        whereToPut.add(equation);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static final boolean LEFT_SIDE = false, RIGHT_SIDE = true;

    private static Monomial uniteMonomialsIntoOne(LinkedList<Monomial> monomials) {
        if (monomials.size() >= 2) {
            return new Polynomial(monomials);
        } else {
            return monomials.get(0);
        }
    }

    private static Monomial ManageMonomialWithVariable(Monomial bigElementWithVariable,LinkedList<Monomial> dataForNewEquationRight,Monomial newRight, HashSet<Monomial> monomialsWithVariable){
        var monomialsSubObjects = bigElementWithVariable.getAllSubObjects();
        int iterationsToMake = monomialsSubObjects.size();
        if (bigElementWithVariable instanceof RaisedInThePower) {
            iterationsToMake--;
            Monomial reverseRITP_power = new RaisedInThePower(new LinkedList<>(Collections.singletonList((Monomial) monomialsSubObjects.getLast())), GeometryNumber.createNumber(-1));
            newRight = new RaisedInThePower(new LinkedList<>(Collections.singletonList(newRight)), reverseRITP_power);
        }
        dataForNewEquationRight.add(newRight);

        for (GeometryObject term : monomialsSubObjects) {
            if (iterationsToMake == 0)
                break;
            iterationsToMake--;
            Monomial monomialTerm = (Monomial) term;
            if (monomialsWithVariable.contains(monomialTerm)) {
                bigElementWithVariable = monomialTerm;
            } else {
                dataForNewEquationRight.add(new RaisedInThePower(new LinkedList<>( Collections.singletonList(monomialTerm)), GeometryNumber.createNumber(-1)));
            }
        }
        return bigElementWithVariable;
    }

    private static Monomial ManagePolynomialWithVariable(Polynomial sideWithVariable, HashSet<Monomial> monomialsWithVariable, LinkedList<Monomial> whereToPut){
        Monomial bigElementWithVariable = null;
        var monomialsSubObjects = sideWithVariable.getAllSubObjects();
            for (GeometryObject term : monomialsSubObjects) {
        Monomial monomialTerm = (Monomial) term;

        if (monomialsWithVariable.contains(monomialTerm)) {
            //This is a monomial with variable, so we leave it where it is
            //We expect to see only one appearance of variable
            assert (bigElementWithVariable==null);
            bigElementWithVariable = monomialTerm;
            continue;
        }

        LinkedList<Monomial> constructableMonomial = new LinkedList<>();
        constructableMonomial.add(monomialTerm);
        constructableMonomial.add(GeometryNumber.createNumber(-1));

                whereToPut.add(new Monomial(constructableMonomial));
    }
            return bigElementWithVariable;
    }


    public static Monomial expressVariableFromEquation(NumberValue variable, EqualityFact equation) {

        return expressVariableFromEquation(variable, (Monomial) equation.left, (Monomial) equation.right);
    }

    public static Monomial expressVariableFromEquation(NumberValue variable, Monomial equationLeft, Monomial equationRight) {
        HashSet<Monomial> monomialsWithVariable = new HashSet<>();
        boolean equationSideWithVariable;

        if (tryFindVariableAndPutItInHashSet(variable, equationRight, monomialsWithVariable)) {
            equationSideWithVariable = RIGHT_SIDE;
        } else if (tryFindVariableAndPutItInHashSet(variable, equationLeft, monomialsWithVariable)) {
            equationSideWithVariable = LEFT_SIDE;
        } else {
            throw new RuntimeException("ALGEBRA PROCESSOR::EXPRESSOR ERROR: No variable in equation, can't express it");
        }

        return expressVariableFromEquation(variable, equationLeft, equationRight, monomialsWithVariable, equationSideWithVariable);
    }


    public static Monomial expressVariableFromEquation(NumberValue variable, Monomial equationLeft, Monomial equationRight, HashSet<Monomial> monomialsWithVariable, boolean equationSideWithVariable) {

        Monomial sideWithVariable, sideWithoutVariable;
        if (equationSideWithVariable == LEFT_SIDE) {
            sideWithVariable = (Monomial) equationLeft;
            sideWithoutVariable = (Monomial) equationRight;
        } else {
            sideWithVariable = (Monomial) equationRight;
            sideWithoutVariable = (Monomial) equationLeft;
        }

        LinkedList<Monomial> dataForNewEquationRight = new LinkedList<>();
        if (!(sideWithoutVariable instanceof Polynomial)){
            dataForNewEquationRight.add(sideWithoutVariable);
        }else {
            for (GeometryObject term : sideWithoutVariable.getAllSubObjects()) {
                dataForNewEquationRight.add((Monomial) term);
            }
        }

        Monomial bigElementWithVariable;

        if (sideWithVariable instanceof Polynomial) {
            bigElementWithVariable = ManagePolynomialWithVariable((Polynomial) sideWithVariable,monomialsWithVariable,dataForNewEquationRight);
        }else{
            // If it's not polynomial, the whole monomial IS the big element with variable
            bigElementWithVariable=sideWithVariable;

        }

        Monomial newRight = uniteMonomialsIntoOne(dataForNewEquationRight);

        while (!(bigElementWithVariable instanceof NumberValue) && !(bigElementWithVariable instanceof Polynomial)) {
            dataForNewEquationRight = new LinkedList<>();

            bigElementWithVariable =  ManageMonomialWithVariable(bigElementWithVariable,dataForNewEquationRight,newRight,monomialsWithVariable);

            if (dataForNewEquationRight.size() != 1)
                newRight = new Monomial(dataForNewEquationRight);
            else
                newRight = dataForNewEquationRight.getFirst();

        }

        if (bigElementWithVariable instanceof NumberValue && ((NumberValue) bigElementWithVariable).equals(variable)) {
            return newRight;
        } else {
            return expressVariableFromEquation(variable, bigElementWithVariable, newRight, monomialsWithVariable, LEFT_SIDE);
        }
    }


}
