package simulator.model;

import java.util.List;

import simulator.misc.Utils;
import simulator.misc.Vector2D;
import simulator.model.Animal.Diet;
import simulator.model.Animal.State;

public class Wolf extends Animal {

	private Animal _hunt_target;
	private SelectionStrategy _hunting_strategy;

	private static final String _wolf_genetic_code = "Wolf";
	private static final double _wolf_sight_vision = 50.0;
	private static final double _wolf_speed = 60.0;
	private static final double oldest = 14.0;
	private static final double zero = 0.0;
	private static final double hundred = 100.0;
	private static final double min_distance = 8.0;
	private static final double decimal_number = 0.007;
	private static final double normal_energy = 18.0;
	private static final double normal_desire = 30.0;
	private static final double minmun_desire = 65.0;
	private static final double minimun_energy = 50.0;
	private static final double multiplier = 3.0;
	private static final double energy_multiplier = 1.2;
	private static final double mate_multiplier = 2.0;
	private static final double probability = 0.9;


	private static final Diet _wolf_diet = Diet.CARNIVORE;

	public Wolf(SelectionStrategy mate_strategy, SelectionStrategy hunting_strategy, Vector2D pos) {
		super(Wolf._wolf_genetic_code, Wolf._wolf_diet, Wolf._wolf_sight_vision, Wolf._wolf_speed,  mate_strategy,
				pos);
		this._hunting_strategy = hunting_strategy;
	}

	protected Wolf(Wolf p1, Animal p2) {
		super(p1, p2);
		this._mate_strategy = p1._mate_strategy;
		this._hunting_strategy = p1._hunting_strategy;
		this._hunt_target = null;
	}
 //////////GETTERS//////////////////
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
	
	 //////////////////////////////////

	private void checkLimits() {                      //adjusts the Wolf's position if it moves outside the simulation boundaries.
		if (resetPosition()) {
			this._state = State.NORMAL;
		}
	}

	private void checkDeath() {                        //updates the wolf's state to DEAD if its energy drops to zero or if it exceeds its lifespan.
		if (this._energy <= Wolf.zero || this._age > Wolf.oldest) {
			this._state = State.DEAD;
		}

	}

	@Override
	public boolean is_pregnent() {                  //This function checks if our wolf is pregnant or not
		boolean pregnent = false;
		if (this._baby != null) {
			pregnent = true;
		}
		return pregnent;
	}

	private void normal_move(double dt) {

		if (this.get_position().distanceTo(this._dest) < Wolf.min_distance) {
			double newX = Utils._rand.nextDouble(this._region_mngr.get_width());
			double newY = Utils._rand.nextDouble(this._region_mngr.get_height());                                          //normal_move, update_stats, normal_state, hunger_state, and mate_state are helper methods detailing the wolf's actions and state transitions
			this._dest = new Vector2D(newX, newY);
		}
		double normal_speed = this._speed * dt * Math.exp((_energy - Wolf.hundred) * Wolf.decimal_number);
		move(normal_speed);
		update_stats(Wolf.normal_energy, Wolf.normal_desire, dt);

	}

	
	private void normal_state(double dt) {
		normal_move(dt);
		if (this._energy < Wolf.minimun_energy) {
			this._state = State.HUNGER;
			this._mate_target = null;
		} else if (this._energy >= Wolf.minimun_energy && this._desire >= Wolf.minmun_desire) {
			this._state = State.MATE;
			this._hunt_target = null;
		}

	}

	private void hunger_state(double dt) {
		if((this._hunt_target == null) || (this._hunt_target != null && this._hunt_target.get_state() == State.DEAD) || (this._hunt_target != null &&this.get_position().distanceTo(this._hunt_target.get_position()) > this.get_sight_range())) {
			List<Animal> prey_animals_in_sight = this._region_mngr.get_animals_in_range(this,
					animal -> animal.get_state() != State.DEAD && animal.get_diet() == Diet.HERBIVORE);
			this._hunt_target = this._hunting_strategy.select(this,prey_animals_in_sight);

		
		}
		
		
		if(this._hunt_target == null) {
			normal_move(dt);
		}
		else {
			this._dest = _hunt_target.get_position();
			double hunting_speed = Wolf.multiplier*_speed*dt*Math.exp((_energy-Wolf.hundred)*Wolf.decimal_number);
			move(hunting_speed);
			update_stats(Wolf.normal_energy*Wolf.energy_multiplier, Wolf.normal_desire, dt);
			if(this.get_position().distanceTo(this._hunt_target.get_position())<Wolf.min_distance) {
				this._hunt_target._state = State.DEAD;
				this._hunt_target = null;
				this._energy += Wolf.minimun_energy;
				this._energy = Utils.constrain_value_in_range(this._energy, Wolf.zero, Wolf.hundred);
			}
			
		}
		
		if(this._energy > Wolf.minimun_energy) {
			if(this._desire < Wolf.minmun_desire) {
				this._state = State.NORMAL;
				this._hunt_target = null;
				this._mate_target = null;
			}
			else {
				this._state = State.MATE;
				this._hunt_target = null;
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

					animal -> animal.get_state() != State.DEAD && animal.get_diet() == Diet.CARNIVORE);

			this._mate_target = this._mate_strategy.select(this, animals_in_sight);



			if (this._mate_target == null)

				normal_move(dt);

		} else {

			this._dest = _mate_target.get_position();

			double mate_speed = Wolf.mate_multiplier * _speed * dt * Math.exp((_energy - Wolf.hundred) * Wolf.decimal_number);

			move(mate_speed);
            update_stats(Wolf.normal_energy*Wolf.energy_multiplier, Wolf.normal_desire, dt);


			if (this.get_position().distanceTo(this._mate_target.get_position()) < Wolf.min_distance) {

				this._desire = Wolf.zero;

				this._mate_target._desire = Wolf.zero;

				if (!this.is_pregnent()) {

					double prob = Utils._rand.nextDouble();

					if (prob < Wolf.probability) {

						this._baby = new Wolf(this, this._mate_target);

					}

				}

				this._mate_target = null;

			}

		}
		if(this._energy < Wolf.minimun_energy) {
			this._state = State.HUNGER;
			this._mate_target = null;
		}
		else if(this._energy >= Wolf.minimun_energy && this._desire < Wolf.minmun_desire) {
			this._state = State.NORMAL;
			this._mate_target = null;
			this._hunt_target = null;
		}

	}

	@Override
	public void update(double dt) {                 //The update method adjusts the wolf's behavior according to its current state (NORMAL, HUNGER, MATE, DEAD). It involves energy consumption, age increment, and changes in mating desire over time, alongside environmental interactions like hunting and mating
		switch (this._state) {
		case NORMAL:
			normal_state(dt);                       //n the NORMAL state, the wolf moves randomly within its environment and decides whether to enter the HUNGER or MATE state based on its energy levels and mating desire
			break;
		case HUNGER:
			hunger_state(dt);                       //In the HUNGER state, the wolf seeks out herbivorous animals to hunt. If a target is within range and the hunt is successful, the wolf's energy increases. The wolf returns to NORMAL or MATE states based on its energy and desire levels after hunting.
			break;
		case MATE:
			mate_state(dt);                         //In the MATE state, the wolf seeks a mate. Successful mating results in offspring, and the wolf's state transitions are based on its energy and mating desire post-mating
			break;
		case DEAD:
			break;
		default:
			break;
		}

		checkLimits();          //Here we check if its out of boundaries
		checkDeath();          //Here we check if our animal is dead
 
	}

}
