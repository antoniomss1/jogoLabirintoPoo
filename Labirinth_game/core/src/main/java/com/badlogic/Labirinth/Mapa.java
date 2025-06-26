package com.badlogic.Labirinth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.io.File;
import java.util.HashSet;
import java.util.List;


import java.util.ArrayList;
import java.util.Set;

public class Mapa {
    private static Mapa instanciaUnica;
//    private List<NPC> npcs;
    private char[][] dadosDoMapa;
    public final int TILE_SIZE = 64;
    public int worldWidth;
    public int worldHeight;
    int rows;
    int cols;

    private ArrayList<Chave> chaves = new ArrayList<>();
    private Texture chaveTexture;
    private int numChaves;

//    public void criarChaves(){
//        chaveTexture = new Texture(Gdx.files.internal("Chave.png"));
//        this.numChaves = (worldHeight-1);
//
//
//        Set<String> usedPositions = new HashSet<>();
//
//        for (int i = 0; i < numChaves; i++) {
//            int x, y;
//            String key;
//            do {
//                x = MathUtils.random(cols - 1);
//                y = MathUtils.random(rows - 1);
//                key = x + "," + y;
//            } while (dadosDoMapa[y][x] != ' ' || usedPositions.contains(key));
//            usedPositions.add(key);
//
////            boolean estadoInicial = (i % 2 == 0);
////            Texture textura = estadoInicial ? texturaAberta : texturaFechada;
//            Sprite sprite = new Sprite(chaveTexture);
//
////            dadosDoMapa[y][x] = estadoInicial ? ' ' : '\\';
//
//            float worldX = x * TILE_SIZE;
//            float worldY = (dadosDoMapa.length - 1 - y) * TILE_SIZE;
//
//            sprite.setPosition(worldX, worldY);
//            Chave chave = new Chave(chaveTexture, x, y);
//
////            int dono = (i < numChaves / 2) ? 0 : 1; // metade para cada jogador
////            Porta porta = new Porta(texturaAberta, texturaFechada, estadoInicial, x, y, dono);
////            portas.add(porta);
//            chaves.add(chave);
//        }
//    }

public void criarChaves() {
    if (dadosDoMapa == null) return; // segurança extra

    chaveTexture = new Texture(Gdx.files.internal("Chave.png"));
    this.numChaves = Math.min(10, rows * cols / 20); // define máximo de chaves

    Set<String> usedPositions = new HashSet<>();

    for (int i = 0; i < numChaves; i++) {
        int x, y;
        String key;
        do {
            x = MathUtils.random(cols - 1);
            y = MathUtils.random(rows - 1);
            key = x + "," + y;
        } while (dadosDoMapa[y][x] != ' ' || usedPositions.contains(key));
        usedPositions.add(key);

        Chave chave = new Chave(chaveTexture, x, y);
        chaves.add(chave);

        System.out.println("Chave criada em tile: (" + x + ", " + y + ")");
    }
}


    public ArrayList<Chave> getChaves(){
        return this.chaves;
    }

    public void setDadosDoMapa(String mapTextName) {
        char[][] matriz = loadMap(mapTextName);
        this.dadosDoMapa = matriz;

        worldWidth = TILE_SIZE * cols;
        worldHeight = TILE_SIZE * rows;

        criarChaves();
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

    private boolean isWallAt(float x, float y) {
        int tileX = (int)(x / TILE_SIZE);
        int tileY = dadosDoMapa.length - 1 - (int)(y / TILE_SIZE);

        if (tileX < 0 || tileX >= cols || tileY < 0 || tileY >= rows) {
            return true; // trata bordas como parede
        }
        return Mapa.getInstance().getDadosDoMapa()[tileY][tileX] != ' ';
    }

    public boolean isCollidingWithWall(float x, float y, float largura, float altura) {
        return isWallAt(x, y) ||
            isWallAt(x + largura, y) ||
            isWallAt(x, y + altura) ||
            isWallAt(x + largura, y + altura);
    }


    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getAltura() {
        return getDadosDoMapa().length;
    }

    private boolean isTileTransponivel(char tile) {
        return tile == ' '; // ou: return " ._".indexOf(tile) != -1;
    }


    public void tirarChaveDoMapa(Chave chave) {
        chaves.remove(chave);
    }

    public void drawChaves(SpriteBatch batch){
        for (Chave chave : Mapa.getInstance().getChaves()) {
            chave.draw(batch);
        }
    }

    public int getNumChaves() {
        return numChaves;
    }
}

