package ru.gsemenov.walker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import ru.gsemenov.walker.entity.WalkingEntity;
import ru.gsemenov.walker.util.Camera;
import ru.gsemenov.walker.util.GameTimer;
import ru.gsemenov.walker.util.SoundPlayer;
import ru.gsemenov.walker.world.GameWorld;

public class GameEngine {

    /**
     * Игровые сущности
     */
    public WalkingEntity walker = null;

    public Camera camera;

    public GameWorld world;

    /**
     * Таймер анимации
     */
    protected final GameTimer animationTimer = new GameTimer(130, () -> {
        walker.nextFrame();
    });

    /**
     * Игровые ресурсы (шрифты, звуки и пр.)
     */
    protected SoundPlayer backgroundMusic;
    protected Paint fontPaint;

    /**
     * Конструктор игры
     *
     * @param context активности, в которой создана наша игра
     */
    public GameEngine(Context context) {
        initResources(context);
        world = new GameWorld();
        walker = new WalkingEntity(ResourceManager.WALKER);
        walker.spawn(0, 0);
        walker.setMaxSpeed(8);
        walker.setLimits(world.getLimits());
    }

    /**
     * Вспомогательный метод для инициализации необходимых игре ресурсов (мобы, звуки, текстуры и пр.)
     * @param context, из которого будут браться необходимые ресурсы из папки res
     */
    void initResources(Context context) {
        ResourceManager.init(context);
        fontPaint = ResourceManager.FONT;
        backgroundMusic = new SoundPlayer(context, R.raw.soliloquy);
    }

    /**
     * Что должно произойти за один игровой такт
     */
    void tick() {
        walker.tick();
        if (camera != null) {
            camera.centerX = -walker.getX();
            camera.centerY = walker.getY();
        }
    }

    /**
     * Нарисовать игровое поле на холсте
     *
     * @param canvas           холст
     */
    public void draw(Canvas canvas) {
        if (camera == null) {
            camera = new Camera(canvas.getWidth(), canvas.getHeight());
        }
        world.draw(canvas, camera);
        walker.draw(canvas, camera);
        canvas.drawText("vx: " + walker.getVx(), 10, 100, fontPaint);
        canvas.drawText("vy: " + walker.getVy(), 10, 300, fontPaint);
        canvas.drawText("x = " + walker.getX() + ", y = " + walker.getY(), 20, 800, ResourceManager.GREEN);
    }

    /**
     * Запустить игру
     */
    public void startOrResume() {
        animationTimer.startOrResume();
        backgroundMusic.startOrResume();
    }

    /**
     * Приостановить игру
     */
    public void pauseGame() {
        backgroundMusic.pause();
        animationTimer.pause();
    }

}
