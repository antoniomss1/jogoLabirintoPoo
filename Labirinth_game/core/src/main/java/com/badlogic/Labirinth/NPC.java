package com.badlogic.Labirinth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;


public abstract class NPC extends Character {

    // ... (código existente: sprite, textura, x, y, etc.)
    protected Sprite sprite;
    protected Texture textura;
    protected float x, y; // posição no mapa (em tiles)

    int tileSize = Mapa.getInstance().TILE_SIZE;

    private float Width;
    private float Height;
    private float speed;

    private Vector2 destino = null; // posição-alvo em pixels
    private boolean emMovimento = false;

    public NPC(Texture textura, int x, int y, float speed) {
        this.textura = textura;
        this.x = x;
        this.y = y;
        sprite = new Sprite(textura);
        sprite.setSize(tileSize-20, tileSize-20);

        // Posição inicial do sprite no mundo (considerando a inversão Y)
        float worldX = x * tileSize;
        float worldY = (Mapa.getInstance().getAltura() - 1 - y) * tileSize;
        sprite.setPosition(worldX, worldY);

        this.Width =  (float) Mapa.getInstance().TILE_SIZE -20;
        this.Height = (float) Mapa.getInstance().TILE_SIZE -20;
        this.speed = speed;
    }

    public abstract void update(float delta);

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    // CORREÇÃO AQUI: Conversão correta do eixo Y
    protected boolean podeMoverPara(float newX, float newY) {
        // Pega o centro do sprite para verificar o tile alvo
        float checkX = newX + sprite.getWidth() / 2;
        float checkY = newY + sprite.getHeight() / 2;

        // Converte coordenadas do MUNDO para coordenadas do GRID
        int tileX = (int) (checkX / tileSize);
        int tileY = Mapa.getInstance().getAltura() - 1 - (int) (checkY / tileSize);

        char tile = Mapa.getInstance().getTile(tileX, tileY);
        // O NPC pode se mover em espaços vazios (não em portas fechadas '\')
        return (tile == ' ' || tile == '\\');
    }

    public void updateMovimento(float delta) {
        if (emMovimento) {
            atualizarMovimento(delta);
        }
    }

    // ... (resto da classe NPC, getPos, getters, etc.)
    // O método movement() e atualizarMovimento() estão bons!
    public Vector2 getPos() {
        return new Vector2(sprite.getX(), sprite.getY());
    }

    public float getNPCWidth(){
        return Width;
    }

    public float getNPCHeight(){
        return Height;
    }

    public boolean movement(int move) {
        if (emMovimento) {
            // Já está em movimento, não pode iniciar um novo
            return false;
        }

        float dirX = 0, dirY = 0;

        switch (move) {
            case 1: dirX = 1; break; // direita
            case 2: dirX = -1; break; // esquerda
            case 3: dirY = 1; break; // cima
            case 4: dirY = -1; break; // baixo
            default: return false;
        }

        // Calcula a posição final do centro do tile alvo
        float alvoX = ( (int)(sprite.getX()/tileSize) + dirX) * tileSize;
        float alvoY = ( (int)(sprite.getY()/tileSize) + dirY) * tileSize;

        // O método podeMoverPara espera as coordenadas do canto inferior esquerdo do sprite
        // Vamos checar o tile para o qual estamos indo
        float checkX = sprite.getX() + dirX * tileSize;
        float checkY = sprite.getY() + dirY * tileSize;

        if (podeMoverPara(checkX, checkY)) {
            destino = new Vector2(alvoX, alvoY);
            emMovimento = true;
            return true;
        }

        return false;
    }

    public boolean isEmMovimento() {
        return emMovimento;
    }

    private void atualizarMovimento(float delta) {
        if (destino == null) return;

        Vector2 atual = new Vector2(sprite.getX(), sprite.getY());
        Vector2 direcao = new Vector2(destino).sub(atual);

        // Se a distância for muito pequena, considera que chegou
        if (direcao.len() < speed * delta) {
            sprite.setPosition(destino.x, destino.y); // ajusta para alinhar perfeitamente no tile
            emMovimento = false;
            destino = null;
            return;
        }

        direcao.nor().scl(speed * delta);
        sprite.translate(direcao.x, direcao.y);
    }

    public float getSpeed() {
        return speed;
    }
}

//
//public abstract class NPC extends Character{
//
//    protected Sprite sprite;
//    protected Texture textura;
//    protected float x, y; // posição no mapa (em tiles)
//
//    int tileSize = Mapa.getInstance().TILE_SIZE;
//
//    private float Width;
//    private float Height;
//    private float speed;
//
//    private Vector2 destino = null; // posição-alvo
//    private boolean emMovimento = false;
//
//    public NPC(Texture textura, int x, int y, float speed) {
//        this.textura = textura;
//        this.x = x;
//        this.y = y;
//        sprite = new Sprite(textura);
//        sprite.setSize(tileSize-20, tileSize-20);
//        sprite.setX(x * tileSize);
//        sprite.setY(y * tileSize);
//
//        this.Width =  (float) Mapa.getInstance().TILE_SIZE -20;
//        this.Height = (float) Mapa.getInstance().TILE_SIZE -20;
//        this.speed = speed;
//    }
////
//    public abstract void update(float delta);
//
//    public void draw(SpriteBatch batch) {
//        sprite.draw(batch);
//    }
//
//    protected boolean podeMoverPara(float newX, float newY) {
////        int tileX = (int) (newX / Mapa.getInstance().TILE_SIZE);
////        int tileY = Mapa.getInstance().getDadosDoMapa().length - 1 - (int) (newY / Mapa.getInstance().TILE_SIZE);
//
////        int tileX = (int) Math.floor(newX / tileSize);
////        int tileY = Mapa.getInstance().getAltura() - 1 - (int) Math.floor(newY / tileSize);
//
//
//        int tileX = (int)(newX / Mapa.getInstance().TILE_SIZE);
//        int tileY =  (int)(newY / Mapa.getInstance().TILE_SIZE);
//
//        //        Mapa.getInstance().getDadosDoMapa().length - 1 -
//
//        char tile = Mapa.getInstance().getTile(tileX, tileY);
//        return tile == ' '; // evita paredes e portas
//    }
//
//    public Vector2 getPos() {
//        return new Vector2(sprite.getX(), sprite.getY());
//    }
//
//    public float getNPCWidth(){
//        return Width;
//    }
//
//    public float getNPCHeight(){
//        return Height;
//    }
//
//    public boolean movement(int move) {
//        if (emMovimento) {
//            atualizarMovimento(Gdx.graphics.getDeltaTime());
//            return true;
//        }
//
//        float dirX = 0, dirY = 0;
//
//        switch (move) {
//            case 1: dirX = 1; break; // direita
//            case 2: dirX = -1; break; // esquerda
//            case 3: dirY = 1; break; // cima
//            case 4: dirY = -1; break; // baixo
//            default: return false;
//        }
//
//        float alvoX = sprite.getX() + dirX * tileSize;
//        float alvoY = sprite.getY() + dirY * tileSize;
//
//        if (podeMoverPara(alvoX, alvoY)) {
//            destino = new Vector2(alvoX, alvoY);
//            emMovimento = true;
//            return true;
//        }
//
//        return false;
//    }
//
//    public boolean isEmMovimento() {
//        return emMovimento;
//    }
//
//    private void atualizarMovimento(float delta) {
//        if (destino == null) return;
//
//        Vector2 atual = new Vector2(sprite.getX(), sprite.getY());
//        Vector2 direcao = new Vector2(destino).sub(atual);
//
//        if (direcao.len() < 1f) {
//            sprite.setPosition(destino.x, destino.y); // ajusta para alinhar
//            emMovimento = false;
//            destino = null;
//            return;
//        }
//
//        direcao.nor().scl(speed * delta);
//        sprite.translate(direcao.x, direcao.y);
//    }
//
////    public boolean movement(int move){
////        float moveX=0;
////        float moveY=0;
////        float delta = Gdx.graphics.getDeltaTime();
////
////        switch (move){
////            case (1):
////                moveX += speed * delta;
////                break;
////            case (2):
////                moveX -= speed * delta;
////                break;
////            case (3):
////                moveY += speed * delta;
////                break;
////            case (4):
////                moveY -= speed * delta;
////                break;
////            default:
////                break;
////        }
////
////        float newX = this.sprite.getX() + moveX;
////        float newY = this.sprite.getY() + moveY;
////
////        boolean a = false;
////
////        if (!Mapa.getInstance().isCollidingWithWall(newX, this.sprite.getY(), this.Width, this.Height)) {
////            this.sprite.translateX(moveX);
////            a = true;
////        }
////        if (!Mapa.getInstance().isCollidingWithWall(this.sprite.getX(), newY, this.Width, this.Height)) {
////            this.sprite.translateY(moveY);
////            a = true;
////        }
////
////        return a;
////
////    }
//
//    public float getSpeed() {
//        return speed;
//    }
//}
