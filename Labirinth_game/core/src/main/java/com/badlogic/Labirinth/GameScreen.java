package com.badlogic.Labirinth;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;

///////////////////////
import com.badlogic.gdx.utils.viewport.*;

import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
///


public class GameScreen implements Screen {
    private final Game game;
    private SpriteBatch batch;
    Texture wallTexture, floorTexture, WizardTexture, Wizard2Texture;
    FillViewport viewport, viewportJogador2;
    FitViewport miniMapa;
    Vector2 touchPos;
    Texture floorATexture, floorBTexture, floorCTexture, floorDTexture, floorHTexture, floorTTexture, floorUTexture, floorVTexture, floorWTexture, floorXTexture, floorPTexture, floorETexture, floorGTexture, floorFTexture, floorITexture, transfiguradorTexture, keyTexture;
    Portas portas;
    int TILE_SIZE;
    Jogador mago, mago2;
    Transfigurador transfigurador;
    Chave key;
    boolean camera = false;

    Stage uiStage;

    ImageButton button;
    boolean clickedButton = false;
    Texture letterBackgroundTexture;
    Skin skin;
    Table letterTable;
    TextArea messageField;
    boolean isLetterWriting = false;
    BitmapFont letterFont;
    FreeTypeFontGenerator generator;
    Texture backgroundLetter;

    public GameScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
                            uiStage = new Stage(new ScreenViewport(), batch);

                            generator = new FreeTypeFontGenerator(Gdx.files.internal("Babynotes.otf"));
                            FreeTypeFontParameter parameter = new FreeTypeFontParameter();
                            parameter.size = 26;
                            parameter.color = Color.BLACK;
                            letterFont = generator.generateFont(parameter);

                            skin = new Skin(Gdx.files.internal("uiskin.json"));
                            backgroundLetter = new Texture(Gdx.files.internal("background_letter.png"));
                            TextureRegionDrawable backgroundDrawable = new TextureRegionDrawable(backgroundLetter);

                            skin.get("default", TextField.TextFieldStyle.class).background = backgroundDrawable;
                            skin.add("default-font", letterFont);
                            skin.get("default", TextField.TextFieldStyle.class).font = letterFont;

                            letterBackgroundTexture = new Texture(Gdx.files.internal("letter_view.png"));
                            Texture tex = new Texture("botao_carta.png");
                            ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
                            style.imageUp = new TextureRegionDrawable(new TextureRegion(tex));
                            style.imageDown = style.imageUp;
                            button = new ImageButton(style);
                            button.setSize(100, 90);
                            button.setPosition(20, 20);

        wallTexture = new Texture(Gdx.files.internal("wall.png"));
        floorTexture = new Texture(Gdx.files.internal("floor.png"));
        WizardTexture = new Texture(Gdx.files.internal("Wizard.png"));
        Wizard2Texture = new Texture(Gdx.files.internal("magoBola.png"));
        floorATexture = new Texture(Gdx.files.internal("floorA.png"));
        floorBTexture = new Texture(Gdx.files.internal("floorB.png"));
        floorCTexture = new Texture(Gdx.files.internal("floorC.png"));
        floorDTexture = new Texture(Gdx.files.internal("floorD.png"));
        floorHTexture = new Texture(Gdx.files.internal("floorH.png"));
        floorTTexture = new Texture(Gdx.files.internal("floorT.png"));
        floorUTexture = new Texture(Gdx.files.internal("floorU.png"));
        floorVTexture = new Texture(Gdx.files.internal("floorV.png"));
        floorWTexture = new Texture(Gdx.files.internal("floorW.png"));
        floorXTexture = new Texture(Gdx.files.internal("floorX.png"));
        floorPTexture = new Texture(Gdx.files.internal("floor+.png"));
        floorETexture = new Texture(Gdx.files.internal("floorE.png"));
        floorGTexture = new Texture(Gdx.files.internal("floorG.png"));
        floorFTexture = new Texture(Gdx.files.internal("floorF.png"));
        floorITexture = new Texture(Gdx.files.internal("floorI.png"));
        transfiguradorTexture = new Texture(Gdx.files.internal("transfigurador1.png"));
        keyTexture = new Texture(Gdx.files.internal("Chave.png"));

        Mapa.iniciarMapa();
        Mapa.getInstance().setDadosDoMapa("map.txt");
        portas = new Portas();
        TILE_SIZE = Mapa.getInstance().TILE_SIZE;
        viewportJogador2 = new FillViewport(Mapa.getInstance().worldWidth , Mapa.getInstance().worldHeight);
        ((OrthographicCamera)viewportJogador2.getCamera()).zoom=.5f;
        miniMapa = new FitViewport((float) Mapa.getInstance().worldWidth/10, (float) Mapa.getInstance().worldHeight/10);
        ((OrthographicCamera)miniMapa.getCamera()).zoom=15f;

        viewport = new FillViewport(Mapa.getInstance().worldWidth , Mapa.getInstance().worldHeight);
        ((OrthographicCamera)viewport.getCamera()).zoom=.5f;

        mago2 = new Jogador(
            Input.Keys.W, Input.Keys.S, Input.Keys.D, Input.Keys.A, Input.Keys.Q,
            Wizard2Texture,
            TILE_SIZE, TILE_SIZE
        );
        mago = new Jogador(
            Input.Keys.UP, Input.Keys.DOWN, Input.Keys.RIGHT, Input.Keys.LEFT, Input.Keys.ENTER,
            WizardTexture,
            (Mapa.getInstance().getRows()-2)*TILE_SIZE, (Mapa.getInstance().getCols()-3)*TILE_SIZE
        );
        touchPos = new Vector2();
        transfigurador = new Transfigurador(transfiguradorTexture,  1,1, 300f);
//        key = new Chave(keyTexture, 1, 1);

        //descomente abaixo para o jogo começar com tela cheia, mas acho q tem algum problema em fazer só isso
        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());


                                                        button.setTouchable(Touchable.enabled);
                                                        button.addListener(new ClickListener() {
                                                            @Override
                                                            public void clicked(InputEvent event, float x, float y){
                                                                isLetterWriting = true;
                                                                letterTable.setVisible(true);
                                                                uiStage.setKeyboardFocus(messageField);
                                                            }
                                                        });

                                                        createLetterUI(); // Chama o novo método que cria a UI
                                                        uiStage.addActor(button);
                                                        uiStage.addActor(letterTable);

    }

    @Override
    public void render(float delta) {
        if (mago == null || mago2 == null || transfigurador == null || portas == null) return;
        input();
        logic();
        draw();

        // --- ATUALIZA E DESENHA A UI (que fica por cima de tudo) ---
        uiStage.getViewport().apply();
        uiStage.act(Gdx.graphics.getDeltaTime()); // Atualiza a UI (importante para cursor, etc.)
        uiStage.draw();

//        input();
//        logic();
//
//        ScreenUtils.clear(Color.BLACK);
//
//        // --- Desenha a primeira viewport (jogador 1) ---
//        drawGame(viewport);
//
//        // --- Desenha a minimapa ---
//        drawMiniMap();
//
//        // --- Desenha a segunda viewport (jogador 2) ---
//        drawGame(viewportJogador2);
//
//        // --- ATUALIZA E DESENHA A UI (que fica por cima de tudo) ---
//        uiStage.getViewport().apply();
//        uiStage.act(Gdx.graphics.getDeltaTime()); // Atualiza a UI (importante para cursor, etc.)
//        uiStage.draw();

    }

    private void input(){


//        mago.updatePlayer(viewport, portas);
//        mago2.updatePlayer(viewportJogador2, portas);

//        transfigurador.update(Gdx.graphics.getDeltaTime());

        if(Gdx.input.isKeyJustPressed(Input.Keys.P)){
            camera = !camera;
        }
        if (!isLetterWriting) {
            mago.updatePlayer(viewport, portas);
            mago2.updatePlayer(viewportJogador2, portas);
            transfigurador.update(Gdx.graphics.getDeltaTime());
        }

    }

    private void logic() {
//        mago.atualizarCamera(miniMapa);
//
//        if(!camera){
//            mago.atualizarCamera(viewportJogador2);
//            mago2.atualizarCamera(viewport);
//        }
//        else{
//            mago.atualizarCamera(viewport);
//            mago2.atualizarCamera(viewportJogador2);
//        }

        if (!isLetterWriting) {
//            mago.atualizarCamera(miniMapa);

            if(Gdx.input.isKeyJustPressed(Input.Keys.P)){
                camera = !camera;
            }
            if(camera){
                mago.atualizarCamera(viewportJogador2);
                mago2.atualizarCamera(viewport);
            }
            else{
                mago.atualizarCamera(viewport);
                mago2.atualizarCamera(viewportJogador2);
            }
        }

    }

    private void drawMiniMap() {
        miniMapa.apply();
        int factor = 5;
        // Posição corrigida para o canto inferior direito da primeira viewport
        Gdx.gl.glViewport(
            viewport.getScreenX() + viewport.getScreenWidth() - factor * TILE_SIZE - 10,
            viewport.getScreenY() + 10,
            TILE_SIZE * factor,
            TILE_SIZE * factor
        );

        batch.setProjectionMatrix(miniMapa.getCamera().combined);
        batch.begin();
        drawGameContent(batch);
        batch.end();

        // Restaura o viewport global para não bagunçar o desenho seguinte
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    // Método auxiliar para desenhar o conteúdo do jogo
    private void drawGameContent(SpriteBatch batch) {
        for (int y = 0; y < Mapa.getInstance().getDadosDoMapa().length; y++) {
            for (int x = 0; x < Mapa.getInstance().getDadosDoMapa()[y].length; x++) {
                Texture tex = getTextureForTile(Mapa.getInstance().getDadosDoMapa()[y][x]);
                if (tex != null) {
                    batch.draw(tex, x * TILE_SIZE, (Mapa.getInstance().getDadosDoMapa().length - 1 - y) * TILE_SIZE);
                }
            }
        }
        portas.draw(batch);
        mago.draw(batch);
        mago2.draw(batch);
        transfigurador.draw(batch);
        key.draw(batch);
    }

    // Desenha em uma viewport específica
    private void drawGame(Viewport vp) {
        vp.apply();
        batch.setProjectionMatrix(vp.getCamera().combined);
        batch.begin();
        drawGameContent(batch);
        batch.end();
    }

    private Texture getTextureForTile(char tileChar) {
        switch (tileChar) {
            case 'a': return floorATexture; case 'b': return floorBTexture;
            case 'c': return floorCTexture; case 'd': return floorDTexture;
            case 'e': return floorETexture; case 'h': return floorHTexture;
            case 't': return floorTTexture; case 'u': return floorUTexture;
            case 'v': return floorVTexture; case 'w': return floorWTexture;
            case 'x': return floorXTexture; case '+': return floorPTexture;
            case 'g': return floorGTexture; case 'f': return floorFTexture;
            case 'i': return floorITexture; case ' ': return floorTexture;
            default: return null;
        }
    }

    private void draw(){
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
            desenharMapa(batch);
            portas.draw(batch);
            mago.draw(batch);
            mago2.draw(batch);
            transfigurador.draw(batch);
//            key.draw(batch);
            Mapa.getInstance().drawChaves(batch);
        batch.end();

        miniMapa.apply();
        int factor=5;
        Gdx.gl.glViewport(viewport.getScreenWidth()-factor*TILE_SIZE-10, 10, TILE_SIZE*factor, TILE_SIZE*factor);
        batch.setProjectionMatrix(miniMapa.getCamera().combined);
        batch.begin();
            desenharMapa(batch);
            portas.draw(batch);
            mago.draw(batch);
            mago2.draw(batch);
//            key.draw(batch);
            Mapa.getInstance().drawChaves(batch);
        batch.end();

        viewportJogador2.apply();
        batch.setProjectionMatrix(viewportJogador2.getCamera().combined);
        batch.begin();
            desenharMapa(batch);
            portas.draw(batch);
            mago.draw(batch);
            mago2.draw(batch);
            transfigurador.draw(batch);
//            key.draw(batch);
            Mapa.getInstance().drawChaves(batch);
        batch.end();
    }

    private void desenharMapa(SpriteBatch batch) {
        char[][] mapa = Mapa.getInstance().getDadosDoMapa();
        for (int y = 0; y < mapa.length; y++) {
            for (int x = 0; x < mapa[y].length; x++) {
                Texture tex = getTextureFromChar(mapa[y][x]);
                if (tex != null) {
                    batch.draw(tex, x * TILE_SIZE, (mapa.length - 1 - y) * TILE_SIZE);
                }
            }
        }
    }

    private Texture getTextureFromChar(char tile) {
        switch (tile) {
            case 'a': return floorATexture;
            case 'b': return floorBTexture;
            case 'c': return floorCTexture;
            case 'd': return floorDTexture;
            case 'e': return floorETexture;
            case 'f': return floorFTexture;
            case 'g': return floorGTexture;
            case 'h': return floorHTexture;
            case 'i': return floorITexture;
            case 't': return floorTTexture;
            case 'u': return floorUTexture;
            case 'v': return floorVTexture;
            case 'w': return floorWTexture;
            case 'x': return floorXTexture;
            case '+': return floorPTexture;
            case ' ': return floorTexture;
            default: return null;
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width/2, height, true);
        viewportJogador2.update(width/2, height, true);
        miniMapa.update(width/2, height, true);
        viewport.setScreenBounds(0, 0, width / 2, height);
        viewportJogador2.setScreenBounds(width / 2, 0, width / 2, height);

                                                        uiStage.getViewport().update(width, height, true);
                                                        if (letterTable != null) {
                                                            letterTable.setPosition(
                                                                (width - letterTable.getWidth()) / 2,
                                                                (height - letterTable.getHeight()) / 2
                                                            );
                                                        }

                                                        Gdx.input.setInputProcessor(uiStage);

    }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() { dispose(); }
    @Override
    public void dispose() {
        if (batch != null) batch.dispose();
        if (wallTexture != null) wallTexture.dispose();
        if (floorTexture != null) floorTexture.dispose();
        if (WizardTexture != null) WizardTexture.dispose();
        if (floorATexture != null) floorATexture.dispose();
        if (floorBTexture != null) floorBTexture.dispose();
        if (floorCTexture != null) floorCTexture.dispose();
        if (floorDTexture != null) floorDTexture.dispose();
        if (floorHTexture != null) floorHTexture.dispose();
        if (floorTTexture != null) floorTTexture.dispose();
        if (floorUTexture != null) floorUTexture.dispose();
        if (floorVTexture != null) floorVTexture.dispose();
        if (floorWTexture != null) floorWTexture.dispose();
        if (floorXTexture != null) floorXTexture.dispose();
        if (floorPTexture != null) floorPTexture.dispose();
        if (floorETexture != null) floorETexture.dispose();
        if (floorGTexture != null) floorGTexture.dispose();
        if (floorFTexture != null) floorFTexture.dispose();
        if (floorITexture != null) floorITexture.dispose();
        if (portas != null) portas.dispose();
    }

    private void createLetterUI() {
        letterTable = new Table();
        letterTable.setBackground(new TextureRegionDrawable(new TextureRegion(letterBackgroundTexture)));
        letterTable.setSize(600, 400);

        messageField = new TextArea("Escreva sua mensagem aqui...", skin);

        TextButton sendButton = new TextButton("Enviar", skin);
        sendButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Mensagem enviada: " + messageField.getText());
                closeLetter();
            }
        });

        TextButton cancelButton = new TextButton("Cancelar", skin);
        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Escrita cancelada.");
                closeLetter();
            }
        });

        letterTable.add(messageField).width(500).height(225).pad(20).colspan(2).row();
        letterTable.add(cancelButton).pad(10);
        letterTable.add(sendButton).pad(10);

        letterTable.setVisible(false);
    }

    private void closeLetter() {
        messageField.setText("Escreva sua mensagem aqui...");
        letterTable.setVisible(false);
        isLetterWriting = false;
        uiStage.setKeyboardFocus(null);
    }

}
