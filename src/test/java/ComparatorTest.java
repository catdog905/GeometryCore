import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.HashSet;

import core.facts.ExistFact;
import core.facts.Fact;
import core.facts.equation.EqualityFact;
import core.model.Model;
import core.objects.LineSegment;
import core.objects.Vertex;
import core.objects.expression.GeometryNumber;
import core.objects.expression.Monomial;
import core.objects.expression.Polynomial;
import core.objects.expression.RaisedInThePower;

public class ComparatorTest {
    @Test
    public void EqualityTest(){

        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment BC = new LineSegment(B, C);
        LineSegment AC = new LineSegment(A, C);
        GeometryNumber num2 = GeometryNumber.get(2);
        EqualityFact equation =  new EqualityFact(
                new RaisedInThePower(AB.getMonomial(), GeometryNumber.get(2)),
                new Polynomial(
                        new RaisedInThePower(new Monomial(AC.getMonomial(),num2), num2),
                        new RaisedInThePower(BC.getMonomial(), num2)
                ));// AB.getMonomial()^2 = (2AC)^2+BC.getMonomial()^2
        EqualityFact equation2 =  new EqualityFact(
                new RaisedInThePower(AB.getMonomial(), num2),
                new Polynomial(
                        new RaisedInThePower(new Monomial(AC.getMonomial(),num2), num2),
                        new RaisedInThePower(BC.getMonomial(), num2)
                ));// AB.getMonomial()^2 = (2AC)^2+BC.getMonomial()^2

        assertEquals(equation,equation2);
    }
    @Test
    public void InequalityTest(){

        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment BC = new LineSegment(B, C);
        LineSegment AC = new LineSegment(A, C);
        GeometryNumber num2 = GeometryNumber.get(2);
        EqualityFact equation =  new EqualityFact(
                new RaisedInThePower(AB.getMonomial(), num2),
                new Polynomial(
                        new RaisedInThePower(new Monomial(AC.getMonomial(),num2), num2),
                        new RaisedInThePower(BC.getMonomial(), num2)
                ));// AB.getMonomial()^2 = (2AC)^2+BC.getMonomial()^2
        EqualityFact equation2 =  new EqualityFact(
                new RaisedInThePower(AB.getMonomial(), num2),
                new Polynomial(
                        new RaisedInThePower(new Monomial(AC.getMonomial(),num2), num2),
                        new RaisedInThePower(BC.getMonomial(), GeometryNumber.get(22)
                )));// AB.getMonomial()^2 = (2AC)^2+BC.getMonomial()^22
        EqualityFact equation3 =  new EqualityFact(
                new RaisedInThePower(AB.getMonomial(), num2),
                new Polynomial(
                        new RaisedInThePower(new Monomial(AC.getMonomial(),num2), num2),
                        new RaisedInThePower(AC.getMonomial(), num2)
                ));// AB.getMonomial()^2 = (2AC)^2+AC.getMonomial()^2
        assert (!equation.equals(equation2));
        assert (!equation2.equals(equation3));
        assert (!equation.equals(equation3));
    }
    @Test
    public void ModelBriefTest(){

        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment BC = new LineSegment(B, C);
        LineSegment AC = new LineSegment(A, C);
        GeometryNumber num2 = GeometryNumber.get(3);
        EqualityFact equation =  new EqualityFact(
                new RaisedInThePower(AB.getMonomial(), num2),
                new Polynomial(
                        new RaisedInThePower(new Monomial(AC.getMonomial(),num2), num2),
                        new RaisedInThePower(BC.getMonomial(), num2)
                ));// AB.getMonomial()^2 = (2AC)^2+BC.getMonomial()^2
        EqualityFact equation2 =  new EqualityFact(
                new RaisedInThePower(AB.getMonomial(), num2),
                new Polynomial(
                        new RaisedInThePower(new Monomial(AC.getMonomial(),num2), num2),
                        new RaisedInThePower(BC.getMonomial(), num2)
                ));// AB.getMonomial()^2 = (2AC)^2+BC.getMonomial()^2
        EqualityFact equation3 =  new EqualityFact(
                new RaisedInThePower(AB.getMonomial(), num2),
                new Polynomial(
                        new RaisedInThePower(new Monomial(AC.getMonomial(),num2), num2),
                        new RaisedInThePower(AC.getMonomial(), num2)
                ));// AB.getMonomial()^2 = (2AC)^2+AC.getMonomial()^2
        HashSet<Fact> first = new HashSet<>(),second = new HashSet<>(),third = new HashSet<>();
        first.add(equation);
        second.add(equation2);
        third.add(equation3);
        Model model1 = new Model(first),model2 = new Model(second),model3 = new Model(third);
        assert(model1.equals(model2));
        assert (!model1.equals(model3));
        assert (!model2.equals(model3));
    }

    @Test
    public void EquivalenceTest(){

        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment BC = new LineSegment(B, C);
        LineSegment AC = new LineSegment(A, C);
        GeometryNumber num2 = GeometryNumber.get(2);
        RaisedInThePower mine = new RaisedInThePower(AB.getMonomial(), num2);
        EqualityFact equation =  new EqualityFact(
                mine,
                new Polynomial(
                        new RaisedInThePower(new Monomial(AC.getMonomial(),num2), num2),
                        new RaisedInThePower(BC.getMonomial(), num2)
                ));// AB.getMonomial()^2 = (2AC)^2+BC.getMonomial()^2
        ExistFact existFact1 = new ExistFact(mine);


        RaisedInThePower yours = new RaisedInThePower(AB.getMonomial(), num2);
        EqualityFact equation2 =  new EqualityFact(
                yours,
                new Polynomial(
                        new RaisedInThePower(new Monomial(AC.getMonomial(),num2), num2),
                        new RaisedInThePower(BC.getMonomial(), num2)
                ));// AB.getMonomial()^2 = (2AC)^2+BC.getMonomial()^2
        ExistFact existFact2 = new ExistFact(yours);


        HashSet<Fact> original = new HashSet<>(),second = new HashSet<>();
        original.add(equation);
        original.add(existFact1);
        second.add(equation2);
        second.add(existFact2);
        Model model1 = new Model(original),model2 = new Model(second);
        assert (model1.isEquivalentTo(model2));
    }
    @Test
    public void NonEquivalenceByGeometryNumberTest(){

        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment BC = new LineSegment(B, C);
        LineSegment AC = new LineSegment(A, C);
        GeometryNumber num2 = GeometryNumber.get(2);

        RaisedInThePower mine = new RaisedInThePower(AB.getMonomial(), num2);
        EqualityFact equation =  new EqualityFact(
                mine,
                new Polynomial(
                        new RaisedInThePower(new Monomial(AC.getMonomial(),num2), num2),
                        new RaisedInThePower(BC.getMonomial(), num2)
                ));// AB.getMonomial()^2 = (2AC)^2+BC.getMonomial()^2
        ExistFact existFact1 = new ExistFact(mine);


        RaisedInThePower yours = new RaisedInThePower(AB.getMonomial(), num2);
        EqualityFact equation2 =  new EqualityFact(
                yours,
                new Polynomial(
                        new RaisedInThePower(new Monomial(AC.getMonomial(),num2), num2),
                        new RaisedInThePower(BC.getMonomial(), GeometryNumber.get(3))
                ));// AB.getMonomial()^2 = (2AC)^2+BC.getMonomial()^3
        ExistFact existFact2 = new ExistFact(yours);


        HashSet<Fact> original = new HashSet<>(),second = new HashSet<>();
        original.add(equation);
        original.add(existFact1);
        second.add(equation2);
        second.add(existFact2);
        Model model1 = new Model(original),model2 = new Model(second);
        assert (!model1.isEquivalentTo(model2));
    }
    @Test
    public void NonEquivalenceByDifferentStructureTest(){

        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment BC = new LineSegment(B, C);
        LineSegment AC = new LineSegment(A, C);
        GeometryNumber num2 = GeometryNumber.get(2);

        RaisedInThePower mine = new RaisedInThePower(AB.getMonomial(), num2);
        EqualityFact equation =  new EqualityFact(
                mine,
                new Polynomial(
                        new RaisedInThePower(new Monomial(AC.getMonomial(),num2), num2),
                        new RaisedInThePower(BC.getMonomial(), num2),
                        new RaisedInThePower(BC.getMonomial(), num2)
                ));// AB.getMonomial()^2 = (2AC)^2+BC.getMonomial()^2+BC.getMonomial()^2
        ExistFact existFact1 = new ExistFact(mine);


        RaisedInThePower yours = new RaisedInThePower(AB.getMonomial(), num2);
        EqualityFact equation2 =  new EqualityFact(
                yours,
                new Polynomial(
                        new RaisedInThePower(new Monomial(AC.getMonomial(),num2), num2),
                        new RaisedInThePower(BC.getMonomial(), num2)
                ));// AB.getMonomial()^2 = (2AC)^2+BC.getMonomial()^2
        ExistFact existFact2 = new ExistFact(yours);


        HashSet<Fact> original = new HashSet<>(),second = new HashSet<>();
        original.add(equation);
        original.add(existFact1);
        second.add(equation2);
        second.add(existFact2);
        Model model1 = new Model(original),model2 = new Model(second);
        assert (!model1.isEquivalentTo(model2));
    }
    @Test
    public void NonEquivalenceByDifferentObjectToFactRelationsTest(){

        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment BC = new LineSegment(B, C);
        LineSegment AC = new LineSegment(A, C);
        GeometryNumber num2 = GeometryNumber.get(2);



        RaisedInThePower mine = new RaisedInThePower(AB.getMonomial(), num2);
        EqualityFact equation =  new EqualityFact(
                new RaisedInThePower(AB.getMonomial(), num2),
                new Polynomial(
                        new RaisedInThePower(new Monomial(AC.getMonomial(),num2), num2),
                        new RaisedInThePower(BC.getMonomial(), num2)
                ));// AB.getMonomial()^2 = (2AC)^2+BC.getMonomial()^2
        ExistFact existFact1 = new ExistFact(mine);


        RaisedInThePower yours = new RaisedInThePower(AB.getMonomial(), num2);
        EqualityFact equation2 =  new EqualityFact(
                yours,
                new Polynomial(
                        new RaisedInThePower(new Monomial(AC.getMonomial(),num2), num2),
                        new RaisedInThePower(BC.getMonomial(), num2)
                ));// AB.getMonomial()^2 = (2AC)^2+BC.getMonomial()^2
        ExistFact existFact2 = new ExistFact(yours);


        HashSet<Fact> original = new HashSet<>(),second = new HashSet<>();
        original.add(equation);
        original.add(existFact1);
        second.add(equation2);
        second.add(existFact2);
        Model model1 = new Model(original),model2 = new Model(second);
        assert (!model1.isEquivalentTo(model2));
    }
}
