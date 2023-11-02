package tp1.logic.gameobjects;

import tp1.logic.Game;
import tp1.logic.Position;
import tp1.view.Messages;

public class Ufo {
    private boolean enabled;
    private Game game;
    private Position pos;
    private int resistance;
    private boolean destroyed;

    public Ufo(Game game) {
        this.game = game;
        this.pos = new Position(8, 0); // El OVNI aparece en la casilla (8, 0)
        this.resistance = 1; // Resistencia del OVNI
        this.destroyed = false;
        this.enabled = false; // Llama al método para habilitar el OVNI

    }

    public void computerAction() {
        if (!enabled) {
            enabled = canGenerateRandomUfo(); // Intenta habilitar el OVNI
            if (enabled) {
                pos = new Position(9, 0); // Coloca el OVNI en la posición inicial
            }
        }

        if (enabled) {
            if (pos.getX() > 0 && !isDestroyed()) {
                pos = new Position(pos.getX() - 1, pos.getY());
            } else {
                // El OVNI ha alcanzado el borde y desaparece
                onDelete();
            }
        }
    }
    public void onDelete() {
        // Realiza acciones cuando el OVNI se elimina del juego.
        enabled = false;
        pos = new Position( - 1, -1);
    }

    public boolean receiveAttack(UCMLaser laser) {
    	boolean ataque = false;
        // Verifica si el láser está en la misma posición que el OVNI
        if (pos.equals(laser.getPosition())) {
            resistance -= laser.DMG;
            if (resistance <= 0) {
                // El OVNI ha sido destruido
                destroy();
            }
            ataque = true;
        }
        return ataque; // El ataque no tuvo éxito
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    private void destroy() {
        enabled = false;
        destroyed = true;
    }
    
    public String getSymbol() {
		return Messages.UFO_SYMBOL;
	}
    
 public boolean isOnPosition(Position p) {
		
		return (this.pos.getX() == p.getX()) && (this.pos.getY() == p.getY()) && enabled;
		
	}

    private boolean canGenerateRandomUfo() {
        return game.getRandom().nextDouble() < game.getLevel().getUfoFrequency();
    }
}

