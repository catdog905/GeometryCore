import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.stream.Collectors;

import GeometryCore.ExpertSystem;
import GeometryCore.Facts.BelongFact;
import GeometryCore.Facts.EqualityFact;
import GeometryCore.Facts.ExistFact;
import GeometryCore.Facts.Fact;
import GeometryCore.Facts.InscribedFact;
import GeometryCore.Facts.RightAngledFact;
import GeometryCore.Facts.TouchedFact;
import GeometryCore.GeometryObjects.Angle;
import GeometryCore.GeometryObjects.Circle;
import GeometryCore.GeometryObjects.Degree;
import GeometryCore.GeometryObjects.GeometryNumber;
import GeometryCore.GeometryObjects.GeometryObject;
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

        assertTrue(model.facts.stream().anyMatch(x -> x instanceof ExistFact &&
                ((Triangle)((ExistFact)x).object).lineSegments.contains(AB) &&
                ((Triangle)((ExistFact)x).object).lineSegments.contains(BC) &&
                ((Triangle)((ExistFact)x).object).lineSegments.contains(AC)));
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

        assertEquals(1, model.facts.stream().filter(x -> x instanceof ExistFact &&
                ((Triangle) ((ExistFact) x).object).lineSegments.contains(AB) &&
                ((Triangle) ((ExistFact) x).object).lineSegments.contains(BC) &&
                ((Triangle) ((ExistFact) x).object).lineSegments.contains(AC)).collect(Collectors.toList()).size());
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
        // facts.add(new EqualityFact(AB, new NumberEnveloper(4)));
        // facts.add(new EqualityFact(AB, new NumberEnveloper(3)));
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
        Angle ACB = new Angle(new LinkedList(Arrays.asList(AC,  BC)));
        HashSet<Fact> facts = new HashSet<>();
        facts.add(new RightAngledFact(new Triangle(new HashSet<>(Arrays.asList(AB, AC, BC)))));
        facts.add(new EqualityFact(ACB, Degree.createNumber(90)));
        //facts.add(new EqualityFact(AB, new NumberEnveloper(4)));
        //facts.add(new EqualityFact(AB, new NumberEnveloper(3)));
        Model model = new Model(facts);
        ExpertSystem.ForwardPass(model);



        int leftCount = 0;
        int rightCount = 0;
        for (Fact fact : model.facts) {
            if (fact instanceof EqualityFact && ((EqualityFact)fact).left instanceof RaisedInThePower) {
                if (((EqualityFact) fact).left.getAllSubObjects()
                        .stream().filter(x -> x instanceof NumberValue).findAny().get().getAllSubObjects().contains(AB))
                    leftCount++;
                for (GeometryObject mono : (((EqualityFact) fact).right.getAllSubObjects())){
                    if (mono.getAllSubObjects()
                            .stream().filter(x -> x instanceof NumberValue).findAny().get().getAllSubObjects().contains(BC))
                        rightCount++;
                    if (mono.getAllSubObjects()
                            .stream().filter(x -> x instanceof NumberValue).findAny().get().getAllSubObjects().contains(AC))
                        rightCount++;
                }
            }
        }
        assertEquals(1, leftCount);
        assertEquals(2, rightCount);
    }

    @Test
    public void reversePythagoreanTheoremTest() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment BC = new LineSegment(B, C);
        LineSegment AC = new LineSegment(A, C);
        HashSet<Fact> facts = new HashSet<>();
        facts.add(new EqualityFact(
                new RaisedInThePower(new NumberValue(AB, null), GeometryNumber.createNumber(2)),
                new Polynomial(
                        new RaisedInThePower(new NumberValue(AC, null), GeometryNumber.createNumber(2)),
                        new RaisedInThePower(new NumberValue(BC, null), GeometryNumber.createNumber(2))
                )));

        Model model = new Model(facts);
        ExpertSystem.ForwardPass(model);
        assertTrue(model.facts.stream().anyMatch(x -> x instanceof RightAngledFact));
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
        assertTrue(model.facts.stream().anyMatch(x -> x instanceof RightAngledFact));
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

        int counter = 0;
        for (Fact fact : model.facts) {
            if (fact instanceof BelongFact) {
                Boolean check = false;
                for (GeometryObject obj : fact.getAllSubObjects()) {
                    if (obj.getAllSubObjects().containsAll(Arrays.asList(A, C)) ||
                            obj.getAllSubObjects().containsAll(Arrays.asList(C, B)))
                        check = true;
                }
                if (check && fact.getAllSubObjects().contains(AB))
                    counter += 1;

            }
        }
        assertEquals(2, counter);
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

        for (Fact fact : model.facts) {
            if (fact instanceof BelongFact) {
                if (fact.getAllSubObjects().contains(AB)) {
                    int counter = 0;
                    for (GeometryObject obj : fact.getAllSubObjects()) {
                        if (obj.getAllSubObjects().containsAll(Arrays.asList(A, M)) ||
                                obj.getAllSubObjects().containsAll(Arrays.asList(M, B)))
                            counter += 1;
                    }
                    assertEquals(2, counter);
                }
                if (fact.getAllSubObjects().contains(AC)) {
                    int counter = 0;
                    for (GeometryObject obj : fact.getAllSubObjects()) {
                        if (obj.getAllSubObjects().containsAll(Arrays.asList(A, N)) ||
                                obj.getAllSubObjects().containsAll(Arrays.asList(N, C)))
                            counter += 1;
                    }
                    assertEquals(2, counter);
                }
                if (fact.getAllSubObjects().contains(BC)) {
                    int counter = 0;
                    for (GeometryObject obj : fact.getAllSubObjects()) {
                        if (obj.getAllSubObjects().containsAll(Arrays.asList(C, P)) ||
                                obj.getAllSubObjects().containsAll(Arrays.asList(P, B)))
                            counter += 1;
                    }
                    assertEquals(2, counter);
                }

            }
        }
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
        assertTrue(model.facts.stream().anyMatch(x -> x instanceof RightAngledFact));
    }
}
