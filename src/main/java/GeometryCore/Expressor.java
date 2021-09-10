package GeometryCore;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;

import GeometryCore.GeometryObjects.GeometryNumber;
import GeometryCore.GeometryObjects.GeometryObject;
import GeometryCore.GeometryObjects.Monomial;
import GeometryCore.GeometryObjects.NumberValue;
import GeometryCore.GeometryObjects.Polynomial;
import GeometryCore.GeometryObjects.RaisedInThePower;

public class Expressor {
    private static HashSet<Monomial> findAllMonomialsWithVariable(NumberValue variable, Monomial equation) {
        HashSet<Monomial> answer;
        if (equation instanceof Polynomial) {
            //Polynomial
            Polynomial polynomialEquation = (Polynomial) equation;
            for (GeometryObject term : polynomialEquation.getAllSubObjects()) {
                Monomial monomialTerm = (Monomial) term;
                answer = findAllMonomialsWithVariable(variable, monomialTerm);
                if (answer != null) {
                    answer.add(equation);
                    return answer;
                }
            }
        } else {
            //Monomial
            for (GeometryObject term : equation.getAllSubObjects()) {
                if (term instanceof NumberValue) {
                    //A number, finally
                    NumberValue numberValueOfTerm = (NumberValue) term;
                    if (numberValueOfTerm.equals(variable)) {
                        answer = new HashSet<>();
                        answer.add(numberValueOfTerm);
                        answer.add(equation);
                        return answer;
                    }

                } else {
                    //Monomial inside of monomial
                    answer = findAllMonomialsWithVariable(variable, (Monomial) term);
                    if (answer != null) {
                        answer.add(equation);
                        return answer;
                    }
                }
            }
        }
        return null;
    }

    private static final boolean LEFT_SIDE = false, RIGHT_SIDE = true;


    private static Monomial DeconstructPolynomialWithVariable(Polynomial sideWithVariable, HashSet<Monomial> monomialsWithVariable, LinkedList<Monomial> whereToPut) {
        Monomial bigElementWithVariable = null;
        var monomialsSubObjects = sideWithVariable.getAllSubObjects();
        for (GeometryObject term : monomialsSubObjects) {
            Monomial monomialTerm = (Monomial) term;

            if (monomialsWithVariable.contains(monomialTerm)) {
                //This is a monomial with variable, so we leave it where it is
                //We expect to see only one appearance of variable
                assert (bigElementWithVariable == null);
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

    private static Monomial DeconstructMonomialWithVariable(Monomial bigElementWithVariable, LinkedList<Monomial> dataForNewEquationRight, Monomial newRight, HashSet<Monomial> monomialsWithVariable) {
        var monomialsSubObjects = bigElementWithVariable.getAllSubObjects();
        int iterationsToMake = monomialsSubObjects.size();
        if (bigElementWithVariable instanceof RaisedInThePower) {
            // We do not want to iterate through the last element of RaisedInThePower [the power]
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
                dataForNewEquationRight.add(new RaisedInThePower(new LinkedList<>(Collections.singletonList(monomialTerm)), GeometryNumber.createNumber(-1)));
            }
        }
        return bigElementWithVariable;
    }

    private static Monomial expressVariableFromEquation(NumberValue variable, Monomial equationLeft, Monomial equationRight, HashSet<Monomial> monomialsWithVariable, boolean equationSideWithVariable) {

        // New equation left = our variable
        Monomial newLeft;

        // New equation right = some expression
        Monomial newRight;

        if (equationSideWithVariable == LEFT_SIDE) {
            newLeft = (Monomial) equationLeft;
            newRight = (Monomial) equationRight;
        } else {
            newLeft = (Monomial) equationRight;
            newRight = (Monomial) equationLeft;
        }


        while (!(newLeft instanceof NumberValue)) {

            if (newLeft instanceof Polynomial) {
                // --- POLYNOMIAL DECONSTRUCTION ---
                LinkedList<Monomial> constructablePolynomial = new LinkedList<>();

                if (newRight instanceof Polynomial) {
                    for (GeometryObject term : newRight.getAllSubObjects()) {
                        constructablePolynomial.add((Monomial) term);
                    }
                } else {
                    constructablePolynomial.add(newRight);
                }

                newLeft = DeconstructPolynomialWithVariable((Polynomial) newLeft, monomialsWithVariable, constructablePolynomial);
                if (constructablePolynomial.size() == 1){
                    newRight = constructablePolynomial.getFirst();
                }else{
                    newRight = new Polynomial(constructablePolynomial);
                }
            } else {
                // --- MONOMIAL DECONSTRUCTION ---
                LinkedList<Monomial> constructableMonomial = new LinkedList<>();


                newLeft = DeconstructMonomialWithVariable(newLeft, constructableMonomial, newRight, monomialsWithVariable);
                if (constructableMonomial.size() == 1)
                    newRight = constructableMonomial.getFirst();
                else
                    newRight = new Monomial(constructableMonomial);
            }
        }

        // We assume that the number value that's on the left is the variable
        assert (variable.equals((NumberValue) newLeft));

        return newRight;
    }

    public static Monomial expressVariableFromEquation(NumberValue variable, Monomial equationLeft, Monomial equationRight) {
        boolean equationSideWithVariable;

        HashSet<Monomial> monomialsWithVariable = findAllMonomialsWithVariable(variable, equationRight);
        if (monomialsWithVariable != null) {
            equationSideWithVariable = RIGHT_SIDE;

            // We assume that variable is only on one side
            assert (findAllMonomialsWithVariable(variable, equationLeft) == null);
        } else {
            monomialsWithVariable = findAllMonomialsWithVariable(variable, equationLeft);

            // We assume that variable exists
            assert (monomialsWithVariable != null);

            equationSideWithVariable = LEFT_SIDE;
        }

        return expressVariableFromEquation(variable, equationLeft, equationRight, monomialsWithVariable, equationSideWithVariable);
    }
}
