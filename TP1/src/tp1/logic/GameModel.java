package tp1.logic;

import tp1.control.InitialConfiguration;
import tp1.control.commands.InitializationException;
import tp1.control.commands.LaserInFlightException;
import tp1.control.commands.NoShockWaveException;
import tp1.control.commands.NotAllowedMoveException;
import tp1.control.commands.NotEnoughPointsException;
import tp1.control.commands.OffWorldException;

public interface GameModel {

	public void movePlayer(Move move) throws OffWorldException, NotAllowedMoveException;
	public void exit();
	public void shockwave()throws NoShockWaveException;
	public void shootLaser() throws LaserInFlightException;
	public void shootSuperLaser()throws NotEnoughPointsException, LaserInFlightException;

	public void reset(InitialConfiguration selectedConfiguration) throws InitializationException;
	
	public boolean isFinished();
	public void update();
	public  String infoToString();
	// ...
}