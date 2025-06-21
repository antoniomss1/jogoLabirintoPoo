package com.badlogic.Labirinth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.Labirinth.Main;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Portas {
    private static final int TILE_SIZE = Mapa.getInstance().TILE_SIZE;
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

        texturaAberta = new Texture(Gdx.files.internal("porta_aberta.png"));
        texturaFechada = new Texture(Gdx.files.internal("porta_fechada.png"));

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
            Texture textura = estados[i] ? texturaAberta: texturaFechada ;
            Sprite sprite = new Sprite(textura);


            // Marca no mapData para futuras colisões
            mapData[y][x] = estados[i] ? ' ':  '\\';

            // converte para coordenadas do mundo
            float worldX = x * TILE_SIZE;
            float worldY = (mapData.length - 1 - y) * TILE_SIZE;

            sprite.setPosition(worldX, worldY);
            sprites[i] = sprite;

            posX[i] = x;
            posY[i] = y;

        }
    }

//    public void invertDoor(int i, char[][] mapData) {
//        if (i >= 0 && i < size) {
//            estados[i] = !estados[i];
//            sprites[i].setTexture(estados[i] ? texturaAberta : texturaFechada);
//
//
//            //correção do chat:
//            Texture novaTextura = estados[i] ? texturaAberta : texturaFechada;
//            sprites[i].setTexture(novaTextura);
//            sprites[i].setBounds(sprites[i].getX(), sprites[i].getY(),
//                novaTextura.getWidth(), novaTextura.getHeight());
//
//            int x = posX[i];
//            int y = posY[i];
////            mapData[y][x] = estados[i] ? ' ' : '\\'; // atualiza mapData
//
//            if(estados[i]){
//                Mapa.getInstance().setTile(x, y, ' ');
//            }else{
//                Mapa.getInstance().setTile(x, y, '\\');
//            }
//
//            if (i % 2 == 0 && i + 1 < size) {
//
//                estados[i + 1] = !estados[i + 1];
////                sprites[i+1].setTexture(estados[i+1] ? texturaAberta : texturaFechada);
////                if(estados[i+1]){
////                    Mapa.getInstance().setTile(x, y, ' ');
////                }else{
////                    Mapa.getInstance().setTile(x, y, '\\');
////                }
//
//                int x2 = posX[i + 1];
//                int y2 = posY[i + 1];
////                mapData[y2][x2] = estados[i + 1] ? ' ' : '\\';
//                if(estados[i+1]){
//                    Mapa.getInstance().setTile(x2, y2, ' ');
//                }else{
//                    Mapa.getInstance().setTile(x2, y2, '\\');
//                }
//
//            } else if (i % 2 != 0 && i - 1 >= 0) {
//
//                estados[i - 1] = !estados[i-1];
////                sprites[i-1].setTexture(estados[i-1] ? texturaAberta : texturaFechada);
////                if(estados[i-1]){
////                    Mapa.getInstance().setTile(x, y, ' ');
////                }else{
////                    Mapa.getInstance().setTile(x, y, '\\');
////                }
//
//                int x2 = posX[i - 1];
//                int y2 = posY[i - 1];
////                mapData[y2][x2] = estados[i - 1] ? ' ' : '\\';
//
//                if(estados[i]){
//                    Mapa.getInstance().setTile(x2, y2, ' ');
//                }else{
//                    Mapa.getInstance().setTile(x2, y2, '\\');
//                }
//            }
////            Mapa.getInstance().setDadosDoMapa(mapData);
//
//        }
//    }

public void invertDoor(int i) {
    if (i >= 0 && i < size) {
        // Porta principal
        estados[i] = !estados[i];
        Texture novaTextura = estados[i] ? texturaAberta : texturaFechada;
        sprites[i].setTexture(novaTextura);
        sprites[i].setBounds(sprites[i].getX(), sprites[i].getY(),
            novaTextura.getWidth(), novaTextura.getHeight());

        int x = posX[i];
        int y = posY[i];
        Mapa.getInstance().setTile(x, y, estados[i] ? ' ' : '\\');
        System.out.println(" AQUI: x = "+ x+ " y = "+y + " caractere: "+Mapa.getInstance().getTile(x, y));

        // Porta gêmea
        int j = (i % 2 == 0) ? i + 1 : i - 1;
        if (j >= 0 && j < size) {
            estados[j] = !estados[j];
            Texture novaTextura2 = estados[j] ? texturaAberta : texturaFechada;
            sprites[j].setTexture(novaTextura2);
            sprites[j].setBounds(sprites[j].getX(), sprites[j].getY(),
                novaTextura2.getWidth(), novaTextura2.getHeight());


            int x2 = posX[j];
            int y2 = posY[j];
            Mapa.getInstance().setTile(x2, y2, estados[j] ? ' ' : '\\');

            System.out.println(" aqui: x2 = "+ x2+ " y2 = "+y2 + " caractere: "+Mapa.getInstance().getTile(x2, y2));
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

    public Sprite[] getSprites(){
    return this.sprites;
    }

}

