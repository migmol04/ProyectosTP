package simulator.model;

import java.util.List;

import simulator.misc.Utils;
import simulator.misc.Vector2D;

public class Sheep extends Animal {

	protected Animal _danger_source;
	public SelectionStrategy _danger_strategy;
	private static final String _sheep_genetic_code = "Sheep";
	private static final Diet _sheep_diet = Diet.HERBIVORE;
	private static final double zero = 0.0;
	private static final double hundred = 100.0;
	private static final double oldest = 8.0;
	private static final double min_distance = 8.0;
	private static final double decimal_number = 0.007;
	private static final double normal_energy = 20.0;
	private static final double normal_desire = 40.0;
	private static final double minimun_desire = 65.0;
	private static final double multiplier = 2.0;
	private static final double energy_multiplier = 1.2;
	private static final double _sheep_sight_vision = 40.0;
	private static final double _sheep_speed = 35.0;
	private static final double probability = 0.9;

	public Sheep(SelectionStrategy mate_strategy, SelectionStrategy danger_strategy, Vector2D pos) {
		super(Sheep._sheep_genetic_code, Sheep._sheep_diet, Sheep._sheep_sight_vision, Sheep._sheep_speed,
				 mate_strategy, pos);
		this._danger_strategy = danger_strategy;

	}

	protected Sheep(Sheep p1, Animal p2) {
		super(p2, p2);
		this._mate_strategy = p1._mate_strategy;
		this._danger_strategy = p1._danger_strategy;
		this._danger_source = null;
	}
	
	////GETTERS/////

	@Override
	public State get_state() {
		return this._state;
	}

	@Override
	public Vector2D get_position() {
		return this._pos;
	}

	@Override
	public String get_genetic_code() {
		return this._genetic_code;
	}

	@Override
	public Diet get_diet() {
		return this._diet;                                          
	}

	@Override
	public double get_speed() {
		return this._speed;
	}

	@Override
	public double get_sight_range() {
		return this._sight_range;
	}

	@Override
	public double get_energy() {
		return this._energy;
	}

	@Override
	public double get_age() {
		return this._age;
	}

	@Override
	public Vector2D get_destination() {
		return this._dest;
	}
	
    //////////////

	private void checkLimits() {              //adjusts the sheep's position if it moves outside the simulation boundaries.
		if (resetPosition()) {
			this._state = State.NORMAL;
		}
		;
	}

	private void checkDeath() {               //updates the sheep's state to DEAD if its energy drops to zero or if it exceeds its lifespan.
		if (this._energy <= Sheep.zero || this._age > Sheep.oldest) {
			this._state = State.DEAD;
		}

	}

	@Override
	public boolean is_pregnent() {                                  //This function checks if our sheep is pregnant or not
		boolean pregnent = false;
		if (this._baby != null) {
			pregnent = true;
		}
		return pregnent;
	}


	private void normal_move(double dt) {

		if (this.get_position().distanceTo(this._dest) < Sheep.min_distance) {
			double newX = Utils._rand.nextDouble(this._region_mngr.get_width());
			double newY = Utils._rand.nextDouble(this._region_mngr.get_height());
			this._dest = new Vector2D(newX, newY);
		}
		double normal_speed = this._speed * dt * Math.exp((_energy - Sheep.hundred) * Sheep.decimal_number);
		move(normal_speed);
		update_stats(Sheep.normal_energy, Sheep.normal_desire, dt);

	}

	private void normal_state(double dt) {
		normal_move(dt);                                                                                                //normal_move, normal_state, danger_state, and mate_state are helper methods that define the sheep's behavior in each respective state.
		if (_danger_source == null) {
			List<Animal> dangerous_animals_in_sight = this._region_mngr.get_animals_in_range(this,
					animal -> animal.get_state() != State.DEAD && animal.get_diet() == Diet.CARNIVORE);
			this._danger_source = this._danger_strategy.select(this, dangerous_animals_in_sight);

		} else if (this._danger_source != null) {
			this._state = State.DANGER;
			this._mate_target = null;

		} else if (this._danger_source == null && this._desire > Sheep.minimun_desire) {
			this._state = State.MATE;
			this._danger_source = null;
		}

	}

	private void danger_state(double dt) {
		if (this._danger_source != null && this._danger_source.get_state() == State.DEAD) {
			this._danger_source = null;
		}

		if (this._danger_source == null) {
			normal_move(dt);
		} else {
			this._dest = _pos.plus(_pos.minus(_danger_source.get_position()).direction());
			double danger_speed = Sheep.multiplier * _speed * dt * Math.exp((_energy - Sheep.hundred) * Sheep.decimal_number);
			move(danger_speed);
			update_stats(Sheep.normal_energy * Sheep.energy_multiplier, Sheep.normal_desire, dt);
		}

		if (this._danger_source == null
				|| this.get_position().distanceTo(this._danger_source.get_position()) > this.get_sight_range()) {
			List<Animal> dangerous_animals_in_sight = this._region_mngr.get_animals_in_range(this,
					animal -> animal.get_state() != State.DEAD && animal.get_diet() == Diet.CARNIVORE);
			this._danger_source = this._danger_strategy.select(this, dangerous_animals_in_sight);
			if (this._danger_source == null) {
				if (this._desire < Sheep.minimun_desire) {
					this._state = State.NORMAL;
					this._danger_source = null;
					this._mate_target = null;
				} else {
					this._state = State.MATE;
					this._danger_source = null;
				}

			}

		}

	}

	private void mate_state(double dt) {

		if ((this._mate_target != null) && (this._mate_target.get_state() == State.DEAD) || (this._mate_target != null)

				&& (this.get_position().distanceTo(this._mate_target.get_position())) > this.get_sight_range()) {

			this._mate_target = null;

		}

		if (this._mate_target == null) {

			List<Animal> animals_in_sight = this._region_mngr.get_animals_in_range(this,

					animal -> animal.get_state() != State.DEAD && animal.get_diet() == Diet.HERBIVORE);

			this._mate_target = this._mate_strategy.select(this, animals_in_sight);

			if (this._mate_target == null)

				normal_move(dt);

		} else {

			this._dest = _mate_target.get_position();

			double mate_speed = Sheep.multiplier * _speed * dt * Math.exp((_energy - Sheep.hundred) * Sheep.decimal_number);

			move(mate_speed);

			update_stats(Sheep.normal_energy * Sheep.energy_multiplier, Sheep.normal_desire, dt);

			if (this.get_position().distanceTo(this._mate_target.get_position()) < Sheep.min_distance) {

				this._desire = Sheep.zero;

				this._mate_target._desire = Sheep.zero;

				if (!this.is_pregnent()) {

					double prob = Utils._rand.nextDouble();

					if (prob < Sheep.probability) {

						this._baby = new Sheep(this, this._mate_target);

					}

				}

				this._mate_target = null;

			}

		}

		if (this._danger_source == null) {

			List<Animal> dangerous_animals_in_sight = this._region_mngr.get_animals_in_range(this,

					animal -> animal.get_state() != State.DEAD && animal.get_diet() == Diet.CARNIVORE);

			this._danger_source = this._danger_strategy.select(this, dangerous_animals_in_sight);

		}

		if (this._danger_source != null) {

			this._state = State.DANGER;

			this._mate_target = null;

		} else if (this._danger_source == null && this._desire < Sheep.minimun_desire) {

			this._state = State.NORMAL;

			this._danger_source = null;

			this._mate_target = null;

		}

	}

	@Override
	public void update(double dt) {                            //The sheep's interactions with the environment include moving within the ecosystem, consuming food (if available), avoiding predators, and seeking mates
		switch (this._state) {                                 //It uses the _region_mngr (from the Animal class) to interact with its surroundings, such as getting food and identifying other animals nearby.
		case NORMAL:
			normal_state(dt);       //In the NORMAL state, the sheep moves randomly and checks for danger or potential mates in its vicinity. If it perceives a threat, it switches to the DANGER state; if it feels a strong desire to mate and no dangers are present, it switches to the MATE state
			break;
		case DANGER:
			danger_state(dt);       //In the DANGER state, the sheep tries to move away from the threat. If the threat is no longer present or perceivable, it reverts to the NORMAL or MATE state based on its mating desire
			break;
		case MATE:
			mate_state(dt);         //In the MATE state, the sheep seeks out a mate. If successful in mating, it may generate offspring. If a threat appears, it switches to the DANGER state; otherwise, it may return to NORMAL state if the desire to mate decreases
			break;
		case DEAD:
			break;
		default:
			break;
		}
		checkDeath();              //Here we check if our animal is dead
		checkLimits();             //Here we check if its out of boundaries
		
		this._energy += this._region_mngr.get_food(this, dt);   
		this._energy = Utils.constrain_value_in_range(this._energy, Sheep.zero, Sheep.hundred);
	}

}
