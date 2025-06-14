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

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main implements ApplicationListener {

    private SpriteBatch batch;

    Texture wallTexture;
    Texture floorTexture;
    Texture WizardTexture;

    char[][] mapData;
    final int TILE_SIZE = 64;
    int worldWidth;
    int worldHeight;

    FillViewport viewport;

    int rows;
    int cols;

    Sprite WizardSprite;

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

    @Override
    public void create() {
        // Prepare your application here.
        batch = new SpriteBatch();

        wallTexture = new Texture(Gdx.files.internal("wall.png"));
        floorTexture = new Texture(Gdx.files.internal("floor.png"));
        WizardTexture = new Texture(Gdx.files.internal("Wizard.png"));

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
        mapData = loadMap("map.txt");

        viewport = new FillViewport(worldWidth,worldHeight);
        ((OrthographicCamera)viewport.getCamera()).zoom=.3f;

        WizardSprite = new Sprite(WizardTexture);
        WizardSprite.setSize((float) TILE_SIZE-20, (float) TILE_SIZE-20);
        WizardSprite.setX(TILE_SIZE);
        WizardSprite.setY(worldHeight - 2* TILE_SIZE);

        touchPos = new Vector2();

    }

    private char[][] loadMap(String filename) {
        String[] lines = Gdx.files.internal(filename).readString().split("\n");
        rows = lines.length;
        cols = lines[0].length();

        worldWidth = TILE_SIZE * (rows-1);
        worldHeight = TILE_SIZE * (cols-1);

        char[][] map = new char[rows][cols];

        for (int y = 0; y < rows; y++) {

            map[y] = lines[y].toCharArray();
        }
        return map;
    }

    @Override
    public void resize(int width, int height) {
        // Resize your application here. The parameters represent the new window size.
        viewport.update(width, height, true);
    }
    float speed = 400f;
    private void input(){

        float delta = Gdx.graphics.getDeltaTime();
        float WizardWidth = WizardSprite.getWidth();
        float WizardHeight = WizardSprite.getHeight();
        float moveX=0;
        float moveY=0;

        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            ((OrthographicCamera)viewport.getCamera()).zoom-=.01f;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.S)){
            ((OrthographicCamera)viewport.getCamera()).zoom+=.01f;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.K)){
            speed+=100f;
        }else if(Gdx.input.isKeyPressed(Input.Keys.L)){
            speed-=100f;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
//            WizardSprite.translateX(speed * delta); // move the Wizard right
            moveX += speed * delta;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
//            WizardSprite.translateX(-speed * delta); // move the Wizard leff
            moveX -=speed * delta;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
//            WizardSprite.translateY(speed * delta);
            moveY += speed * delta;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
//            WizardSprite.translateY(-speed * delta);
            moveY -= speed * delta;
        }

        if (Gdx.input.isTouched()) { // If the user has clicked or tapped the screen
            touchPos.set(Gdx.input.getX(), Gdx.input.getY()); // Get where the touch happened on screen
            viewport.unproject(touchPos); // Convert the units to the world units of the viewport

            float dirX = touchPos.x - WizardSprite.getX();
            float dirY = touchPos.y - WizardSprite.getY();
            float norma = (float) Math.sqrt(dirX * dirX + dirY * dirY);

            if(norma != 0){
                moveX += dirX / norma * speed * delta;
                moveY += dirY / norma * speed * delta;
            }
        }

        float newX = WizardSprite.getX() + moveX;
        float newY = WizardSprite.getY() + moveY;


        if (!isCollidingWithWall(newX, WizardSprite.getY())) {
            WizardSprite.translateX(moveX);
        }
        if (!isCollidingWithWall(WizardSprite.getX(), newY)) {
            WizardSprite.translateY(moveY);
        }

    }

    private void logic() {
        float WizardWidth = WizardSprite.getWidth();
        float WizardHeight = WizardSprite.getHeight();
        viewport.getCamera().position.set(WizardSprite.getX() + WizardWidth / 2, WizardSprite.getY() + WizardHeight / 2, 0);

    }

    private boolean isCollidingWithWall(float x, float y) {
        float width = WizardSprite.getWidth()/2;
        float height = WizardSprite.getHeight()/2;

        // Checa os 4 cantos do sprite
        return isWallAt(x, y) ||
            isWallAt(x + width, y) ||
            isWallAt(x, y + height) ||
            isWallAt(x + width, y + height);
    }

    private boolean isWallAt(float x, float y) {
        int tileX = (int)(x / TILE_SIZE);
        int tileY = mapData.length - 1 - (int)(y / TILE_SIZE); // cuidado com a inversão Y

        if (tileX < 0 || tileX >= cols || tileY < 0 || tileY >= rows) {
            return true; // trata bordas como parede
        }
        return mapData[tileY][tileX] != ' ';
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

        for (int y = 0; y < mapData.length; y++) {
            for (int x = 0; x < mapData[y].length; x++) {
                Texture tex = null;
                switch (mapData[y][x]) {
                    case 'a':tex = floorATexture;break;
                    case 'b':tex = floorBTexture;break;
                    case 'c':tex = floorCTexture;break;
                    case 'd':tex = floorDTexture;break;
                    case 'h':tex = floorHTexture;break;
                    case 't':tex = floorTTexture;break;
                    case 'u':tex = floorUTexture;break;
                    case 'v':tex = floorVTexture;break;
                    case 'w':tex = floorWTexture;break;
                    case 'x':tex = floorXTexture;break;
                    case '+':tex = floorPTexture;break;

                    case ' ':
                        tex = floorTexture;
                        break;
                }
                if (tex != null) {
                    batch.draw(tex, x * TILE_SIZE, (mapData.length - 1 - y) * TILE_SIZE);
                }
            }
        }
//        batch.draw(WizardTexture, 0, 0);
        WizardSprite.draw(batch);

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
        // Destroy application's resources here.
    }
}
