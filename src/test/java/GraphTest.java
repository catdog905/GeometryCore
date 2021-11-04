import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;

import core.facts.BelongFact;
import core.facts.ExistFact;
import core.facts.Fact;
import core.model.Model;
import core.objects.LineSegment;
import core.objects.Triangle;
import core.objects.Vertex;
import core.rule.Graph;
import core.rule.MaskGraph;
import core.rule.ModelGraph;
import core.rule.Rule;

public class GraphTest {
    @Test
    public void getAllIsomorphicSubGraphsIn4TrianglesTest() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        Vertex D = new Vertex();
        Vertex E = new Vertex();
        Vertex F = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment BC = new LineSegment(B, C);
        LineSegment CD = new LineSegment(C, D);
        LineSegment DE = new LineSegment(D, E);
        LineSegment FE = new LineSegment(F, E);
        LineSegment AF = new LineSegment(A, F);
        LineSegment BF= new LineSegment(B, F);
        LineSegment BD = new LineSegment(B, D);
        LineSegment DF = new LineSegment(D, F);
        HashSet<Fact> facts = new HashSet<>();
        facts.add(new BelongFact(A, AB));
        facts.add(new BelongFact(A, AF));
        facts.add(new BelongFact(C, BC));
        facts.add(new BelongFact(C, CD));
        facts.add(new BelongFact(E, FE));
        facts.add(new BelongFact(E, DE));
        facts.add(new BelongFact(B, AB));
        facts.add(new BelongFact(B, BF));
        facts.add(new BelongFact(B, BD));
        facts.add(new BelongFact(B, BC));
        facts.add(new BelongFact(D, CD));
        facts.add(new BelongFact(D, BD));
        facts.add(new BelongFact(D, DF));
        facts.add(new BelongFact(D, DE));
        facts.add(new BelongFact(F, AF));
        facts.add(new BelongFact(F, BF));
        facts.add(new BelongFact(F, DF));
        facts.add(new BelongFact(F, FE));
        Model model = new Model(facts);

        Vertex M = new Vertex();
        Vertex N = new Vertex();
        Vertex K = new Vertex();
        LineSegment MN = new LineSegment(M, N);
        LineSegment MK = new LineSegment(M, K);
        LineSegment NK = new LineSegment(N, K);
        HashSet<Fact> maskFacts = new HashSet<>();
        maskFacts.add(new BelongFact(M, MN));
        maskFacts.add(new BelongFact(M, MK));
        maskFacts.add(new BelongFact(N, MN));
        maskFacts.add(new BelongFact(N, NK));
        maskFacts.add(new BelongFact(K, NK));
        maskFacts.add(new BelongFact(K, MK));
        Triangle triangle = new Triangle(MN, MK, NK);
        Fact newFact = new ExistFact(triangle);
        Rule rule = new Rule(new LinkedList<>(maskFacts), new LinkedList<>(Arrays.asList(newFact)));
        MaskGraph maskGraph = new MaskGraph(rule);
        ModelGraph modelGraph = new ModelGraph(model);
        LinkedList<Graph.Correspondence> correspondences =
                modelGraph.getAllSubGraphsIsomorphicToMask(maskGraph);
    }

    @Test
    public void getAllIsomorphicSubGraphsIn1TrianglesTest() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment AC = new LineSegment(A, C);
        LineSegment BC = new LineSegment(B, C);
        HashSet<Fact> facts = new HashSet<>();
        facts.add(new ExistFact(A));
        facts.add(new ExistFact(B));
        facts.add(new ExistFact(C));
        facts.add(new ExistFact(AB));
        facts.add(new ExistFact(AC));
        facts.add(new ExistFact(BC));
        facts.add(new BelongFact(A, AB));
        facts.add(new BelongFact(A, AC));
        facts.add(new BelongFact(B, AB));
        facts.add(new BelongFact(B, BC));
        facts.add(new BelongFact(C, BC));
        facts.add(new BelongFact(C, AC));
        Model model = new Model(facts);

        Vertex M = new Vertex();
        Vertex N = new Vertex();
        Vertex K = new Vertex();
        LineSegment MN = new LineSegment(M, N);
        LineSegment MK = new LineSegment(M, K);
        LineSegment NK = new LineSegment(N, K);
        HashSet<Fact> maskFacts = new HashSet<>();
        maskFacts.add(new ExistFact(M));
        maskFacts.add(new ExistFact(N));
        maskFacts.add(new ExistFact(K));
        maskFacts.add(new ExistFact(MN));
        maskFacts.add(new ExistFact(MK));
        maskFacts.add(new ExistFact(NK));
        maskFacts.add(new BelongFact(M, MN));
        maskFacts.add(new BelongFact(M, MK));
        maskFacts.add(new BelongFact(N, MN));
        maskFacts.add(new BelongFact(N, NK));
        maskFacts.add(new BelongFact(K, NK));
        maskFacts.add(new BelongFact(K, MK));
        Triangle triangle = new Triangle(MN, MK, NK);
        Fact newFact = new ExistFact(triangle);
        Rule rule = new Rule(new LinkedList<>(maskFacts), new LinkedList<>(Arrays.asList(newFact)));
        MaskGraph maskGraph = new MaskGraph(rule);
        ModelGraph modelGraph = new ModelGraph(model);
        LinkedList<Graph.Correspondence> correspondences =
                modelGraph.getAllSubGraphsIsomorphicToMask(maskGraph);
        Model newModel = new Model(maskGraph.getConsequencesGraph(correspondences.get(0)));

    }

    //@Test
    //public void createTriangleGraph() {
    //    Vertex A = new Vertex();
    //    Vertex B = new Vertex();
    //    Vertex C = new Vertex();
    //    LineSegment AB = new LineSegment(A, B);
    //    LineSegment AC = new LineSegment(A, C);
    //    LineSegment BC = new LineSegment(B, C);
    //    HashSet<Fact> facts = new HashSet<>();
    //    ExistFact Af = new ExistFact(A);
    //    ExistFact Bf = new ExistFact(B);
    //    ExistFact Cf = new ExistFact(C);
    //    ExistFact ABf = new ExistFact(AB);
    //    ExistFact ACf = new ExistFact(AC);
    //    ExistFact BCf = new ExistFact(BC);
    //    facts.addAll(Arrays.asList(Af, Bf, Cf, ABf, ACf, BCf));
    //    Fact A_AB = new BelongFact(A, AB);
    //    Fact A_AC = new BelongFact(A, AC);
    //    Fact B_AB = new BelongFact(B, AB);
    //    Fact B_BC = new BelongFact(B, BC);
    //    Fact C_BC = new BelongFact(C, BC);
    //    Fact C_AC = new BelongFact(C, AC);
    //    facts.addAll(Arrays.asList(A_AB, A_AC, B_AB, B_BC, C_BC, C_AC));
    //    Model model = new Model(facts);
    //    Graph graph = new Graph(model);
    //    assertTrue(graph.adjacencyList.size() == 6);
    //    assertTrue(graph.getAdjEdgesOfNode(new Node(Af)).containsAll(Arrays.asList(
    //            new Edge(new Node(Af), new Node(ABf), BelongFact.class),
    //            new Edge(new Node(Af), new Node(ACf), BelongFact.class))));
    //    assertTrue(graph.getAdjEdgesOfNode(new Node(Bf)).containsAll(Arrays.asList(
    //            new Edge(new Node(Bf), new Node(ABf), BelongFact.class),
    //            new Edge(new Node(Bf), new Node(BCf), BelongFact.class))));
    //    assertTrue(graph.getAdjEdgesOfNode(new Node(Cf)).containsAll(Arrays.asList(
    //            new Edge(new Node(Cf), new Node(BCf), BelongFact.class),
    //            new Edge(new Node(Cf), new Node(ACf), BelongFact.class))));
    //    assertTrue(graph.getAdjEdgesOfNode(new Node(ABf)).containsAll(Arrays.asList(
    //            new Edge(new Node(ABf), new Node(Af), BelongFact.class),
    //            new Edge(new Node(ABf), new Node(Bf), BelongFact.class))));
    //    assertTrue(graph.getAdjEdgesOfNode(new Node(BCf)).containsAll(Arrays.asList(
    //            new Edge(new Node(BCf), new Node(Bf), BelongFact.class),
    //            new Edge(new Node(BCf), new Node(Cf), BelongFact.class))));
    //    assertTrue(graph.getAdjEdgesOfNode(new Node(ACf)).containsAll(Arrays.asList(
    //            new Edge(new Node(ACf), new Node(Af), BelongFact.class),
    //            new Edge(new Node(ACf), new Node(Cf), BelongFact.class))));
    //}
}
