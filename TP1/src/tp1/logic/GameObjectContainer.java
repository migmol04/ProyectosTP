package tp1.logic;

import java.util.ArrayList;
import java.util.List;

import tp1.logic.gameobjects.ExplosiveAlien;
import tp1.logic.gameobjects.GameItem;
import tp1.logic.gameobjects.GameObject;

public class GameObjectContainer {
 
	private List<GameObject> objects;

	public GameObjectContainer() {
		objects = new ArrayList<>();
	}

	public void add(GameObject object) {
		objects.add(object);
	}

	public void remove(GameObject object) {
		objects.remove(object);
	}
	
	
	public void automaticMoves() {
		for (int i=0;i<objects.size();i++) {
				GameObject object = objects.get(i);
				object.automaticMove();
		}

	}

	public void computerActions() {
		for (int i = objects.size() - 1; i >= 0; i--)  {
			GameObject object = objects.get(i);
			object.computerAction();
	     }	
		}
	
	public boolean onBorder() {
		boolean enBorde = false;
		  for (GameObject object : objects) {
			  if(object.onBorder()) {
				enBorde = true;
			  }
	  }
		  return enBorde;
	}
	
	public void performAttack(GameItem Aobject) {
		for(int i = objects.size() - 1; i >= 0; i--) {
			GameObject Robject = objects.get(i);
			if(Aobject != Robject) {
				Aobject.performAttack(Robject);
				
			}
		
		}
	}
	
	public void checkAttack() {
		for(int i = objects.size() - 1; i >= 0; i--) {
			GameItem item = objects.get(i);
			performAttack(item);
		}
		
	}
		
	public void shockwaveAttack() {
		for(int i = 0; i < objects.size(); i++) {
			GameObject object = objects.get(i);
			object.getShockWaveDamage();
		}
	}
	
	

	//TODO fill with your code
	public String toString(Position position) {
	    for (GameObject object : objects) {
	        if (object.getPosition().equals(position)) {
	            return object.status();
	        }
	    }
	    return "";
	}

	public void explosion(ExplosiveAlien explosiveAlien, Position adjacentPosition) {
		for(int i = objects.size() - 1; i >= 0; i--) {
					GameObject object = objects.get(i);
					if(adjacentPosition.equals(object.getPosition()))
						object.recieveDamage(explosiveAlien.getDamage());
			}
	}
}
