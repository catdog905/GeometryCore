import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;

import GeometryCore.ExpertSystem;
import GeometryCore.Facts.BelongFact;
import GeometryCore.Facts.EqualityFact;
import GeometryCore.Facts.ExistFact;
import GeometryCore.Facts.Fact;
import GeometryCore.Facts.RightAngledFact;
import GeometryCore.GeometryObjects.Angle;
import GeometryCore.GeometryObjects.Degree;
import GeometryCore.GeometryObjects.LineSegment;
import GeometryCore.GeometryObjects.Triangle;
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

        assertTrue(model.facts.stream().anyMatch(x -> x instanceof ExistFact &&
                ((Triangle)((ExistFact)x).object).lineSegments.contains(AB) &&
                ((Triangle)((ExistFact)x).object).lineSegments.contains(BC) &&
                ((Triangle)((ExistFact)x).object).lineSegments.contains(AC)));
    }

    @Test
    public void FindRightTriangleTest() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment BC = new LineSegment(B, C);
        LineSegment AC = new LineSegment(A, C);
        Angle ACB = new Angle(new LinkedList(Arrays.asList(A, B, C)));
        HashSet<Fact> facts = new HashSet<>();
        facts.add(new ExistFact(new Triangle(new HashSet<>(Arrays.asList(AB, AC, BC)))));
        facts.add(new EqualityFact(ACB, Degree.createNumber(90)));
        //facts.add(new EqualityFact(AB, new NumberEnveloper(4)));
        //facts.add(new EqualityFact(AB, new NumberEnveloper(3)));
        Model model = new Model(facts);
        ExpertSystem.ForwardPass(model);

        assertTrue(model.facts.stream().anyMatch(x -> x instanceof RightAngledFact));
    }

    @Test
    public void pythagoreanTheoremTest() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment BC = new LineSegment(B, C);
        LineSegment AC = new LineSegment(A, C);
        Angle ACB = new Angle(new LinkedList(Arrays.asList(A, B, C)));
        HashSet<Fact> facts = new HashSet<>();
        facts.add(new RightAngledFact(new Triangle(new HashSet<>(Arrays.asList(AB, AC, BC)))));
        facts.add(new EqualityFact(ACB, Degree.createNumber(90)));
        //facts.add(new EqualityFact(AB, new NumberEnveloper(4)));
        //facts.add(new EqualityFact(AB, new NumberEnveloper(3)));
        Model model = new Model(facts);
        ExpertSystem.ForwardPass(model);

        assertTrue(model.facts.stream().anyMatch(x -> x instanceof RightAngledFact));
    }
}
