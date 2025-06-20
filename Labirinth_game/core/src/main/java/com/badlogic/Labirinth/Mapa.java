package com.badlogic.Labirinth;

import com.badlogic.gdx.math.Vector2;
import java.util.List;


import java.util.ArrayList;

public class Mapa {
    private static Mapa instanciaUnica;
    private List<NPC> npcs;
    private char[][] dadosDoMapa;

    public void setDadosDoMapa(char[][] mapa) {
        this.dadosDoMapa = mapa;
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
        npcs = new ArrayList<>();
        instanciaUnica = this;
    }

    public static void iniciarMapa() {
        if (instanciaUnica == null)
            instanciaUnica = new Mapa();
    }

    public static Mapa getInstance() {
        if (instanciaUnica == null) {
            instanciaUnica = new Mapa();
        }
        return instanciaUnica;
    }

    public void adicionarNPC(NPC npc) {
        npcs.add(npc);
    }

    public List<NPC> getNPCsProximos(Vector2 posicao) {
        List<NPC> proximos = new ArrayList<>();

        for (NPC npc : npcs) {
            if (npc.getPos().dst(posicao) < 64) { // distância de interação, por exemplo
                proximos.add(npc);
            }
        }
        return proximos;
    }

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


}

