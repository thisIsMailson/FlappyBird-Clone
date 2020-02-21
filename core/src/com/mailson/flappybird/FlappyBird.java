package com.mailson.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {

    private SpriteBatch batch;
    private Texture backbround;
//    private ShapeRenderer shapeRenderer;
    private Texture topTube;
    private Texture bottomTube;
    private Texture[] birds;
    private Circle birdCircle;
    private Rectangle[] topTubeRectangle;
    private Rectangle[] bottomTubeRectangle;
    float flapState = 0;
    float birdY = 0;
    float velocity = 0;
    int score = 0;
    int scoringTube = 0;

    int gameState = 0;
    float gravity = 2.5f;
    float gap = 200;
    float maxTubeOffset;
    Random randomGenerator;
    float deviceHeight;
    float deviceWidth;
    float tubeVelocity = 3;
    int numberOfTubes = 4;
    float[] tubeX = new float[numberOfTubes];
    float[] tubeOffset = new float[numberOfTubes];
    float distanceBetweenTunbes;
    @Override
    public void create() {
        deviceHeight = Gdx.graphics.getHeight();
        deviceWidth = Gdx.graphics.getWidth();
        batch = new SpriteBatch();
        backbround = new Texture("background-night.png");
        topTube = new Texture("cano_topo.png");
        bottomTube = new Texture("cano_baixo.png");

//        shapeRenderer = new ShapeRenderer();
        birdCircle = new Circle();
        topTubeRectangle = new Rectangle[numberOfTubes];
        bottomTubeRectangle = new Rectangle[numberOfTubes];

        birds = new Texture[3];
        birds[0] = new Texture("passaro1.png");
        birds[1] = new Texture("passaro2.png");
        birds[2] = new Texture("passaro3.png");
        birdY = (deviceHeight / 2) - (birds[0].getHeight() / 2);

        maxTubeOffset = deviceHeight / 2 - gap / 2 - 100;
        randomGenerator = new Random();
        distanceBetweenTunbes = deviceWidth * 3 / 4 ;

        for (int i = 0; i < numberOfTubes; ++i) {

            tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f) * (deviceHeight - gap - 200);

            tubeX[i] = deviceWidth / 2 - topTube.getWidth() / 2 + deviceWidth + i * distanceBetweenTunbes;

            topTubeRectangle[i] = new Rectangle();
            bottomTubeRectangle[i] = new Rectangle();
        }


    }

    @Override
    public void render() {
        batch.begin();
        batch.draw(backbround, 0, 0, deviceWidth, deviceHeight);


        if (gameState != 0) {
            if (tubeX[scoringTube] < deviceWidth / 2) {
                score++;
                if (scoringTube < numberOfTubes - 1)  {
                    scoringTube ++;
                    Gdx.app.log("Score ", "" + scoringTube);
                } else
                    scoringTube = 0;
            }
            if (Gdx.input.justTouched()) {

                velocity = -25;

            }
            for (int i = 0; i < numberOfTubes; ++i) {

                if (tubeX[i] < -topTube.getWidth()) {

                    tubeX[i] += numberOfTubes * distanceBetweenTunbes;
                    tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f) * (deviceHeight - gap - 200);


                } else {
                    tubeX[i] -= tubeVelocity;

                }


                batch.draw(topTube, tubeX[i], deviceHeight / 2 + gap / 2 + tubeOffset[i]);
                batch.draw(bottomTube, tubeX[i], deviceHeight / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i]);


                topTubeRectangle[i] = new Rectangle(tubeX[i], deviceHeight / 2 + gap / 2 + tubeOffset[i], topTube.getWidth(), topTube.getHeight());
                bottomTubeRectangle[i] = new Rectangle(tubeX[i], deviceHeight / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i], bottomTube.getWidth(), bottomTube.getHeight());


            }
            if (birdY > 0 || velocity < 0) {

                velocity += gravity;
                birdY -= velocity;

            }


        } else {

            if (Gdx.input.justTouched()) {
                gameState = 1;
            }
        }
        if (flapState > 1) {
            flapState = 0;
        } else
            flapState++;


        batch.draw(birds[(int) flapState], (deviceWidth / 2) - (birds[(int) flapState].getWidth() / 2), birdY);

        batch.end();
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//        shapeRenderer.setColor(Color.RED);
        birdCircle.set(deviceWidth/2, birdY + birds[(int) flapState].getHeight() / 2, birds[(int) flapState].getWidth());
//        shapeRenderer.circle(birdCircle.x, birdCircle.y, birdCircle.radius);
        for (int i = 0; i < numberOfTubes; ++i) {
//            shapeRenderer.rect(tubeX[i], deviceHeight / 2 + gap / 2 + tubeOffset[i], topTube.getWidth(), topTube.getHeight());
//            shapeRenderer.rect(tubeX[i], deviceHeight / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i], bottomTube.getWidth(), bottomTube.getHeight());

            if (Intersector.overlaps(birdCircle, topTubeRectangle[i]) || Intersector.overlaps(birdCircle, bottomTubeRectangle[i])) {
                Gdx.app.log("Colision", "aye ma nikka");
            }
        }
//        shapeRenderer.end();


    }
}