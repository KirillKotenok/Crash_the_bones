package com.baloonink.crashthebones;

import java.util.ArrayList;
import java.util.List;

public class SpriteFactory {
    public Sprite getAnotherSprite(Sprite sprite){
        return new Sprite(sprite.getGameView(), sprite.getBitmap());
    }

    public List<Sprite> getAnotherSprite(Sprite sprite, int numberOfCopy){
        List<Sprite> list = new ArrayList<>();
        for (int i=0; i<numberOfCopy; i++){
            list.add(new Sprite(sprite.getGameView(), sprite.getBitmap()));
        }
        return list;
    }
}
