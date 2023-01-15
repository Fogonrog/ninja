package ru.gsemenov.walker.world;

import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

import ru.gsemenov.walker.interfaces.IDrawable;
import ru.gsemenov.walker.util.Camera;

public class GameWorld implements IDrawable {

    public static final Random rnd = new Random();
    public static final int CHUNK_SIZE = 1000;
    public static final int WORLD_SIZE = 5;

    Chunk[][] world = new Chunk[WORLD_SIZE][WORLD_SIZE];

    public GameWorld() {
        for (int i = 0; i < WORLD_SIZE; i++) {
            for (int j = 0; j < WORLD_SIZE; j++) {
                // TODO: задание 2 - в следующих строках ошибка
                // Чанки должны нумероваться слева-направо, снизу-вверх (сейчас это не так)
                int x = i - WORLD_SIZE / 2;
                int y = j - WORLD_SIZE / 2;
                world[i][j] = new Chunk((x * CHUNK_SIZE) - CHUNK_SIZE / 2, (y * CHUNK_SIZE) - CHUNK_SIZE / 2, CHUNK_SIZE);
            }
        }
    }

    Chunk getCenterChunk() {
        return world[world.length / 2][world[0].length / 2];
    }

    @Override
    public void draw(Canvas canvas, Camera camera) {

        for (int i = 0; i < WORLD_SIZE; i++) {
            for (int j = 0; j < WORLD_SIZE; j++) {
                world[i][j].draw(canvas, camera);
                canvas.drawText("i = " + i + ", j = " + j, camera.x(world[i][j].leftBottomX) + 30, camera.y(world[i][j].leftBottomY) - 300, world[i][j].paint);

//                if (world[i][j].contains(camera.centerX, camera.centerY)) {
//                    for (int a = Math.max(0, i - 1); a <= Math.min(WORLD_SIZE - 1, i + 1); a++) {
//                        for (int b = Math.max(0, j - 1); b <= Math.min(WORLD_SIZE - 1, j + 1); b++) {
//                            world[a][b].draw(canvas, camera);
//                            canvas.drawText("(" + world[a][b].leftBottomX + ", " + world[a][b].leftBottomY + ")", camera.x(world[a][b].leftBottomX), camera.y(world[a][b].leftBottomY) - 300, world[a][b].paint);
//                        }
//                    }
//                }

            }
        }
    }

    public Rect getLimits() {
        return new Rect(- CHUNK_SIZE * WORLD_SIZE / 2,
                - CHUNK_SIZE * WORLD_SIZE / 2,
                CHUNK_SIZE * WORLD_SIZE / 2,
                CHUNK_SIZE * WORLD_SIZE / 2);
    }

}
