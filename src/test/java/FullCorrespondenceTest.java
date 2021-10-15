import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.HashMap;

import core.correspondence.FullCorrespondence;
import core.objects.GeometryObject;
import core.objects.LineSegment;
import core.objects.Triangle;
import core.objects.Vertex;

public class FullCorrespondenceTest {
    @Test
    public void TwoAdjacentTrianglesTest() {
        Vertex A = (Vertex) new Vertex().setDebugName("A");
        Vertex B = (Vertex) new Vertex().setDebugName("B");
        Vertex C = (Vertex) new Vertex().setDebugName("C");
        Vertex D = (Vertex) new Vertex().setDebugName("D");
        LineSegment AB = (LineSegment) new LineSegment(A, B).setDebugName("AB");
        LineSegment AD = (LineSegment) new LineSegment(A, D).setDebugName("AD");
        LineSegment BC = (LineSegment) new LineSegment(B, C).setDebugName("BC");
        LineSegment CD = (LineSegment) new LineSegment(C, D).setDebugName("CD");
        LineSegment BD = (LineSegment) new LineSegment(B, D).setDebugName("BD");
        Triangle triangle1 = (Triangle) new Triangle(AB, BD, AD).setDebugName("triangle1");
        Triangle triangle2 = (Triangle) new Triangle(BC, CD, BD).setDebugName("triangle2");
        Vertex A1 = (Vertex) new Vertex().setDebugName("A1");
        Vertex B1 = (Vertex) new Vertex().setDebugName("B1");
        Vertex C1 = (Vertex) new Vertex().setDebugName("C1");
        Vertex D1 = (Vertex) new Vertex().setDebugName("D1");
        LineSegment AB1 = (LineSegment) new LineSegment(A1, B1).setDebugName("AB1");
        LineSegment AD1 = (LineSegment) new LineSegment(A1, D1).setDebugName("AD1");
        LineSegment BC1 = (LineSegment) new LineSegment(B1, C1).setDebugName("BC1");
        LineSegment CD1 = (LineSegment) new LineSegment(C1, D1).setDebugName("CD1");
        LineSegment BD1 = (LineSegment) new LineSegment(B1, D1).setDebugName("BD1");
        Triangle triangle11 = (Triangle) new Triangle(AB1, BD1, AD1).setDebugName("triangle11");
        Triangle triangle21 = (Triangle) new Triangle(BC1, CD1, BD1).setDebugName("triangle21");
        HashMap<GeometryObject, GeometryObject> correspondence = new HashMap<>();
        correspondence.put(triangle11, triangle1);
        correspondence.put(triangle21, triangle2);
        FullCorrespondence corr = new FullCorrespondence(correspondence);
        assertTrue(corr.get(A1) == A && corr.get(C1) == C &&
                ((corr.get(B1) == B && corr.get(D1) == D) ||
                        (corr.get(B1) == D && corr.get(D1) == B)));
    }
}
