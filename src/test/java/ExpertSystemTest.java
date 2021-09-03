import org.junit.Test;

import java.util.HashSet;

import GeometryCore.ExpertSystem;
import GeometryCore.Facts.BelongFact;
import GeometryCore.Facts.Fact;
import GeometryCore.GeometryObjects.LineSegment;
import GeometryCore.GeometryObjects.Vertex;
import GeometryCore.Model;

public class ExpertSystemTest{
    @Test
    public void FindTriangleTest() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment BC = new LineSegment(B, C);
        LineSegment AC = new LineSegment(A, C);
        HashSet<Fact> facts = new HashSet<>();
        facts.add(new BelongFact(A, AB));
        facts.add(new BelongFact(B, AB));
        facts.add(new BelongFact(A, AC));
        facts.add(new BelongFact(B, BC));
        facts.add(new BelongFact(C, AC));
        facts.add(new BelongFact(C, BC));
        Model model = new Model(facts);
        ExpertSystem.ForwardPass(model);

        System.out.println("This is the testcase in this class");
        //String str1="This is the testcase in this class";
        //assertEquals("This is the testcase in this class", str1);
    }
}
