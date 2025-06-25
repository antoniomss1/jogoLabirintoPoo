package com.badlogic.Labirinth;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.security.cert.X509Certificate;

public class Chave {

    private Sprite sprite;
    private Texture texture;
    private int posX, posY;

    int TILE_SIZE = Mapa.getInstance().TILE_SIZE;
    int rows = Mapa.getInstance().rows;

    public Chave(Texture texture, int x, int y){
        this.sprite = new Sprite();
        this.posX = x;
        this.posY = y;
        float meio = TILE_SIZE/2;
        this.sprite = new Sprite(texture);

        this.sprite.setSize(meio, meio);

        this.sprite.setPosition(x * TILE_SIZE, y * TILE_SIZE);
        this.sprite.setPosition(x * TILE_SIZE + meio, y * TILE_SIZE+ meio);
    }

    public void colidirComCharacter(Character character){

    }

    public void draw(SpriteBatch batch){
        sprite.draw(batch);
    }



}
