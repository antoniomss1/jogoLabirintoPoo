package com.badlogic.Labirinth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Jogador extends Character{
//    private String nome;

    private int Up, Down, Right, Left, button;
    private Sprite jogadorSprite;

    float jogadorWidth;
    float jogadorHeight;

    private float speed = 250f;

    private boolean wasTouched = false;
    Vector2 touchPos;

    private static int i = -1;
    private int ID=0;

    private static int pontos=0;

    private int cahvesColetadas;

    public boolean colidiuComChave(){
        int TILE_SIZE = Mapa.getInstance().TILE_SIZE;
        int i=0;
        for(Chave c: Mapa.getInstance().getChaves()){

            if(this.getX() == (float) c.getX() ){
                if(this.getY() == (float) c.getY()  ){
                    Mapa.getInstance().tirarChaveDoMapa(c);
                    return true;
                }
            }
            i++;
        }
        return  false;
    }

    public Jogador(int up, int down, int right, int left, int doorButton, Texture gamerTexture, int initialX, int initialY) {
        this.Down   = down;
        this.Up     = up;
        this.Left   = left;
        this.Right  = right;
        this.button = doorButton;
        this.jogadorSprite = new Sprite(gamerTexture);
        jogadorSprite.setSize((float) Mapa.getInstance().TILE_SIZE-20, (float) Mapa.getInstance().TILE_SIZE-20);
        jogadorWidth = jogadorSprite.getWidth();
        jogadorHeight = jogadorSprite.getHeight();
        jogadorSprite.setX(initialX);
        jogadorSprite.setY(initialY);
        touchPos = new Vector2();
        i++;
        this.ID=i;
    }

    public boolean updatePlayer(Viewport viewport, Portas portas){

        moviment();
        doorInvertion(portas);
        zoomControl(viewport);
        setSpeed();

        if (this.colidiuComChave()){
            pontos++;
            System.out.println("colidiu com chave: "+pontos+" pontos");
            if(pontos == Mapa.getInstance().getNumChaves()){
                return true;
            }
        }
        return false;
//        if (Gdx.input.isTouched()) {
//            if (!wasTouched) {
//                touchPos.set(Gdx.input.getX(), Gdx.input.getY());
//                viewport.unproject(touchPos);
//                System.out.println("clicked: X = "+ (int)touchPos.x/Mapa.getInstance().TILE_SIZE + " Y = "+(int)touchPos.y/Mapa.getInstance().TILE_SIZE);
//
//                int portaClicada = portas.getClickedDoor(touchPos.x, touchPos.y);
//                portas.invertDoor(portaClicada, this);
//                wasTouched = true;
//            }
//
//        } else {
//            wasTouched = false; // apenas aqui reseta o toque
//        }

    }

//    public void updatePlayer(Viewport viewport){
//
////        zoomControl(viewport);
////        setSpeed();
//
//    }


//    public void escreverNaCarta(Carta carta, String texto) {
//        carta.editarConteudo(texto);
//    }
//
//    public void enviarCarta(Carta carta, Jogador outroJogador, Papeis PapeisManager) {
//        PapeisManager.getInstance().enviarCarta(this, outroJogador, carta);
//    }
//
//    public void receberCarta(Carta carta) {
//        System.out.println("Recebeu carta: " + carta.getConteudo());
//    }

    public float getJogadorWidth(){
        return jogadorWidth;
    }

    public float getJogadorHeight(){
        return jogadorHeight;
    }

    public void draw(SpriteBatch batch) {
        jogadorSprite.draw(batch);
    }

    public Sprite getSprite() {
        return jogadorSprite;
    }


    public void atualizarCamera(Viewport viewport) {
        OrthographicCamera cam = (OrthographicCamera) viewport.getCamera();
        cam.position.set(jogadorSprite.getX() + jogadorWidth / 2f,
            jogadorSprite.getY() + jogadorHeight / 2f, 0);
    }

    private void moviment(){
        float moveX=0;
        float moveY=0;
        float delta = Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Right)) {
            //            jogadorSprite.translateX(speed * delta); // move the Wizard right
            moveX += speed * delta;
        }
        else if (Gdx.input.isKeyPressed(Left)) {
            //            jogadorSprite.translateX(-speed * delta); // move the Wizard leff
            moveX -=speed * delta;
        }
        if(Gdx.input.isKeyPressed(Up)){
            //            jogadorSprite.translateY(speed * delta);
            moveY += speed * delta;
        }
        else if(Gdx.input.isKeyPressed(Down)){
            //            jogadorSprite.translateY(-speed * delta);
            moveY -= speed * delta;
        }

        float newX = jogadorSprite.getX() + moveX;
        float newY = jogadorSprite.getY() + moveY;


        if (!Mapa.getInstance().isCollidingWithWall(newX, jogadorSprite.getY(), this.jogadorWidth, this.jogadorHeight)) {
            jogadorSprite.translateX(moveX);
        }
        if (!Mapa.getInstance().isCollidingWithWall(jogadorSprite.getX(), newY, this.jogadorWidth, this.jogadorHeight)) {
            jogadorSprite.translateY(moveY);
        }
    }

    public int getX(){
        return (int)this.getSprite().getX()/Mapa.getInstance().TILE_SIZE;
    }

    public int getY(){
        return Mapa.getInstance().rows - 1 -(int)(this.getSprite().getY())/Mapa.getInstance().TILE_SIZE;
    }


    //door Invertion
    private void doorInvertion(Portas portas) {
        if (!Gdx.input.isKeyJustPressed(this.button)) return;

        Sprite[] portaSprites = portas.getSprites();
        if (portaSprites == null || portaSprites.length == 0) return;

        float jogadorCentroX = jogadorSprite.getX() + jogadorSprite.getWidth() / 2f;
        float jogadorCentroY = jogadorSprite.getY() + jogadorSprite.getHeight() / 2f;

        int tileSize = Mapa.getInstance().TILE_SIZE;
        int jogadorTileX = (int)(jogadorCentroX / tileSize);
        int jogadorTileY = Mapa.getInstance().getRows() - 1 - (int)(jogadorCentroY / tileSize);

        int[][] offsets = {
            {-1, -1}, {-1,  0}, {-1, 1},
            { 0, -1},          { 0, 1},
            { 1, -1}, { 1,  0}, { 1, 1}
        };

        int portaMaisProxima = -1;
        float menorDist = Float.MAX_VALUE;

        for (int i = 0; i < portaSprites.length; i++) {
            Sprite porta = portaSprites[i];
            float portaCentroX = porta.getX() + porta.getWidth() / 2f;
            float portaCentroY = porta.getY() + porta.getHeight() / 2f;

            int portaTileX = (int)(portaCentroX / tileSize);
            int portaTileY = Mapa.getInstance().getRows() - 1 - (int)(portaCentroY / tileSize);

            for (int[] offset : offsets) {
                int nx = jogadorTileX + offset[0];
                int ny = jogadorTileY + offset[1];

                if (portaTileX == nx && portaTileY == ny) {
                    float dx = jogadorCentroX - portaCentroX;
                    float dy = jogadorCentroY - portaCentroY;
                    float dist2 = dx * dx + dy * dy;

                    if (dist2 < menorDist) {
                        menorDist = dist2;
                        portaMaisProxima = i;
                    }
                }
            }
        }

        if (portaMaisProxima != -1) {
            portas.invertDoor(portaMaisProxima, this.ID);
        }
    }


    //    public void setZoom(){
//
//    }
    private void zoomControl(Viewport viewport){
        if(Gdx.input.isKeyPressed(Input.Keys.Z)){
            ((OrthographicCamera)viewport.getCamera()).zoom-=.01f;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.X)){
            ((OrthographicCamera)viewport.getCamera()).zoom+=.01f;
        }

    }

    private void setSpeed(){
//        if(Gdx.input.isKeyPressed(Input.Keys.K)){
//            speed+=10f;
//        }else if(Gdx.input.isKeyPressed(Input.Keys.L)){
//            speed-=10f;
//        }
    }

}


//codigo para movimentação com o mouse:
//            touchPos.set(Gdx.input.getX(), Gdx.input.getY()); // Get where the touch happened on screen
//            viewport.unproject(touchPos); // Convert the units to the world units of the viewport
//
//            float dirX = touchPos.x - jogadorSprite.getX();
//            float dirY = touchPos.y - jogadorSprite.getY();
//            float norma = (float) Math.sqrt(dirX * dirX + dirY * dirY);
//
//            if(norma != 0){
//                moveX += dirX / norma * speed * delta;
//                moveY += dirY / norma * speed * delta;
//            }
