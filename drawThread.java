public class drawThread implements Runnable{
    Display screen;
    public drawThread(Display s){
        screen = s;
    }
    public void run(){
        screen.draw();
    }
}