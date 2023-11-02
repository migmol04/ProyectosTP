package tp1.logic.lists;

import tp1.logic.gameobjects.DestroyerAlien;
import tp1.logic.gameobjects.RegularAlien;
import tp1.logic.gameobjects.UCMLaser;

public class DestroyerAlienList {

	private DestroyerAlien[] aliens;
    private int num;

    public DestroyerAlienList(int capacity) {
        aliens = new DestroyerAlien[capacity];
        this.num = capacity;
    }

    public void add(DestroyerAlien alien, int pos) {
        if (pos < aliens.length) {
            aliens[pos] = alien;
        } 
    }
    
    public boolean readyToDescent() {
    	boolean ready = false;
    	for(int i = 0; i < num; i++) {
    		if(aliens[i].readyToDescent()) {
    			ready = true;
    		}
    	}
    	return ready;
    }
    
    
    public boolean isInBorder() {
    	boolean esBord = false;
       for(int i = 0; i < num && !esBord; i++) {
    	   if(aliens[i].isInBorder()) {
    		   esBord = true;
    	   }
       }
       return esBord;
    }
    

    public void remove(DestroyerAlien alien) {
        int index = -1;

        for (int i = 0; i < num; i++) {
            if (aliens[i] == alien) {
                index = i;
            }
        }

        if (index != -1) {
            for (int i = index; i < num - 1; i++) {
                aliens[i] = aliens[i + 1];
            }
            num--;
        }
    }
    
    public boolean isInFinalRow() {
    	boolean row = false;
    	for(int i = 0; i < num; i++) {
    		if(aliens[i].isinFinalRow()) {
    			row = true;
    		}
    	}
    	return row;
    }


    public int size() {
        return num;
    }

    public DestroyerAlien getObjectInPosition(int index) {
    	DestroyerAlien alien;
        if (index >= 0 && index < num) {
           alien = aliens[index];
        }
        
        else
        	alien = null;
        return alien;
    }
    

    public void computerActions() {
        for (int i = 0; i < num; i++) {
            aliens[i].computerAction();
        }
    }

    public void automaticMoves() {
        for (int i = 0; i < num; i++) {
           aliens[i].automaticMove();
        }
    }

    public void removeDead() {
        for (int i = 0; i < num; i++) {
            if (aliens[i].isAlive()) {
                remove(aliens[i]);
            }
        }
    }

    public void checkAttack(UCMLaser laser) {
        for (int i = 0; i < num; i++) {
            aliens[i].receiveAttack(laser);
        }
    }
}