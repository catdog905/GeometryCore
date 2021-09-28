package core.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import core.facts.Fact;
import core.objects.GeometryObject;

public class Model {
    public HashSet<Fact> facts;

    public Model(HashSet<Fact> facts) {
        this.facts = facts;
    }

    public boolean isEquivalentTo(Model model){
        if (model.facts.size() != facts.size())
            return false;
        HashSet<Fact> theirFacts = new HashSet<>( model.facts);
        ModelEquivalenceMaster modelEquivalenceMaster = new ModelEquivalenceMaster(getObjectToFactsHashMap(), model.getObjectToFactsHashMap());
        for(Fact ourFact : facts){
            boolean equivalenceFound = false;
            if (modelEquivalenceMaster.equivalenceToFactFound(ourFact))
                continue;
            LinkedList<GeometryObject> ourSubObjects =  ourFact.getAllSubObjects();
            for (Fact theirFact : theirFacts){
                if (modelEquivalenceMaster.equivalenceToFactFound(theirFact))
                    continue;
                if (modelEquivalenceMaster.isOurFactEquivalentToTheir(ourFact,theirFact,ourSubObjects))
                {
                    equivalenceFound=true;
                    break;
                }
            }

            if (!equivalenceFound)
                return false;
        }



        return true;
    }

    private HashMap<GeometryObject,LinkedList<Fact>> getObjectToFactsHashMap(){
        HashMap<GeometryObject,LinkedList<Fact>> answer = new HashMap<>();
        for (Fact fact:facts) {
            for (var subObject:fact.getAllSubObjects()){
                if (!answer.containsKey(subObject)){
                    LinkedList<Fact> list = new LinkedList<>();
                    list.add(fact);
                    answer.put(subObject,list);
                }else{
                    answer.get(subObject).add(fact);
                }
            }
        }
        return answer;
    }
    @Override
    public boolean equals(Object o) {

        if (o == null )
            return false;

        //Same link
        if (this == o)
            return true;

        if (!o.getClass().equals(getClass()))
            return false;

        Model model = (Model) o;

        // HashSet<>::containsAll for some reason doesn't seem to call equals, so I have
        // to convert it to LinkedList<>, which does.
        LinkedList<Fact> theirFacts = new LinkedList<>(model.facts);
        LinkedList<Fact> ourFacts = new LinkedList<>(facts);

        return ourFacts.size()==theirFacts.size()&&
                ourFacts.containsAll(theirFacts);
    }
}
