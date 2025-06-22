package com.badlogic.Labirinth.Algorithms;

import com.badlogic.Labirinth.Mapa;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Pathfinding {

    public static List<Vector2> encontrarCaminho(int startX, int startY, int goalX, int goalY) {
        List<Node> openSet = new ArrayList<>();
        List<Node> closedSet = new ArrayList<>();

        Node startNode = new Node(startX, startY, null);
        Node goalNode = new Node(goalX, goalY, null);

        openSet.add(startNode);

        while (!openSet.isEmpty()) {
            // Pega o nó com menor fCost
            Node current = openSet.get(0);
            for (Node n : openSet) {
                if (n.getFCost() < current.getFCost()) current = n;
            }

            if (current.equals(goalNode)) {
                return reconstruirCaminho(current);
            }

            openSet.remove(current);
            closedSet.add(current);

            for (Vector2 vizinho : getVizinhos(current)) {
                int nx = (int) vizinho.x;
                int ny = (int) vizinho.y;

                if (!ehTileValido(nx, ny)) continue;

                Node neighborNode = new Node(nx, ny, current);
                if (closedSet.contains(neighborNode)) continue;

                float novoG = current.gCost + 1;

                boolean melhorCaminho = false;
                if (!openSet.contains(neighborNode)) {
                    melhorCaminho = true;
                    neighborNode.hCost = heuristica(nx, ny, goalX, goalY);
                    openSet.add(neighborNode);
                } else if (novoG < neighborNode.gCost) {
                    melhorCaminho = true;
                }

                if (melhorCaminho) {
                    neighborNode.gCost = novoG;
                    neighborNode.parent = current;
                }
            }
        }

        return new ArrayList<>(); // Sem caminho
    }

    private static List<Vector2> reconstruirCaminho(Node node) {
        List<Vector2> caminho = new ArrayList<>();
        while (node != null) {
            caminho.add(0, new Vector2(node.x, node.y));
            node = node.parent;
        }
        return caminho;
    }

    private static float heuristica(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2); // Manhattan
    }

    private static List<Vector2> getVizinhos(Node node) {
        List<Vector2> vizinhos = new ArrayList<>();
        vizinhos.add(new Vector2(node.x + 1, node.y));
        vizinhos.add(new Vector2(node.x - 1, node.y));
        vizinhos.add(new Vector2(node.x, node.y + 1));
        vizinhos.add(new Vector2(node.x, node.y - 1));
        return vizinhos;
    }

    private static boolean ehTileValido(int x, int y) {
//        Mapa mapa = Mapa.getInstance();
        char tile = Mapa.getInstance().getTile(x, y);
        return tile == ' ';
    }
}
