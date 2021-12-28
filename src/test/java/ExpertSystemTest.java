import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.stream.Collectors;

import core.ExpertSystem;
import core.Rule;
import core.model.facts.BelongFact;
import core.model.facts.ExistFact;
import core.model.facts.Fact;
import core.model.facts.RightAngledFact;
import core.model.facts.equation.EqualityFact;
import core.model.Model;
import core.model.facts.objects.Angle;
import core.model.facts.objects.LineSegment;
import core.model.facts.objects.Triangle;
import core.model.facts.objects.Vertex;
import core.model.facts.objects.expression.monomials.Degree;
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
        facts.add(new BelongFact(A, AB));
        facts.add(new BelongFact(B, AB));
        facts.add(new BelongFact(A, AC));
        facts.add(new BelongFact(B, BC));
        facts.add(new BelongFact(C, AC));
        facts.add(new BelongFact(C, BC));
        Model model = new Model(facts);
        model = ExpertSystem.ForwardPass(model);
        RuleStorage.getInstance().rules.stream().filter(x ->((Rule)x).name.equals("pythagorean theorem") ).findFirst().get().outputBenchmarks();

        assertTrue(model.facts().stream().anyMatch(x -> x instanceof ExistFact &&
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
        model = ExpertSystem.ForwardPass(model);

        assertEquals(1, model.facts().stream().filter(x -> x instanceof ExistFact &&
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
        facts.add(new EqualityFact(ACB, Degree.get(90)));
        // facts.add(new EqualityFact(AB, new NumberEnveloper(4)));
        // facts.add(new EqualityFact(AB, new NumberEnveloper(3)));
        Model model = new Model(facts);
        model = ExpertSystem.ForwardPass(model);

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
        model = ExpertSystem.ForwardPass(model);



        //TODO There is test check from another PR
    }
}
