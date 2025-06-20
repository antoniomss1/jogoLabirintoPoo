package com.badlogic.Labirinth;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.Labirinth.Main;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Portas {
    private static final int TILE_SIZE = 64;
    private Sprite[] sprites;
    private boolean[] estados; // true = aberta, false = fechada
    private int size = 16;

    private Texture texturaAberta;
    private Texture texturaFechada;

    private int[] posX;
    private int[] posY;

//    char[][] mapData, int rows, int cols
    public Portas() {
        char[][] mapData = Mapa.getInstance().getDadosDoMapa();
        int rows = mapData.length;
        int cols = mapData[0].length;

        texturaAberta = new Texture("porta_aberta.png");
        texturaFechada = new Texture("porta_fechada.png");

        sprites = new Sprite[size];
        estados = new boolean[size];

        posX = new int[size];
        posY = new int[size];


        Set<String> usedPositions = new HashSet<>();

        for (int i = 0; i < size; i++) {
            int x, y;
            String key;
            // sorteia até achar um lugar válido
            do {
                x = MathUtils.random(cols - 1);
                y = MathUtils.random(rows - 1);

                key = x + "," + y;
            } while (mapData[y][x] != ' ' || usedPositions.contains(key));

            usedPositions.add(key);

            estados[i] = (i % 2 == 0); // alterna estado inicial
            Texture textura = estados[i] ? texturaFechada : texturaAberta;
            Sprite sprite = new Sprite(textura);


            // Marca no mapData para futuras colisões
            mapData[y][x] = estados[i] ? '\\' : ' ';

            // converte para coordenadas do mundo
            float worldX = x * TILE_SIZE;
            float worldY = (mapData.length - 1 - y) * TILE_SIZE;

            sprite.setPosition(worldX, worldY);
            sprites[i] = sprite;

            posX[i] = x;
            posY[i] = y;

        }
    }

    public void invertDoor(int i, char[][] mapData) {
        if (i >= 0 && i < size) {
            estados[i] = !estados[i];
            sprites[i].setTexture(estados[i] ? texturaAberta : texturaFechada);

            int x = posX[i];
            int y = posY[i];
            mapData[y][x] = estados[i] ? ' ' : '\\'; // atualiza mapData

            if (i % 2 == 0 && i + 1 < size) {

                estados[i + 1] = !estados[i + 1];
                sprites[i+1].setTexture(estados[i+1] ? texturaAberta : texturaFechada);

                int x2 = posX[i + 1];
                int y2 = posY[i + 1];
                mapData[y2][x2] = estados[i + 1] ? ' ' : '\\';


            } else if (i % 2 != 0 && i - 1 >= 0) {

                estados[i - 1] = !estados[i-1];
                sprites[i-1].setTexture(estados[i-1] ? texturaAberta : texturaFechada);

                int x2 = posX[i - 1];
                int y2 = posY[i - 1];
                mapData[y2][x2] = estados[i - 1] ? ' ' : '\\';
            }


        }
    }

    public void draw(com.badlogic.gdx.graphics.g2d.SpriteBatch batch) {
        for (Sprite sprite : sprites) {
            sprite.draw(batch);
        }
    }

    public int getClickedDoor(float x, float y) {
        for (int i = 0; i < size; i++) {
            if (sprites[i].getBoundingRectangle().contains(x, y)) {
                return i;
            }
        }
        return -1;
    }

    public void dispose() {
        texturaAberta.dispose();
        texturaFechada.dispose();
    }

//    private boolean isWallAt(float x, float y, char[][] mapData) {
//        int rows = lines.length;
//        int cols = lines[0].length();
//
//        int tileX = (int)(x / TILE_SIZE);
//        int tileY = mapData.length - 1 - (int)(y / TILE_SIZE); // cuidado com a inversão Y
//
//        if (tileX < 0 || tileX >= cols || tileY < 0 || tileY >= rows) {
//            return true; // trata bordas como parede
//        }
//        return mapData[tileY][tileX] != ' ';
//    }


}

