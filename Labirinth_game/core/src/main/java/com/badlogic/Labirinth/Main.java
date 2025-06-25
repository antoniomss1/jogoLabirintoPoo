    package com.badlogic.Labirinth;

    import com.badlogic.gdx.ApplicationListener;
    import com.badlogic.gdx.Gdx;
    import com.badlogic.gdx.Input;
    import com.badlogic.gdx.graphics.Color;
    import com.badlogic.gdx.graphics.GL20;
    import com.badlogic.gdx.graphics.OrthographicCamera;
    import com.badlogic.gdx.graphics.Texture;
    import com.badlogic.gdx.graphics.g2d.Sprite;
    import com.badlogic.gdx.graphics.g2d.SpriteBatch;
    import com.badlogic.gdx.math.MathUtils;
    import com.badlogic.gdx.math.Vector2;
    import com.badlogic.gdx.utils.Scaling;
    import com.badlogic.gdx.utils.ScreenUtils;
    import com.badlogic.gdx.utils.viewport.ExtendViewport;
    import com.badlogic.gdx.utils.viewport.FillViewport;
    import com.badlogic.gdx.utils.viewport.FitViewport;
    import com.badlogic.gdx.utils.viewport.ScalingViewport;

    import java.awt.*;

    /** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
    public class Main implements ApplicationListener {


        private SpriteBatch batch;

        Texture wallTexture;
        Texture floorTexture;
        Texture WizardTexture;
        Texture Wizard2Texture;

        FillViewport viewport;
        FitViewport miniMapa;
        FillViewport viewportJogador2;

        Vector2 touchPos;

        Texture floorATexture;
        Texture floorBTexture;
        Texture floorCTexture;
        Texture floorDTexture;
        Texture floorHTexture;
        Texture floorTTexture;
        Texture floorUTexture;
        Texture floorVTexture;
        Texture floorWTexture;
        Texture floorXTexture;
        Texture floorPTexture;
        Texture floorETexture;
        Texture floorGTexture;
        Texture floorFTexture;
        Texture floorITexture;
        Texture transfiguradorTexture;
        Texture keyTexture;

        Portas portas;
        int TILE_SIZE = Mapa.getInstance().TILE_SIZE;

        Jogador mago;
        Jogador mago2;

        Transfigurador transfigurador;

        Chave key;

        @Override
        public void create() {
            // Prepare your application here.
            batch = new SpriteBatch();

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
            keyTexture    = new Texture(Gdx.files.internal("Chave.png"));

            Mapa.iniciarMapa();
            Mapa.getInstance().setDadosDoMapa("map.txt");

            portas = new Portas();


            viewport = new FillViewport(Mapa.getInstance().worldWidth , Mapa.getInstance().worldHeight);
            ((OrthographicCamera)viewport.getCamera()).zoom=.5f;

            miniMapa = new FitViewport((float) Mapa.getInstance().worldWidth/10, (float) Mapa.getInstance().worldHeight/10);
            ((OrthographicCamera)miniMapa.getCamera()).zoom=15f;

            viewportJogador2 = new FillViewport(Mapa.getInstance().worldWidth , Mapa.getInstance().worldHeight);
            ((OrthographicCamera)viewportJogador2.getCamera()).zoom=2f;

            mago2 = new Jogador(
                Input.Keys.W, Input.Keys.S, Input.Keys.D, Input.Keys.A, Input.Keys.Q,
                Wizard2Texture,
                TILE_SIZE, TILE_SIZE
            );

            mago = new Jogador(
                Input.Keys.UP, Input.Keys.DOWN, Input.Keys.RIGHT, Input.Keys.LEFT, Input.Keys.ENTER,
                WizardTexture,
                TILE_SIZE, TILE_SIZE
            );
            touchPos = new Vector2();

            //descomente abaixo para o jogo começar com tela cheia, mas acho q tem algum problema em fazer só isso
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());

            transfigurador = new Transfigurador(transfiguradorTexture,  1,1, 300f);


            key = new Chave(keyTexture, 1, 1);
        }



        @Override
        public void resize(int width, int height) {
            // Resize your application here. The parameters represent the new window size.
            viewport.update(width/2, height, true);
            viewportJogador2.update(width/2, height, true);
            miniMapa.update(width/2, height, true);

            viewport.setScreenBounds(0, 0, width / 2, height);        // lado esquerdo
            viewportJogador2.setScreenBounds(width / 2, 0, width / 2, height); // lado direito


        }

        private void input(){

            mago.updatePlayer(viewport, portas);
            mago2.updatePlayer(viewportJogador2, portas);
            transfigurador.update(Gdx.graphics.getDeltaTime());

        }

        boolean camera = false;

        private void logic() {

            mago.atualizarCamera(miniMapa);

            //porque sim!
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

        @Override
        public void render() {
            input();
            logic();
            draw();
        }

        private void draw(){
            // Draw your application here.
            ScreenUtils.clear(Color.BLACK);

            viewport.apply();
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            batch.setProjectionMatrix(viewport.getCamera().combined);

            batch.begin();

            desenharMapa(batch);
//            for (int y = 0; y < Mapa.getInstance().getDadosDoMapa().length; y++) {
//                for (int x = 0; x < Mapa.getInstance().getDadosDoMapa()[y].length; x++) {
//                    Texture tex = null;
//                    switch (Mapa.getInstance().getDadosDoMapa()[y][x]) {
//                        case 'a':tex = floorATexture;break;
//                        case 'b':tex = floorBTexture;break;
//                        case 'c':tex = floorCTexture;break;
//                        case 'd':tex = floorDTexture;break;
//                        case 'e':tex = floorETexture;break;
//                        case 'h':tex = floorHTexture;break;
//                        case 't':tex = floorTTexture;break;
//                        case 'u':tex = floorUTexture;break;
//                        case 'v':tex = floorVTexture;break;
//                        case 'w':tex = floorWTexture;break;
//                        case 'x':tex = floorXTexture;break;
//                        case '+':tex = floorPTexture;break;
//                        case 'g':tex = floorGTexture;break;
//                        case 'f':tex = floorFTexture;break;
//                        case 'i':tex = floorITexture;break;
//
//                        case ' ':
//                            tex = floorTexture;
//                            break;
//                    }
//                    if (tex != null) {
//                        batch.draw(tex, x * TILE_SIZE, (Mapa.getInstance().getDadosDoMapa().length -1 - y) * TILE_SIZE);
//                    }
//                }
//            }

            portas.draw(batch);
//            WizardSprite.draw(batch);
            mago.draw(batch);
            mago2.draw(batch);
            transfigurador.draw(batch);

            key.draw(batch);

            batch.end();

//            miniMapa.apply();


            miniMapa.apply();
            int factor=5;
            Gdx.gl.glViewport(viewport.getScreenWidth()-factor*TILE_SIZE-10, 10, TILE_SIZE*factor, TILE_SIZE*factor); // canto inferior esquerdo

            batch.setProjectionMatrix(miniMapa.getCamera().combined);
            batch.begin();
            desenharMapa(batch);


            portas.draw(batch);
//            WizardSprite.draw(batch);
            mago.draw(batch);
            mago2.draw(batch);

            key.draw(batch);

            batch.end();

            viewportJogador2.apply();
            batch.setProjectionMatrix(viewportJogador2.getCamera().combined);
            batch.begin();

            desenharMapa(batch);

            portas.draw(batch);
//            WizardSprite.draw(batch);
            mago.draw(batch);
            mago2.draw(batch);
            transfigurador.draw(batch);
            key.draw(batch);

            batch.end();
        }

        @Override
        public void pause() {
            // Invoked when your application is paused.
        }

        @Override
        public void resume() {
            // Invoked when your application is resumed after pause.
        }

        @Override
        public void dispose() {
            System.out.println("DISPOSE CHAMADO");
            // Destroy application's resources here.
            // Libera o lote de desenho
            if (batch != null) batch.dispose();

            // Libera texturas
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

        public Portas getPortas(){
            return this.portas;
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
                default: return null; // pode retornar uma textura padrão de erro, se quiser
            }
        }


    }
