package tp1.logic.gameobjects;

import tp1.logic.Move;
import tp1.logic.Position;
import tp1.view.GamePrinter;

public class Bomb {

    private Position pos;
    private Move direction;
    private int damage;
    private boolean active;

    public Bomb(Position pos) {
        this.pos = pos;
        this.direction = Move.DOWN; // Movimiento vertical hacia abajo
        this.damage = 1; // 1 punto de daño
        this.active = true; // La bomba está activa al crearse
    }

    public Position getPosition() {
        return pos;
    }

    public boolean isActive() {
        return active;
    }

    public void activate() {
    	active =true;
    }
    

    public void performMove() {
        // Mueve la bomba en la dirección especificada
        if (isActive()) {
            pos = new Position(pos.getX() + direction.getX(), pos.getY() + direction.getY());
        }
    }

    public int getDamage() {
        return damage;
    }

    public void deactivate() {
        active = false;
    }
    
    public void automaticMove() {
    	if(isActive()) {
    		performMove();
    		if (pos.getY() < 0) {
                active = false; // Desactiva la bomba cuando llega al borde superior
            }
    	}
    }

}
