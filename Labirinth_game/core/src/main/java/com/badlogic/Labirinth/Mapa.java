package com.badlogic.Labirinth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.io.File;
import java.util.List;


import java.util.ArrayList;

public class Mapa {
    private static Mapa instanciaUnica;
//    private List<NPC> npcs;
    private char[][] dadosDoMapa;
    public final int TILE_SIZE = 64;
    public int worldWidth;
    public int worldHeight;
    int rows;
    int cols;

    public void setDadosDoMapa(String mapText) {


        char[][] matriz = loadMap(mapText);
        this.dadosDoMapa = matriz;

//        String[] lines = Gdx.files.internal(filename).readString().split("\n");
//        int rows = lines.length;
//        int cols = lines[0].length();

        worldWidth = TILE_SIZE * cols;
        worldHeight = TILE_SIZE * rows;

    }

    public char[][] getDadosDoMapa() {
        return dadosDoMapa;
    }


    //precisa ser private para ser singleton, né?
    private Mapa() {
        //adicionar depois, só se for necessário
        //        if (instanciaUnica != null) {
        //            throw new RuntimeException("Use getInstance() para acessar o Mapa!");
        //        }
//        npcs = new ArrayList<>();
        instanciaUnica = this;
    }

    public static void iniciarMapa() {
        if (instanciaUnica == null){
            instanciaUnica = new Mapa();
        }

    }

    public static Mapa getInstance() {
        if (instanciaUnica == null) {
            instanciaUnica = new Mapa();
        }
        return instanciaUnica;
    }

//    public void adicionarNPC(NPC npc) {
//        npcs.add(npc);
//    }

//    public List<NPC> getNPCsProximos(Vector2 posicao) {
//        List<NPC> proximos = new ArrayList<>();
//
//        for (NPC npc : npcs) {
//            if (npc.getPos().dst(posicao) < 64) { // distância de interação, por exemplo
//                proximos.add(npc);
//            }
//        }
//        return proximos;
//    }

    public char getTile(int x, int y) {
        if (y < 0 || y >= dadosDoMapa.length || x < 0 || x >= dadosDoMapa[0].length)
            return '#'; // ou qualquer coisa que represente parede/borda
        return dadosDoMapa[y][x];
    }

    public void setTile(int x, int y, char valor) {
        if (y >= 0 && y < dadosDoMapa.length && x >= 0 && x < dadosDoMapa[0].length) {
            dadosDoMapa[y][x] = valor;
        }
    }

    private char[][] loadMap(String filename) {
        String[] lines = Gdx.files.internal(filename).readString().split("\n");
        rows = lines.length;
        cols = lines[0].length();

        worldWidth = TILE_SIZE * (cols - 1);
        worldHeight = TILE_SIZE * (rows - 1);


        char[][] map = new char[rows][cols];

        for (int y = 0; y < rows; y++) {

            map[y] = lines[y].toCharArray();
        }
        return map;
    }


    public boolean isCollidingWithWall(float x, float y, Jogador jogador) {
        float width = jogador.getJogadorWidth();
        float height = jogador.getJogadorHeight();

        // Checa os 4 cantos do sprite
        return isWallAt(x, y) ||
            isWallAt(x + width, y) ||
            isWallAt(x, y + height) ||
            isWallAt(x + width, y + height);
    }

    //    private boolean isWallAt(float x, float y) {
    //        int tileX = (int)(x / TILE_SIZE);
    //        int tileY = mapData.length - 1 - (int)(y / TILE_SIZE); // cuidado com a inversão Y
    //
    //        if (tileX < 0 || tileX >= cols || tileY < 0 || tileY >= rows) {
    //            return true; // borda é parede
    //        }
    //
    //        char tile = mapData[tileY][tileX];
    //
    //        if(tile!=' '){
    //            if(tile == '/'){
    //                return false;
    //            }
    //            return true;
    //        }
    //        return false;
    ////        return tile == '#' || tile == '\\'; // bloqueia parede e porta FECHADA
    //    }


    private boolean isWallAt(float x, float y) {
        int tileX = (int)(x / TILE_SIZE);
        int tileY = dadosDoMapa.length - 1 - (int)(y / TILE_SIZE);

        if (tileX < 0 || tileX >= cols || tileY < 0 || tileY >= rows) {
            return true; // trata bordas como parede
        }
        return Mapa.getInstance().getDadosDoMapa()[tileY][tileX] != ' ';
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

}

