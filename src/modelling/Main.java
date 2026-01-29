package modelling;

import java.util.*;
public class Main {
    public static void main(String[] args) {
            
        //Déclaration des variables
        Set<Object> S1= Set.of(1,2,3);
        Set<Object> S2 = Set.of(2,3);
        Set<Object> S3 = Set.of(1,2,3);
        Variable x = new Variable("X",S1);
        Variable y = new Variable("Y", S2);
        BooleanVariable b1 = new BooleanVariable("A");
        BooleanVariable b2 = new BooleanVariable("B");
        BooleanVariable b3 = new BooleanVariable("A"); // meme nom que b1 pour les tests manuels 
        //Déclaration des contraintes
        Constraint t1 = new DifferenceConstraint(x,y);
        Constraint t2 = new Implication(x,S1,y,S2);
        Constraint t3 = new UnaryConstraint(x,S2);

        //Affichage des scopes 

        System.out.println("Scope t1 :" + t1.getScope());
        System.out.println("Scope t2 :" + t2.getScope());
        System.out.println("Scope t3 :" + t3.getScope());

                    // *****TEST MANUEL DE VARIABLES*****
        System.out.println("\n==== TEST MANUEL DES VARIABLES ====");
        //1- Les Accesseurs 
        System.out.println("Nom de la variable x :" + x.getName());
        System.out.println("Domaine de la variable  x : " + x.getDomain());

        //2-Test equals  
        System.out.println("x.equals(y):" + x.equals(y));
        System.out.println("b1.equals(b3):" + b1.equals(b3));//true car meme nom
        System.out.println("b1.equals(b2):" + b1.equals(b2)); //false car nom different

                    //***** TEST MANUEL DES CONTRAINTES *******

        System.out.println("\n==== TEST MANUEL DES CONTRAINTES ====");
        Map<Variable,Object> ins1 = Map.of(x,1,y,2,b2,true);
        Map<Variable,Object> ins2 = Map.of(x,2,y,2,b2,false);

        for(Map<Variable,Object>instanciation : List.of(ins1,ins2)){
            System.out.println("Instanciation testée: " +instanciation);
            for(Constraint contrainte : List.of(t1,t2,t3)){
                try{
                    boolean result = contrainte.isSatisfiedBy(instanciation);
                    System.out.println("contrainte : " + contrainte + "-->" +(result ? " satisfaite " : "Non satisfaite"));
                }
                catch(IllegalArgumentException e){
                    System.out.println("contrainte : " + contrainte + "-->" + e.getMessage());
                }

            
        }}

     

        
        
   

}
}