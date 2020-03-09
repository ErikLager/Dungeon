/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 *
 * @author Te41905
 */
public class Player extends Sprite {

    private int healthPoints;
    private float speed;
    private int fireRate;
    private FileHandle bulletFile;
    private int lastShot = 0;
    private boolean disabled = false;

    public Player(Texture texture, int xpos, int ypos, int health, float sp, int fRate) {
        super(texture);
        super.setPosition(xpos, ypos);
        healthPoints = health;
        speed = sp;
        bulletFile = Gdx.files.internal("bullet.png");
        fireRate = fRate;
    }

    public Shot fire() {
        Shot newShot = new Shot(new Texture(this.bulletFile), (int) (this.getX() + this.getWidth() / 2), (int) (this.getY()), -1, 2.0f);
        return newShot;
    }
    
    public boolean isDisabled(){
        return disabled;
    }
    
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public void decreaseHealth(int health) {
        healthPoints -= health;
    }

    public void move() {
        if (Gdx.input.isKeyPressed(Input.Keys.A) && getX() > 0) {
            translateX(-speed);
            if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) && getX() > -1) {
                translateX(-speed * 2);
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) && getX() < Gdx.graphics.getWidth() - getWidth()) {
            translateX(speed);
            if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) && getX() < Gdx.graphics.getWidth()) {
                translateX(speed * 2);
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W) && getY() < Gdx.graphics.getHeight() - getHeight()) {
            translateY(speed);
            if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) && getY() < Gdx.graphics.getWidth()) {
                translateY(speed * 2);
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) && getY() > 0) {
            translateY(-speed);
            if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) && getX() > -1) {
                translateY(-speed * 2);
            }
        }

    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int getFireRate() {
        return fireRate;
    }

    public void setFireRate(int fireRate) {
        this.fireRate = fireRate;
    }

    public FileHandle getBulletFile() {
        return bulletFile;
    }

    public void setBulletFile(FileHandle bulletFile) {
        this.bulletFile = bulletFile;
    }

    public int getLastShot() {
        return lastShot;
    }

    public void setLastShot(int lastShot) {
        this.lastShot = lastShot;
    }

    
}
