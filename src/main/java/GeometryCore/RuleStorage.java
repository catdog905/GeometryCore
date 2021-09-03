package GeometryCore;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;

import GeometryCore.Facts.BelongFact;
import GeometryCore.Facts.ExistFact;
import GeometryCore.Facts.Fact;
import GeometryCore.GeometryObjects.LineSegment;
import GeometryCore.GeometryObjects.Triangle;
import GeometryCore.GeometryObjects.Vertex;

public class RuleStorage {
    public HashSet<Rule> rules = new HashSet<>();
    private static RuleStorage singleton = null;

    private RuleStorage() {
        rules.add(triangleCreateRule());
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
}
