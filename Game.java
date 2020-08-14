import java.util.*;

public class Game implements Runnable{
    int x;
    public Game(){
        x = 0;
    }
    public void run(){
        while(true) {
            try{
                Thread.sleep(10);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}