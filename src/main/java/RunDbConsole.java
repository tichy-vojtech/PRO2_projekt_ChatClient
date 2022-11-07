import org.apache.derby.tools.ij;

public class RunDbConsole {
    public static void main(String[] args){
        //connect 'jdbc:derby:ChatClientDb_skA;create=true';

        try{
            ij.main(args);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
