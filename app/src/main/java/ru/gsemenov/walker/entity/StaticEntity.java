package ru.gsemenov.walker.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import ru.gsemenov.walker.util.Camera;
import ru.gsemenov.walker.util.SpriteTexture;

public class StaticEntity extends Entity {

    private final SpriteTexture texture;
    private final int N_ANIMATION_STATES;
    private int state = 0;

    public StaticEntity(SpriteTexture texture) {
        assert (texture.countLines() > 0);
        this.texture = texture;
        this.N_ANIMATION_STATES = texture.countFramesInLine(0);
    }

    @Override
    public void draw(Canvas canvas, Camera camera) {
        if (!alive) {
            return;
        }
        Bitmap bmp = texture.getScaledFrame(0, state, camera.zoom);
        canvas.drawBitmap(bmp, camera.x(x) - bmp.getWidth() / 2, camera.y(y) - bmp.getHeight() / 2, paint);
    }

    public void nextFrame() {
        if (state + 1 == N_ANIMATION_STATES) {
            state = 0;
        } else {
            state++;
        }
    }

}
