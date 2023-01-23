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
    float dx, dy;

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
                dx = event.getRawX();
                dy = event.getRawY();
                System.out.println(((getWidth()/11)*10) + " < " + dx + " < " + getWidth());
                System.out.println(((getHeight()/11)*10) + " < " + dy + " < " + getHeight());
                if (dx>((getWidth()/11)*5) && dx<((getWidth()/11)*6) && dy>(getHeight()/2)){
                    game.walker.setSpeed(0,-8);
                    break;
                }else if (dx>((getWidth()/11)*5) && dx<((getWidth()/11)*6) && dy<(getHeight()/2)){
                    game.walker.setSpeed(0,8);
                    break;
                }else if (dy>((getHeight()/11)*5) && dy<((getHeight()/11)*6) && dx>(getWidth()/2)){
                    game.walker.setSpeed(8,0);
                    break;
                }else if (dy>((getHeight()/11)*4) && dy<((getHeight()/11)*7) && dx<(getWidth()/2)) {
                    game.walker.setSpeed(-8, 0);
                    break;
                }else if(dx>((getWidth()/11)*6) && dx<((getWidth()/11)*7) && (getHeight() - dy)>((getHeight()/11)*6) && (getHeight() - dy)<((getHeight()/11)*7) || (dx>((getWidth()/11)*8) && dx<((getWidth()/11)*9) && (getHeight() - dy)>((getHeight()/11)*8) && (getHeight() - dy)<((getHeight()/11)*9)) || (dx>((getWidth()/11)*9) && dx<((getWidth()/11)*10) && (getHeight() - dy)>((getHeight()/11)*9) && (getHeight() - dy)<((getHeight()/11)*10))){
                    game.walker.setSpeed(8, 8);
                    break;
                }else if(dx>((getWidth()/11)*7) && dx<((getWidth()/11)*8) && (getHeight() - dy)>((getHeight()/11)*7) && (getHeight() - dy)<((getHeight()/11)*8)){
                    game.walker.setSpeed(8, 8);
                    break;
                }else if(dx>((getWidth()/11)*10) && dx<getWidth() && (getHeight() - dy)>((getHeight()/11)*10) && (getHeight() - dy)<getHeight()){
                    game.walker.setSpeed(8, 8);
                    break;
                }else if((dx>((getWidth()/11)*6) && dx<((getWidth()/11)*7) && (getHeight() - dy)>((getHeight()/11)*4) && (getHeight() - dy)<((getHeight()/11)*5)) || (dx>((getWidth()/11)*7) && dx<((getWidth()/11)*8) && (getHeight() - dy)>((getHeight()/11)*3) && (getHeight() - dy)<((getHeight()/11)*4)) || (dx>((getWidth()/11)*8) && dx<((getWidth()/11)*9) && (getHeight() - dy)>((getHeight()/11)*2) && (getHeight() - dy)<((getHeight()/11)*3)) || ((dx>((getWidth()/11)*9) && dx<((getWidth()/11)*10) && (getHeight() - dy)>(getHeight()/11) && (getHeight() - dy)<((getHeight()/11)*2))) || (dx>((getWidth()/11)*10) && dx<getWidth() && (getHeight() - dy)>0 && (getHeight() - dy)<(getHeight()/11))){
                    game.walker.setSpeed(8, -8);
                    break;
                }else if(dx>((getWidth()/11)*6) && dx<((getWidth()/11)*7) && (getHeight() - dy)>0 && (getHeight() - dy)<((getHeight()/11)*2)){
                    game.walker.setSpeed(2, -8);
                    break;
                }else if(dx>((getWidth()/11)*4) && dx<((getWidth()/11)*5) && (getHeight() - dy)>0 && (getHeight() - dy)<((getHeight()/11)*2)){
                    game.walker.setSpeed(-2, -8);
                    break;
                }else if(dx>((getWidth()/11)*9) && dx<getWidth() && (getHeight() - dy)>((getHeight()/11)*4)&& (getHeight() - dy)<((getHeight()/11)*5)){
                    game.walker.setSpeed(8, -2);
                    break;
                } else if (dx>((getWidth()/11)*9) && dx<getWidth() && (getHeight() - dy)>((getHeight()/11)*6)&& (getHeight() - dy)<((getHeight()/11)*7)) {
                    game.walker.setSpeed(8, 2);
                    break;
                } else if(dx>((getWidth()/11)*4) && dx<((getWidth()/11)*5) && (getHeight() - dy)>((getHeight()/11)*9) && (getHeight() - dy)<getHeight()){
                    game.walker.setSpeed(-2, 8);
                    break;
                }else if(dx>((getWidth()/11)*6) && dx<((getWidth()/11)*7) && (getHeight() - dy)>((getHeight()/11)*9) && (getHeight() - dy)<getHeight()){
                    game.walker.setSpeed(2, 8);
                    break;
                }else if(dx>0 && dx<((getWidth()/11)*2) && (getHeight() - dy)>((getHeight()/11)*4) && (getHeight() - dy)<((getHeight()/11)*5)){
                    game.walker.setSpeed(-8, -2);
                    break;
                }else if(dx>0 && dx<((getWidth()/11)*2) && (getHeight() - dy)>((getHeight()/11)*6) && (getHeight() - dy)<((getHeight()/11)*7)){
                    game.walker.setSpeed(-8, 2);
                    break;
                }

                //break;
            //case MotionEvent.ACTION_MOVE:
                //float speedX = (event.getRawX() - dX) / getWidth();
                //float speedY = (event.getRawY() - dY) / getHeight();
                //int lol_x = (int) (40 * speedX);
                //int lol_y = (int) (40 * -speedY);
                //game.walker.setSpeed(lol_x, lol_y);
                //break;
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
