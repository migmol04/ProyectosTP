package tp1.logic.gameobjects;

public class UCMShip {

	private int posX;
	private int posY;
	private int resist;
	private boolean shockwave;
	private boolean laser;
	private String icono;
	private String death;
	public static final int ARMOR = 10;
	public static final int DMG = 10;
	
	public UCMShip() { 
		
		posX = 7;
		posY = 4;
		resist = 3;
		shockwave = false;
		laser = false;
		icono = "^__^";
		death = "!xx!";
	}
	

	public int getPosX() {
		
		return this.posX;
	}
	
	public void setPosX(int x) {
		
		this.posX = x;
	}
	
	public int getPosY() {
		
		return posY;
	}
	
	public void setPosY(int posY) {
		
		this.posY = posY;
	}
	
	public int getResist() {
		
		return resist;
	}
	
	public void setResist(int resist) {
		
		this.resist = resist;
	}
	
	public boolean getLaser() {
		
		return laser;
	}
	
	public void setLaser(boolean laser) {
		
		this.laser = laser;
	}
	
	public boolean getShockwave() {
		
		return shockwave;
	}
	
	public void setShockwave(boolean shockwave) {
		
		this.shockwave = shockwave;
	}
	
	public String getDeath() {
		
		return death;
	}
	
	public void setDeath(String death) {
		
		this.death = death;
	}
	
	public String getIcono() {
		
		return icono;
	}
	
	public void setIcono(String icono) {
		
		this.icono = icono;
	}
}