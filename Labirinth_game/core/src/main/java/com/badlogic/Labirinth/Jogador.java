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

    private int Up, Down, Right, Left;
    private int X, Y;
    private Sprite jogadorSprite;

    float jogadorWidth;
    float jogadorHeight;

    private float speed = 400f;

    private boolean wasTouched = false;
    Vector2 touchPos;

    //int []portas;/////////////////////////////////////////////////////////

    public Jogador(int up, int down, int right, int left, Texture gamerTexture, int initialX, int initialY) {
        this.Down   = down;
        this.Up     = up;
        this.Left   = left;
        this.Right  = right;
        this.jogadorSprite = new Sprite(gamerTexture);
        jogadorSprite.setSize((float) Mapa.getInstance().TILE_SIZE-20, (float) Mapa.getInstance().TILE_SIZE-20);
        jogadorWidth = jogadorSprite.getWidth();
        jogadorHeight = jogadorSprite.getHeight();
        jogadorSprite.setX(initialX);
        jogadorSprite.setY(initialY);
        touchPos = new Vector2();
    }

    public void updatePlayer(Viewport viewport, Portas portas){
//        wasTouched = false;
        float moveX=0;
        float moveY=0;
        float delta = Gdx.graphics.getDeltaTime();

        if(Gdx.input.isKeyPressed(Input.Keys.Z)){
            ((OrthographicCamera)viewport.getCamera()).zoom-=.01f;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.X)){
            ((OrthographicCamera)viewport.getCamera()).zoom+=.01f;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.K)){
            speed+=100f;
        }else if(Gdx.input.isKeyPressed(Input.Keys.L)){
            speed-=100f;
        }

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

        if (Gdx.input.isTouched()) {
            if (!wasTouched) {
                touchPos.set(Gdx.input.getX(), Gdx.input.getY());
                viewport.unproject(touchPos);
                System.out.println("clicked: X = "+ (int)touchPos.x/Mapa.getInstance().TILE_SIZE + " Y = "+(int)touchPos.y/Mapa.getInstance().TILE_SIZE);

                int portaClicada = portas.getClickedDoor(touchPos.x, touchPos.y);
                portas.invertDoor(portaClicada);
                wasTouched = true;
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


        } else {
            wasTouched = false; // apenas aqui reseta o toque
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
}

