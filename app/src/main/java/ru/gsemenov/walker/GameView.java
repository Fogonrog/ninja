package ru.gsemenov.walker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;

import ru.gsemenov.walker.util.GameTimer;

public class GameView extends SurfaceView implements SurfaceHolder.Callback  {

    protected GameEngine game = null;

    public GameView(Context context) {
        super(context);
        game = new GameEngine(getContext());
        getHolder().addCallback(this);
    }

    protected final GameTimer renderTimer = new GameTimer(10, () -> {
        Canvas canvas = getHolder().lockCanvas();
        if (canvas != null) {
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            try {
                game.tick();
                game.draw(canvas);
            } catch (Throwable t) {
                throw t;
            } finally {
                getHolder().unlockCanvasAndPost(canvas);
            }
        }
    });

    // Координаты начального касания пальцами для высчитывания смещения и скорости
    float dX, dY;

    /**
     * Управление драконом с помощью свайпов пальцами
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (game == null) {
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dX = event.getRawX();
                dY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float speedX = (event.getRawX() - dX) / getWidth();
                float speedY = (event.getRawY() - dY) / getHeight();
                game.walker.setSpeed((int) (40.0 * speedX), (int) (40.0 * -speedY));
                break;
            default:
                return false;
        }
        return true;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        game.startOrResume();
        renderTimer.startOrResume();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        pause();
    }

    boolean isPaused = false;

    public void pause() {
        isPaused = true;
        game.pauseGame();
        renderTimer.pause();
    }

    public void resume() {
        if (isPaused) {
            game.startOrResume();
            renderTimer.startOrResume();
        }
    }

}
