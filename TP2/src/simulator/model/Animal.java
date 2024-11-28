package simulator.model;

import org.json.JSONObject;

import simulator.misc.Utils;
import simulator.misc.Vector2D;

public abstract class Animal implements AnimalInfo, Entity {

	
	public enum Diet {
		HERBIVORE, CARNIVORE
	}

	public enum State {
		NORMAL, MATE, HUNGER, DANGER, DEAD
	}

	protected String _genetic_code;
	protected Diet _diet;
	protected State _state;
	protected Vector2D _pos;
	protected Vector2D _dest;
	protected double _energy;
	protected double _speed;
	protected double _age;
	protected double _desire;
	protected double _sight_range;                                        //necessary attributes for our animal
	protected Animal _mate_target;
	protected Animal _baby;
	protected SelectionStrategy _mate_strategy;
	protected AnimalMapView _region_mngr;
	private static final double speed_factor = 0.1;
	private static final double two = 2;
	private static final double one =1;
	private static final double negative_one = -1;
	private static final double multiplier =60.0;
	private static final double random_parameter = 0.0;
	private static final double _initial_energy = 100.0;
	private static final double _initial_desire = 0.0;
    



	
	protected Animal(String genetic_code, Diet diet, double sight_range, double init_speed,
			SelectionStrategy mate_strategy, Vector2D pos) {
		if (genetic_code == null || genetic_code.isEmpty()) {
			throw new IllegalArgumentException("Genetic code cannot be null or empty");
		} else if (sight_range <= 0) {
			throw new IllegalArgumentException("Sight range must be a positive number");
		} else if (init_speed <= 0) {
			throw new IllegalArgumentException("Initial speed must be a positive number");
		} else if (mate_strategy == null) {
			throw new IllegalArgumentException("Mate strategy cannot be null");
		}
                                                                                                          //constructor for our animal with exceptions
		this._pos = pos;
		this._genetic_code = genetic_code;
		this._diet = diet;
		this._sight_range = sight_range;
		this._speed = Utils.get_randomized_parameter(init_speed, Animal.speed_factor);
		this._mate_strategy = mate_strategy;
		this._state = State.NORMAL;
		this._energy = Animal._initial_energy;
		this._desire = Animal._initial_desire;
		this._dest = null;
		this._mate_target = null;
		this._baby = null;
		this._region_mngr = null;
	}

	protected Animal(Animal p1, Animal p2) {
		this._dest = null;                                                                                  //constructor for our baby
		this._baby = null;
		this._mate_target = null;
		this._region_mngr = null;
		this._state = State.NORMAL;
		this._desire = 0.0;
		this._genetic_code = p1._genetic_code;
		this._diet = p1._diet;
		this._energy = (p1._energy + p2._energy) / Animal.two;
		this._pos = p1.get_position()
				.plus(Vector2D.get_random_vector(Animal.negative_one, Animal.one).scale(Animal.multiplier * (Utils._rand.nextGaussian() + Animal.one)));
		this._sight_range = Utils.get_randomized_parameter((p1.get_sight_range() + p2.get_sight_range()) / Animal.two, Animal.random_parameter);
		this._speed = Utils.get_randomized_parameter((p1.get_speed() + p2.get_speed()) / Animal.two, Animal.random_parameter);

	}

	protected void move(double speed) {
		this._pos = _pos.plus(_dest.minus(_pos).direction().scale(speed));         //Correctly updates the animal's position based on its destination and speed.
	}

	protected void init(AnimalMapView reg_mngr) {
		this._region_mngr = reg_mngr;

		// Generates a random starting position if _pos is null.
		if (this._pos == null) {
			double x = Utils._rand.nextDouble(_region_mngr.get_width());                //Initializes the animal within the context of the region manager, setting its initial position and destination. It also adjusts the position to ensure the animal is within the region boundaries.
			double y = Utils._rand.nextDouble(_region_mngr.get_height());
			this._pos = new Vector2D(x, y);
		}

		// Adjust the position to ensure it is within the map boundaries.
		resetPosition();

		//Generate and assign a random destination.
		double x2 = Utils._rand.nextDouble(_region_mngr.get_width());
		double y2 = Utils._rand.nextDouble(_region_mngr.get_height());
		this._dest = new Vector2D(x2, y2);
	}

	protected Animal deliver_baby() {
		Animal _babyToDeliver = this._baby; // We assign the baby to a new variable             //This method handles the delivery, returning the baby and resetting the corresponding attribute to null.
		this._baby = null; // we put the baby null
		return _babyToDeliver;
	}

	protected boolean resetPosition() {                  //Ensures the animal is always within the map boundaries, adjusting its position if necessary and marking if the animal has reached the edge
		boolean esBorde = false;
		double x = this._pos.getX();
		double y = this._pos.getY();
		while (x >= _region_mngr.get_width()) {                                            
			esBorde = true;
			x -= _region_mngr.get_width();

		}

		while (x < 0) {
			esBorde = true;
			x += _region_mngr.get_width();

		}

		while (y >= _region_mngr.get_height()) {
			esBorde = true;
			y -= _region_mngr.get_height();

		}

		while (y < 0) {
			esBorde = true;
			y += _region_mngr.get_height();

		}

		this._pos = new Vector2D(x, y);
		return esBorde;

	}
	
	protected void update_stats(double energy, double desire, double dt) {                      //update the attributes of our wolf
		this._age += dt;
		this._energy -= energy * dt;
		this._energy = Utils.constrain_value_in_range(this._energy, 0, 100);
		this._desire += desire * dt;
		this._desire = Utils.constrain_value_in_range(this._desire, 0, 100);

	}


	public JSONObject as_JSON() {                                           //This method provides a JSON representation of the animal's current state, including its position, genetic code, diet, and status.
		JSONObject json = new JSONObject();
		json.put("pos", new double[] { _pos.getX(), _pos.getY() });
		json.put("gcode", _genetic_code);
		json.put("diet", _diet.name());
		json.put("state", _state.name());
		return json;

	}

}
