package tp1.logic.gameobjects;

import tp1.logic.AlienManager;
import tp1.logic.Game;
import tp1.logic.Move;
import tp1.logic.Position;
import tp1.view.Messages;

/**
 * Class representing a regular alien
 */
public class RegularAlien {

    private int armor;
    private Position pos;
    private int life;
    private Game game;
    private Move dir;
    private int cyclesToMove;
    private int speed;
    private AlienManager alienManager;

    public RegularAlien(Game game, AlienManager alienManager) {
        this.armor = 3;
        this.pos = new Position(4,4);
        this.life = 10;
        this.game = game;
        this.dir = Move.LEFT;
        this.cyclesToMove = 4;
        this.speed = 1;
        this.alienManager = alienManager;
    }

    public boolean isAlive() {
        boolean alive = true;
        
        if (this.life == 0) {
           alive = false; 
        }
        
        return alive;
    }
    

    public int getLife() {
        return this.life;
    }

    public void die() {
        this.life = 0;
    }

    public boolean isOnPosition(Position position) {
    	return (this.pos.getX() == position.getX()) && (this.pos.getY() == position.getY());
    }

    public Position getPosition() {
        return this.pos;
    }

    public void receiveDamage(int damage) {
        life -= damage;
    }

    public boolean isOut() {
        return pos.getY() < 0 || pos.getX() < 0 || pos.getX() >= Game.DIM_X; 
    }

    public boolean isinFinalRow() {
        return pos.getY() >= Game.DIM_Y; 
    }
    private void performMovement(Move dir) {
        Move newDir = dir; 
        
        if (isInBorder()) {
            if (dir == Move.RIGHT) {
                newDir = Move.LEFT;
            } else {
                newDir = Move.RIGHT;
            }
        }
        
        pos = new Position(pos.getX() + newDir.getX(), pos.getY() + newDir.getY());
    }


    public String getSymbol() {
        return Messages.REGULAR_ALIEN_SYMBOL; 
    }

    public String toString() {
        return "";
    }

    public String getInfo() {
        return Messages.ALIEN_DESCRIPTION;
    }

    public String getDescription() {
        return Messages.REGULAR_ALIEN_DESCRIPTION ; 
    }

    public int getDamage() {
        return armor; //
    }

    public void computerAction() {
     
    }

    public void onDelete() {
       
    }

    public void automaticMove() {
    	  performMovement(dir);
          cyclesToMove--;

          if (cyclesToMove == 0) {
              if (dir == Move.RIGHT) {
                  dir = Move.LEFT;
              } else {
                  dir = Move.RIGHT;
              }
              cyclesToMove = speed; 
          }

          if (pos.getY() >= Game.DIM_Y) {
              onDelete(); 
          }
    }

    private void descent() {
    	performMovement(Move.DOWN);
        
       
    }

    private boolean isInBorder() {
        return pos.getX() == 0 || pos.getX() >= Game.DIM_X - 1;
    }

    public boolean receiveAttack(UCMLaser laser) {
    	boolean disp = false;
        if (pos.equals(laser.pos)) {
            life -= UCMLaser.DMG;
            disp = true;
        }
        return disp; 
    }
}
