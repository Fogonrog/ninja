package ru.gsemenov.walker.util;

import android.graphics.Canvas;

import ru.gsemenov.walker.world.Chunk;

public class Camera {

    public float zoom = 1.0f;

    public float centerX = 0.0f;
    public float centerY = 0.0f;

    public final int screenW;
    public final int screenH;

    public Camera(int W, int H) {
        screenW = W;
        screenH = H;
    }

    public float x(float x) {
        //TODO: задание 2 - в этой строке ошибка
        return screenW / 2 + zoom * (centerX + x);
    }

    public float y(float y) {
        return screenH / 2 + zoom * (centerY - y);
    }

}
