import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import core.objects.expression.BracketsExpander;
import core.objects.GeometryObject;
import core.objects.LineSegment;
import core.objects.expression.GeometryNumber;
import core.objects.expression.Monomial;
import core.objects.expression.Polynomial;
import core.objects.expression.RaisedInThePower;

public class BracketsExpanderTest {
    @Test
    public void expandBracketsTest() {
        LineSegment A = new LineSegment();
        LineSegment B = new LineSegment();
        LineSegment C = new LineSegment();
        LineSegment D = new LineSegment();
        LineSegment E = new LineSegment();
        Monomial expression = new Monomial(
               A.getMonomial(),
                new Polynomial(B.getMonomial(),C.getMonomial(),
                    new Polynomial(D.getMonomial(),E.getMonomial())
        ));
        Monomial newExpression = new BracketsExpander(expression).get();

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
                A.getMonomial(),
                B.getMonomial(),
                C.getMonomial(),
                D.getMonomial(),
                E.getMonomial()
        );
        Monomial newExpression = new BracketsExpander(expression).get();

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
                A.getMonomial(),
                new Polynomial(
                        B.getMonomial(),
                        C.getMonomial()),
                new Polynomial(
                        D.getMonomial(),
                        new Polynomial(
                                F.getMonomial(),
                                G.getMonomial()
                        )
                )
        );
        Monomial newExpression = new BracketsExpander(expression).get();
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
                                A.getMonomial(),
                                B.getMonomial()
                        ),
                        GeometryNumber.get(2)
                )
        );
        Monomial newExpression = new BracketsExpander(expression).get();

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
