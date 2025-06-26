package com.badlogic.Labirinth;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.Input;

public class InitialScreen implements Screen {
    private final Game game;
    private SpriteBatch batch;
    private Texture background;
    private Texture btnIniciar, btnOpcoes, btnSair;
    private Rectangle rectIniciar, rectOpcoes, rectSair;
    private Texture titulo;

    public InitialScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        try {
            background = new Texture(Gdx.files.internal("background.png"));
            btnIniciar = new Texture(Gdx.files.internal("sem_fundo_INICIARJOGO.png"));
            btnOpcoes = new Texture(Gdx.files.internal("sem_fundo_OPCOES.png"));
            btnSair = new Texture(Gdx.files.internal("sem_fundo_SAIR.png"));
            titulo = new Texture(Gdx.files.internal("TITULO.png"));
        } catch (Exception e) {
            // É uma boa prática imprimir o stack trace para depuração
            e.printStackTrace();
            System.out.println("Erro ao carregar imagens da tela inicial: " + e.getMessage());
        }

        // --- LÓGICA DE POSICIONAMENTO CORRIGIDA ---
        // 1. Obter dimensões da tela
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        // 2. Definir o tamanho e espaçamento dos botões de forma relativa à tela
        float buttonWidth = screenWidth * 0.4f; // Botões terão 40% da largura da tela
        float buttonHeight = buttonWidth / 4f; // Altura proporcional (1/4 da largura)
        float spacing = screenHeight * 0.02f; // Espaçamento de 2% da altura da tela

        // 3. Calcular a posição X (horizontal), que será a mesma para todos os botões
        float buttonX = (screenWidth - buttonWidth) / 2f;

        // 4. Calcular as posições Y (vertical)
        // O botão OPÇÕES será centralizado na metade inferior da tela
        float centerY = screenHeight * 0.35f; // Mais para baixo
        float yOpcoes = centerY - (buttonHeight / 2);
        float yIniciar = yOpcoes + buttonHeight + spacing;
        float ySair = yOpcoes - buttonHeight - spacing;

        // 5. Criar os retângulos de colisão com as posições calculadas
        rectIniciar = new Rectangle(buttonX, yIniciar, buttonWidth, buttonHeight);
        rectOpcoes = new Rectangle(buttonX, yOpcoes, buttonWidth, buttonHeight);
        rectSair = new Rectangle(buttonX, ySair, buttonWidth, buttonHeight);

        // Imprimir as novas coordenadas para verificação
        System.out.println("--- NOVAS COORDENADAS ---");
        System.out.println("Botão INICIAR: x=" + rectIniciar.x + ", y=" + rectIniciar.y + ", w=" + rectIniciar.width + ", h=" + rectIniciar.height);
        System.out.println("Botão OPÇÕES: x=" + rectOpcoes.x + ", y=" + rectOpcoes.y + ", w=" + rectOpcoes.width + ", h=" + rectOpcoes.height);
        System.out.println("Botão SAIR: x=" + rectSair.x + ", y=" + rectSair.y + ", w=" + rectSair.width + ", h=" + rectSair.height);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        if (background != null) batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        if (titulo != null) {
            float tituloWidth = Gdx.graphics.getWidth() * 0.6f;
            float tituloScale = tituloWidth / titulo.getWidth();
            float tituloHeight = titulo.getHeight() * tituloScale;
            float tituloX = (Gdx.graphics.getWidth() - tituloWidth) / 2f;
            float tituloY = rectIniciar.y + rectIniciar.height + 60f;
            if (tituloY + tituloHeight > Gdx.graphics.getHeight()) {
                tituloY = Gdx.graphics.getHeight() - tituloHeight - 20f;
            }
            batch.draw(titulo, tituloX, tituloY, tituloWidth, tituloHeight);
        }
        // Desenhar botões mantendo proporção da textura
        if (btnIniciar != null) {
            float scale = Math.min(rectIniciar.width / btnIniciar.getWidth(), rectIniciar.height / btnIniciar.getHeight()) * 4.0f;
            float drawW = btnIniciar.getWidth() * scale;
            float drawH = btnIniciar.getHeight() * scale;
            float drawX = rectIniciar.x + (rectIniciar.width - drawW) / 2f;
            float drawY = rectIniciar.y + (rectIniciar.height - drawH) / 2f;
            batch.draw(btnIniciar, drawX, drawY, drawW, drawH);
        }
        if (btnOpcoes != null) {
            float scale = Math.min(rectOpcoes.width / btnOpcoes.getWidth(), rectOpcoes.height / btnOpcoes.getHeight()) * 4.0f * 0.6f;
            float drawW = btnOpcoes.getWidth() * scale;
            float drawH = btnOpcoes.getHeight() * scale;
            float drawX = rectOpcoes.x + (rectOpcoes.width - drawW) / 2f;
            float drawY = rectOpcoes.y + (rectOpcoes.height - drawH) / 2f;
            batch.draw(btnOpcoes, drawX, drawY, drawW, drawH);
        }
        if (btnSair != null) {
            float scale = Math.min(rectSair.width / btnSair.getWidth(), rectSair.height / btnSair.getHeight()) * 4.0f * 0.6f;
            float drawW = btnSair.getWidth() * scale;
            float drawH = btnSair.getHeight() * scale;
            float drawX = rectSair.x + (rectSair.width - drawW) / 2f;
            float drawY = rectSair.y + (rectSair.height - drawH) / 2f;
            batch.draw(btnSair, drawX, drawY, drawW, drawH);
        }
        batch.end();
        handleInput();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            // Inverter Y pois o 0,0 do mouse é no topo esquerdo
            touch.y = Gdx.graphics.getHeight() - touch.y;
            if (rectIniciar.contains(touch.x, touch.y)) {
                // Colocar em tela cheia antes de iniciar o jogo
                Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                game.setScreen(new GameScreen(game));
            } else if (rectOpcoes.contains(touch.x, touch.y)) {
                // Aqui você pode abrir uma tela de opções futuramente
                System.out.println("Opções clicado");
            } else if (rectSair.contains(touch.x, touch.y)) {
                Gdx.app.exit();
            }
        }
    }

    @Override
    public void resize(int width, int height) {}
    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() { dispose(); }
    @Override
    public void dispose() {
        if (batch != null) batch.dispose();
        if (background != null) background.dispose();
        if (btnIniciar != null) btnIniciar.dispose();
        if (btnOpcoes != null) btnOpcoes.dispose();
        if (btnSair != null) btnSair.dispose();
        if (titulo != null) titulo.dispose();
    }
}
