package simulator.launcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.SwingUtilities;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

import simulator.control.Controller;
import simulator.factories.Builder;
import simulator.factories.BuilderBasedFactory;
import simulator.factories.DefaultRegionBuilder;
import simulator.factories.DynamicSupplyRegionBuilder;
import simulator.factories.Factory;
import simulator.factories.SelectClosestBuilder;
import simulator.factories.SelectFirstBuilder;
import simulator.factories.SelectYoungestBuilder;
import simulator.factories.SheepBuilder;
import simulator.factories.TimeDefaulRegionBuilder;
import simulator.factories.WolfBuilder;
import simulator.misc.Utils;
import simulator.model.Animal;
import simulator.model.NumDead;
import simulator.model.NumMuertos;
import simulator.model.OBSMAYOR;
import simulator.model.Region;
import simulator.model.SelectionStrategy;
import simulator.model.Simulator;
import simulator.model.Supera;
import simulator.view.MainWindow;

public class Main {

	public static Factory<Animal> _animal_factory;
	public static Factory<Region> _region_factory;

	private enum ExecMode {
		BATCH("batch", "Batch mode"), GUI("gui", "Graphical User Interface mode");

		private String _tag;
		private String _desc;

		private ExecMode(String modeTag, String modeDesc) {
			_tag = modeTag;
			_desc = modeDesc;
		}

		public String get_tag() {
			return _tag;
		}

		public String get_desc() {
			return _desc;
		}
	}

	// default values for some parameters
	//
	private final static Double _default_time = 10.0; // in seconds

	// some attributes to stores values corresponding to command-line parameters

	//
	public static Double _delta_time = 0.03; // Valor por defecto
	private static String _out_file = null;
	private static boolean _simple_viewer = false;
	private static Double _time = null;
	private static String _in_file = null;
	private static ExecMode _mode = null;

	private static void parse_args(String[] args) { // It builds options, parses them from the command line, and assigns
													// values to variables like input file, output file, simulation
													// time, delta time, and viewer mode. It also handles help requests.
		Options cmdLineOptions = build_options();
		CommandLineParser parser = new DefaultParser();

		try {
			CommandLine line = parser.parse(cmdLineOptions, args);
			parse_mode_option(line);
			parse_help_option(line, cmdLineOptions);
			parse_in_file_option(line);
			parse_time_option(line);
			parse_out_file_option(line);
			parse_delta_time_option(line);
			parse_simple_viewer_option(line);
			

		} catch (ParseException e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}
	}

	private static void parse_out_file_option(CommandLine line) {
		if (line.hasOption("o")) {
			_out_file = line.getOptionValue("o");
		}
	}

	private static void parse_delta_time_option(CommandLine line) throws ParseException {
		if (line.hasOption("dt")) {
			try {
				_delta_time = Double.parseDouble(line.getOptionValue("dt", "0.03"));
				if (_delta_time <= 0) {
					throw new ParseException("Delta time must be greater than zero.");
				}
			} catch (NumberFormatException e) {
				throw new ParseException("Invalid value for delta-time: " + line.getOptionValue("dt"));
			}
		}
	}

	private static void parse_simple_viewer_option(CommandLine line) {
		_simple_viewer = line.hasOption("sv");
	}

	private static Options build_options() {
		Options cmdLineOptions = new Options();

		// help
		cmdLineOptions.addOption(Option.builder("h").longOpt("help").desc("Print this message.").build());

		// input file
		cmdLineOptions.addOption(Option.builder("i").longOpt("input").hasArg().desc("A configuration file.").build());

		// steps
		cmdLineOptions.addOption(Option.builder("t").longOpt("time").hasArg()
				.desc("An real number representing the total simulation time in seconds. Default value: "
						+ _default_time + ".")
				.build());

		// output
		cmdLineOptions.addOption(
				Option.builder("o").longOpt("output").hasArg().desc("Output file, where output is written.").build());

		// delta time
		cmdLineOptions.addOption(Option.builder("dt").longOpt("delta-time").hasArg()
				.desc("A double representing actual time, in seconds, per simulation step. Default value: 0.03.")
				.build());

		// simple viewer
		cmdLineOptions.addOption(
				Option.builder("sv").longOpt("simple-viewer").desc("Show the viewer window in console mode.").build());

		// GUI
		cmdLineOptions.addOption(Option.builder("m").longOpt("mode").hasArg().desc(
				"Execution mode. Possible values: 'batch' (Batch mode), 'gui' (Graphical User Interface mode). Default value: 'gui'.")
				.build());

		return cmdLineOptions;
	}

	private static void parse_help_option(CommandLine line, Options cmdLineOptions) {
		if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), cmdLineOptions, true);
			System.exit(0);
		}
	}

	private static void parse_in_file_option(CommandLine line) throws ParseException {
		_in_file = line.getOptionValue("i");
		if (_mode == ExecMode.BATCH && _in_file == null) {
			throw new ParseException("In batch mode an input configuration file is required");
		}
	}
	
	private static void parse_mode_option(CommandLine line) throws ParseException {
	    String mode = line.getOptionValue("m", "gui");
	    switch (mode) {
	        case "batch":
	            _mode = ExecMode.BATCH;
	            break;
	        case "gui":
	            _mode = ExecMode.GUI;
	            break;
	        default:
	            throw new ParseException("Invalid mode: " + mode);
	    }
	}

	private static void parse_time_option(CommandLine line) throws ParseException {
		String t = line.getOptionValue("t", _default_time.toString());
		try {
			_time = Double.parseDouble(t);
			assert (_time >= 0);
		} catch (Exception e) {
			throw new ParseException("Invalid value for time: " + t);
		}
	}

	private static void init_factories() { // initializes factories for selection strategies, animals, and regions using
											// builder patterns
		// Initialize the selection strategy factory
		List<Builder<SelectionStrategy>> selectionStrategyBuilders = new ArrayList<>();
		selectionStrategyBuilders.add(new SelectFirstBuilder());
		selectionStrategyBuilders.add(new SelectClosestBuilder());
		selectionStrategyBuilders.add(new SelectYoungestBuilder());
		Factory<SelectionStrategy> selection_strategy_factory = new BuilderBasedFactory<>(selectionStrategyBuilders);

		// Initialize the animal factory
		List<Builder<Animal>> animalBuilders = new ArrayList<>();
		animalBuilders.add(new SheepBuilder(selection_strategy_factory));
		animalBuilders.add(new WolfBuilder(selection_strategy_factory));
		_animal_factory = new BuilderBasedFactory<>(animalBuilders);

		// Initialize the region factory
		List<Builder<Region>> regionBuilders = new ArrayList<>();
		regionBuilders.add(new DefaultRegionBuilder());
		regionBuilders.add(new DynamicSupplyRegionBuilder());
		regionBuilders.add(new TimeDefaulRegionBuilder()); 
		
		_region_factory = new BuilderBasedFactory<>(regionBuilders);

	}

	private static JSONObject load_JSON_file(InputStream in) { // loads and parses JSON configuration files needed to
																// set up the simulation environment.
		return new JSONObject(new JSONTokener(in));
	}

	private static void start_batch_mode() throws Exception { // sets up the simulation based on input parameters, loads
																// data, and runs the simulation, writing the results to
																// a file
		InputStream is = new FileInputStream(new File(_in_file));
		JSONObject jsonInput = load_JSON_file(is);
		OutputStream os = new FileOutputStream(new File(_out_file));

		Simulator simulator = new Simulator(jsonInput.getInt("cols"), jsonInput.getInt("rows"),
				jsonInput.getInt("width"), jsonInput.getInt("height"), _animal_factory, _region_factory);
		Controller controller = new Controller(simulator);
		NumMuertos N = new NumMuertos(controller);
		controller.load_data(jsonInput); // Load the data to the simulator
		NumDead d= new NumDead(controller);
OBSMAYOR m = new OBSMAYOR(controller);
Supera s = new Supera(controller);

		
	
		controller.run(_time, _delta_time, _simple_viewer, os); // Run the simulation
		//m.print();
		//d.print();
		s.print();
		is.close();
		os.close();
	}

	private static void start_GUI_mode() throws Exception {
	    Simulator simulator;
	    if (_in_file != null) {
	        InputStream is = new FileInputStream(new File(_in_file));
	        JSONObject jsonInput = load_JSON_file(is);
	        simulator = new Simulator(jsonInput.getInt("cols"), jsonInput.getInt("rows"),
	                                  jsonInput.getInt("width"), jsonInput.getInt("height"), _animal_factory, _region_factory);
	        Controller ctrl = new Controller(simulator);
	        ctrl.load_data(jsonInput);  
	        SwingUtilities.invokeAndWait(() -> new MainWindow(ctrl));
	        is.close();
	    } else {
	      
	        simulator = new Simulator(20, 15, 800, 600, _animal_factory, _region_factory);
	        Controller ctrl = new Controller(simulator);
	        SwingUtilities.invokeAndWait(() -> new MainWindow(ctrl));
	    }
	}

	private static void start(String[] args) throws Exception {
		init_factories();
		parse_args(args);
		switch (_mode) {
		case BATCH:
			start_batch_mode();
			break;
		case GUI:
			start_GUI_mode();
			break;
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		Utils._rand.setSeed(2147483647l);
		try {
			start(args);
		} catch (Exception e) {
			System.err.println("Something went wrong ...");
			System.err.println();
			e.printStackTrace();
		}
	}
}
