package GeometryCore;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;

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
import GeometryCore.GeometryObjects.Polynomial;
import GeometryCore.GeometryObjects.RaisedInThePower;
import GeometryCore.GeometryObjects.Triangle;
import GeometryCore.GeometryObjects.NumberValue;
import GeometryCore.GeometryObjects.Vertex;

public class RuleStorage {
    public HashSet<Rule> rules = new HashSet<>();
    private static RuleStorage singleton = null;

    private RuleStorage() {
        rules.add(triangle());
        rules.add(rightTriangle());
        rules.add(pythagoreanTheorem());
        rules.add(equalityTriangleFactBy2SidesAndAngleBetween());
        rules.add(equalityTriangleFactBy2AnglesAndLineBetween());
        rules.add(equalityTriangleFactBy3Sides());
        rules.add(belongingOfInnerSegmentsToOuter());
        rules.add(perpendicularityOfRadiusAndTouchedLineSegment());
        rules.add(touchedLineSegmentOfInscribedCircleInTriangle());
    }

    public static RuleStorage getInstance() {
        if (singleton == null) {
            singleton = new RuleStorage();
        }
        return singleton;
    }

    private Rule triangle() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment BC = new LineSegment(B, C);
        LineSegment AC = new LineSegment(A, C);

        LinkedList<Fact> facts = new LinkedList<>();
        facts.add(new BelongFact(A, AB));
        facts.add(new BelongFact(B, AB));
        facts.add(new BelongFact(A, AC));
        facts.add(new BelongFact(B, BC));
        facts.add(new BelongFact(C, AC));
        facts.add(new BelongFact(C, BC));

        LinkedList<Fact> consequences = new LinkedList<>();
        consequences.add(new ExistFact(new Triangle(new HashSet<>(Arrays.asList(AB, AC, BC)))));
        return new Rule(facts, consequences);
    }

    private Rule rightTriangle() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment BC = new LineSegment(B, C);
        LineSegment AC = new LineSegment(A, C);
        Angle ACB = new Angle(new LinkedList(Arrays.asList(AC, BC)));
        Triangle triangle = new Triangle(new HashSet<>(Arrays.asList(AB, AC, BC)));

        LinkedList<Fact> facts = new LinkedList<>();
        facts.add(new ExistFact(triangle));
        facts.add(new EqualityFact(ACB, Degree.createNumber(90)));

        LinkedList<Fact> consequences = new LinkedList<>();
        consequences.add(new RightAngledFact(triangle));
        return new Rule(facts, consequences);
    }

    private Rule pythagoreanTheorem() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment BC = new LineSegment(B, C);
        LineSegment AC = new LineSegment(A, C);
        Angle ACB = new Angle(new LinkedList(Arrays.asList(AC, BC)));
        Triangle triangle = new Triangle(new HashSet<>(Arrays.asList(AB, AC, BC)));

        LinkedList<Fact> facts = new LinkedList<>();
        facts.add(new RightAngledFact(triangle));
        facts.add(new EqualityFact(ACB, Degree.createNumber(90)));

        LinkedList<Fact> consequences = new LinkedList<>();
        consequences.add(new EqualityFact(
                new RaisedInThePower(new NumberValue(AB, null), GeometryNumber.createNumber(2)),
                new Polynomial(
                        new RaisedInThePower(new NumberValue(AC, null), GeometryNumber.createNumber(2)),
                        new RaisedInThePower(new NumberValue(BC, null), GeometryNumber.createNumber(2))
                )));
        return new Rule(facts, consequences);
    }

    private Rule equalityTriangleFactBy2SidesAndAngleBetween() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment BC = new LineSegment(B, C);
        LineSegment AC = new LineSegment(A, C);
        Angle ACB = new Angle(new LinkedList(Arrays.asList(AC, BC)));
        Triangle triangle = new Triangle(new HashSet<>(Arrays.asList(AB, AC, BC)));
        Vertex A1 = new Vertex();
        Vertex B1 = new Vertex();
        Vertex C1 = new Vertex();
        LineSegment AB1 = new LineSegment(A1, B1);
        LineSegment BC1 = new LineSegment(B1, C1);
        LineSegment AC1 = new LineSegment(A1, C1);
        Angle ACB1 = new Angle(new LinkedList(Arrays.asList(AC1, BC1)));
        Triangle triangle1 = new Triangle(new HashSet<>(Arrays.asList(AB1, AC1, BC1)));

        LinkedList<Fact> facts = new LinkedList<>();
        facts.add(new EqualityFact(AC, AC1));
        facts.add(new EqualityFact(BC, BC1));
        facts.add(new EqualityFact(ACB, ACB1));

        LinkedList<Fact> consequences = new LinkedList<>();
        consequences.add(new EqualityFact(triangle, triangle1));
        return new Rule(facts, consequences);
    }

    private Rule equalityTriangleFactBy2AnglesAndLineBetween() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment BC = new LineSegment(B, C);
        LineSegment AC = new LineSegment(A, C);
        Angle ACB = new Angle(new LinkedList(Arrays.asList(AC, BC)));
        Angle BAC = new Angle(new LinkedList(Arrays.asList(AB, AC)));
        Triangle triangle = new Triangle(new HashSet<>(Arrays.asList(AB, AC, BC)));
        Vertex A1 = new Vertex();
        Vertex B1 = new Vertex();
        Vertex C1 = new Vertex();
        LineSegment AB1 = new LineSegment(A1, B1);
        LineSegment BC1 = new LineSegment(B1, C1);
        LineSegment AC1 = new LineSegment(A1, C1);
        Angle ACB1 = new Angle(new LinkedList(Arrays.asList(AC1, BC1)));
        Angle BAC1 = new Angle(new LinkedList(Arrays.asList(AB1, AC1)));
        Triangle triangle1 = new Triangle(new HashSet<>(Arrays.asList(AB1, AC1, BC1)));

        LinkedList<Fact> facts = new LinkedList<>();
        facts.add(new EqualityFact(AC, AC1));
        facts.add(new EqualityFact(BAC, BAC1));
        facts.add(new EqualityFact(ACB, ACB1));

        LinkedList<Fact> consequences = new LinkedList<>();
        consequences.add(new EqualityFact(triangle, triangle1));
        return new Rule(facts, consequences);
    }

    private Rule equalityTriangleFactBy3Sides() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment BC = new LineSegment(B, C);
        LineSegment AC = new LineSegment(A, C);
        Triangle triangle = new Triangle(new HashSet<>(Arrays.asList(AB, AC, BC)));
        Vertex A1 = new Vertex();
        Vertex B1 = new Vertex();
        Vertex C1 = new Vertex();
        LineSegment AB1 = new LineSegment(A1, B1);
        LineSegment BC1 = new LineSegment(B1, C1);
        LineSegment AC1 = new LineSegment(A1, C1);
        Triangle triangle1 = new Triangle(new HashSet<>(Arrays.asList(AB1, AC1, BC1)));

        LinkedList<Fact> facts = new LinkedList<>();
        facts.add(new EqualityFact(AC, AC1));
        facts.add(new EqualityFact(BC, BC1));
        facts.add(new EqualityFact(AB, AB1));

        LinkedList<Fact> consequences = new LinkedList<>();
        consequences.add(new EqualityFact(triangle, triangle1));
        return new Rule(facts, consequences);
    }

    private Rule belongingOfInnerSegmentsToOuter() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment AC = new LineSegment(A, C);
        LineSegment CB = new LineSegment(C, B);

        LinkedList<Fact> facts = new LinkedList<>();
        facts.add(new BelongFact(C, AB));

        LinkedList<Fact> consequences = new LinkedList<>();
        consequences.add(new BelongFact(AC, AB));
        consequences.add(new BelongFact(CB, AB));
        return new Rule(facts, consequences);
    }

    private Rule perpendicularityOfRadiusAndTouchedLineSegment() {
        LineSegment a = new LineSegment();
        Vertex center = new Vertex();
        Circle circle = new Circle(center);
        Vertex touchPlace = new Vertex();

        LinkedList<Fact> facts = new LinkedList<>();
        facts.add(new TouchedFact(touchPlace, a, circle));

        LinkedList<Fact> consequences = new LinkedList<>();
        LineSegment radius = new LineSegment(center, touchPlace);
        consequences.add(new ExistFact(radius));
        consequences.add(new BelongFact(center, radius));
        consequences.add(new BelongFact(touchPlace, radius));
        consequences.add(new PerpendicularityFact(a, radius));
        return new Rule(facts, consequences);
    }

    private Rule touchedLineSegmentOfInscribedCircleInTriangle() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment BC = new LineSegment(B, C);
        LineSegment AC = new LineSegment(A, C);
        Triangle triangle = new Triangle(new HashSet<>(Arrays.asList(AB, AC, BC)));
        Circle circle = new Circle();

        LinkedList<Fact> facts = new LinkedList<>();
        facts.add(new InscribedFact(circle, triangle));

        Vertex P = new Vertex();
        Vertex M = new Vertex();
        Vertex N = new Vertex();
        LinkedList<Fact> consequences = new LinkedList<>();
        consequences.add(new BelongFact(P, AB));
        consequences.add(new BelongFact(M, BC));
        consequences.add(new BelongFact(N, AC));
        consequences.add(new TouchedFact(P, circle, AB));
        consequences.add(new TouchedFact(M, circle, BC));
        consequences.add(new TouchedFact(N, circle, AC));

        return new Rule(facts, consequences);
    }
}
