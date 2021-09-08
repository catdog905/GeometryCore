import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import GeometryCore.AlgebraProcessor;
import GeometryCore.GeometryObjects.GeometryNumber;
import GeometryCore.GeometryObjects.GeometryObject;
import GeometryCore.GeometryObjects.LineSegment;
import GeometryCore.GeometryObjects.Monomial;
import GeometryCore.GeometryObjects.NumberValue;
import GeometryCore.GeometryObjects.Polynomial;
import GeometryCore.GeometryObjects.RaisedInThePower;

public class AlgebraProcessorTest {
    @Test
    public void expandBracketsTest() {
        LineSegment A = new LineSegment();
        LineSegment B = new LineSegment();
        LineSegment C = new LineSegment();
        LineSegment D = new LineSegment();
        LineSegment E = new LineSegment();
        Monomial expression = new Monomial(
                new NumberValue(A, null),
                new Polynomial(
                        new NumberValue(B, null),
                        new NumberValue(C, null)),
                new Polynomial(
                        new NumberValue(D, null),
                        new NumberValue(E, null)
                )
        );
        Monomial newExpression = AlgebraProcessor.simplifyExpression(expression);

        long count = 0L;
        for (GeometryObject x : newExpression.getAllSubObjects()) {
            List<List<GeometryObject>> bigList =
                x.getAllSubObjects().stream().map(y -> (List<GeometryObject>)y.getAllSubObjects()).collect(Collectors.toList());
            List<GeometryObject> list = bigList.stream()
                    .map(y -> y.stream().filter(z -> z instanceof LineSegment).findAny().get())
                    .collect(Collectors.toList());
            if ((list.containsAll(Arrays.asList(A, B, D)))
                    || (list.containsAll(Arrays.asList(A, C, D)))
                    || (list.containsAll(Arrays.asList(A, B, E)))
                    || (list.containsAll(Arrays.asList(A, C, E)))) {
                count++;
            }
        }
        assertEquals(4, count);
    }

    @Test
    public void expandBracketsTestWithoutBrackets() {
        LineSegment A = new LineSegment();
        LineSegment B = new LineSegment();
        LineSegment C = new LineSegment();
        LineSegment D = new LineSegment();
        LineSegment E = new LineSegment();
        Monomial expression = new Monomial(
                new NumberValue(A, null),
                new NumberValue(B, null),
                new NumberValue(C, null),
                new NumberValue(D, null),
                new NumberValue(E, null)
        );
        Monomial newExpression = AlgebraProcessor.simplifyExpression(expression);

        assertTrue(newExpression.getAllSubObjects().stream().map(x -> x.getAllSubObjects().get(0))
                .collect(Collectors.toCollection(LinkedList::new)).containsAll(Arrays.asList(A, B, C, D, E)));
    }

    @Test
    public void expandDeepIncludedBracketsTest() {
        LineSegment A = new LineSegment();
        LineSegment B = new LineSegment();
        LineSegment C = new LineSegment();
        LineSegment D = new LineSegment();
        LineSegment E = new LineSegment();
        LineSegment F = new LineSegment();
        LineSegment G = new LineSegment();
        Monomial expression = new Monomial(
                new NumberValue(A, null),
                new Polynomial(
                        new NumberValue(B, null),
                        new NumberValue(C, null)),
                new Polynomial(
                        new NumberValue(D, null),
                        new Polynomial(
                                new NumberValue(F, null),
                                new NumberValue(G, null)
                        )
                )
        );
        Monomial newExpression = AlgebraProcessor.simplifyExpression(expression);

        long count = 0L;
        for (GeometryObject x : newExpression.getAllSubObjects()) {
            List<List<GeometryObject>> bigList =
                    x.getAllSubObjects().stream().map(y -> (List<GeometryObject>)y.getAllSubObjects()).collect(Collectors.toList());
            List<GeometryObject> list = bigList.stream()
                    .map(y -> y.stream().filter(z -> z instanceof LineSegment).findAny().get())
                    .collect(Collectors.toList());
            if ((list.containsAll(Arrays.asList(A, B, D)))
                    || (list.containsAll(Arrays.asList(A, B, F)))
                    || (list.containsAll(Arrays.asList(A, B, G)))
                    || (list.containsAll(Arrays.asList(A, C, D)))
                    || (list.containsAll(Arrays.asList(A, C, F)))
                    || (list.containsAll(Arrays.asList(A, C, G)))) {
                count++;
            }
        }
        assertEquals(6, count);
    }

    @Test
    public void expandBracketsWithPowersTest() {
        LineSegment A = new LineSegment();
        LineSegment B = new LineSegment();
        Monomial expression = new Monomial(
                new RaisedInThePower(
                        new Monomial(
                                new NumberValue(A),
                                new NumberValue(B)
                        ),
                        GeometryNumber.createNumber(2)
                )
        );
        Monomial newExpression = AlgebraProcessor.simplifyExpression(expression);

        long count = 0L;
        for (GeometryObject x : newExpression.getAllSubObjects()) {
            List<List<GeometryObject>> bigList =
                    x.getAllSubObjects().stream().map(y -> (List<GeometryObject>)y.getAllSubObjects()).collect(Collectors.toList());
            List<GeometryObject> list = bigList.stream()
                    .map(y -> y.stream().filter(z -> z instanceof LineSegment).findAny().get())
                    .collect(Collectors.toList());
            if ((list.containsAll(Arrays.asList(A)))
                    || (list.containsAll(Arrays.asList(B)))) {
                count++;
            }
        }
        assertEquals(2, count);
    }
}
