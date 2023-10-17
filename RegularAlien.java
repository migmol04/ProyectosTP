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

    public RegularAlien(int armor, Position pos, int life, Game game, Move dir, int cyclesToMove, int speed, AlienManager alienManager) {
        this.armor = armor;
        this.pos = new Position(4,4);
        this.life = life;
        this.game = game;
        this.dir = dir;
        this.cyclesToMove = cyclesToMove;
        this.speed = speed;
        this.alienManager = alienManager;
    }

    public boolean isAlive() {
        return life > 0;
    }

    public int getLife() {
        return life;
    }

    public void die() {
        life = 0;
    }

    public boolean isOnPosition(Position position) {
    	return (this.pos.getX() == position.getX()) && (this.pos.getY() == position.getY());
    }

    public Position getPosition() {
        return pos;
    }

    public void receiveDamage(int damage) {
        life -= damage;
    }

    public boolean isOut() {
        return pos.getY() < 0 /*se podria quitar?*/ || pos.getX() < 0 || pos.getX() >= Game.DIM_X; 
    }

    public boolean isinFinalRow() {
        return pos.getY() >= Game.DIM_Y; 
    }

    private void performMovement(Move dir) {
        if (isInBorder()) {
            if (dir == Move.RIGHT) {
                dir = Move.LEFT;
            } else {
                dir = Move.RIGHT;
            }
        }
        pos = new Position(pos.getX() + dir.getX(), pos.getY() + dir.getY());
        descent();
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
        return armor; // Reemplaza con el valor correcto
    }

    public void computerAction() {
        // Implementa la lógica para la acción del alien
    }

    public void onDelete() {
        // Implementa lo que se debe hacer cuando se elimina el alien
    }

    public void automaticMove() {
        performMovement(dir);
        cyclesToMove--;
        if (cyclesToMove == 0) {
            descent();
        }
    }

    private void descent() {
        performMovement(Move.DOWN);
    }

    private boolean isInBorder() {
        return pos.getX() == 0 || pos.getX() >= Game.DIM_X - 1;
    }

    public boolean receiveAttack(UCMLaser laser) {
        if (pos.equals(laser.pos)) {
            life -= UCMLaser.DMG;
            return true;
        }
        return false; 
    }
}
