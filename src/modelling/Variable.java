package modelling ;
import java.util.Objects;
import java.util.Set;
public class Variable{
    private String name ;
    private Set<Object> domaine;
    public Variable(String name , Set<Object> domaine){
        this.name = name ;
        this.domaine =domaine;
    }
    public String getName(){
        return this.name;

    }
    public Set<Object> getDomain(){
        return this.domaine;
    }
    @Override 
    public boolean equals(Object obj){
        if(this==obj) return true;
        if(!(obj instanceof Variable)) return false;
        Variable other = (Variable) obj;
        return Objects.equals(this.name, other.name);

    }
    @Override 
    public int hashCode(){
        return Objects.hash(name);
    }
    @Override 
    public String toString(){
        return this.name;
    }

}