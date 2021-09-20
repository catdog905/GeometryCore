package core.rules.storage;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;

import core.Rule;
import core.facts.BelongFact;
import core.facts.EqualityFact;
import core.facts.ExistFact;
import core.facts.Fact;
import core.facts.InscribedFact;
import core.facts.PerpendicularityFact;
import core.facts.RightAngledFact;
import core.facts.TouchedFact;
import core.objects.Angle;
import core.objects.Circle;
import core.objects.LineSegment;
import core.objects.Polynomial;
import core.objects.RaisedInThePower;
import core.objects.Triangle;
import core.objects.Vertex;
import core.objects.numbers.Degree;
import core.objects.numbers.GeometryNumber;
import core.objects.numbers.NumberValue;

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
        rules.add(equalsDistanceFromCenterToTouchedIntersectsLines());
        rules.add(equalsDistanceFromCenterToTouchedNotIntersectsLines());
        //rules.add(reversePythagoreanTheorem());
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
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex center = new Vertex();
        LineSegment a = new LineSegment(A, B);
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
        Vertex center = new Vertex();
        Circle circle = new Circle(center);

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

    private Rule equalsDistanceFromCenterToTouchedIntersectsLines() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment BC = new LineSegment(B, C);
        Vertex center = new Vertex();
        Circle circle = new Circle(center);
        Vertex touchPlace1 = new Vertex();
        Vertex touchPlace2 = new Vertex();

        LinkedList<Fact> facts = new LinkedList<>();
        facts.add(new TouchedFact(touchPlace1, circle, AB));
        facts.add(new TouchedFact(touchPlace2, circle, BC));

        LinkedList<Fact> consequences = new LinkedList<>();
        consequences.add(new EqualityFact(
                new LineSegment(touchPlace1, center), new LineSegment(touchPlace2, center)));

        return new Rule(facts, consequences);
    }

    private Rule equalsDistanceFromCenterToTouchedNotIntersectsLines() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex D = new Vertex();
        Vertex C = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment DC = new LineSegment(D, C);
        Vertex center = new Vertex();
        Circle circle = new Circle(center);
        Vertex touchPlace1 = new Vertex();
        Vertex touchPlace2 = new Vertex();

        LinkedList<Fact> facts = new LinkedList<>();
        facts.add(new TouchedFact(touchPlace1, circle, AB));
        facts.add(new TouchedFact(touchPlace2, circle, DC));

        LinkedList<Fact> consequences = new LinkedList<>();
        consequences.add(new EqualityFact(
                new LineSegment(touchPlace1, center), new LineSegment(touchPlace2, center)));

        return new Rule(facts, consequences);
    }

    private Rule reversePythagoreanTheorem() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment BC = new LineSegment(B, C);
        LineSegment AC = new LineSegment(A, C);
        Angle ACB = new Angle(new LinkedList(Arrays.asList(AC, BC)));
        Triangle triangle = new Triangle(new HashSet<>(Arrays.asList(AB, AC, BC)));
        LinkedList<Fact> consequences = new LinkedList<>();
        LinkedList<Fact> facts = new LinkedList<>();
        facts.add(new EqualityFact(
                new RaisedInThePower(new NumberValue(AB, null), GeometryNumber.createNumber(2)),
                new Polynomial(
                        new RaisedInThePower(new NumberValue(AC, null), GeometryNumber.createNumber(2)),
                        new RaisedInThePower(new NumberValue(BC, null), GeometryNumber.createNumber(2))
                )));
        consequences.add(new RightAngledFact(triangle));
        consequences.add(new EqualityFact(ACB, Degree.createNumber(90)));
        return new Rule(facts, consequences);
    }
}
