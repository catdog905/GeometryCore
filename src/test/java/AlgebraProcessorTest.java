import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import GeometryCore.AlgebraProcessor;
import GeometryCore.GeometryObjects.GeometryObject;
import GeometryCore.GeometryObjects.LineSegment;
import GeometryCore.GeometryObjects.Monomial;
import GeometryCore.GeometryObjects.NumberValue;
import GeometryCore.GeometryObjects.Polynomial;

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
                x.getAllSubObjects().stream().map(y -> y.getAllSubObjects()).collect(Collectors.toList());
            List<GeometryObject> list = bigList.stream()
                    .map(y -> y.stream().filter(z -> z instanceof LineSegment).findAny().get())
                    .collect(Collectors.toList());
            if ((list.containsAll(Arrays.asList(A, B, D)))
                    || (list.containsAll(Arrays.asList(A, C, D))
                    || (list.containsAll(Arrays.asList(A, B, E))
                    || (list.containsAll(Arrays.asList(A, C, E)))))) {
                count++;
            }
        }
        assertEquals(4, count);
    }
}
