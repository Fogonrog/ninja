package ru.gsemenov.walker;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    protected GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Убрать заголовок активности
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        // Запустить активность на полный экран
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Скрыть меню навигации
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        // Создать новый игровой графический элемент GameView
        gameView = new GameView(this);

        // Поставить наш GameView в качестве корневого графического элемента нашей активности
        setContentView(gameView);

    }

    /**
     * Когда активность скрывают, необходимо приостановить игру
     */
    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    /**
     * Когда активность появляется вновь, необходимо возобновить игру
     */
    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

}