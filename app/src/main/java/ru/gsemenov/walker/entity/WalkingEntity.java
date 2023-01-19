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
        int direction = 6;
        boolean walking = true;

        if (Math.abs(vx) < SPEED_LIMIT_X && Math.abs(vy) < SPEED_LIMIT_Y) {
            walking = false;
        //} else if (Math.abs(vx) < SPEED_LIMIT_X) {
            //direction = vy > 0 ? 3 : 0;
        //} else if (Math.abs(vy) < SPEED_LIMIT_Y) {
            //direction = vx > 0 ? 2 : 1;
        } else {
            double angle = Math.atan2(vy, vx);
            if (angle < 0) {
                angle = 2 * Math.PI + angle;
                System.out.println(angle);
            }

            if (angle < 0.39269908169 || angle> 5.89048622549) {
                direction = 4;
                System.out.println("Иду по направлению вправо");
            } else if (angle> 0.39269908169 && angle<1.17809724507){
                direction = 3;
                System.out.println("Иду по направлению вправо-вверх");
            } else if (angle>1.17809724507 && angle<1.96349540845){
                direction = 2;
                System.out.println("Иду по направлению вверх");
            } else if (angle > 1.96349540845 &&  angle < 2.74889357183) {
                direction = 1;
                System.out.println("Иду по направлению влево-вверх");
            } else if (angle > 2.74889357183 && angle < 3.53429173521) {
                direction = 0;
                System.out.println("Иду по направлению влево");
            }else if (angle >3.53429173521 && angle<4.31968989859){
                direction = 7;
                System.out.println("Иду по направлению влево-вниз");
            }else if (angle >4.31968989859 && angle<5.10508806197){
                direction = 6;
                System.out.println("Иду по направлению вниз");
            }else if (angle >5.10508806197 && angle<5.89048622535) {
                direction = 5;
                System.out.println("Иду по направлению вправо-вниз");
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
