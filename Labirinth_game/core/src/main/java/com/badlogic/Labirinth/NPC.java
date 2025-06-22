package com.badlogic.Labirinth;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class NPC {

    protected Sprite sprite;
    protected float x, y; // posição no mapa (em tiles)
    protected Texture textura;
    int tileSize = Mapa.getInstance().TILE_SIZE;
    private float Width;
    private float Height;


    public NPC(Texture textura, int x, int y) {
        this.textura = textura;
        this.x = x;
        this.y = y;
        sprite = new Sprite(textura);
        sprite.setSize(tileSize-20, tileSize-20);

//        sprite.setPosition(x ,y);

//        sprite.setX(x * tileSize);
//        sprite.setY((Mapa.getInstance().getAltura() - 1 - y) * tileSize);

        sprite.setX(x * tileSize);
        sprite.setY(y * tileSize);

        this.Width =  (float) Mapa.getInstance().TILE_SIZE -20;
        this.Height = (float) Mapa.getInstance().TILE_SIZE -20;
    }
//
    public abstract void update(float delta);

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    protected boolean podeMoverPara(float newX, float newY) {
//        int tileX = (int) (newX / Mapa.getInstance().TILE_SIZE);
//        int tileY = Mapa.getInstance().getDadosDoMapa().length - 1 - (int) (newY / Mapa.getInstance().TILE_SIZE);

//        int tileX = (int) Math.floor(newX / tileSize);
//        int tileY = Mapa.getInstance().getAltura() - 1 - (int) Math.floor(newY / tileSize);


        int tileX = (int)(newX / Mapa.getInstance().TILE_SIZE);
        int tileY =  (int)(newY / Mapa.getInstance().TILE_SIZE);

        //        Mapa.getInstance().getDadosDoMapa().length - 1 -

        char tile = Mapa.getInstance().getTile(tileX, tileY);
        return tile == ' '; // evita paredes e portas
    }

    public Vector2 getPos() {
        return new Vector2(sprite.getX(), sprite.getY());
    }

    public float getNPCWidth(){
        return Width;
    }

    public float getNPCHeight(){
        return Height;
    }
}
