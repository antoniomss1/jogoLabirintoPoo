package com.badlogic.Labirinth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Portas {
    private final Texture texturaAberta;
    private final Texture texturaFechada;
    private final ArrayList<Porta> portas = new ArrayList<>();
    private int TILE_SIZE = Mapa.getInstance().TILE_SIZE;
    int size = 16;

//    public Portas() {
//        char[][] mapData = Mapa.getInstance().getDadosDoMapa();
//        int rows = mapData.length;
//        int cols = mapData[0].length;
//
//        texturaAberta = new Texture(Gdx.files.internal("porta_aberta.png"));
//        texturaFechada = new Texture(Gdx.files.internal("porta_fechada.png"));
//        portas = new ArrayList<>();
//
//        Set<String> ocupados = new HashSet<>();
//
//        int quantidade = 16;
//        for (int i = 0; i < quantidade; i++) {
//            int x, y;
//            String key;
//
//            do {
//                x = MathUtils.random(cols - 1);
//                y = MathUtils.random(rows - 1);
//                key = x + "," + y;
//            } while (mapData[y][x] != ' ' || ocupados.contains(key));
//
//            ocupados.add(key);
//
//            boolean estadoInicial = (i % 2 == 0);
//            char novoChar = estadoInicial ? ' ' : '\\';
//            Mapa.getInstance().setTile(x, y, novoChar);
//
//            Porta porta = new Porta(texturaAberta, texturaFechada, estadoInicial, x, y, TILE_SIZE, rows);
//            portas.add(porta);
//        }
//    }
public Portas() {
    char[][] mapData = Mapa.getInstance().getDadosDoMapa();
    int rows = mapData.length;
    int cols = mapData[0].length;

    texturaAberta = new Texture(Gdx.files.internal("porta_aberta.png"));
    texturaFechada = new Texture(Gdx.files.internal("porta_fechada.png"));

    Set<String> usedPositions = new HashSet<>();

    for (int i = 0; i < size; i++) {
        int x, y;
        String key;
        do {
            x = MathUtils.random(cols - 1);
            y = MathUtils.random(rows - 1);
            key = x + "," + y;
        } while (mapData[y][x] != ' ' || usedPositions.contains(key));
        usedPositions.add(key);

        boolean estadoInicial = (i % 2 == 0);
        Texture textura = estadoInicial ? texturaAberta : texturaFechada;
        Sprite sprite = new Sprite(textura);

        mapData[y][x] = estadoInicial ? ' ' : '\\';

        float worldX = x * TILE_SIZE;
        float worldY = (mapData.length - 1 - y) * TILE_SIZE;

        sprite.setPosition(worldX, worldY);

        int dono = (i < size / 2) ? 0 : 1; // metade para cada jogador
        Porta porta = new Porta(texturaAberta, texturaFechada, estadoInicial, x, y, dono);
        portas.add(porta);
    }
}


//    public void invertDoor(int i) {
//        if (i < 0 || i >= portas.size()) return;
//
//        portas.get(i).alternar(texturaAberta, texturaFechada);
//
//        int j = (i % 2 == 0) ? i + 1 : i - 1;
//        if (j < portas.size()) {
//            portas.get(j).alternar(texturaAberta, texturaFechada);
//        }
//    }
public void invertDoor(int i, int jogador) {

    if (i >= 0 && i < portas.size()) {
        Porta porta = portas.get(i);
        if (porta.getDono() != jogador) return; // não é o dono

        boolean novoEstado = !porta.getEstado();
        porta.setEstado(novoEstado);

        Texture novaTextura = novoEstado ? texturaAberta : texturaFechada;
        porta.getSprite().setTexture(novaTextura);
        porta.getSprite().setBounds(
            porta.getSprite().getX(),
            porta.getSprite().getY(),
            novaTextura.getWidth(),
            novaTextura.getHeight()
        );

        int x = porta.getX();
        int y = porta.getY();
        Mapa.getInstance().setTile(x, y, novoEstado ? ' ' : '\\');

        int j = (i % 2 == 0) ? i + 1 : i - 1;
        if (j < portas.size()) {
            portas.get(j).alternar(texturaAberta, texturaFechada);
        }
    }
}


    public int getClickedDoor(float x, float y) {
        for (int i = 0; i < portas.size(); i++) {
            if (portas.get(i).contem(x, y)) {
                return i;
            }
        }
        return -1;
    }

    public void draw(SpriteBatch batch) {
        for (Porta porta : portas) {
            porta.draw(batch);
        }
    }

    public void dispose() {
        texturaAberta.dispose();
        texturaFechada.dispose();
    }

    public ArrayList<Porta> getPortas() {
        return portas;
    }

    public Sprite[] getSprites() {
        Sprite[] sprites = new Sprite[portas.size()];
        for (int i = 0; i < portas.size(); i++) {
            sprites[i] = portas.get(i).getSprite();
        }
        return sprites;
    }
}
