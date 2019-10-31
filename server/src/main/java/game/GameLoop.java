package game;

import handlers.MovementHandler;

public class GameLoop implements Runnable {
    private boolean running;

    public GameLoop(){
        this.running = true;
    }

    @Override
    public void run() {

        while (running) {
            MovementHandler.movementLoopList.entrySet().forEach(System.out::println);
            try {
                Thread.sleep(160);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}
