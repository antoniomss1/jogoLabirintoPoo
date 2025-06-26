package com.badlogic.Labirinth;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Porta {
    private Sprite sprite;
    private boolean estado; // true = aberta, false = fechada
    private int posX, posY;
    private int dono;
    int TILE_SIZE = Mapa.getInstance().TILE_SIZE;
    int rows = Mapa.getInstance().rows;

    private Texture texturaAberta;
    private Texture texturaFechada;

    public Porta(Texture texturaAberta, Texture texturaFechada, boolean estadoInicial, int x, int y, int dono) {
        this.estado = estadoInicial;
        this.posX = x;
        this.posY = y;
        this.texturaAberta = texturaAberta;
        this.texturaFechada = texturaFechada;

        Texture textura = estado ? texturaAberta : texturaFechada;
        this.sprite = new Sprite(textura);
        this.sprite.setPosition(x * TILE_SIZE, (rows - 1 - y) * TILE_SIZE);
//        this.sprite.setPosition(x*TILE_SIZE, y*TILE_SIZE);

        this.dono = dono;
    }

    public void alternar( ) {
        estado = !estado;
        Texture novaTextura = estado ? this.texturaAberta : this.texturaFechada;
        sprite.setTexture(novaTextura);
        sprite.setBounds(sprite.getX(), sprite.getY(), novaTextura.getWidth(), novaTextura.getHeight());

        // Atualiza o mapa
        char novoChar = estado ? ' ' : '\\';
        Mapa.getInstance().setTile(posX , posY , novoChar);
    }

    public boolean contem(float x, float y) {
        return sprite.getBoundingRectangle().contains(x, y);
    }

    public void draw(com.badlogic.gdx.graphics.g2d.SpriteBatch batch) {
        sprite.draw(batch);
    }

    public boolean getEstado() {
        return estado;
    }

    public int getX() {
        return posX;
    }

    public int getY() {
        return posY;
    }

    public Sprite getSprite() {
        return this.sprite;
    }

    public int getDono() {
        return dono;
    }

    public void setEstado(boolean novoEstado) {
        this.estado = novoEstado;
    }
    public Texture getTexturaAberta(){
        return this.texturaAberta;
    }

    public Texture getTexturaFechada() {
        return texturaFechada;
    }
}
