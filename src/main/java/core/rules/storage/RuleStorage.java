package core.rules.storage;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;

import core.Rule;
import core.facts.BelongFact;
import core.facts.ExistFact;
import core.facts.Fact;
import core.facts.InscribedFact;
import core.facts.IntersectionFact;
import core.facts.PerpendicularityFact;
import core.facts.RightAngledFact;
import core.facts.TouchedFact;
import core.facts.equation.EqualityFact;
import core.objects.Angle;
import core.objects.Circle;
import core.objects.LineSegment;
import core.objects.Triangle;
import core.objects.Vertex;
import core.objects.expression.Degree;
import core.objects.expression.GeometryNumber;
import core.objects.expression.Polynomial;
import core.objects.expression.RaisedInThePower;

public class RuleStorage {
    public HashSet<Rule> rules = new HashSet<>();
    private static RuleStorage singleton = null;

    private RuleStorage() {
        rules.addAll(new EqualityTriangleRules().get());
        rules.addAll(new SegmentsIntersectionRules().get());
        rules.add(findTriangle());
        rules.add(triangleTo6BelongingFact());
        rules.add(rightTriangle());
        rules.add(pythagoreanTheorem());
        rules.add(belongingOfInnerSegmentsToOuter());
        rules.add(perpendicularityOfRadiusAndTouchedLineSegment());
        rules.add(perpendicularLinesCreatesAngles90Degree());
        rules.add(touchedLineSegmentOfInscribedCircleInTriangle());
        rules.add(equalsDistanceFromCenterToTouchedIntersectsLines());
        rules.add(equalsDistanceFromCenterToTouchedNotIntersectsLines());
        rules.add(belongingPointsToExistLine());
        //rules.add(reversePythagoreanTheorem());
    }

    public static RuleStorage getInstance() {
        if (singleton == null) {
            singleton = new RuleStorage();
        }
        return singleton;
    }

    private Rule findTriangle() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        LineSegment AC = new LineSegment(A, C);
        LineSegment BC = new LineSegment(B, C);
        LineSegment AB = new LineSegment(A, B);

        LinkedList<Fact> facts = new LinkedList<>();
        facts.add(new IntersectionFact(A, AB, AC));
        facts.add(new IntersectionFact(B, AB, BC));
        facts.add(new IntersectionFact(C, AC, BC));

        LinkedList<Fact> consequences = new LinkedList<>();
        consequences.add(new ExistFact(new Triangle(AC, BC, AB)));
        return new Rule(facts, consequences);
    }

    private Rule triangleTo6BelongingFact() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment BC = new LineSegment(B, C);
        LineSegment AC = new LineSegment(A, C);

        LinkedList<Fact> facts = new LinkedList<>();
        facts.add(new ExistFact(new Triangle(AB, AC, BC)));

        LinkedList<Fact> consequences = new LinkedList<>();
        consequences.add(new BelongFact(A, AB));
        consequences.add(new BelongFact(B, AB));
        consequences.add(new BelongFact(A, AC));
        consequences.add(new BelongFact(B, BC));
        consequences.add(new BelongFact(C, AC));
        consequences.add(new BelongFact(C, BC));
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
        facts.add(new EqualityFact(ACB, Degree.get(90)));

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
        facts.add(new EqualityFact(ACB, Degree.get(90)));

        GeometryNumber num2 = GeometryNumber.get(2);
        LinkedList<Fact> consequences = new LinkedList<>();
        consequences.add(new EqualityFact(
                new RaisedInThePower(AB.getMonomial(), num2),
                new Polynomial(
                        new RaisedInThePower(AC.getMonomial(), num2),
                        new RaisedInThePower(BC.getMonomial(), num2)
                )));
        return new Rule(facts, consequences,"pythagorean theorem");
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
        consequences.add(new BelongFact(A, AC));
        consequences.add(new BelongFact(C, AC));
        consequences.add(new BelongFact(C, CB));
        consequences.add(new BelongFact(B, CB));
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

    private Rule perpendicularLinesCreatesAngles90Degree() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        Vertex D = new Vertex();
        LineSegment h = new LineSegment(C, D);
        LineSegment a = new LineSegment(A, B);
        Angle ACD = new Angle(new LineSegment(A, C), h);
        Angle BCD = new Angle(new LineSegment(B, C), h);

        LinkedList<Fact> facts = new LinkedList<>();
        facts.add(new PerpendicularityFact(a, h));
        facts.add(new BelongFact(C, a));

        LinkedList<Fact> consequences = new LinkedList<>();
        consequences.add(new EqualityFact(ACD, Degree.get(90)));
        consequences.add(new EqualityFact(BCD, Degree.get(90)));
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

    private Rule belongingPointsToExistLine() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LinkedList<Fact> facts = new LinkedList<>();
        facts.add(new ExistFact(AB));

        LinkedList<Fact> consequences = new LinkedList<>();
        consequences.add(new BelongFact(A, AB));
        consequences.add(new BelongFact(B, AB));

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
                new RaisedInThePower(AB.getMonomial(), GeometryNumber.get(2)),
                new Polynomial(
                        new RaisedInThePower(AC.getMonomial(), GeometryNumber.get(2)),
                        new RaisedInThePower(BC.getMonomial(), GeometryNumber.get(2))
                )));
        consequences.add(new RightAngledFact(triangle));
        consequences.add(new EqualityFact(ACB, Degree.get(90)));
        return new Rule(facts, consequences);
    }
}
