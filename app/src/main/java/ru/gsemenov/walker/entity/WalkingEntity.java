package ru.gsemenov.walker.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import ru.gsemenov.walker.ResourceManager;
import ru.gsemenov.walker.util.Camera;
import ru.gsemenov.walker.util.SpriteTexture;

public class WalkingEntity extends Entity {

    private final float SPEED_LIMIT_X = 0.05f;
    private final float SPEED_LIMIT_Y = 0.05f;

    private final SpriteTexture texture;
    private final int N_ANIMATION_STATES;
    private int state = 0;

    public WalkingEntity(SpriteTexture texture) {
        assert(texture.countLines() > 0);
        this.texture = texture;
        this.N_ANIMATION_STATES = texture.countFramesInLine(0);
    }

    @Override
    public void draw(Canvas canvas, Camera camera) {
        if (!alive) { return; }
        int direction = 0;
        boolean walking = true;

        if (Math.abs(vx) < SPEED_LIMIT_X && Math.abs(vy) < SPEED_LIMIT_Y) {
            walking = false;
        } else if (Math.abs(vx) < SPEED_LIMIT_X) {
            direction = vy > 0 ? 3 : 0;
        } else if (Math.abs(vy) < SPEED_LIMIT_Y) {
            direction = vx > 0 ? 2 : 1;
        } else {
            double angle = Math.atan2(vy, vx);
            if (angle < 0) { angle = 2 * Math.PI + angle; }
            if (angle <= Math.PI / 4 || angle >= 7.0 * Math.PI / 4) {
                direction = 2;
            } else if (angle > Math.PI / 4 && angle <= 3.0 * Math.PI / 4) {
                direction = 3;
            } else if (angle > 3.0 * Math.PI / 4 && angle <= 5.0 * Math.PI / 4) {
                direction = 1;
            }
        }



//        if (vx >= SPEED_LIMIT) {
//            if (vy >= SPEED_LIMIT) {
//                direction = 0;
//            } else if (vy <= -SPEED_LIMIT) {
//                direction = 0;
//            } else {
//                direction = 2;
//            }
//        } else if (vx <= -SPEED_LIMIT) {
//            if (vy >= SPEED_LIMIT) {
//                direction = 0;
//            } else if (vy <= -SPEED_LIMIT) {
//                direction = 0;
//            } else {
//                direction = 1;
//            }
//        } else {
//            if (vy >= SPEED_LIMIT) {
//                direction = 3;
//            } else if (vy <= -SPEED_LIMIT) {
//                direction = 0;
//            } else {
//                walking = false;
//                direction = 0;
//            }
//        }
        Bitmap bmp = texture.getScaledFrame(direction, walking ? state : 0, camera.zoom);
        canvas.drawBitmap(bmp, camera.x(x) - bmp.getWidth() / 2, camera.y(y) - bmp.getHeight() / 2, paint);
        canvas.drawCircle(camera.x(x), camera.y(y), 3, ResourceManager.FONT);
    }

    public void nextFrame() {
        if (state + 1 == N_ANIMATION_STATES) {
            state = 0;
        } else {
            state++;
        }
    }

}
