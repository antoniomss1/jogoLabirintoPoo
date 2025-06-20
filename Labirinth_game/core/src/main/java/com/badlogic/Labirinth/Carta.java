package com.badlogic.Labirinth;

import com.badlogic.gdx.math.Vector2;

public class Carta extends SerMovimentante {
//    private String conteudo = "";
//    private Jogador remetente;
//    private Jogador destinatario;
//    private boolean entregue = false;
//    private Vector2 pos; // posição atual da carta
//
//    public Carta(Jogador remetente) {
//        this.remetente = remetente;
//    }
//
//    public void editarConteudo(String novoTexto) {
//        conteudo = novoTexto;
//    }
//
//    public void enviarPara(Jogador destinatario) {
//        this.destinatario = destinatario;
//        this.entregue = false;
//        // Lógica de movimento começa aqui
//    }
//
//    @Override
//    public void update(float deltaTime, Papeis PapeisManager) {
//        super.update(deltaTime); // movimentação básica
//
//        if (!entregue && getPos().dist(destinatario.getPos()) < 5) {
//            entregue = true;
//            destinatario.receberCarta(this);
//            // Assume que há acesso à instância de Papeis
//            PapeisManager.getInstance().entregarCarta(destinatario, this);
//        }
//
//        interagirComNPCs();
//    }
//
////    private void interagirComNPCs() {
////        for (NPC npc : Mapa.getNPCsProximos(getPos())) {
////            if (npc instanceof Transfigurador) {
////                conteudo = ((Transfigurador) npc).modificarConteudo(conteudo);
////            }
////        }
////    }
//
//    private void interagirComNPCs(char[][] mapData) {
//        int x = (int) (pos.x / TILE_SIZE);
//        int y = mapData.length - 1 - (int) (pos.y / TILE_SIZE); // cuidado com a inversão Y
//
//        if (x < 0 || y < 0 || y >= mapData.length || x >= mapData[0].length) return;
//
//        char tile = mapData[y][x];
//
//        switch (tile) {
//            case '@': // Transfigurador
//                conteudo += "\n[Transfigurador alterou a carta]";
//                break;
//            case '%': // Outro NPC
//                conteudo += "\n[NPC misterioso fez anotações]";
//                break;
//        }
//    }
//
//
//    public String getConteudo() {
//        return conteudo;
//    }
//
//    public Jogador getRemetente() {
//        return remetente;
//    }
}

