package core.rules.storage;

import java.util.HashSet;
import java.util.LinkedList;

import core.model.facts.BelongFact;
import core.model.facts.Fact;
import core.model.facts.IntersectionFact;
import core.model.facts.objects.LineSegment;
import core.model.facts.objects.Vertex;
import core.rule.Rule;

public class SegmentsIntersectionRules {
    public HashSet<Rule> get() {
        HashSet<Rule> rules = new HashSet<>();
        rules.add(findIntersectionOfTwoSegments());
        rules.add(findIntersectionOfSegmentAndRay());
        rules.add(findIntersectionOfTwoRays());

        return rules;
    }

    private Rule findIntersectionOfTwoSegments() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        Vertex D = new Vertex();
        Vertex O = new Vertex();
        LineSegment AC = new LineSegment(A, C);
        LineSegment BD = new LineSegment(B, D);

        LinkedList<Fact> facts = new LinkedList<>();
        facts.add(new BelongFact(O, AC));
        facts.add(new BelongFact(O, BD));

        LinkedList<Fact> consequences = new LinkedList<>();
        consequences.add(new IntersectionFact(O, AC, BD));
        return new Rule(facts, consequences);
    }
    private Rule findIntersectionOfSegmentAndRay() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        Vertex D = new Vertex();
        LineSegment AC = new LineSegment(A, C);
        LineSegment BD = new LineSegment(B, D);

        LinkedList<Fact> facts = new LinkedList<>();
        facts.add(new BelongFact(B, AC));
        facts.add(new BelongFact(B, BD));

        LinkedList<Fact> consequences = new LinkedList<>();
        consequences.add(new IntersectionFact(B, AC, BD));
        return new Rule(facts, consequences);
    }
    private Rule findIntersectionOfTwoRays() {
        Vertex A = new Vertex();
        Vertex C = new Vertex();
        Vertex D = new Vertex();
        LineSegment AC = new LineSegment(A, C);
        LineSegment CD = new LineSegment(C, D);

        LinkedList<Fact> facts = new LinkedList<>();
        facts.add(new BelongFact(C, AC));
        facts.add(new BelongFact(C, CD));

        LinkedList<Fact> consequences = new LinkedList<>();
        consequences.add(new IntersectionFact(C, AC, CD));
        return new Rule(facts, consequences);
    }
}
