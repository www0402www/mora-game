package www.sauce.com.moragame.project;

import java.util.Random;

public class Computer extends Player {
    private OnComputerCompletedLister onComputerCompletedLister;

    public Computer(OnComputerCompletedLister onCompletedLister) {
        this.onComputerCompletedLister = onCompletedLister;
        life = 1;
    }

    public void ai() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(250);
                    setMora(getRandomMora());
                    onComputerCompletedLister.complete();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public int getRandomMora() {
        return new Random().nextInt(PAPER + 1);
    }

    public interface OnComputerCompletedLister {
        void complete();
    }
}

