import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;

import core.ExpertSystem;
import core.facts.IntersectionFact;
import core.rule.Rule;
import core.facts.BelongFact;
import core.facts.ExistFact;
import core.facts.Fact;
import core.facts.InscribedFact;
import core.facts.PerpendicularityFact;
import core.facts.RightAngledFact;
import core.facts.TouchedFact;
import core.facts.equation.EqualityFact;
import core.model.Model;
import core.objects.Angle;
import core.objects.Circle;
import core.objects.LineSegment;
import core.objects.Triangle;
import core.objects.Vertex;
import core.objects.expression.Degree;
import core.objects.expression.GeometryNumber;
import core.objects.expression.Polynomial;
import core.objects.expression.RaisedInThePower;
import core.rules.storage.RuleStorage;

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
        facts.add(new ExistFact(A));
        facts.add(new ExistFact(B));
        facts.add(new ExistFact(C));
        facts.add(new ExistFact(AB));
        facts.add(new ExistFact(AC));
        facts.add(new ExistFact(BC));
        facts.add(new IntersectionFact(A, AC, AB));
        facts.add(new IntersectionFact(B, BC, AB));
        facts.add(new IntersectionFact(C, AC, BC));
        Model model = new Model(facts);
        model = ExpertSystem.ForwardPass(model);
        RuleStorage.getInstance().rules.stream().filter(x ->((Rule)x).name.equals("pythagorean theorem") ).findFirst().get().outputBenchmarks();


        HashSet<Fact> checkFacts = new HashSet<>(facts);
        checkFacts.add(new ExistFact(new Triangle(new HashSet<>(Arrays.asList(BC, AC, AB)))));
        Model checkModel = new Model(checkFacts);
        assertTrue(model.containsFactsEquivalentTo(checkModel));
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
        assertTrue(model.containsFactsEquivalentTo(checkModel));
        assertEquals(7, model.facts().size());
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
        facts.add(new EqualityFact(ACB, Degree.get(90)));
        // facts.add(new EqualityFact(AB, new NumberEnveloper(4)));
        // facts.add(new EqualityFact(AB, new NumberEnveloper(3)));
        Model model = new Model(facts);
        ExpertSystem.ForwardPass(model);

        assertTrue(model.facts().stream().anyMatch(x -> x instanceof RightAngledFact));
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
        facts.add(new EqualityFact(ACB, Degree.get(90)));
        //facts.add(new EqualityFact(AB, new NumberEnveloper(4)));
        //facts.add(new EqualityFact(AB, new NumberEnveloper(3)));
        Model model = new Model(facts);
        ExpertSystem.ForwardPass(model);

        HashSet<Fact> checkFacts = new HashSet<>(facts);
        checkFacts.add(new EqualityFact(
                new RaisedInThePower(AB.getMonomial(), GeometryNumber.get(2)),
                new Polynomial(
                        new RaisedInThePower(AC.getMonomial(), GeometryNumber.get(2)),
                        new RaisedInThePower(BC.getMonomial(), GeometryNumber.get(2))
                )));
        Model checkModel = new Model(checkFacts);
        assertTrue(model.containsFactsEquivalentTo(checkModel));
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
        facts.add(new ExistFact(new LineSegment(A, center)));
        facts.add(new ExistFact(new LineSegment(B, center)));
        facts.add(new ExistFact(new LineSegment(C, center)));

        Model model = new Model(facts);
        ExpertSystem.ForwardPass(model);
        assertTrue(model.facts().stream().anyMatch(x -> x instanceof RightAngledFact));
    }

    @Test
    public void equalTrianglesInBigTriangleWithInscribedCircleCenter() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        Vertex O = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment BC = new LineSegment(B, C);
        LineSegment AC = new LineSegment(A, C);
        LineSegment AO = new LineSegment(A, O);
        LineSegment BO = new LineSegment(B, O);
        LineSegment CO = new LineSegment(C, O);
        Vertex M = new Vertex();
        Vertex N = new Vertex();
        Vertex K = new Vertex();
        Triangle triangle = new Triangle(AB, BC, AC);

        HashSet<Fact> facts = new HashSet<>();
        facts.add(new ExistFact(triangle));
        facts.add(new EqualityFact(AO, BO));
        facts.add(new EqualityFact(AO, CO));
        facts.add(new EqualityFact(CO, BO));
        facts.add(new BelongFact(M, AB));
        facts.add(new BelongFact(N, BC));
        facts.add(new BelongFact(K, AC));
        facts.add(new PerpendicularityFact(new LineSegment(M, O), AB));
        facts.add(new PerpendicularityFact(new LineSegment(N, O), BC));
        facts.add(new PerpendicularityFact(new LineSegment(K, O), AC));

        Model model = new Model(facts);
        ExpertSystem.ForwardPass(model);
        assertTrue(model.facts().stream().anyMatch(x -> x instanceof RightAngledFact));
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
        assertTrue(model.containsFactsEquivalentTo(checkModel));

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
        assertTrue(model.containsFactsEquivalentTo(checkModel));
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
        assertTrue(model.containsFactsEquivalentTo(checkModel));
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
        assertTrue(model.containsFactsEquivalentTo(checkModel));
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
        assertTrue(model.containsFactsEquivalentTo(checkModel));
    }

    @Test
    public void equalityTriangleFactBy2SidesAndAngleBetweenWithCommonSideTest() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        Vertex D = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment AC = new LineSegment(A, C);
        LineSegment AD = new LineSegment(A, D);
        LineSegment BC = new LineSegment(B, C);
        LineSegment BD = new LineSegment(B, D);
        Angle BAC = new Angle(AB, AC);
        Angle BAD = new Angle(AB, AD);
        Triangle triangle = new Triangle(AC, BC, AB);
        Triangle triangle1 = new Triangle(AB, BD, AD);
        HashSet<Fact> facts = new HashSet<>();
        facts.add(new ExistFact(triangle));
        facts.add(new ExistFact(triangle1));
        facts.add(new EqualityFact(AC, AD));
        facts.add(new EqualityFact(BAC, BAD));
        Model model = new Model(facts);
        ExpertSystem.ForwardPass(model);

        HashSet<Fact> checkFacts = new HashSet<>(facts);
        checkFacts.add(new EqualityFact(triangle, triangle1));
        Model checkModel = new Model(checkFacts);
        assertTrue(model.containsFactsEquivalentTo(checkModel));
    }
}
