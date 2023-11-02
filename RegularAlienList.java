package tp1.logic.lists;

import tp1.logic.Game;
import tp1.logic.gameobjects.RegularAlien;

import tp1.logic.gameobjects.UCMLaser;

public class RegularAlienList {

    private RegularAlien[] aliens;
    private int num;

    public RegularAlienList(int capacity) {
        aliens = new RegularAlien[capacity];
        this.num = capacity;
    }

    public void add(RegularAlien alien, int pos) {
        if (pos < aliens.length) {
            aliens[pos] = alien;
        } 
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
    
    public void descend() {
    	if(readyToDescent()) {
    		for(int i = 0; i < num; i++) {
        		aliens[i].descent();
        	}
    		
    	}
    }
    
    
    public boolean readyToDescent() {
    	boolean ready = false;
    	for(int i = 0; i < num; i++) {
    		if(aliens[i].isInBorder()) {
    			ready = true;
    		}
    	}
    	return ready;
    }
    


    public void remove(RegularAlien alien) {
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

    public RegularAlien getObjectInPosition(int index) {
    	RegularAlien alien;
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
