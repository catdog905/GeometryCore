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




    public static Monomial expressVariableFromEquation(NumberValue variable, EqualityFact equation) {

        return expressVariableFromEquation(variable, (Monomial) equation.left, (Monomial) equation.right);
    }

    public static Monomial expressVariableFromEquation(NumberValue variable, Monomial equationLeft, Monomial equationRight) {
        return Expressor.expressVariableFromEquation(variable, equationLeft, equationRight);
    }




}
