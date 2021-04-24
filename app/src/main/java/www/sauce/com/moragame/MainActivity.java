package www.sauce.com.moragame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import www.sauce.com.moragame.project.Computer;
import www.sauce.com.moragame.project.Player;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Computer.OnComputerCompletedLister {
    private ImageButton scissorsIbn;
    private ImageButton rockIbn;
    private ImageButton paperIbn;
    private Button startBtn;
    private Button quitBtn;
    private ImageView computerImage, playerImage;
    private final String TAG = "MainActivity";
    private Player player;
    private Computer computer;
    private boolean isPlayerRound;
    public static final int EVEN = 0;
    public static final int PLAYER_WIN = 1;
    public static final int COMPUTER_WIN = 2;

    public void findView() {
        scissorsIbn = findViewById(R.id.scissors_ibn);
        rockIbn = findViewById(R.id.rock_ibn);
        paperIbn = findViewById(R.id.paper_ibn);
        startBtn = findViewById(R.id.start_btn);
        quitBtn = findViewById(R.id.quit_btn);
        computerImage = findViewById(R.id.computer_img);
        playerImage = findViewById(R.id.player_img);
    }

    private void initGame() {
        isPlayerRound = false;
        computer.ai();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findView();

        scissorsIbn.setOnClickListener((View.OnClickListener) this);
        rockIbn.setOnClickListener(this);
        paperIbn.setOnClickListener(this);
        startBtn.setOnClickListener(this);
        quitBtn.setOnClickListener(this);

        player = new Player();
        computerImage.setImageResource(R.drawable.scissors);
        playerImage.setVisibility(View.INVISIBLE);

        computer = new Computer(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.scissors_ibn:
                if (isPlayerRound) {
                    isPlayerRound = false;
                    Log.d(TAG, "onClick: " + getResources().getString(R.string.scissors));
                    player.setMora(Player.SCISSORS);
                    playerImage.setImageResource(R.drawable.scissors);
                    playerImage.setVisibility(View.VISIBLE);
                    checkGameState();
                }
                break;
            case R.id.rock_ibn:
                if (isPlayerRound) {
                    isPlayerRound = false;
                    Log.d(TAG, "onClick: " + getResources().getString(R.string.rock));
                    player.setMora(Player.ROCK);
                    playerImage.setImageResource(R.drawable.rock);
                    playerImage.setVisibility(View.VISIBLE);
                    checkGameState();
                }
                break;
            case R.id.paper_ibn:
                if (isPlayerRound) {
                    isPlayerRound = false;
                    Log.d(TAG, "onClick: " + getResources().getString(R.string.paper));
                    player.setMora(Player.PAPER);
                    playerImage.setImageResource(R.drawable.paper);
                    playerImage.setVisibility(View.VISIBLE);
                    checkGameState();
                }
                break;
            case R.id.start_btn:
                Log.d(TAG, "onClick: " + getResources().getString(R.string.start));
                initGame();
                break;
            case R.id.quit_btn:
                Log.d(TAG, "onClick: " + getResources().getString(R.string.quit));
                break;


        }
    }

    @Override
    public void complete() {
        int mora = computer.getMora();
        if (mora == Player.SCISSORS) {
        } else if (mora == Player.ROCK) {
            computerImage.setImageResource(R.drawable.rock);
        } else if (mora == Player.PAPER) {
            computerImage.setImageResource(R.drawable.paper);
        }
        isPlayerRound = true;

        Log.d(TAG, "complete: " + mora);
    }

    private Handler handler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    computerImage.setImageResource(R.drawable.scissors);
                    isPlayerRound = true;
                    break;
                case 1:
                    computerImage.setImageResource(R.drawable.rock);
                    isPlayerRound = true;
                    break;
                case 2:
                    computerImage.setImageResource(R.drawable.paper);
                    isPlayerRound = true;
                    break;
            }
        }
    };

    public int getWinState(int playermora, int computerMora) {
        if (playermora == computerMora) {
            return EVEN;
        }
        if (playermora == Player.SCISSORS && computerMora == Player.PAPER) {
            return PLAYER_WIN;
        }
        if (computerMora == Player.SCISSORS && playermora == Player.PAPER) {
            return COMPUTER_WIN;
        }
        if (playermora > computerMora) {
            return PLAYER_WIN;

        }
        return COMPUTER_WIN;
    }

    public void checkGameState() {
        int state = getWinState(player.getMora(), computer.getMora());
        if (state == EVEN) {
            Toast.makeText(this, "雙方平手", Toast.LENGTH_SHORT).show();
        } else if (state == PLAYER_WIN) {
            Toast.makeText(this, "玩家勝利", Toast.LENGTH_SHORT).show();
        } else if (state == COMPUTER_WIN) {
            Toast.makeText(this, "電腦勝利", Toast.LENGTH_SHORT).show();
        }

        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                initGame();
            }
        }).start();
    }
}
