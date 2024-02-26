package com.example.hw1.Logic;

import com.example.hw1.Models.Apple;
import com.example.hw1.Models.Candy;
import com.example.hw1.Models.Tooth;
import com.example.hw1.Models.GameObject;

import java.util.Arrays;
import java.util.Random;

public class GameManager {

    private int life;
    private int crashCount = 0;
    private boolean hitCandyFlag;
    private boolean hitAppleFlag;
    private int tooth_col;
    public int candy_count = 0;
    public int[] lastRnd;
    public int rndCount = 0;
    private GameObject[][] locationMatrix;
    private Tooth myTooth;

    public int currentGameScore = 0;

    public void setCrashCount(int wrongAns) {
        this.crashCount = wrongAns;
    }

    public int getCrashCount() {
        return crashCount;
    }

    public GameManager() {
    }

    public GameManager setLife(int life) {
        this.life = life;
        return this;
    }

    public GameManager setLocationMatrix(int rows, int cols) {
        hitCandyFlag = false;
        lastRnd = new int[cols];
        Arrays.fill(lastRnd, 0);
        this.locationMatrix = new GameObject[rows][cols];
        for (int i = 0; i < this.locationMatrix.length; i++) {
            for (int j = 0; j < this.locationMatrix[0].length; j++) {
                this.locationMatrix[i][j] = new GameObject("Empty");
            }
        }
        this.tooth_col = (cols - 1) / 2;
        myTooth = new Tooth();
        this.locationMatrix[rows - 1][tooth_col] = myTooth;
        return this;
    }

    public int getLife() {
        return life;
    }

    public void hit(GameObject object) {//should be changed in part 2 now the game is infinite...
        if (object.getType().equals("Candy")) {
            if (getCrashCount() < 3)
                setCrashCount(getCrashCount() + 1);
            hitCandyFlag = true;
        } else {
            this.currentGameScore += ((Apple) object).SCORE;
            hitAppleFlag = true;
        }
    }

    public void setHitCandyFlag(boolean hitCandyFlag) {
        this.hitCandyFlag = hitCandyFlag;
    }

    public void setHitAppleFlag(boolean hitAppleFlag) {
        this.hitAppleFlag = hitAppleFlag;
    }

    public boolean isHitCandyFlag() {
        return hitCandyFlag;
    }

    public boolean isHitAppleFlag() {
        return hitAppleFlag;
    }

    public void addRandomObj() {
        int type = (Math.random() <= 0.5) ? 1 : 2; // randomizing the numbers with same prob.
        int num = randomIndex(this.locationMatrix[0].length);
        if (type == 2)
            this.locationMatrix[0][num] = new Candy();
        else
            this.locationMatrix[0][num] = new Apple();
        this.candy_count++;
    }

    public int randomIndex(int max) {
        int newIndex;
        Random rnd = new Random();
        newIndex = rnd.nextInt(max);
        lastRnd[rndCount] = newIndex;
        rndCount++;
        if (rndCount == lastRnd.length) {
            while (isAllElementsAreEqualPlusOne(lastRnd) || isAllElementsAreEqualMinusOne(lastRnd)) {
                lastRnd[rndCount - 1] = rnd.nextInt(max);
                newIndex = lastRnd[rndCount - 1];
            }
            for (int i = 0; i < lastRnd.length - 1; i++) {
                lastRnd[i] = lastRnd[i + 1];
            }
        }
        if (rndCount == lastRnd.length)
            rndCount--;

        return newIndex;
    }

    public boolean isAllElementsAreEqualPlusOne(int[] arr) {

        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            if (arr[i] != arr[i + 1] + 1) {
                return false;
            }
        }
        return true;
    }

    public boolean isAllElementsAreEqualMinusOne(int[] arr) {

        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            if (arr[i] != arr[i + 1] - 1) {
                return false;
            }
        }
        return true;
    }

    public void moveTooth(String direction) {
        String type;
        if (direction.equals("Left")) {
            if (tooth_col > 0) {
                this.locationMatrix[this.locationMatrix.length - 1][tooth_col] = new GameObject("Empty");
                tooth_col--;
                type = this.locationMatrix[this.locationMatrix.length - 1][tooth_col].getType();
                if (type.equals("Candy") || type.equals("Apple"))
                    hit(this.locationMatrix[this.locationMatrix.length - 1][tooth_col]);
                this.locationMatrix[this.locationMatrix.length - 1][tooth_col] = new Tooth();
            }
        }
        if (direction.equals("Right")) {
            if (tooth_col < this.locationMatrix[0].length - 1) {
                this.locationMatrix[this.locationMatrix.length - 1][tooth_col] = new GameObject("Empty");
                tooth_col++;
                type = this.locationMatrix[this.locationMatrix.length - 1][tooth_col].getType();
                if (type.equals("Candy") || type.equals("Apple"))
                    hit(this.locationMatrix[this.locationMatrix.length - 1][tooth_col]);
                this.locationMatrix[this.locationMatrix.length - 1][tooth_col] = new Tooth();
            }
        }
    }

    public boolean checkCollision(int i, int j, GameObject obj) {
        if (this.locationMatrix[i + 1][j].getType().equals("Tooth")) {
            hit(obj);
            return true;
        }

        return false;
    }

    public GameObject[][] getLocationMatrix() {
        return locationMatrix;
    }

    public void moveObjDown() {
        String type;
        for (int i = 0; i < this.locationMatrix[0].length; i++) {
            if (!this.locationMatrix[this.locationMatrix.length - 1][i].getType().equals("Tooth"))
                this.locationMatrix[this.locationMatrix.length - 1][i] = new GameObject("Empty");
        }
        for (int i = this.locationMatrix.length - 2; i >= 0; i--) {
            for (int j = 0; j < this.locationMatrix[0].length; j++) {
                type = locationMatrix[i][j].getType();
                if (type.equals("Candy") || type.equals("Apple")) {
                    if (!checkCollision(i, j, locationMatrix[i][j])) {
                        this.locationMatrix[i + 1][j] = this.locationMatrix[i][j];
                        this.locationMatrix[i][j] = new GameObject("Empty");
                    } else {
                        this.locationMatrix[i][j] = new GameObject("Empty");
                    }
                }
            }
        }
        addRandomObj();
    }
//    public boolean gameLost() { // In Part Two It Will help to Stop The game Main Activity.
//        return this.getCrashCount() == this.getLife();
//    }

}

