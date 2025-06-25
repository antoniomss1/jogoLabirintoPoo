package com.badlogic.Labirinth.Algorithms;

import com.badlogic.Labirinth.Mapa;
import com.badlogic.gdx.math.Vector2;

import java.util.*;

// Em Pathfinding.java

import com.badlogic.gdx.math.Vector2;
import java.util.*;


public class Pathfinding {

    // ... (versão anterior do bfsExploracao)

    // NOVA VERSÃO CORRIGIDA
    public static List<Vector2> bfsExploracao(int startX, int startY, char[][] mapa, Set<Vector2> jaVisitadosGlobalmente) {
        int rows = mapa.length;
        int cols = mapa[0].length;

        // Este 'visited' local previne loops dentro desta busca específica.
        boolean[][] visitedNestaBusca = new boolean[rows][cols];
        Queue<Vector2> queue = new LinkedList<>();
        List<Vector2> ordem = new ArrayList<>();

        Vector2 pontoDePartida = new Vector2(startX, startY);

        // **CORREÇÃO AQUI**: Sempre adicionamos o ponto de partida à fila.
        // Ele é a raiz da nossa busca atual.
        queue.add(pontoDePartida);
        visitedNestaBusca[startY][startX] = true;

        // Adicionamos o ponto de partida à lista de visitados globalmente
        // para que ele não seja um destino futuro em outras buscas.
        // (Isso é opcional, mas ajuda a evitar que ele fique parado).
        // jaVisitadosGlobalmente.add(pontoDePartida); // Removido para testar uma lógica mais limpa

        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        while (!queue.isEmpty()) {
            Vector2 atual = queue.poll();

            // Não adicionamos o ponto de partida ao caminho de movimento.
            // O caminho deve conter apenas os *destinos*.
            if (!atual.equals(pontoDePartida)) {
                ordem.add(atual);
            }

            for (int i = 0; i < 4; i++) {
                int nx = (int) atual.x + dx[i];
                int ny = (int) atual.y + dy[i];

                if (nx >= 0 && ny >= 0 && nx < cols && ny < rows) {
                    Vector2 vizinho = new Vector2(nx, ny);

                    // **CONDIÇÃO ATUALIZADA E CORRIGIDA**:
                    // 1. O vizinho é um tile válido para andar (' ' ou porta aberta).
                    // 2. O vizinho não foi visitado NESTA busca (evita loops locais).
                    // 3. O vizinho não foi visitado GLOBALMENTE (evita voltar para trás).
                    if ((mapa[ny][nx] == ' ' || mapa[ny][nx] == '\\') &&
                        !visitedNestaBusca[ny][nx] &&
                        !jaVisitadosGlobalmente.contains(vizinho)) {

                        visitedNestaBusca[ny][nx] = true;
                        queue.add(vizinho);
                    }
                }
            }
        }
        return ordem;
    }
}

//
//public class Pathfinding {
//
//    // Este método permanece como está, para compatibilidade
//    public static List<Vector2> bfsExploracao(int startX, int startY, char[][] mapa) {
//        return bfsExploracao(startX, startY, mapa, new HashSet<>()); // Chama a nova versão com um conjunto vazio
//    }
//
//    // NOVA VERSÃO: Aceita um conjunto de tiles já visitados para ignorar
//    public static List<Vector2> bfsExploracao(int startX, int startY, char[][] mapa, Set<Vector2> jaVisitadosGlobalmente) {
//        int rows = mapa.length;
//        int cols = mapa[0].length;
//
//        boolean[][] visitedNestaBusca = new boolean[rows][cols];
//        Queue<Vector2> queue = new LinkedList<>();
//        List<Vector2> ordem = new ArrayList<>();
//
//        // Adiciona o ponto de partida (se já não foi visitado globalmente)
//        if (!jaVisitadosGlobalmente.contains(new Vector2(startX, startY))) {
////            System.out.println("adicionado: " + startX+" " +startY);
////            System.out.println("visited nesta busca: "+ visitedNestaBusca.toString());
//            queue.add(new Vector2(startX, startY));
//            visitedNestaBusca[startY][startX] = true;
//        }
//
//        int[] dx = {1, -1, 0, 0};
//        int[] dy = {0, 0, 1, -1};
//
//        while (!queue.isEmpty()) {
//            Vector2 atual = queue.poll();
//            ordem.add(atual);
//
//            for (int i = 0; i < 4; i++) {
//                int nx = (int) atual.x + dx[i];
//                int ny = (int) atual.y + dy[i];
//
//                if (nx >= 0 && ny >= 0 && nx < cols && ny < rows) {
//                    Vector2 vizinho = new Vector2(nx, ny);
//                    // CONDIÇÃO ATUALIZADA:
//                    // Verifica se não foi visitado NESTA busca E se não foi visitado GLOBALMENTE
//                    if (!visitedNestaBusca[ny][nx] && !jaVisitadosGlobalmente.contains(vizinho) && (mapa[ny][nx] == ' ' || mapa[ny][nx] == '\\')) {
//                        visitedNestaBusca[ny][nx] = true;
//                        queue.add(vizinho);
//                    }
//                }
//            }
//        }
//
//        return ordem;
//    }
//}

//public class Pathfinding {
//
////    Stack<Character> caminho = new Stack<>();
////
////    public static List<Vector2> bfsExploracao(int startX, int startY, char[][] mapa) {
////
////        return null;
////    }
////
////    private void adicionarCaminhos(int posX, int posY,char[][] mapa){
////
////    }
////
////    private void decidirCaminho(){
////
////    }
////    public static boolean[][] visited = new boolean[Mapa.getInstance().getRows()][Mapa.getInstance().getCols()];
//
//    //retorna os caminhos para onde pode ir
//    public static List<Vector2> bfsExploracao(int startX, int startY, char[][] mapa) {
//        int rows = mapa.length;
//        int cols = mapa[0].length;
//
//        boolean[][] visited = new boolean[rows][cols];
//        Queue<Vector2> queue = new LinkedList<>();
//        List<Vector2> ordem = new ArrayList<>();
//
//        queue.add(new Vector2(startX, startY));
//        visited[startY][startX] = true;
//
//        // Direções: direita, esquerda, baixo, cima (em ordem X, Y)
//        int[] dx = {1, -1, 0, 0};
//        int[] dy = {0, 0, 1, -1};
//
//        while (!queue.isEmpty()) {
//            Vector2 atual = queue.poll();
//            ordem.add(atual);
//
//            for (int i = 0; i < 4; i++) {
//                int nx = (int) atual.x + dx[i];
//                int ny = (int) atual.y + dy[i];
//
//                if (nx >= 0 && ny >= 0 && nx < cols && ny < rows) {
//                    if (!visited[ny][nx] && (mapa[ny][nx] == ' ' || mapa[ny][nx] == '\\')) {
//                        visited[ny][nx] = true;
//                        queue.add(new Vector2(nx, ny));
//                    }
//                }
//            }
//        }
//
//        return ordem;
//    }
//}


//    public static List<Vector2> bfsExploracao(int startX, int startY, char[][] mapa) {
//        int rows = mapa.length;
//        int cols = mapa[0].length;
//
//        boolean[][] visited = new boolean[rows][cols];
////        for(int i=0; i<rows; i++){
////            for(int j=0; j<cols; j++){
////                visited[i][j] = false;
////            }
////        }
//        Queue<Vector2> queue = new LinkedList<>();
//        List<Vector2> ordem = new ArrayList<>();
//
//        queue.add(new Vector2(startX, startY));
//        visited[startY][startX] = true;
//
//        int[] dx = {1, -1, 0, 0};
//        int[] dy = {0, 0, 1, -1};
//
//        while (!queue.isEmpty()) {
//            Vector2 atual = queue.poll();
////            ordem.remove(0);
//            ordem.add(atual);
//
//            for (int i = 0; i < 4; i++) {
//                int nx = (int) atual.x + dx[i];
//                int ny = (int) atual.y + dy[i];
//
//                //verifica se tá dentro dos limites do mapa
//                if (nx >= 0 && ny >= 0 && nx < cols && ny < rows) {
//                    if (!visited[ny][nx] && (mapa[ny][nx] == ' ' || mapa[ny][nx] == '\\')) {
//                        visited[ny][nx] = true;
//                        queue.add(new Vector2(nx, ny));
//                    }
//                }
//            }
//        }
//        System.out.println("ordem: "+ordem.get(1));
//        return ordem;
//
//    }


//
//    public static List<Vector2> bfs(int startX, int startY, int goalX, int goalY, char[][] mapa) {
//        int rows = mapa.length;
//        int cols = mapa[0].length;
//
//        boolean[][] visitado = new boolean[rows][cols];
//        Vector2[][] veioDe = new Vector2[rows][cols];
//
//        Queue<Vector2> fila = new LinkedList<>();
//        fila.add(new Vector2(startX, startY));
//        visitado[startY][startX] = true;
//
//        int[][] direcoes = {
//            {1, 0}, {-1, 0}, {0, 1}, {0, -1}
//        };
//
//        while (!fila.isEmpty()) {
//            Vector2 atual = fila.poll();
//            int ax = (int) atual.x;
//            int ay = (int) atual.y;
//
//            if (ax == goalX && ay == goalY) break;
//
//            for (int[] dir : direcoes) {
//                int nx = ax + dir[0];
//                int ny = ay + dir[1];
//
//                if (nx >= 0 && nx < cols && ny >= 0 && ny < rows &&
//                    !visitado[ny][nx] && mapa[ny][nx] == ' ') {
//                    fila.add(new Vector2(nx, ny));
//                    visitado[ny][nx] = true;
//                    veioDe[ny][nx] = new Vector2(ax, ay);
//                }
//            }
//        }
//
//        // Reconstrói o caminho
//        List<Vector2> caminho = new ArrayList<>();
//        Vector2 atual = new Vector2(goalX, goalY);
//        while (veioDe[(int)atual.y][(int)atual.x] != null) {
//            caminho.add(atual);
//            atual = veioDe[(int)atual.y][(int)atual.x];
//        }
//
//        Collections.reverse(caminho);
//        return caminho;
//    }



//import com.badlogic.Labirinth.Mapa;
//import com.badlogic.gdx.math.Vector2;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Pathfinding {
//
//    public static List<Vector2> encontrarCaminho(int startX, int startY, int goalX, int goalY) {
//        List<Node> openSet = new ArrayList<>();
//        List<Node> closedSet = new ArrayList<>();
//
//        Node startNode = new Node(startX, startY, null);
//        Node goalNode = new Node(goalX, goalY, null);
//
//        openSet.add(startNode);
//
//        while (!openSet.isEmpty()) {
//            // Pega o nó com menor fCost
//            Node current = openSet.get(0);
//            for (Node n : openSet) {
//                if (n.getFCost() < current.getFCost()) current = n;
//            }
//
//            if (current.equals(goalNode)) {
//                return reconstruirCaminho(current);
//            }
//
//            openSet.remove(current);
//            closedSet.add(current);
//
//            for (Vector2 vizinho : getVizinhos(current)) {
//                int nx = (int) vizinho.x;
//                int ny = (int) vizinho.y;
//
//                if (!ehTileValido(nx, ny)) continue;
//
//                Node neighborNode = new Node(nx, ny, current);
//                if (closedSet.contains(neighborNode)) continue;
//
//                float novoG = current.gCost + 1;
//
//                boolean melhorCaminho = false;
//                if (!openSet.contains(neighborNode)) {
//                    melhorCaminho = true;
//                    neighborNode.hCost = heuristica(nx, ny, goalX, goalY);
//                    openSet.add(neighborNode);
//                } else if (novoG < neighborNode.gCost) {
//                    melhorCaminho = true;
//                }
//
//                if (melhorCaminho) {
//                    neighborNode.gCost = novoG;
//                    neighborNode.parent = current;
//                }
//            }
//        }
//
//        return new ArrayList<>(); // Sem caminho
//    }
//
//    private static List<Vector2> reconstruirCaminho(Node node) {
//        List<Vector2> caminho = new ArrayList<>();
//        while (node != null) {
//            caminho.add(0, new Vector2(node.x, node.y));
//            node = node.parent;
//        }
//        return caminho;
//    }
//
//    private static float heuristica(int x1, int y1, int x2, int y2) {
//        return Math.abs(x1 - x2) + Math.abs(y1 - y2); // Manhattan
//    }
//
//    private static List<Vector2> getVizinhos(Node node) {
//        List<Vector2> vizinhos = new ArrayList<>();
//        vizinhos.add(new Vector2(node.x + 1, node.y));
//        vizinhos.add(new Vector2(node.x - 1, node.y));
//        vizinhos.add(new Vector2(node.x, node.y + 1));
//        vizinhos.add(new Vector2(node.x, node.y - 1));
//        return vizinhos;
//    }
//
//    private static boolean ehTileValido(int x, int y) {
////        Mapa mapa = Mapa.getInstance();
//        char tile = Mapa.getInstance().getTile(x, y);
//        return tile == ' ';
//    }
//}
