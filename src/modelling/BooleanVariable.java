package modelling ;
import java.util.Set;

public class BooleanVariable extends Variable{
    private static final Set<Object> domaineB = Set.of(true,false);
    public BooleanVariable(String name){
        super(name,domaineB);
    }
}