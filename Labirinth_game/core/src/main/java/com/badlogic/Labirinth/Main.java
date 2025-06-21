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
    import com.badlogic.gdx.utils.ScreenUtils;
    import com.badlogic.gdx.utils.viewport.FillViewport;
    import com.badlogic.gdx.utils.viewport.FitViewport;

    import java.awt.*;

    /** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
    public class Main implements ApplicationListener {


        private SpriteBatch batch;

        Texture wallTexture;
        Texture floorTexture;
        Texture WizardTexture;
        Texture Wizard2Texture;

//        public char[][] mapData;

        FillViewport viewport;

        int rows;
        int cols;

//        Sprite WizardSprite;

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
        Portas portas;
        int TILE_SIZE = Mapa.getInstance().TILE_SIZE;

        Jogador mago;
//        Jogador mago2;

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
    //        mapData = loadMap("map.txt");

            //testando singleton do mapa
            Mapa.iniciarMapa();
            Mapa.getInstance().setDadosDoMapa("map.txt");
//            mapData = Mapa.getInstance().getDadosDoMapa();

            //para uso no draw
//             = matriz; // Temporário, até remover o uso direto

    //        Mapa.getInstance().getDadosDoMapa(), rows, cols
            portas = new Portas();


            viewport = new FillViewport(Mapa.getInstance().worldWidth, Mapa.getInstance().worldHeight);
            ((OrthographicCamera)viewport.getCamera()).zoom=.3f;

//            WizardSprite = new Sprite(WizardTexture);//
//            WizardSprite.setSize((float) TILE_SIZE-20, (float) TILE_SIZE-20);
//            WizardSprite.setX(TILE_SIZE);
//    //        WizardSprite.setY(worldHeight - 2* TILE_SIZE);
//            WizardSprite.setY(TILE_SIZE);

//            mago2 = new Jogador(
//                Input.Keys.W, Input.Keys.S, Input.Keys.D, Input.Keys.A,
//                Wizard2Texture,
//                TILE_SIZE, TILE_SIZE
//            );

            mago = new Jogador(
                Input.Keys.UP, Input.Keys.DOWN, Input.Keys.RIGHT, Input.Keys.LEFT,
                WizardTexture,
                TILE_SIZE, TILE_SIZE
            );



            touchPos = new Vector2();
//            Gdx.graphics.setFullscreenMode();
        }



        @Override
        public void resize(int width, int height) {
            // Resize your application here. The parameters represent the new window size.
            viewport.update(width, height, true);
        }


//        float speed = 400f;
        private void input(){

            float delta = Gdx.graphics.getDeltaTime();
    //        float WizardWidth = WizardSprite.getWidth();
    //        float WizardHeight = WizardSprite.getHeight();

            //TESTANDO CLASSE JOGADOR
//            float moveX=0;
//            float moveY=0;
//
//            if(Gdx.input.isKeyPressed(Input.Keys.W)){
//                ((OrthographicCamera)viewport.getCamera()).zoom-=.01f;
//            }
//            else if(Gdx.input.isKeyPressed(Input.Keys.S)){
//                ((OrthographicCamera)viewport.getCamera()).zoom+=.01f;
//            }
//
//            if(Gdx.input.isKeyPressed(Input.Keys.K)){
//                speed+=100f;
//            }else if(Gdx.input.isKeyPressed(Input.Keys.L)){
//                speed-=100f;
//            }
//
//            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
//    //            WizardSprite.translateX(speed * delta); // move the Wizard right
//                moveX += speed * delta;
//            }
//            else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
//    //            WizardSprite.translateX(-speed * delta); // move the Wizard leff
//                moveX -=speed * delta;
//            }
//            if(Gdx.input.isKeyPressed(Input.Keys.UP)){
//    //            WizardSprite.translateY(speed * delta);
//                moveY += speed * delta;
//            }
//            else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
//    //            WizardSprite.translateY(-speed * delta);
//                moveY -= speed * delta;
//            }
//
//            if (Gdx.input.isTouched()) {
//                if (!wasTouched) {
//                    touchPos.set(Gdx.input.getX(), Gdx.input.getY());
//                    viewport.unproject(touchPos);
//                    int portaClicada = portas.getClickedDoor(touchPos.x, touchPos.y);
//                    portas.invertDoor(portaClicada, Mapa.getInstance().getDadosDoMapa());
//                    wasTouched = true;
//                }
//
//                //codigo para movimentação com o mouse:
//    //            touchPos.set(Gdx.input.getX(), Gdx.input.getY()); // Get where the touch happened on screen
//    //            viewport.unproject(touchPos); // Convert the units to the world units of the viewport
//    //
//    //            float dirX = touchPos.x - WizardSprite.getX();
//    //            float dirY = touchPos.y - WizardSprite.getY();
//    //            float norma = (float) Math.sqrt(dirX * dirX + dirY * dirY);
//    //
//    //            if(norma != 0){
//    //                moveX += dirX / norma * speed * delta;
//    //                moveY += dirY / norma * speed * delta;
//    //            }
//
//
//            } else {
//                wasTouched = false; // apenas aqui reseta o toque
//            }
//
//            float newX = WizardSprite.getX() + moveX;
//            float newY = WizardSprite.getY() + moveY;
//
//
//            if (!isCollidingWithWall(newX, WizardSprite.getY())) {
//                WizardSprite.translateX(moveX);
//            }
//            if (!isCollidingWithWall(WizardSprite.getX(), newY)) {
//                WizardSprite.translateY(moveY);
//            }

//            mago2.updatePlayer(viewport, portas);
            mago.updatePlayer(viewport, portas);

        }

        private void logic() {
//            float WizardWidth = WizardSprite.getWidth();
//            float WizardHeight = WizardSprite.getHeight();

//            float magoWidth = mago.getJogadorWidth();
//            float magoHeight = mago.getJogadorHeight();
//            viewport.getCamera().position.set(WizardSprite.getX() + magoWidth / 2, WizardSprite.getY() + magoHeight/ 2, 0);

//                mago2.atualizarCamera(viewport);
                mago.atualizarCamera(viewport);
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

            for (int y = 0; y < Mapa.getInstance().getDadosDoMapa().length; y++) {
                for (int x = 0; x < Mapa.getInstance().getDadosDoMapa()[y].length; x++) {
                    Texture tex = null;
                    switch (Mapa.getInstance().getDadosDoMapa()[y][x]) {
                        case 'a':tex = floorATexture;break;
                        case 'b':tex = floorBTexture;break;
                        case 'c':tex = floorCTexture;break;
                        case 'd':tex = floorDTexture;break;
                        case 'e':tex = floorETexture;break;
                        case 'h':tex = floorHTexture;break;
                        case 't':tex = floorTTexture;break;
                        case 'u':tex = floorUTexture;break;
                        case 'v':tex = floorVTexture;break;
                        case 'w':tex = floorWTexture;break;
                        case 'x':tex = floorXTexture;break;
                        case '+':tex = floorPTexture;break;
                        case 'g':tex = floorGTexture;break;
                        case 'f':tex = floorFTexture;break;
                        case 'i':tex = floorITexture;break;

                        case ' ':
                            tex = floorTexture;
                            break;
                    }
                    if (tex != null) {
                        batch.draw(tex, x * TILE_SIZE, (Mapa.getInstance().getDadosDoMapa().length - 1 - y) * TILE_SIZE);
                    }
                }
            }

            portas.draw(batch);
//            WizardSprite.draw(batch);
            mago.draw(batch);
//            mago2.draw(batch);
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

            // Liberação de eventuais recursos internos (se houver)
            // portas.dispose(); // se a classe Portas possuir dispose()

            // Solicita saída da aplicação
//            Gdx.app.exit();

            // Força o encerramento da JVM (útil para encerrar corretamente no IntelliJ)
//            System.exit(0);
        }

        public Portas getPortas(){
            return this.portas;
        }
    }
