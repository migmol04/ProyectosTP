package tp1.logic.gameobjects;

import java.util.Random;

import tp1.logic.Level;

public interface GameWorld{
	public void addObject(GameObject object); 
    public void removeObject(GameObject object); 
	public int getSpeed();
	public Random getRandom();
	Level getLevel();
	public void enableLaser();
	public void activateShockwave();
	public void triggerExplosionDamage(ExplosiveAlien explosiveAlien);
	public void receivePoints(int points);
	
}