package GeometryCore;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;

import GeometryCore.Facts.BelongFact;
import GeometryCore.Facts.EqualityFact;
import GeometryCore.Facts.ExistFact;
import GeometryCore.Facts.Fact;
import GeometryCore.Facts.RightAngledFact;
import GeometryCore.GeometryObjects.Angle;
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
        rules.add(triangleCreateRule());
        rules.add(rightTriangleCreateRule());
        rules.add(pythagoreanTheoremCreateRule());
    }

    public static RuleStorage getInstance() {
        if (singleton == null) {
            singleton = new RuleStorage();
        }
        return singleton;
    }

    private Rule triangleCreateRule() {
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

    private Rule rightTriangleCreateRule() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment BC = new LineSegment(B, C);
        LineSegment AC = new LineSegment(A, C);
        Angle ACB = new Angle(new LinkedList(Arrays.asList(A, B, C)));
        Triangle triangle = new Triangle(new HashSet<>(Arrays.asList(AB, AC, BC)));
        LinkedList<Fact> facts = new LinkedList<>();
        facts.add(new ExistFact(triangle));
        facts.add(new EqualityFact(ACB, Degree.createNumber(90)));

        LinkedList<Fact> consequences = new LinkedList<>();
        consequences.add(new RightAngledFact(triangle));
        return new Rule(facts, consequences);
    }

    private Rule pythagoreanTheoremCreateRule() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment BC = new LineSegment(B, C);
        LineSegment AC = new LineSegment(A, C);
        Angle ACB = new Angle(new LinkedList(Arrays.asList(A, B, C)));
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
}
