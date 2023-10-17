package tp1.logic.lists;

import tp1.logic.gameobjects.RegularAlien;
import tp1.logic.Position;
import tp1.logic.gameobjects.UCMLaser;

public class RegularAlienList {

    private RegularAlien[] objects;
    private int num;

    public RegularAlienList(int capacity) {
        objects = new RegularAlien[capacity];
        num = 0;
    }

    public void add(RegularAlien alien) {
        if (num < objects.length) {
            objects[num] = alien;
            num++;
        } 
    }

    public void remove(RegularAlien alien) {
        int index = -1;

        for (int i = 0; i < num; i++) {
            if (objects[i] == alien) {
                index = i;
            }
        }

        if (index != -1) {
            for (int i = index; i < num - 1; i++) {
                objects[i] = objects[i + 1];
            }
            num--;
        }
    }


    public int size() {
        return num;
    }

    public RegularAlien getObjectInPosition(int index) {
    	RegularAlien alien;
        if (index >= 0 && index < num) {
           alien = objects[index];
        }
        
        else
        	alien = null;
        return alien;
    }

    public void computerAction() {
        for (int i = 0; i < num; i++) {
            objects[i].computerAction();
        }
    }

    public void automaticMove() {
        for (int i = 0; i < num; i++) {
            objects[i].automaticMove();
        }
    }

    public void removeDead() {
        for (int i = 0; i < num; i++) {
            if (!objects[i].isAlive()) {
                remove(objects[i]);
            }
        }
    }

    public void checkAttack(UCMLaser laser) {
        for (int i = 0; i < num; i++) {
            objects[i].receiveAttack(laser);
        }
    }
}
