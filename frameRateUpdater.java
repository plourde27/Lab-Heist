public class frameRateUpdater implements Runnable{
    int fps;
    Display screen;
    public frameRateUpdater(int f, Display s){
        fps = 60;
        screen = s;
    }
    public void run(){
        while(true){
            new Thread(new drawThread(screen)).start();
            try{
                Thread.sleep(1000/fps);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}