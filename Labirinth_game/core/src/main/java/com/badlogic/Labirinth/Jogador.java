package com.badlogic.Labirinth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Jogador {
//    private String nome;

    private int Up, Down, Right, Left, button;
    private Sprite jogadorSprite;

    float jogadorWidth;
    float jogadorHeight;

    private float speed = 400f;

    private boolean wasTouched = false;
    Vector2 touchPos;

    private static int i = -1;
    private int ID=0;

    //int []portas;/////////////////////////////////////////////////////////

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

    public void updatePlayer(Viewport viewport, Portas portas){

        moviment();
        doorInvertion(portas);
        zoomControl(viewport);
        setSpeed();


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


        if (!Mapa.getInstance().isCollidingWithWall(newX, jogadorSprite.getY(), this)) {
            jogadorSprite.translateX(moveX);
        }
        if (!Mapa.getInstance().isCollidingWithWall(jogadorSprite.getX(), newY, this)) {
            jogadorSprite.translateY(moveY);
        }
    }

//    private void doorInvertion(){
//        if(Gdx.input.isKeyJustPressed(this.button)){
//
//        }
//    }

    private void doorInvertion(Portas portas) {
        if (!Gdx.input.isKeyJustPressed(this.button)) return;
        System.out.println("button pressed");

        Sprite[] portaSprites = portas.getSprites();
        if (portaSprites == null || portaSprites.length == 0) return;

        System.out.println("2 button pressed");

        int tileSize = Mapa.getInstance().TILE_SIZE;
        int jogadorTileX = (int)(jogadorSprite.getX() / tileSize);
        int jogadorTileY = Mapa.getInstance().getRows() - 1 - (int)(jogadorSprite.getY() / tileSize); // cuidado com y invertido

        int[][] offsets = {
            {-1, -1}, {-1,  0}, {-1, 1},
            { 0, -1},          { 0, 1},
            { 1, -1}, { 1,  0}, { 1, 1}
        };

        int portaMaisProxima = -1;
        float menorDist = Float.MAX_VALUE;

        for (int i = 0; i < portaSprites.length; i++) {
            float px = portaSprites[i].getX();
            float py = portaSprites[i].getY();

            int portaTileX = (int)(px / tileSize);
            int portaTileY = Mapa.getInstance().getRows() - 1 - (int)(py / tileSize);

            // verifica se está nas 8 posições adjacentes
            for (int[] offset : offsets) {
                int nx = jogadorTileX + offset[0];
                int ny = jogadorTileY + offset[1];

                if (portaTileX == nx && portaTileY == ny) {
                    float dx = jogadorSprite.getX() - px;
                    float dy = jogadorSprite.getY() - py;
                    float dist2 = dx * dx + dy * dy;

                    if (dist2 < menorDist) {
                        menorDist = dist2;
                        portaMaisProxima = i;
                    }
                }
            }
        }

        System.out.println("3 button pressed");

        if (portaMaisProxima != -1) {
            portas.invertDoor(portaMaisProxima, this.ID);
        }


        System.out.println("4 button pressed");
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
        if(Gdx.input.isKeyPressed(Input.Keys.K)){
            speed+=10f;
        }else if(Gdx.input.isKeyPressed(Input.Keys.L)){
            speed-=10f;
        }
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
