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
