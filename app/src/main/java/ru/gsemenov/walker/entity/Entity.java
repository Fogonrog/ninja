package ru.gsemenov.walker.entity;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;

import ru.gsemenov.walker.interfaces.IDrawable;
import ru.gsemenov.walker.util.Camera;

public abstract class Entity implements IDrawable {

    protected final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    protected volatile float x, y;
    protected volatile float vx = 0, vy = 0;
    protected volatile float max_v = 2f;
    protected boolean alive = false;

    public float getX() { return x; }

    public float getY() { return y; }

    public float getVx() {
        return vx;
    }

    public float getVy() {
        return vy;
    }

    /**
     * Поместить игровую сущность на игровое поле с координатами
     * @param x координата
     * @param y координата
     */
    public void spawn(float x, float y) {
        this.vx = 0;
        this.vy = 0;
        this.x = x;
        this.y = y;
        alive = true;
    }

    /**
     * Проверить, находится ли сущность на игровом поле
     * @return true, если сущность находится на игровом поле, false - если нет
     */
    public final boolean isAlive() {
        return alive;
    }

    /**
     * Убрать сущность с игрового поля
     */
    public void kill() {
        alive = false;
    }

    /**
     * Отрисовать сущность на холсте
     * @param canvas холст, на котором необходимо отрисовать сущность
     */
    public abstract void draw(Canvas canvas, Camera camera);

    /**
     * Границы игрового поля, в пределах которых сущность может находиться
     */
    protected Rect limits = new Rect(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);

    /**
     * Установить максимальную скорость сущности
     */
    public void setMaxSpeed(float v) {
        this.max_v = v;
    }

    /**
     * Установить границы игрового поля, в пределах которых сущность может находиться
     * @param limits прямоугольник, за который сущность не может выходить
     */
    public void setLimits(Rect limits) {
        this.limits = limits;
    }

    /**
     * Может ли сущность пойти из текущего местоположения в точку (toX, toY) внутри ее limits
     * @param toX координата x
     * @param toY координата y
     * @return
     */
    private final boolean canGo(float toX, float toY) {
        return limits.contains((int) toX, (int)toY);
    }

    /**
     * Установить текущую скорость сущности
     * @param vx горизонтальная скорость
     * @param vy вертикальная скорость
     */
    public void setSpeed(int vx, int vy) {
        double v = Math.sqrt(vx * vx + vy * vy);
        //if (v > max_v) {
            this.vx = (float) (vx * (max_v / v));
            this.vy = (float) (vy * (max_v / v));
        //}
        //this.vx = vx >= 0 ? Math.min(vx, max_vx) : Math.max(vx, -max_vx);
        //this.vy = vy >= 0 ? Math.min(vy, max_vy) : Math.max(vy, -max_vy);
    }

    /**
     * Что должно произойти с сущностью за один такт игры
     */
    public void tick() {
        if (canGo(x + vx, y + vy)) {
            this.x += this.vx;
            this.y += this.vy;
        } else if (canGo(x, y + vy)) {
            this.y += this.vy;
        } else if (canGo(x + vx, y)) {
            this.x += this.vx;
        }
    }

    /**
     * Пересекается ли сущность с другой сущностью
     * @param entity другая сущность
     * @return true, если пересекаются, false - если не пересекаются
     */
    public boolean intersects(Entity entity) {
        if (!this.alive || !entity.alive) {
            return false;
        }
        return false;
    }

}
