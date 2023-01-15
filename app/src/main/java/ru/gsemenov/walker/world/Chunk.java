package ru.gsemenov.walker.world;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;

import ru.gsemenov.walker.ResourceManager;
import ru.gsemenov.walker.entity.Entity;
import ru.gsemenov.walker.entity.StaticEntity;
import ru.gsemenov.walker.interfaces.IDrawable;
import ru.gsemenov.walker.util.Camera;

public class Chunk implements IDrawable {

    public final Paint paint = new Paint(ResourceManager.FONT);

    int leftBottomX, leftBottomY, size;
    ArrayList<Entity> entities = new ArrayList<>();

    public Chunk(int leftBottomX, int leftBottomY, int size) {

        paint.setStyle(Paint.Style.STROKE);

        this.leftBottomX = leftBottomX;
        this.leftBottomY = leftBottomY;
        this.size = size;

        for (int i = 0; i < 3; i++) {
            StaticEntity tree = new StaticEntity(ResourceManager.RANDOM_TREE());
            tree.spawn(getRandomPoint().x, getRandomPoint().y);
            entities.add(tree);
        }
    }

    protected Point getRandomPoint() {
        return new Point(
               leftBottomX + GameWorld.rnd.nextInt(size),
                leftBottomY + GameWorld.rnd.nextInt(size)
        );
    }

    @Override
    public void draw(Canvas canvas, Camera camera) {
        if (contains(camera.centerX, camera.centerY)) {
            canvas.drawRect(camera.x(leftBottomX), camera.y(leftBottomY + size), camera.x(leftBottomX + size), camera.y(leftBottomY), ResourceManager.GREEN);
        } else {
            canvas.drawRect(camera.x(leftBottomX), camera.y(leftBottomY + size), camera.x(leftBottomX + size), camera.y(leftBottomY), paint);
        }
        canvas.drawText("(" + leftBottomX + ", " + leftBottomY + ")", camera.x(leftBottomX), camera.y(leftBottomY), paint);
        for (Entity e : entities) {
            e.draw(canvas, camera);
        }
    }

    public boolean contains(float x, float y) {
        return x >= leftBottomX && x < leftBottomX + size && y >= leftBottomY && y < leftBottomY + size;
    }

}
