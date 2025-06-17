package com.badlogic.Labirinth;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import java.util.ArrayList;

public class Portas {
    private Sprite[] sprites;
    private boolean[] estados; // true = aberta, false = fechada
    private int size = 16;

    private Texture texturaAberta;
    private Texture texturaFechada;

    public Portas() {
        texturaAberta = new Texture("porta_aberta.png");
        texturaFechada = new Texture("porta_fechada.png");

        sprites = new Sprite[size];
        estados = new boolean[size];

        for (int i = 0; i < size; i++) {
            estados[i] = false; // todas começam abertas

            if(i % 2==0){
                estados[i] = true;
                sprites[i] = new Sprite(texturaFechada);
            }
            else{
                sprites[i] = new Sprite(texturaAberta);
            }
            sprites[i].setPosition(i*64, i*64); // posição horizontal
        }
    }

    public void invertDoor(int i) {
        if (i >= 0 && i < size) {
            estados[i] = !estados[i];
            sprites[i].setTexture(estados[i] ? texturaAberta : texturaFechada);

            if (i % 2 == 0 && i + 1 < size) {
                estados[i + 1] = false;
                sprites[i + 1].setTexture(texturaFechada);
            } else if (i % 2 != 0 && i - 1 >= 0) {
                estados[i - 1] = false;
                sprites[i - 1].setTexture(texturaFechada);
            }
        }
    }

    public void draw(com.badlogic.gdx.graphics.g2d.SpriteBatch batch) {
        for (Sprite sprite : sprites) {
            sprite.draw(batch);
        }
    }

    public int getClickedDoor(float x, float y) {
        for (int i = 0; i < size; i++) {
            if (sprites[i].getBoundingRectangle().contains(x, y)) {
                return i;
            }
        }
        return -1;
    }

    public void dispose() {
        texturaAberta.dispose();
        texturaFechada.dispose();
    }
}


//package com.badlogic.Labirinth;
//
//public class Portas {
//    protected Boolean[] portas;
//    private int size = 16;
//
//    public Portas() {
//        portas = new Boolean[size];
//        for (int i = 0; i < size; i++) {
//            portas[i] = true; // true = porta aberta
//        }
//    }
//
//    public void invertDoor(int door) {
//        if (door >= 0 && door < size) {
//
//            portas[door] = !portas[door];
//
//            if (door % 2 == 0) {
//                if (door + 1 < size) {
//                    portas[door + 1] = false; // false = fechada
//                }
//            }
//            // Se índice é ímpar, tenta fechar i-1
//            else {
//                if (door - 1 >= 0) {
//                    portas[door - 1] = false;
//                }
//            }
//        }
//    }
//
////    // Método opcional para verificar o estado das portas
////    public void printPortas() {
////        for (int i = 0; i < size; i++) {
////            System.out.println("Porta " + i + ": " + (portas[i] ? "Aberta" : "Fechada"));
////        }
////    }
//}
