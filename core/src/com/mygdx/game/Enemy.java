/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 *
 * @author Te41905
 */
public class Enemy extends Sprite {

    private int healthPoints;
    private int direction;
    private float speed;
    private boolean disabled = false;
    private int fireRate;
    private FileHandle shotFile;
    private int lastShot = 0;

    public Enemy(Texture texture, int xpos, int ypos, int health, int dir, float sp, int fRate) {
        super(texture);
        super.setPosition(xpos, ypos);
        direction = dir;
        healthPoints = health;
        speed = sp;
        shotFile = Gdx.files.internal("bullet.png");
        fireRate = fRate;
    }

    public Shot shoot() {
        Shot newShot = new Shot(new Texture(shotFile), (int) (this.getX() + this.getWidth() / 2), (int) (this.getY()), -1, 2.0f);
        newShot.setX(newShot.getX()-15);
        return newShot;
    }

    public void move() {
        if (this.getX() < 0 || this.getX() > Gdx.graphics.getWidth() - this.getWidth()) {
            this.setDirection(-this.getDirection());
        }

    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public boolean isDisabled() {
        return disabled;

    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public void decreaseHealth(int health) {
        healthPoints -= health;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    
}
