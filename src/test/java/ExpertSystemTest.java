import static org.junit.Assert.assertEquals;
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
import GeometryCore.Facts.InscribedFact;
import GeometryCore.Facts.PerpendicularityFact;
import GeometryCore.Facts.RightAngledFact;
import GeometryCore.Facts.TouchedFact;
import GeometryCore.GeometryObjects.Angle;
import GeometryCore.GeometryObjects.Circle;
import GeometryCore.GeometryObjects.Degree;
import GeometryCore.GeometryObjects.GeometryNumber;
import GeometryCore.GeometryObjects.LineSegment;
import GeometryCore.GeometryObjects.NumberValue;
import GeometryCore.GeometryObjects.Polynomial;
import GeometryCore.GeometryObjects.RaisedInThePower;
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

        HashSet<Fact> checkFacts = new HashSet<>(facts);
        checkFacts.add(new ExistFact(new Triangle(new HashSet<>(Arrays.asList(BC, AC, AB)))));
        Model checkModel = new Model(checkFacts);
        assertTrue(checkModel.isEquivalentTo(model));
    }

    @Test
    public void RepeatingTriangleFactsTest() {
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

        HashSet<Fact> checkFacts = new HashSet<>(facts);
        checkFacts.add(new ExistFact(new Triangle(new HashSet<>(Arrays.asList(BC, AC, AB)))));
        Model checkModel = new Model(checkFacts);
        assertTrue(checkModel.isEquivalentTo(model));
        assertEquals(7, model.getFacts().size());
    }

    @Test
    public void FindRightTriangleTest() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment BC = new LineSegment(B, C);
        LineSegment AC = new LineSegment(A, C);
        Angle ACB = new Angle(new LinkedList(Arrays.asList(AC, BC)));
        HashSet<Fact> facts = new HashSet<>();
        facts.add(new ExistFact(new Triangle(new HashSet<>(Arrays.asList(AB, AC, BC)))));
        facts.add(new EqualityFact(ACB, Degree.createNumber(90)));
        Model model = new Model(facts);
        ExpertSystem.ForwardPass(model);

        HashSet<Fact> checkFacts = new HashSet<>(facts);
        checkFacts.add(new RightAngledFact(new Triangle(AB, AC, BC)));
        Model checkModel = new Model(checkFacts);
        assertTrue(checkModel.isEquivalentTo(model));
    }

    @Test
    public void pythagoreanTheoremTest() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment BC = new LineSegment(B, C);
        LineSegment AC = new LineSegment(A, C);
        Angle ACB = new Angle(new LinkedList(Arrays.asList(AC,  BC)));
        HashSet<Fact> facts = new HashSet<>();
        facts.add(new RightAngledFact(new Triangle(new HashSet<>(Arrays.asList(AB, AC, BC)))));
        facts.add(new EqualityFact(ACB, Degree.createNumber(90)));
        Model model = new Model(facts);
        ExpertSystem.ForwardPass(model);

        HashSet<Fact> checkFacts = new HashSet<>(facts);
        checkFacts.add(new EqualityFact(
                new RaisedInThePower(new NumberValue(AB, null), GeometryNumber.createNumber(2)),
                new Polynomial(
                        new RaisedInThePower(new NumberValue(AC, null), GeometryNumber.createNumber(2)),
                        new RaisedInThePower(new NumberValue(BC, null), GeometryNumber.createNumber(2))
                )));
        Model checkModel = new Model(checkFacts);
        assertTrue(checkModel.isEquivalentTo(model));
    }

    @Test
    public void lineFromInscribedCenterToVertexIsBisectorTest() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        Vertex center = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment BC = new LineSegment(B, C);
        LineSegment AC = new LineSegment(A, C);
        Triangle triangle = new Triangle(AB, BC, AC);
        Circle circle = new Circle(center);
        HashSet<Fact> facts = new HashSet<>();
        facts.add(new InscribedFact(circle, triangle));

        Model model = new Model(facts);
        ExpertSystem.ForwardPass(model);
        assertTrue(model.getFacts().stream().anyMatch(x -> x instanceof RightAngledFact));
    }

    @Test
    public void equalsDistanceFromCenterToTouchedLines() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        Vertex center = new Vertex();
        Circle circle = new Circle(center);
        LineSegment AB = new LineSegment(A, B);
        LineSegment BC = new LineSegment(B, C);
        HashSet<Fact> facts = new HashSet<>();
        Vertex touchPlace1 = new Vertex();
        Vertex touchPlace2 = new Vertex();
        facts.add(new TouchedFact(touchPlace1, circle, AB));
        facts.add(new TouchedFact(touchPlace2, circle, BC));

        Model model = new Model(facts);
        ExpertSystem.ForwardPass(model);

        HashSet<Fact> checkFacts = new HashSet<>(facts);
        checkFacts.add(new EqualityFact(new LineSegment(touchPlace1, center),
                new LineSegment(touchPlace2, center)));
        Model checkModel = new Model(checkFacts);
        assertTrue(checkModel.isEquivalentTo(model));

    }

    @Test
    public void sublineInLineTest() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        HashSet<Fact> facts = new HashSet<>();
        facts.add(new BelongFact(C, AB));

        Model model = new Model(facts);
        ExpertSystem.ForwardPass(model);

        HashSet<Fact> checkFacts = new HashSet<>(facts);
        checkFacts.add(new BelongFact(new LineSegment(A, C), AB));
        checkFacts.add(new BelongFact(new LineSegment(B, C), AB));
        Model checkModel = new Model(checkFacts);
        assertTrue(checkModel.isEquivalentTo(model));
    }

    @Test
    public void sublineInLinesOfTriangleTest() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        Vertex M = new Vertex();
        Vertex N = new Vertex();
        Vertex P = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment AC = new LineSegment(A, C);
        LineSegment BC = new LineSegment(B, C);
        Triangle triangle = new Triangle(AB, AC, BC);
        HashSet<Fact> facts = new HashSet<>();
        facts.add(new ExistFact(triangle));
        facts.add(new BelongFact(M, AB));
        facts.add(new BelongFact(N, AC));
        facts.add(new BelongFact(P, BC));

        Model model = new Model(facts);
        ExpertSystem.ForwardPass(model);

        HashSet<Fact> checkFacts = new HashSet<>(facts);
        checkFacts.add(new BelongFact(new LineSegment(A, M), AB));
        checkFacts.add(new BelongFact(new LineSegment(B, M), AB));

        checkFacts.add(new BelongFact(new LineSegment(A, N), AC));
        checkFacts.add(new BelongFact(new LineSegment(C, N), AC));

        checkFacts.add(new BelongFact(new LineSegment(C, P), BC));
        checkFacts.add(new BelongFact(new LineSegment(B, P), BC));
        Model checkModel = new Model(checkFacts);
        assertTrue(checkModel.isEquivalentTo(model));
    }

    @Test
    public void circleTouchLineTest() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex center = new Vertex();
        Vertex touchPlace = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        Circle circle = new Circle(center);
        HashSet<Fact> facts = new HashSet<>();
        facts.add(new TouchedFact(touchPlace, circle, AB));

        Model model = new Model(facts);
        ExpertSystem.ForwardPass(model);

        HashSet<Fact> checkFacts = new HashSet<>(facts);
        checkFacts.add(new PerpendicularityFact(AB, new LineSegment(touchPlace, center)));
        Model checkModel = new Model(checkFacts);
        assertTrue(checkModel.isEquivalentTo(model));
    }

    @Test
    public void equalityTriangleFactBy2SidesAndAngleBetweenTest() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        LineSegment BC = new LineSegment(B, C);
        LineSegment AC = new LineSegment(A, C);
        Angle ACB = new Angle(new LinkedList(Arrays.asList(AC, BC)));
        Vertex A1 = new Vertex();
        Vertex B1 = new Vertex();
        Vertex C1 = new Vertex();
        LineSegment BC1 = new LineSegment(B1, C1);
        LineSegment AC1 = new LineSegment(A1, C1);
        Angle ACB1 = new Angle(new LinkedList(Arrays.asList(AC1, BC1)));

        HashSet<Fact> facts = new HashSet<>();
        facts.add(new EqualityFact(AC, AC1));
        facts.add(new EqualityFact(BC, BC1));
        facts.add(new EqualityFact(ACB, ACB1));
        Model model = new Model(facts);
        ExpertSystem.ForwardPass(model);

        HashSet<Fact> checkFacts = new HashSet<>(facts);
        checkFacts.add(new EqualityFact(
                new Triangle(new LineSegment(A, B), BC, AC),
                new Triangle(new LineSegment(A1, B1), BC1, AC1)));
        Model checkModel = new Model(checkFacts);
        assertTrue(checkModel.isEquivalentTo(model));
    }

    @Test
    public void equalityTriangleFactBy2SidesAndAngleBetweenWithCommonSideTest() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        LineSegment BC = new LineSegment(B, C);
        LineSegment AC = new LineSegment(A, C);
        Angle ACB = new Angle(new LinkedList(Arrays.asList(AC, BC)));
        Vertex A1 = new Vertex();
        Vertex B1 = new Vertex();
        Vertex C1 = new Vertex();
        LineSegment AC1 = new LineSegment(A1, C1);
        Angle ACB1 = new Angle(new LinkedList(Arrays.asList(AC1, BC)));

        HashSet<Fact> facts = new HashSet<>();
        facts.add(new EqualityFact(AC, AC1));
        facts.add(new EqualityFact(BC, BC));
        facts.add(new EqualityFact(ACB, ACB1));
        Model model = new Model(facts);
        ExpertSystem.ForwardPass(model);

        HashSet<Fact> checkFacts = new HashSet<>(facts);
        checkFacts.add(new EqualityFact(
                new Triangle(new LineSegment(A, B), BC, AC),
                new Triangle(new LineSegment(A1, B1), BC, AC1)));
        Model checkModel = new Model(checkFacts);
        assertTrue(checkModel.isEquivalentTo(model));
    }
}
