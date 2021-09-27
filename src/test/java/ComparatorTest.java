import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;

import core.ExpertSystem;
import core.Model;
import core.facts.BelongFact;
import core.facts.EqualityFact;
import core.facts.ExistFact;
import core.facts.Fact;
import core.objects.GeometryObject;
import core.objects.LineSegment;
import core.objects.Polynomial;
import core.objects.RaisedInThePower;
import core.objects.Triangle;
import core.objects.Vertex;
import core.objects.numbers.GeometryNumber;
import core.objects.numbers.NumberValue;

public class ComparatorTest {
    @Test
    public void EqualityTest() {

        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment BC = new LineSegment(B, C);
        LineSegment AC = new LineSegment(A, C);
        NumberValue ac = new NumberValue(AC, null), bc = new NumberValue(BC, null), ab = new NumberValue(AB, null);
        EqualityFact equation = new EqualityFact(
                new RaisedInThePower(ab, GeometryNumber.createNumber(2)),
                new Polynomial(
                        new RaisedInThePower(new LinkedList<>(Arrays.asList(ac, GeometryNumber.createNumber(2))), GeometryNumber.createNumber(2)),
                        new RaisedInThePower(bc, GeometryNumber.createNumber(2))
                ));// AB^2 = (2AC)^2+BC^2
        EqualityFact equation2 = new EqualityFact(
                new RaisedInThePower(ab, GeometryNumber.createNumber(2)),
                new Polynomial(
                        new RaisedInThePower(new LinkedList<>(Arrays.asList(ac, GeometryNumber.createNumber(2))), GeometryNumber.createNumber(2)),
                        new RaisedInThePower(bc, GeometryNumber.createNumber(2))
                ));// AB^2 = (2AC)^2+BC^2

        assertEquals(equation, equation2);
    }

    @Test
    public void InequalityTest() {

        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment BC = new LineSegment(B, C);
        LineSegment AC = new LineSegment(A, C);
        NumberValue ac = new NumberValue(AC, null), bc = new NumberValue(BC, null), ab = new NumberValue(AB, null);
        EqualityFact equation = new EqualityFact(
                new RaisedInThePower(ab, GeometryNumber.createNumber(2)),
                new Polynomial(
                        new RaisedInThePower(new LinkedList<>(Arrays.asList(ac, GeometryNumber.createNumber(2))), GeometryNumber.createNumber(2)),
                        new RaisedInThePower(bc, GeometryNumber.createNumber(2))
                ));// AB^2 = (2AC)^2+BC^2
        EqualityFact equation2 = new EqualityFact(
                new RaisedInThePower(ab, GeometryNumber.createNumber(2)),
                new Polynomial(
                        new RaisedInThePower(new LinkedList<>(Arrays.asList(ac, GeometryNumber.createNumber(2))), GeometryNumber.createNumber(2)),
                        new RaisedInThePower(bc, GeometryNumber.createNumber(22))
                ));// AB^2 = (2AC)^2+BC^22
        EqualityFact equation3 = new EqualityFact(
                new RaisedInThePower(ab, GeometryNumber.createNumber(2)),
                new Polynomial(
                        new RaisedInThePower(new LinkedList<>(Arrays.asList(ac, GeometryNumber.createNumber(2))), GeometryNumber.createNumber(2)),
                        new RaisedInThePower(ac, GeometryNumber.createNumber(2))
                ));// AB^2 = (2AC)^2+AC^2
        assert (!equation.equals(equation2));
        assert (!equation2.equals(equation3));
        assert (!equation.equals(equation3));
    }

    @Test
    public void ModelBriefTest() {

        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment BC = new LineSegment(B, C);
        LineSegment AC = new LineSegment(A, C);
        NumberValue ac = new NumberValue(AC, null), bc = new NumberValue(BC, null), ab = new NumberValue(AB, null);
        EqualityFact equation = new EqualityFact(
                new RaisedInThePower(ab, GeometryNumber.createNumber(2)),
                new Polynomial(
                        new RaisedInThePower(new LinkedList<>(Arrays.asList(ac, GeometryNumber.createNumber(2))), GeometryNumber.createNumber(2)),
                        new RaisedInThePower(bc, GeometryNumber.createNumber(2))
                ));// AB^2 = (2AC)^2+BC^2
        EqualityFact equation2 = new EqualityFact(
                new RaisedInThePower(ab, GeometryNumber.createNumber(2)),
                new Polynomial(
                        new RaisedInThePower(new LinkedList<>(Arrays.asList(ac, GeometryNumber.createNumber(2))), GeometryNumber.createNumber(2)),
                        new RaisedInThePower(bc, GeometryNumber.createNumber(2))
                ));// AB^2 = (2AC)^2+BC^2
        EqualityFact equation3 = new EqualityFact(
                new RaisedInThePower(ab, GeometryNumber.createNumber(2)),
                new Polynomial(
                        new RaisedInThePower(new LinkedList<>(Arrays.asList(ac, GeometryNumber.createNumber(2))), GeometryNumber.createNumber(2)),
                        new RaisedInThePower(ac, GeometryNumber.createNumber(2))
                ));// AB^2 = (2AC)^2+AC^2
        HashSet<Fact> first = new HashSet<>(), second = new HashSet<>(), third = new HashSet<>();
        first.add(equation);
        second.add(equation2);
        third.add(equation3);
        Model model1 = new Model(first), model2 = new Model(second), model3 = new Model(third);
        assert (model1.equals(model2));
        assert (!model1.equals(model3));
        assert (!model2.equals(model3));
    }

    @Test
    public void EquivalenceTest() {

        for (int i = 0; i < 1000; ++i) {
            Vertex A = new Vertex();
            Vertex B = new Vertex();
            Vertex C = new Vertex();
            LineSegment AB = new LineSegment(A, B);
            LineSegment BC = new LineSegment(B, C);
            LineSegment AC = new LineSegment(A, C);
            NumberValue ac = new NumberValue(AC, null), bc = new NumberValue(BC, null), ab = new NumberValue(AB, null);


            RaisedInThePower mine = new RaisedInThePower(ab, GeometryNumber.createNumber(2));
            EqualityFact equation = new EqualityFact(
                    mine,
                    new Polynomial(
                            new RaisedInThePower(new LinkedList<>(Arrays.asList(ac, GeometryNumber.createNumber(2))), GeometryNumber.createNumber(2)),
                            new RaisedInThePower(bc, GeometryNumber.createNumber(2))
                    ));// AB^2 = (2AC)^2+BC^2
            ExistFact existFact1 = new ExistFact(mine);


            RaisedInThePower yours = new RaisedInThePower(ab, GeometryNumber.createNumber(2));
            EqualityFact equation2 = new EqualityFact(
                    yours,
                    new Polynomial(
                            new RaisedInThePower(new LinkedList<>(Arrays.asList(ac, GeometryNumber.createNumber(2))), GeometryNumber.createNumber(2)),
                            new RaisedInThePower(bc, GeometryNumber.createNumber(2))
                    ));// AB^2 = (2AC)^2+BC^2
            ExistFact existFact2 = new ExistFact(yours);


            HashSet<Fact> original = new HashSet<>(), second = new HashSet<>();
            original.add(equation);
            original.add(existFact1);
            second.add(equation2);
            second.add(existFact2);
            Model model1 = new Model(original), model2 = new Model(second);
            assert (model1.isEquivalentTo(model2));
        }

    }

    @Test
    public void NonEquivalenceByGeometryNumberTest() {

        for (int i = 0; i < 1000; ++i) {
            Vertex A = new Vertex();
            Vertex B = new Vertex();
            Vertex C = new Vertex();
            LineSegment AB = new LineSegment(A, B);
            LineSegment BC = new LineSegment(B, C);
            LineSegment AC = new LineSegment(A, C);
            NumberValue ac = new NumberValue(AC, null), bc = new NumberValue(BC, null), ab = new NumberValue(AB, null);


            RaisedInThePower mine = new RaisedInThePower(ab, GeometryNumber.createNumber(2));
            EqualityFact equation = new EqualityFact(
                    mine,
                    new Polynomial(
                            new RaisedInThePower(new LinkedList<>(Arrays.asList(ac, GeometryNumber.createNumber(2))), GeometryNumber.createNumber(2)),
                            new RaisedInThePower(bc, GeometryNumber.createNumber(2))
                    ));// AB^2 = (2AC)^2+BC^2
            ExistFact existFact1 = new ExistFact(mine);


            RaisedInThePower yours = new RaisedInThePower(ab, GeometryNumber.createNumber(2));
            EqualityFact equation2 = new EqualityFact(
                    yours,
                    new Polynomial(
                            new RaisedInThePower(new LinkedList<>(Arrays.asList(ac, GeometryNumber.createNumber(2))), GeometryNumber.createNumber(2)),
                            new RaisedInThePower(bc, GeometryNumber.createNumber(3))
                    ));// AB^2 = (2AC)^2+BC^3
            ExistFact existFact2 = new ExistFact(yours);


            HashSet<Fact> original = new HashSet<>(), second = new HashSet<>();
            original.add(equation);
            original.add(existFact1);
            second.add(equation2);
            second.add(existFact2);
            Model model1 = new Model(original), model2 = new Model(second);
            assert (!model1.isEquivalentTo(model2));
        }

    }

    @Test
    public void NonEquivalenceByDifferentStructureTest() {

        for (int i = 0; i < 1000; ++i) {
            Vertex A = new Vertex();
            Vertex B = new Vertex();
            Vertex C = new Vertex();
            LineSegment AB = new LineSegment(A, B);
            LineSegment BC = new LineSegment(B, C);
            LineSegment AC = new LineSegment(A, C);
            NumberValue ac = new NumberValue(AC, null), bc = new NumberValue(BC, null), ab = new NumberValue(AB, null);


            RaisedInThePower mine = new RaisedInThePower(ab, GeometryNumber.createNumber(2));
            EqualityFact equation = new EqualityFact(
                    mine,
                    new Polynomial(
                            new RaisedInThePower(new LinkedList<>(Arrays.asList(ac, GeometryNumber.createNumber(2))), GeometryNumber.createNumber(2)),
                            new RaisedInThePower(bc, GeometryNumber.createNumber(2)),
                            new RaisedInThePower(bc, GeometryNumber.createNumber(2))
                    ));// AB^2 = (2AC)^2+BC^2+BC^2
            ExistFact existFact1 = new ExistFact(mine);


            RaisedInThePower yours = new RaisedInThePower(ab, GeometryNumber.createNumber(2));
            EqualityFact equation2 = new EqualityFact(
                    yours,
                    new Polynomial(
                            new RaisedInThePower(new LinkedList<>(Arrays.asList(ac, GeometryNumber.createNumber(2))), GeometryNumber.createNumber(2)),
                            new RaisedInThePower(bc, GeometryNumber.createNumber(2))
                    ));// AB^2 = (2AC)^2+BC^2
            ExistFact existFact2 = new ExistFact(yours);


            HashSet<Fact> original = new HashSet<>(), second = new HashSet<>();
            original.add(equation);
            original.add(existFact1);
            second.add(equation2);
            second.add(existFact2);
            Model model1 = new Model(original), model2 = new Model(second);
            assert (!model1.isEquivalentTo(model2));
        }

    }

    @Test
    public void NonEquivalenceByDifferentObjectToFactRelationsTest() {

        for (int i = 0; i < 1000; ++i) {
            Vertex A = new Vertex();
            Vertex B = new Vertex();
            Vertex C = new Vertex();
            LineSegment AB = new LineSegment(A, B);
            LineSegment BC = new LineSegment(B, C);
            LineSegment AC = new LineSegment(A, C);
            NumberValue ac = new NumberValue(AC, null), bc = new NumberValue(BC, null), ab = new NumberValue(AB, null);


            RaisedInThePower mine = new RaisedInThePower(ab, GeometryNumber.createNumber(2));
            EqualityFact equation = new EqualityFact(
                    new RaisedInThePower(ab, GeometryNumber.createNumber(2)),
                    new Polynomial(
                            new RaisedInThePower(new LinkedList<>(Arrays.asList(ac, GeometryNumber.createNumber(2))), GeometryNumber.createNumber(2)),
                            new RaisedInThePower(bc, GeometryNumber.createNumber(2))
                    ));// AB^2 = (2AC)^2+BC^2
            ExistFact existFact1 = new ExistFact(mine);


            RaisedInThePower yours = new RaisedInThePower(ab, GeometryNumber.createNumber(2));
            EqualityFact equation2 = new EqualityFact(
                    yours,
                    new Polynomial(
                            new RaisedInThePower(new LinkedList<>(Arrays.asList(ac, GeometryNumber.createNumber(2))), GeometryNumber.createNumber(2)),
                            new RaisedInThePower(bc, GeometryNumber.createNumber(2))
                    ));// AB^2 = (2AC)^2+BC^2
            ExistFact existFact2 = new ExistFact(yours);


            HashSet<Fact> original = new HashSet<>(), second = new HashSet<>();
            original.add(equation);
            original.add(existFact1);
            second.add(equation2);
            second.add(existFact2);
            Model model1 = new Model(original), model2 = new Model(second);
            assert (!model1.isEquivalentTo(model2));
        }


    }

    @Test
    public void FindTriangleTest() {
        for (int i = 0; i < 1000; ++i) {
            GeometryObject.idRestart();
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


            facts = new HashSet<>();
            A = new Vertex();
            B = new Vertex();
            C = new Vertex();
            AB = new LineSegment(A, B);
            BC = new LineSegment(B, C);
            AC = new LineSegment(A, C);
            facts.add(new BelongFact(A, AB));
            facts.add(new BelongFact(B, AB));
            facts.add(new BelongFact(A, AC));
            facts.add(new BelongFact(B, BC));
            facts.add(new BelongFact(C, AC));
            facts.add(new BelongFact(C, BC));
            facts.add(new ExistFact(new Triangle(new HashSet<>(Arrays.asList(AC, BC, AB)))));
            Model checkModel = new Model(facts);
            boolean a = checkModel.isEquivalentTo(model, 0);
            assertTrue(a);

        }
    }
    @Test
    public void FindTriangleTestOriginal() {
        for (int i = 0; i < 1000; ++i) {
            Vertex A = new Vertex();
            Vertex B = new Vertex();
            Vertex C = new Vertex();
            Vertex D = new Vertex();
            LineSegment CD = new LineSegment(C, D);
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

            HashSet checkFacts = new HashSet(facts);
            checkFacts.add(new ExistFact(new Triangle(new HashSet<>(Arrays.asList(BC, AC, CD)))));
            Model checkModel = new Model(checkFacts);
            assertTrue(!checkModel.isEquivalentTo(model));
        }
    }
}
