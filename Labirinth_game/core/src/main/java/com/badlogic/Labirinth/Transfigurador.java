package com.badlogic.Labirinth;

import com.badlogic.Labirinth.Algorithms.Pathfinding;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Transfigurador extends NPC {

    private List<Vector2> caminho = new ArrayList<>();
    private int passoAtual = 0;

    private Set<Vector2> visitadosGlobalmente = new HashSet<>();

    public Transfigurador(Texture textura, int x, int y, float speed) {
        super(textura, x, y, speed);
    }

    @Override
    public void update(float delta) {
        if (isEmMovimento()) {
            updateMovimento(delta);

            // Se o movimento terminou NESTE FRAME, atualize a memória
            if (!isEmMovimento()) {
                int xTile = (int) (sprite.getX() / tileSize);
                int yTile = Mapa.getInstance().getAltura() - 1 - (int) (sprite.getY() / tileSize);
                visitadosGlobalmente.add(new Vector2(xTile, yTile));
            }
            return;
        }

        if (caminho == null || passoAtual >= caminho.size()) {
            recalcularCaminho();
            if (caminho.isEmpty()) {
                System.out.println("Exploração completa. Resetando memória.");
                visitadosGlobalmente.clear();
                recalcularCaminho();
                if (caminho.isEmpty()) return;
            }
        }

        Vector2 proximoTile = caminho.get(passoAtual);

        int xAtual = (int) (sprite.getX() / tileSize);
        int yAtual = Mapa.getInstance().getAltura() - 1 - (int) (sprite.getY() / tileSize);

        int dx = (int) proximoTile.x - xAtual;
        int dy = (int) proximoTile.y - yAtual;

        boolean moveIniciado = false;
        if (dx == 1)      moveIniciado = movement(1);
        else if (dx == -1) moveIniciado = movement(2);
        else if (dy == -1) moveIniciado = movement(3);
        else if (dy == 1)  moveIniciado = movement(4);

        if (moveIniciado) {
            passoAtual++;
        } else {
            caminho = null;
            passoAtual = 0;
        }
    }

    private void recalcularCaminho() {
        int tileSize = Mapa.getInstance().TILE_SIZE;

        int xTile = (int) (sprite.getX() / tileSize);
        int yTile = Mapa.getInstance().getAltura() - 1 - (int) (sprite.getY() / tileSize);

        // **CORREÇÃO AQUI**: Não adicionamos a posição atual à memória ANTES da busca.
        // A posição atual é o ponto de partida, não um lugar a ser evitado.

        caminho = Pathfinding.bfsExploracao(xTile, yTile, Mapa.getInstance().getDadosDoMapa(), visitadosGlobalmente);

        // O caminho retornado pelo BFS já começa pelos vizinhos, então começamos do índice 0.
        passoAtual = 0;

        System.out.println("Novo caminho: " + caminho);

    }
}

//public class Transfigurador extends NPC {
//
//    private List<Vector2> caminho = new ArrayList<>();
//    private int passoAtual = 0;
//
//    private Set<Vector2> visitadosGlobalmente = new HashSet<>();
//
//    public Transfigurador(Texture textura, int x, int y, float speed) {
//        // O construtor do NPC agora lida com a posição inicial corretamente
//        super(textura, x, y, speed);
//    }
//
//    @Override
//    public void update(float delta) {
//        // 1. Se o NPC já está se movendo de um tile para outro, apenas atualize a animação de movimento.
//        if (isEmMovimento()) {
//            updateMovimento(delta);
//            return;
//        }
//
//        // 2. Se o NPC não está se movendo, verifique se precisa de um novo caminho.
//        if (caminho == null || passoAtual >= caminho.size()) {
//            recalcularCaminho();
//            // Se não encontrou caminho, não faz nada neste frame
//            if (caminho == null || caminho.isEmpty()) {
//                return;
//            }
//        }
//
//        // 3. Se tem um caminho válido, pegue o próximo passo e inicie o movimento.
//        Vector2 proximoTile = caminho.get(passoAtual);
//
//        // Posição atual do NPC em tiles (com conversão Y correta)
//        int xAtual = (int) (sprite.getX() / tileSize);
//        int yAtual = Mapa.getInstance().getAltura() - 1 - (int) (sprite.getY() / tileSize);
//
//        // Calcula a direção em coordenadas do GRID
//        int dx = (int) proximoTile.x - xAtual;
//        int dy = (int) proximoTile.y - yAtual;
//
//        // Inicia o movimento na direção correta
//        boolean moveIniciado = false;
//        if (dx == 1)      moveIniciado = movement(1); // Direita (Mundo X+)
//        else if (dx == -1) moveIniciado = movement(2); // Esquerda (Mundo X-)
//        else if (dy == -1) moveIniciado = movement(3); // Cima (Mundo Y+) -> Grid Y diminui
//        else if (dy == 1)  moveIniciado = movement(4); // Baixo (Mundo Y-) -> Grid Y aumenta
//
//        // Se o movimento foi iniciado com sucesso, prepare-se para o próximo passo do caminho
//        if (moveIniciado) {
//            passoAtual++;
//        } else {
//            // Se não conseguiu se mover (ex: caminho bloqueado por uma porta que fechou),
//            // recalcula o caminho no próximo frame.
//            caminho = null;
//            passoAtual = 0;
//        }
//    }
//
//    private void recalcularCaminho() {
//        int tileSize = Mapa.getInstance().TILE_SIZE;
//
//        // Converte a posição atual do sprite (mundo) para coordenadas do grid
//        int xTile = (int) (sprite.getX() / tileSize);
//        int yTile = Mapa.getInstance().getAltura() - 1 - (int) (sprite.getY() / tileSize);
//
//
//        visitadosGlobalmente.add(new Vector2(xTile, yTile));
//        // Gera o caminho de exploração a partir da posição atual
//        caminho = Pathfinding.bfsExploracao(xTile, yTile, Mapa.getInstance().getDadosDoMapa(), visitadosGlobalmente);
//
//        // Começa a seguir o caminho a partir do segundo elemento,
//        // pois o primeiro (índice 0) é a própria posição inicial.
//        passoAtual = 1;
//    }
//}

//
//public class Transfigurador extends NPC{
//    private float moveTimer = 0;
//    private Vector2 direction = new Vector2(0, 0);
//
//    private List<Vector2> caminho = new ArrayList<>();
//    private int passoAtual = 0;
//
//    private Vector2 alvoAtual = null;
//    private List<Vector2> exploracao = new ArrayList<>();
//
//    public Transfigurador(Texture textura, int x, int y, float speed) {
//        super(textura, x, y, speed);
//        this.sprite.setSize((float) Mapa.getInstance().TILE_SIZE-20, (float) Mapa.getInstance().TILE_SIZE-20);
//
//    }
//    List<Vector2> vector = null;
//    float timer = 0;
//
//    @Override
//    public void update(float delta) {
//        timer += delta;
//
//        int tileSize = Mapa.getInstance().TILE_SIZE;
//
//        // Recalcula caminho se não tiver ou tiver chegado ao fim
//        if (caminho == null || passoAtual >= caminho.size()) {
//            int xTile = (int)(this.getPos().x / tileSize);
//            int yTile = (int)(this.getPos().y / tileSize);
//
//            caminho = Pathfinding.bfsExploracao(xTile, yTile, Mapa.getInstance().getDadosDoMapa());
//            passoAtual = 1;
//            return;
//        }
//
//        if (passoAtual < caminho.size()) {
//            Vector2 destino = caminho.get(passoAtual);
//            float destX = destino.x * tileSize;
//            float destY = destino.y * tileSize;
//
//            // Verifica se já chegou ao centro do tile destino
//            float tolerance = 2f;
//            if (Math.abs(sprite.getX() - destX) < tolerance && Math.abs(sprite.getY() - destY) < tolerance) {
//                sprite.setPosition(destX, destY); // Alinha exatamente no tile
//                passoAtual++;
//                return;
//            }
//
//            // Decide direção com base na posição atual
//            int xAtual = (int)(sprite.getX() / tileSize);
//            int yAtual = (int)(sprite.getY() / tileSize);
//
//            int dx = (int)(destino.x - xAtual);
//            int dy = (int)(destino.y - yAtual);
//
//            boolean andou = false;
//            if (dx == 1 && dy == 0) andou = movement(1);
//            else if (dx == -1 && dy == 0) andou = movement(2);
//            else if (dx == 0 && dy == 1) andou = movement(3);
//            else if (dx == 0 && dy == -1) andou = movement(4);
//
//            if (andou) {
//                System.out.printf("Andando para (%f, %f)\n", destino.x, destino.y);
//            }
//        }
//    }
//
//
//
////    @Override
////    public void update(float delta) {
////        timer += delta;
////
////        if (timer > .5f) { // só tenta mover a cada 0.5s
////            timer = 0;
////
////            // posição atual em tiles
////            int xTile = (int)(this.getPos().x / Mapa.getInstance().TILE_SIZE);
////            int yTile = (int)(this.getPos().y / Mapa.getInstance().TILE_SIZE);
////
////            // pega a lista de tiles acessíveis a partir da posição atual
////            List<Vector2> acessiveis = Pathfinding.bfsExploracao(xTile, yTile, Mapa.getInstance().getDadosDoMapa());
////
////            if (acessiveis.size() > 1) {
////                // pega o próximo tile acessível (evita pegar o próprio tile atual, que é o primeiro da lista)
////                Vector2 proximo = acessiveis.get(1);
////
////                int dx = (int)(proximo.x - xTile);
////                int dy = (int)(proximo.y - yTile);
////
////                if (dx == 1 && dy == 0) {
////                    movement(1); // direita
////                } else if (dx == -1 && dy == 0) {
////                    movement(2); // esquerda
////                } else if (dx == 0 && dy == 1) {
////                    movement(3); // cima
////                } else if (dx == 0 && dy == -1) {
////                    movement(4); // baixo
////                }
////
////                System.out.printf("Movendo de (%d,%d) para (%.0f,%.0f)\n", xTile, yTile, proximo.x, proximo.y);
////            }
////        }
////    }
//
//
////    @Override
////    public void update(float delta) {
////        timer += delta; // Adds the current delta to the timer
////        if (timer > 0.1f) { // Check if it has been more than a second
////            timer = 0; // Reset the timer
////
////            vector = Pathfinding.bfsExploracao((int)this.getPos().x/Mapa.getInstance().TILE_SIZE, (int)this.getPos().y/Mapa.getInstance().TILE_SIZE, Mapa.getInstance().getDadosDoMapa());
////            vector.get(1).nor();
////            if(vector.contains(new Vector2(1, 0))){
////                movement(1);
////            }
////            if(vector.contains(new Vector2(-1, 0))){
////                movement(2);
////            }
////            if(vector.contains(new Vector2(0, 1))){
////                movement(3);
////            }
////            if(vector.contains(new Vector2(0, -1))){
////                movement(4);
////            }
////
////            System.out.println("vector: "+vector.toString());
////        }
////    }
//
//    private void escolherNovaDirecao() {
//        // Escolhe uma das 4 direções possíveis aleatoriamente
//        Vector2[] direcoes = {
//            new Vector2(1, 0), new Vector2(-1, 0),
//            new Vector2(0, 1), new Vector2(0, -1)
//        };
//
//        direction = direcoes[MathUtils.random(direcoes.length - 1)];
//    }
//}
//
//
////FUNCIONA PARA MOVIMENTO ALEATORIO
////    @Override
////    public void update(float delta) {
////        float speed = this.getSpeed();
////        moveTimer -= delta;
////
////        if (moveTimer <= 0) {
////            escolherNovaDirecao();
////            moveTimer = 0.5f + MathUtils.random(0.5f); // muda de direção entre 0.5 e 1 segundo
////        }
////
////        float dx = direction.x * speed * delta;
////        float dy = direction.y * speed * delta;
////
////        float newX = sprite.getX() + dx;
////        float newY = sprite.getY() + dy;
////
////        // Checa colisão com as bordas do sprite
////        if (!Mapa.getInstance().isCollidingWithWall(newX, newY, sprite.getWidth(), sprite.getHeight())) {
////            sprite.setPosition(newX, newY);
////        } else {
////            moveTimer = 0; // força nova direção no próximo frame
////        }
////    }
//
////      verificar código
////    @Override
////    public void update(float delta) {
////        Mapa mapa = Mapa.getInstance();
////        int alturaMapa = mapa.getAltura(); // usado para correção do eixo Y
////
////        // Se não tiver um caminho atual, recalcula
////        if (exploracao.isEmpty()) {
////            int xTile = (int)(sprite.getX() / mapa.TILE_SIZE);
////            int yTile = alturaMapa - 1 - (int)(sprite.getY() / mapa.TILE_SIZE); // inverter Y para matriz
////
////            exploracao = Pathfinding.bfsExploracao(xTile, yTile, mapa.getDadosDoMapa());
////            passoAtual = 0;
////
////            if (!exploracao.isEmpty()) {
////                Vector2 alvoTile = exploracao.get(passoAtual);
////                alvoAtual = new Vector2(
////                    alvoTile.x * mapa.TILE_SIZE,
////                    (alturaMapa - 1 - (int)alvoTile.y) * mapa.TILE_SIZE // inverter Y
////                );
////            }
////        }
////
////        // Caminho acabou, reinicia exploração
////        if (passoAtual >= exploracao.size()) {
////            exploracao.clear();
////            return;
////        }
////
////        Vector2 posAtual = new Vector2(sprite.getX(), sprite.getY());
////        Vector2 direcao = new Vector2(alvoAtual).sub(posAtual);
////
////        // Chegou no destino
////        if (direcao.len() < 2f) {
////            passoAtual++;
////            if (passoAtual < exploracao.size()) {
////                Vector2 alvoTile = exploracao.get(passoAtual);
////                alvoAtual = new Vector2(
////                    alvoTile.x * mapa.TILE_SIZE,
////                    (alturaMapa - 1 - (int)alvoTile.y) * mapa.TILE_SIZE
////                );
////            }
////        } else {
////            // Normaliza a direção e aplica velocidade
////            direcao.nor().scl(this.getSpeed() * delta);
////
////            float newX = sprite.getX() + direcao.x;
////            float newY = sprite.getY() + direcao.y;
////
////            // Checa colisões antes de mover
////            if (!mapa.isCollidingWithWall(newX, sprite.getY(), sprite.getWidth(), sprite.getHeight())) {
////                sprite.setX(newX);
////            }
////            if (!mapa.isCollidingWithWall(sprite.getX(), newY, sprite.getWidth(), sprite.getHeight())) {
////                sprite.setY(newY);
////            }
////        }
////    }
//
////    Mapa mapa = Mapa.getInstance();
////    @Override
////    public void update(float delta) {
////
////        if (exploracao.isEmpty()) {
////
////            int xTile = (int)(sprite.getX() / mapa.TILE_SIZE);
////            int yTile = (int)(sprite.getY() / mapa.TILE_SIZE);
////            exploracao = Pathfinding.bfsExploracao(xTile, yTile, mapa.getDadosDoMapa());
////            passoAtual = 0;
////            System.out.println("xTile: "+xTile + " yTile: "+ yTile);
////            System.out.println("explo: "+exploracao.get(0) + " explo 1: " + exploracao.get(1));
////            if (!exploracao.isEmpty()) {
////                Vector2 alvoTile = exploracao.get(passoAtual);
//////                alvoAtual = new Vector2(alvoTile.x * mapa.TILE_SIZE, alvoTile.y * mapa.TILE_SIZE);
////
////                int alturaMapa = mapa.getDadosDoMapa().length;
////                alvoAtual = new Vector2(
////                    alvoTile.x * mapa.TILE_SIZE,
////                    (alturaMapa - 1 - (int)alvoTile.y) * mapa.TILE_SIZE
////                );
////
////
////                System.out.println("(no if) alvo atual: "+ alvoTile.y + "  " + alvoTile.y);
////            }
////        }
////
//////        if (passoAtual >= exploracao.size()) return;
////        //exploração indefinida
////        if (passoAtual >= exploracao.size()) {
////            exploracao.clear();
////            return;
////        }
////
////
////        Vector2 posAtual = new Vector2(sprite.getX(), sprite.getY());
////        Vector2 direcao = new Vector2(alvoAtual).sub(posAtual);
////
////        if (direcao.len() < 2f) {
////            passoAtual++;
////            if (passoAtual < exploracao.size()) {
////                Vector2 alvoTile = exploracao.get(passoAtual);
////                System.out.println("novo alvo: " + alvoTile.toString());
////
//////                alvoAtual = new Vector2(((int)alvoTile.x) * mapa.TILE_SIZE, ((int)alvoTile.y) * mapa.TILE_SIZE);
////
////                int alturaMapa = mapa.getDadosDoMapa().length;
////                alvoAtual = new Vector2(
////                    alvoTile.x * mapa.TILE_SIZE,
////                    (alturaMapa - 1 - (int)alvoTile.y) * mapa.TILE_SIZE
////                );
////
////                System.out.printf("alvo atual: x = %f y = %f\n", alvoAtual.x, alvoAtual.y);
////            }
////        } else {
////            System.out.println("antes de normalizar: "+ direcao.toString());
////            direcao.nor().scl(this.getSpeed() * delta);
//////            if(!Mapa.getInstance().isCollidingWithWall(direcao.x, direcao.y, this.getNPCWidth(), this.getNPCHeight()))
//////                sprite.translate(direcao.x, direcao.y );
////                sprite.translateX(direcao.x);
////                sprite.translateY(direcao.y);
////            System.out.println("direções: x: " + direcao.x + " y: " + direcao.y);
////        }
////    }
//
