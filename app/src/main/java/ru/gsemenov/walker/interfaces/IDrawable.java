package ru.gsemenov.walker.interfaces;

import android.graphics.Canvas;

import ru.gsemenov.walker.util.Camera;

public interface IDrawable {
    void draw(Canvas canvas, Camera camera);
}
