package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sun.java.swing.plaf.windows.resources.windows;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;

public class MyGdxGame extends ApplicationAdapter {

    SpriteBatch batch;
    Texture background;
    SpriteBatch Batch;
    Player cyrus;
    Enemy enemy;
    Enemy boss;
    int enemyDirection = 1;
    int bossDirection = 1;
    float Espeed = 5f;
    List<Shot> shotlist = new ArrayList<Shot>();
    List<Enemy> enemyList = new ArrayList<Enemy>();
    List<Shot> enemyShot = new ArrayList<Shot>();
    float shotSpeed = 2f;
    int playerFireRate = 20;
    int playerLastShot = 0;
    int shotsfired = 0;
    String fires;
    BitmapFont fired;

    @Override //Run one time (start)
    public void create() {
        batch = new SpriteBatch();
        cyrus = new Player(new Texture("cyrusS.png"), 0, 0, 10, 1f, 100); //xPos, yPos, Health, speed, firerate
        cyrus.setScale(1.5f);
        FileHandle enemyFile = Gdx.files.internal("enemy.png");
        FileHandle bossFile = Gdx.files.internal("boss.png");
        enemy = new Enemy(new Texture(enemyFile), 0, 200, 10, 0, 2, 0);// xPos, yPos, HP, direction, speed, fireRate
        boss = new Enemy(new Texture(bossFile), Gdx.graphics.getWidth() - 100, 400, 50, 0, 1, 0); // xPos, yPos, HP, direction, speed, fireRate
        boss.setScale(3f);
        enemy.setScale(1.5f);
        enemyList.add(enemy);
        enemyList.add(boss);
        fires = "bullets: " + shotsfired;
        fired = new BitmapFont();

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        if (!enemy.isDisabled()) {
            enemy.draw(batch);
        }
        if (!boss.isDisabled()) {
            boss.draw(batch);
        }
        enemy.translateX(Espeed * enemyDirection);
        boss.translateX((float) (Espeed * 0.5 * -bossDirection));
        if (enemy.getX() > Gdx.graphics.getWidth()) {
            enemyDirection *= -1;
        } else if (enemy.getX() <= 0) {
            enemyDirection *= -1;
        }
        if (boss.getX() > Gdx.graphics.getWidth()) {
            bossDirection *= -1;
        } else if (boss.getX() <= 0) {
            bossDirection *= -1;
        }
        playerLastShot++;
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && playerLastShot > playerFireRate) {
            shotlist.add(cyrus.fire());
            playerLastShot = 0;
            shotsfired++;
            fires = "bullets: " + shotsfired;
        }
        if (enemy.getX() == cyrus.getX() || enemy.getX() == cyrus.getX() - 1 || enemy.getX() == cyrus.getX() + 1 || enemy.getX() == cyrus.getX() - 2 || enemy.getX() == cyrus.getX() + 2) {
            enemyShot.add(enemy.shoot());
            System.out.println("Helath: "+cyrus.getHealthPoints());
        }if (boss.getX() == cyrus.getX() || boss.getX() == cyrus.getX() - 1 || boss.getX() == cyrus.getX() + 1 || boss.getX() == cyrus.getX() - 2 || boss.getX() == cyrus.getX() + 2) {
            enemyShot.add(boss.shoot());
            System.out.println("Helath: "+cyrus.getHealthPoints());
        }
        
        for (Shot ene_bullet : enemyShot) {
            ene_bullet.translateY(shotSpeed * -4);
            if (ene_bullet.isDisabled() == false && ene_bullet.getBoundingRectangle().overlaps(cyrus.getBoundingRectangle())) {
                if (cyrus.isDisabled() == false) {
                    ene_bullet.setDisabled(true);
                    cyrus.decreaseHealth(1);
                }
                if (cyrus.getHealthPoints() < 1) {
                    cyrus.setDisabled(true);
                    ene_bullet.setDisabled(true);
                }
            }
        }
        for (Shot cyrus_bullet : shotlist) {
            cyrus_bullet.translateY(shotSpeed * 2);
            for (Enemy en : enemyList) {
                if (cyrus_bullet.isDisabled() == false && cyrus_bullet.getBoundingRectangle().overlaps(en.getBoundingRectangle())) {
                    if (en.isDisabled() == false) {
                        cyrus_bullet.setDisabled(true);
                        en.decreaseHealth(1);
                    }
                    if (en.getHealthPoints() < 1) {
                        en.setDisabled(true);
                        cyrus_bullet.setDisabled(true);
                    }
                    if (boss.getHealthPoints() < 1) {
                        boss.setDisabled(true);

                    }
                }
            }
        }
        if (enemy.isDisabled()) {
            enemyList.remove(enemy);
        }
        if (boss.isDisabled()) {
            enemyList.remove(boss);
        }

        for (Shot eneshot : enemyShot) {
            if (!eneshot.isDisabled()) {
                eneshot.draw(batch);
            }
        }
        for (Shot cyrus_bullet : shotlist) {
            if (!cyrus_bullet.isDisabled()) {
                cyrus_bullet.draw(batch);
            }
        }
        if (!cyrus.isDisabled() == true) {
            cyrus.draw(batch);
            cyrus.move();
        }
        if (cyrus.isDisabled()== true){
            Gdx.app.exit();
        }
        fired.setColor(0f, 0f, 0f, 1.0f);
        fired.draw(batch, fires, 25, 100);
        batch.end();

    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
