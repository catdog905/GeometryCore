package core.rules.storage;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;

import core.Rule;
import core.facts.EqualityFact;
import core.facts.ExistFact;
import core.facts.Fact;
import core.objects.Angle;
import core.objects.LineSegment;
import core.objects.Triangle;
import core.objects.Vertex;
import core.objects.numbers.Degree;

public class EqualityTriangleRules {
    public HashSet<Rule> get() {
        HashSet<Rule> rules = new HashSet<>();
        rules.add(equalityTriangleFactBy2SidesAndAngleBetween());
        rules.add(equalityTriangleFactBy2SidesAndAngleBetweenWithCommonSide());
        rules.add(equalityTriangleFactBy2SidesAndAngleBetweenWithCommonSideInv());
        rules.add(equalityTriangleFactBy2AnglesAndLineBetween());
        rules.add(equalityTriangleFactBy2AnglesAndLineBetweenWithCommonSide());
        rules.add(equalityTriangleFactBy2AnglesAndLineBetweenWithCommonSideInv());
        rules.add(equalityTriangleFactBy3Sides());
        rules.add(equalityTriangleFactBy3SidesWithCommonSide());
        rules.add(equalityTriangleFactBy3SidesWithCommonSideInv());
        rules.add(equalityTriangleFactByLegAndHypotenuse());
        rules.add(equalityTriangleFactByLegAndHypotenuseWithCommonLeg());
        rules.add(equalityTriangleFactByLegAndHypotenuseWithCommonLegInv());
        rules.add(equalityTriangleFactByLegAndHypotenuseWithCommonHypotenuse());
        rules.add(equalityTriangleFactByLegAndHypotenuseWithCommonHypotenuseInv());
        rules.add(equalityTriangleFactByHypotenuseAndAcuteLeg());
        rules.add(equalityTriangleFactByHypotenuseAndAcuteLegWithCommonLeg());
        rules.add(equalityTriangleFactByHypotenuseAndAcuteLegWithCommonLegInv());

        return rules;
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
        facts.add(new ExistFact(triangle));
        facts.add(new ExistFact(triangle1));
        facts.add(new EqualityFact(AC, AC1));
        facts.add(new EqualityFact(BC, BC1));
        facts.add(new EqualityFact(ACB, ACB1));

        LinkedList<Fact> consequences = new LinkedList<>();
        consequences.add(new EqualityFact(triangle, triangle1));
        return new Rule(facts, consequences);
    }
    private Rule equalityTriangleFactBy2SidesAndAngleBetweenWithCommonSide() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        Vertex D = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment BC = new LineSegment(B, C);
        LineSegment AC = new LineSegment(A, C);
        LineSegment AD = new LineSegment(A, D);
        LineSegment CD = new LineSegment(C, D);
        Angle DAC = new Angle(AD, AC);
        Angle CAB = new Angle(AC, AB);
        Triangle triangle = new Triangle(AB, BC, AC);
        Triangle triangle1 = new Triangle(AC, AD, CD);

        LinkedList<Fact> facts = new LinkedList<>();

        facts.add(new ExistFact(triangle));
        facts.add(new ExistFact(triangle1));
        facts.add(new EqualityFact(AB, AD));
        facts.add(new EqualityFact(DAC, CAB));

        LinkedList<Fact> consequences = new LinkedList<>();
        consequences.add(new EqualityFact(triangle, triangle1));

        return new Rule(facts, consequences);
    }
    private Rule equalityTriangleFactBy2SidesAndAngleBetweenWithCommonSideInv() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        Vertex D = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment BC = new LineSegment(B, C);
        LineSegment AC = new LineSegment(A, C);
        LineSegment AD = new LineSegment(A, D);
        LineSegment CD = new LineSegment(C, D);
        Angle ACD = new Angle(AC, CD);
        Angle CAB = new Angle(AC, AB);
        Triangle triangle = new Triangle(AB, BC, AC);
        Triangle triangle1 = new Triangle(AC, AD, CD);

        LinkedList<Fact> facts = new LinkedList<>();
        facts.add(new ExistFact(triangle));
        facts.add(new ExistFact(triangle1));
        facts.add(new EqualityFact(AB, CD));
        facts.add(new EqualityFact(ACD, CAB));

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
        facts.add(new ExistFact(triangle));
        facts.add(new ExistFact(triangle1));
        facts.add(new EqualityFact(AC, AC1));
        facts.add(new EqualityFact(BAC, BAC1));
        facts.add(new EqualityFact(ACB, ACB1));

        LinkedList<Fact> consequences = new LinkedList<>();
        consequences.add(new EqualityFact(triangle, triangle1));
        return new Rule(facts, consequences);
    }
    private Rule equalityTriangleFactBy2AnglesAndLineBetweenWithCommonSide() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        Vertex D = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment BC = new LineSegment(B, C);
        LineSegment AD = new LineSegment(A, D);
        LineSegment CD = new LineSegment(C, D);
        LineSegment BD = new LineSegment(B, D);
        Angle ABD = new Angle(AB, BD);
        Angle CBD = new Angle(BC, BD);
        Angle ADB = new Angle(AD, BD);
        Angle CDB = new Angle(CD, BD);
        Triangle triangle = new Triangle(AB, BD, AD);
        Triangle triangle1 = new Triangle(BC, CD, BD);
        LinkedList<Fact> facts = new LinkedList<>();
        facts.add(new ExistFact(triangle));
        facts.add(new ExistFact(triangle1));
        facts.add(new EqualityFact(ABD, CBD));
        facts.add(new EqualityFact(ADB, CDB));

        LinkedList<Fact> consequences = new LinkedList<>();
        consequences.add(new EqualityFact(triangle, triangle1));

        return new Rule(facts, consequences);
    }
    private Rule equalityTriangleFactBy2AnglesAndLineBetweenWithCommonSideInv() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        Vertex D = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment BC = new LineSegment(B, C);
        LineSegment AD = new LineSegment(A, D);
        LineSegment CD = new LineSegment(C, D);
        LineSegment BD = new LineSegment(B, D);
        Angle ABD = new Angle(AB, BD);
        Angle CBD = new Angle(BC, BD);
        Angle ADB = new Angle(AD, BD);
        Angle CDB = new Angle(CD, BD);
        Triangle triangle = new Triangle(AB, BD, AD);
        Triangle triangle1 = new Triangle(BC, CD, BD);
        LinkedList<Fact> facts = new LinkedList<>();
        facts.add(new ExistFact(triangle));
        facts.add(new ExistFact(triangle1));
        facts.add(new EqualityFact(ABD, CDB));
        facts.add(new EqualityFact(ADB, CBD));

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
        facts.add(new ExistFact(triangle));
        facts.add(new ExistFact(triangle1));
        facts.add(new EqualityFact(AC, AC1));
        facts.add(new EqualityFact(BC, BC1));
        facts.add(new EqualityFact(AB, AB1));

        LinkedList<Fact> consequences = new LinkedList<>();
        consequences.add(new EqualityFact(triangle, triangle1));
        return new Rule(facts, consequences);
    }
    private Rule equalityTriangleFactBy3SidesWithCommonSide() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        Vertex D = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment BC = new LineSegment(B, C);
        LineSegment AD = new LineSegment(A, D);
        LineSegment CD = new LineSegment(C, D);
        LineSegment BD = new LineSegment(B, D);
        Triangle triangle = new Triangle(AB, BD, AD);
        Triangle triangle1 = new Triangle(BC, CD, BD);

        LinkedList<Fact> facts = new LinkedList<>();
        facts.add(new ExistFact(triangle));
        facts.add(new ExistFact(triangle1));
        facts.add(new EqualityFact(AB, BC));
        facts.add(new EqualityFact(AD, CD));

        LinkedList<Fact> consequences = new LinkedList<>();
        consequences.add(new EqualityFact(triangle, triangle1));
        return new Rule(facts, consequences);
    }
    private Rule equalityTriangleFactBy3SidesWithCommonSideInv() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        Vertex D = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment BC = new LineSegment(B, C);
        LineSegment AD = new LineSegment(A, D);
        LineSegment CD = new LineSegment(C, D);
        LineSegment BD = new LineSegment(B, D);
        Triangle triangle = new Triangle(AB, BD, AD);
        Triangle triangle1 = new Triangle(BC, CD, BD);

        LinkedList<Fact> facts = new LinkedList<>();
        facts.add(new ExistFact(triangle));
        facts.add(new ExistFact(triangle1));
        facts.add(new EqualityFact(AB, CD));
        facts.add(new EqualityFact(AD, BC));

        LinkedList<Fact> consequences = new LinkedList<>();
        consequences.add(new EqualityFact(triangle, triangle1));
        return new Rule(facts, consequences);
    }
    private Rule equalityTriangleFactByLegAndHypotenuse() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment BC = new LineSegment(B, C);
        LineSegment AC = new LineSegment(A, C);
        Angle ACB = new Angle(AC, BC);
        Triangle triangle = new Triangle(AB, BC, AC);
        Vertex A1 = new Vertex();
        Vertex B1 = new Vertex();
        Vertex C1 = new Vertex();
        LineSegment A1B1 = new LineSegment(A1, B1);
        LineSegment B1C1 = new LineSegment(B1, C1);
        LineSegment A1C1 = new LineSegment(A1, C1);
        Angle A1C1B1 = new Angle(A1C1, B1C1);
        Triangle triangle1 = new Triangle(A1B1, B1C1, A1C1);

        LinkedList<Fact> facts = new LinkedList<>();
        facts.add(new ExistFact(triangle));
        facts.add(new ExistFact(triangle1));
        facts.add(new EqualityFact(AB, A1B1));
        facts.add(new EqualityFact(BC, B1C1));
        facts.add(new EqualityFact(ACB, Degree.createNumber(90)));
        facts.add(new EqualityFact(A1C1B1, Degree.createNumber(90)));

        LinkedList<Fact> consequences = new LinkedList<>();
        consequences.add(new EqualityFact(triangle, triangle1));
        return new Rule(facts, consequences);
    }
    private Rule equalityTriangleFactByLegAndHypotenuseWithCommonLeg() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        Vertex D = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment BC = new LineSegment(B, C);
        LineSegment AD = new LineSegment(A, D);
        LineSegment CD = new LineSegment(C, D);
        LineSegment BD = new LineSegment(B, D);
        Angle ADB = new Angle(AD, BD);
        Angle CDB = new Angle(CD ,BD);
        Triangle triangle = new Triangle(AB, BD, AD);
        Triangle triangle1 = new Triangle(BD, BC, CD);

        LinkedList<Fact> facts = new LinkedList<>();
        facts.add(new ExistFact(triangle));
        facts.add(new ExistFact(triangle1));
        facts.add(new EqualityFact(AB, BC));
        facts.add(new EqualityFact(ADB, Degree.createNumber(90)));
        facts.add(new EqualityFact(CDB, Degree.createNumber(90)));

        LinkedList<Fact> consequences = new LinkedList<>();
        consequences.add(new EqualityFact(triangle, triangle1));
        return new Rule(facts, consequences);
    }
    private Rule equalityTriangleFactByLegAndHypotenuseWithCommonLegInv() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        Vertex D = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment BC = new LineSegment(B, C);
        LineSegment AD = new LineSegment(A, D);
        LineSegment CD = new LineSegment(C, D);
        LineSegment BD = new LineSegment(B, D);
        Angle ADB = new Angle(AD, BD);
        Angle CBD = new Angle(BC, BD);
        Triangle triangle = new Triangle(AB, BD, AD);
        Triangle triangle1 = new Triangle(BD, BC, CD);

        LinkedList<Fact> facts = new LinkedList<>();
        facts.add(new ExistFact(triangle));
        facts.add(new ExistFact(triangle1));
        facts.add(new EqualityFact(AB, CD));
        facts.add(new EqualityFact(ADB, Degree.createNumber(90)));
        facts.add(new EqualityFact(CBD, Degree.createNumber(90)));

        LinkedList<Fact> consequences = new LinkedList<>();
        consequences.add(new EqualityFact(triangle, triangle1));
        return new Rule(facts, consequences);
    }
    private Rule equalityTriangleFactByLegAndHypotenuseWithCommonHypotenuse() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        Vertex D = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment BC = new LineSegment(B, C);
        LineSegment AD = new LineSegment(A, D);
        LineSegment CD = new LineSegment(C, D);
        LineSegment BD = new LineSegment(B, D);
        Angle BAD = new Angle(AB, AD);
        Angle BCD = new Angle(BC, CD);
        Triangle triangle = new Triangle(AB, BD, AD);
        Triangle triangle1 = new Triangle(BC, BD, CD);

        LinkedList<Fact> facts = new LinkedList<>();
        facts.add(new ExistFact(triangle));
        facts.add(new ExistFact(triangle1));
        facts.add(new EqualityFact(AB, BC));
        facts.add(new EqualityFact(BAD, Degree.createNumber(90)));
        facts.add(new EqualityFact(BCD, Degree.createNumber(90)));

        LinkedList<Fact> consequences = new LinkedList<>();
        consequences.add(new EqualityFact(triangle, triangle1));
        return new Rule(facts, consequences);
    }
    private Rule equalityTriangleFactByLegAndHypotenuseWithCommonHypotenuseInv() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        Vertex D = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment BC = new LineSegment(B, C);
        LineSegment AD = new LineSegment(A, D);
        LineSegment CD = new LineSegment(C, D);
        LineSegment BD = new LineSegment(B, D);
        Angle BAD = new Angle(AB, AD);
        Angle BCD = new Angle(BC, CD);
        Triangle triangle = new Triangle(AB, BD, AD);
        Triangle triangle1 = new Triangle(BC, BD, CD);

        LinkedList<Fact> facts = new LinkedList<>();
        facts.add(new ExistFact(triangle));
        facts.add(new ExistFact(triangle1));
        facts.add(new EqualityFact(AB, CD));
        facts.add(new EqualityFact(BAD, Degree.createNumber(90)));
        facts.add(new EqualityFact(BCD, Degree.createNumber(90)));

        LinkedList<Fact> consequences = new LinkedList<>();
        consequences.add(new EqualityFact(triangle, triangle1));
        return new Rule(facts, consequences);
    }
    private Rule equalityTriangleFactByHypotenuseAndAcuteLeg() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment BC = new LineSegment(B, C);
        LineSegment AC = new LineSegment(A, C);
        Angle ACB = new Angle(AC, BC);
        Angle ABC = new Angle(AB, BC);
        Triangle triangle = new Triangle(AB, BC, AC);
        Vertex A1 = new Vertex();
        Vertex B1 = new Vertex();
        Vertex C1 = new Vertex();
        LineSegment A1B1 = new LineSegment(A1, B1);
        LineSegment B1C1 = new LineSegment(B1, C1);
        LineSegment A1C1 = new LineSegment(A1, C1);
        Angle A1C1B1 = new Angle(A1C1, B1C1);
        Angle A1B1C1 = new Angle(A1B1, B1C1);
        Triangle triangle1 = new Triangle(A1B1, B1C1, A1C1);

        LinkedList<Fact> facts = new LinkedList<>();
        facts.add(new ExistFact(triangle));
        facts.add(new ExistFact(triangle1));
        facts.add(new EqualityFact(AB, A1B1));
        facts.add(new EqualityFact(ABC, A1B1C1));
        facts.add(new EqualityFact(ACB, Degree.createNumber(90)));
        facts.add(new EqualityFact(A1C1B1, Degree.createNumber(90)));

        LinkedList<Fact> consequences = new LinkedList<>();
        consequences.add(new EqualityFact(triangle, triangle1));
        return new Rule(facts, consequences);
    }
    private Rule equalityTriangleFactByHypotenuseAndAcuteLegWithCommonLeg() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        Vertex D = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment BC = new LineSegment(B, C);
        LineSegment AD = new LineSegment(A, D);
        LineSegment CD = new LineSegment(C, D);
        LineSegment BD = new LineSegment(B, D);
        Angle BAD = new Angle(AB, AD);
        Angle BCD = new Angle(BC, CD);
        Angle ADB = new Angle(AD, BD);
        Angle CDB = new Angle(CD, BD);
        Triangle triangle = new Triangle(AB, AD, BD);
        Triangle triangle1 = new Triangle(BD, BC, CD);

        LinkedList<Fact> facts = new LinkedList<>();
        facts.add(new ExistFact(triangle));
        facts.add(new ExistFact(triangle1));
        facts.add(new EqualityFact(ADB, CDB));
        facts.add(new EqualityFact(BAD, Degree.createNumber(90)));
        facts.add(new EqualityFact(BCD, Degree.createNumber(90)));

        LinkedList<Fact> consequences = new LinkedList<>();
        consequences.add(new EqualityFact(triangle, triangle1));
        return new Rule(facts, consequences);
    }
    private Rule equalityTriangleFactByHypotenuseAndAcuteLegWithCommonLegInv() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        Vertex D = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment BC = new LineSegment(B, C);
        LineSegment AD = new LineSegment(A, D);
        LineSegment CD = new LineSegment(C, D);
        LineSegment BD = new LineSegment(B, D);
        Angle BAD = new Angle(AB, AD);
        Angle BCD = new Angle(BC, CD);
        Angle ADB = new Angle(AD, BD);
        Angle CBD = new Angle(BC, CD);
        Triangle triangle = new Triangle(AB, AD, BD);
        Triangle triangle1 = new Triangle(BD, BC, CD);

        LinkedList<Fact> facts = new LinkedList<>();
        facts.add(new ExistFact(triangle));
        facts.add(new ExistFact(triangle1));
        facts.add(new EqualityFact(ADB, CBD));
        facts.add(new EqualityFact(BAD, Degree.createNumber(90)));
        facts.add(new EqualityFact(BCD, Degree.createNumber(90)));

        LinkedList<Fact> consequences = new LinkedList<>();
        consequences.add(new EqualityFact(triangle, triangle1));
        return new Rule(facts, consequences);
    }
}
