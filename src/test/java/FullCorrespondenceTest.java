import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.HashMap;

import core.correspondence.FullCorrespondence;
import core.model.facts.objects.GeometryObject;
import core.model.facts.objects.LineSegment;
import core.model.facts.objects.Triangle;
import core.model.facts.objects.Vertex;

public class FullCorrespondenceTest {
    @Test
    public void TwoAdjacentTrianglesTest() {
        Vertex A = (Vertex) new Vertex();
        Vertex B = (Vertex) new Vertex();
        Vertex C = (Vertex) new Vertex();
        Vertex D = (Vertex) new Vertex();
        LineSegment AB = (LineSegment) new LineSegment(A, B);
        LineSegment AD = (LineSegment) new LineSegment(A, D);
        LineSegment BC = (LineSegment) new LineSegment(B, C);
        LineSegment CD = (LineSegment) new LineSegment(C, D);
        LineSegment BD = (LineSegment) new LineSegment(B, D);
        Triangle triangle1 = (Triangle) new Triangle(AB, BD, AD);
        Triangle triangle2 = (Triangle) new Triangle(BC, CD, BD);
        Vertex A1 = (Vertex) new Vertex();
        Vertex B1 = (Vertex) new Vertex();
        Vertex C1 = (Vertex) new Vertex();
        Vertex D1 = (Vertex) new Vertex();
        LineSegment AB1 = (LineSegment) new LineSegment(A1, B1);
        LineSegment AD1 = (LineSegment) new LineSegment(A1, D1);
        LineSegment BC1 = (LineSegment) new LineSegment(B1, C1);
        LineSegment CD1 = (LineSegment) new LineSegment(C1, D1);
        LineSegment BD1 = (LineSegment) new LineSegment(B1, D1);
        Triangle triangle11 = (Triangle) new Triangle(AB1, BD1, AD1);
        Triangle triangle21 = (Triangle) new Triangle(BC1, CD1, BD1);
        HashMap<GeometryObject, GeometryObject> correspondence = new HashMap<>();
        correspondence.put(triangle11, triangle1);
        correspondence.put(triangle21, triangle2);
        FullCorrespondence corr = new FullCorrespondence(correspondence);
        assertTrue(corr.get(A1) == A && corr.get(C1) == C &&
                ((corr.get(B1) == B && corr.get(D1) == D) ||
                        (corr.get(B1) == D && corr.get(D1) == B)));
    }
}
