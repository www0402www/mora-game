package www.sauce.com.moragame.project;

public class Player {
    public static final int READY=-1;
    public static final int SCISSORS=0;
    public static final int ROCK=1;
    public static final int PAPER=2;

    public int mora;
    public int life;

    public Player(){
        mora=READY;
        life=3;
    }

    public void setMora(int mora) {
        this.mora = mora;
    }

    public int getMora() {
        return mora;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }
}
