package com.badlogic.Labirinth;

import com.badlogic.Labirinth.Algorithms.Pathfinding;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Transfigurador extends NPC{
    private float moveTimer = 0;
    private Vector2 direction = new Vector2(0, 0);
    private float speed = 64*7f;

    private List<Vector2> caminho = new ArrayList<>();
    private int passoAtual = 0;



    public Transfigurador(Texture textura, int x, int y) {
        super(textura, x, y);
        this.sprite.setSize((float) Mapa.getInstance().TILE_SIZE-20, (float) Mapa.getInstance().TILE_SIZE-20);

    }

    @Override
    public void update(float delta) {
        moveTimer -= delta;

        if (moveTimer <= 0) {
            escolherNovaDirecao();
            moveTimer = 0.5f + MathUtils.random(0.5f); // muda de direção entre 0.5 e 1 segundo
        }

        float dx = direction.x * speed * delta;
        float dy = direction.y * speed * delta;

        float newX = sprite.getX() + dx;
        float newY = sprite.getY() + dy;

        // Checa colisão com as bordas do sprite
        if (!Mapa.getInstance().isCollidingWithWall(newX, newY, sprite.getWidth(), sprite.getHeight())) {
            sprite.setPosition(newX, newY);
        } else {
            moveTimer = 0; // força nova direção no próximo frame
        }
    }


//    private List<Vector2> caminho = new ArrayList<>();
//    private int passoAtual = 0;
//    private float speed = 100f; // pixels por segundo
//    private Vector2 alvoAtual = new Vector2(Mapa.getInstance().getRows(), Mapa.getInstance().getCols());

//    @Override
//    public void update(float delta) {
//        if (caminho.isEmpty() || passoAtual >= caminho.size()) {
//            // Define o destino, por exemplo, centro do mapa:
//            int destinoX = Mapa.getInstance().getCols() / 2;
//            int destinoY = Mapa.getInstance().getRows() / 2;
//
//            // Posição atual do NPC em tiles
//            int atualX = (int)(sprite.getX() / Mapa.getInstance().TILE_SIZE);
//            int atualY = (int)(sprite.getY() / Mapa.getInstance().TILE_SIZE);
//
//            caminho = Pathfinding.encontrarCaminho(atualX, atualY, destinoX, destinoY);
//            passoAtual = 0;
//
//            if (!caminho.isEmpty()) {
//                alvoAtual = caminho.get(passoAtual).scl(Mapa.getInstance().TILE_SIZE);
//            }
//        }
//
//        if (alvoAtual != null) {
//            Vector2 posAtual = new Vector2(sprite.getX(), sprite.getY());
//            Vector2 direcao = new Vector2(alvoAtual).sub(posAtual);
//
//            if (direcao.len() < 2f) {
//                // Chegou ao tile alvo
//                passoAtual++;
//                if (passoAtual < caminho.size()) {
//                    alvoAtual = caminho.get(passoAtual).scl(Mapa.getInstance().TILE_SIZE);
//                } else {
//                    alvoAtual = null;
//                }
//            } else {
//                direcao.nor().scl(speed * delta);
//                sprite.translate(direcao.x, direcao.y);
//            }
//        }
//    }



    private void escolherNovaDirecao() {
        // Escolhe uma das 4 direções possíveis aleatoriamente
        Vector2[] direcoes = {
            new Vector2(1, 0), new Vector2(-1, 0),
            new Vector2(0, 1), new Vector2(0, -1)
        };

        direction = direcoes[MathUtils.random(direcoes.length - 1)];
    }
}
