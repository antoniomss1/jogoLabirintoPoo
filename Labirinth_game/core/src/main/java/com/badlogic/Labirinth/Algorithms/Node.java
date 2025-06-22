package com.badlogic.Labirinth.Algorithms;

public class Node {
    public int x, y;
    public Node parent;
    public float gCost; // custo do início até este ponto
    public float hCost; // heurística (distância até o objetivo)

    public Node(int x, int y, Node parent) {
        this.x = x;
        this.y = y;
        this.parent = parent;
    }

    public float getFCost() {
        return gCost + hCost;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Node)) return false;
        Node other = (Node) obj;
        return this.x == other.x && this.y == other.y;
    }
}
