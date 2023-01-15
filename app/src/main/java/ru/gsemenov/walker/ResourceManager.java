package ru.gsemenov.walker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import ru.gsemenov.walker.util.SpriteTexture;
import ru.gsemenov.walker.world.GameWorld;

/**
 * Вспомогательный класс для получения текстур, шрифтов и пр.
 */
public class ResourceManager {

    public static SpriteTexture WALKER = null;
    public static Paint FONT = null;
    public static Paint GREEN = null;
    public static SpriteTexture[] TREES = null;

    public static SpriteTexture RANDOM_TREE() {
        assert(TREES != null);
        return TREES[GameWorld.rnd.nextInt(TREES.length)];
    }

    public static void init(Context context) {
        {
            Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.walker);
            WALKER = new SpriteTexture.Builder(bmp).addLine(12).addLine(12).addLine(12).addLine(12).build();
        }
        {
            Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
            p.setTypeface(context.getResources().getFont(R.font.pixeloid_sans));
            p.setTextSize(70.0f);
            p.setColor(Color.parseColor("#dd1111"));
            FONT = p;
        }
        {
            Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
            p.setTypeface(context.getResources().getFont(R.font.pixeloid_sans));
            p.setTextSize(70.0f);
            p.setStyle(Paint.Style.STROKE);
            p.setStrokeWidth(3.0f);
            p.setColor(Color.parseColor("#00ff00"));
            GREEN = p;
        }
        {
            TREES = new SpriteTexture[3];
            for (int i = 0; i < TREES.length; i++) {
                Bitmap bmp;
                if (i == 0) {
                    bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.tree07);
                } else if (i == 1) {
                    bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.tree17);
                } else {
                    bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.tree18);
                }
                TREES[i] = new SpriteTexture.Builder(bmp).addLine(1).build();
            }
        }
    }

}
