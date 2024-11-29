# Ecosystem Simulation - README

## Description
This project implements a simulation of an ecosystem, where various entities (such as animals, plants, and environmental factors) interact within a defined space. The simulation allows you to configure parameters such as the size of the ecosystem, the number of entities, and specific behaviors. Additionally, it supports a random seed for reproducible simulation runs.

### Features:
- Ecosystem configuration options.
- Support for random or deterministic (seed-based) simulations.
- Interactive console-based visualization of the ecosystem's evolution.
- Encapsulation of simulation logic within separate modules for maintainability.

---

## Prerequisites

1. **Java Development Kit (JDK):** Ensure you have JDK 8 or higher installed.  
   To verify your Java version, run:
   ```bash
   java -version


## IDE or Console
The program can be run from an IDE (e.g., IntelliJ IDEA, Eclipse, etc.) or directly from the terminal.

---

## Project Structure
The project is organized into the following packages:

- **`ecosystem`**: Contains the `Main` class, the entry point of the simulation.
- **`ecosystem.control`**: Manages the control flow of the simulation (e.g., starting, pausing, and stopping).
- **`ecosystem.logic`**: Implements the core simulation logic, including:
  - Entity behaviors (e.g., movement, reproduction, predation).
  - Environmental rules.
- **`ecosystem.view`**: Handles user interaction and displays the ecosystem state.

---

## Running the Program

## Description
This program allows the execution of a simulation with two main modes: **Batch Mode** and **GUI Mode**. The user can configure the simulation parameters using command-line arguments. Below, you will find details on how to execute the program, including argument descriptions and examples.

---

## Execution Modes

### **1. Batch Mode (`batch`)**
- Executes the simulation without a graphical interface.
- Input data is provided through a JSON configuration file.
- Results are written to an output file.

### **2. GUI Mode (`gui`)**
- Launches the simulation with a graphical user interface.
- Allows visual interaction for setting up and observing the simulation.
- Optionally, a JSON configuration file can be provided as input.

---

## Command-Line Arguments

| **Argument**         | **Description**                                                                                           | **Required in Batch** | **Required in GUI** |
|-----------------------|-----------------------------------------------------------------------------------------------------------|------------------------|----------------------|
| `-m` / `--mode`       | Execution mode: `batch` or `gui`. Default: `gui`.                                                        | Yes                   | No                   |
| `-i` / `--input`      | JSON configuration file for the simulation.                                                              | Yes                   | Optional             |
| `-o` / `--output`     | Output file where results are written.                                                                   | Yes                   | No                   |
| `-t` / `--time`       | Total simulation time in seconds. Default: `10.0`.                                                       | Optional              | Optional             |
| `-dt` / `--delta-time`| Simulation step time in seconds. Default: `0.03`.                                                        | Optional              | Optional             |
| `-sv` / `--simple-viewer` | Activates a simple console-based viewer for the simulation.                                           | Optional              | Optional             |
| `-h` / `--help`       | Displays help information with a list of available arguments.                                            | No                    | No                   |

---

## Notes

### **Error Handling**
1. **Missing Input File**:
   - In batch mode, the input file is required. If it is not provided, the program will terminate with an error.

2. **Invalid Mode**:
   - If an unsupported mode is specified (other than `batch` or `gui`), the program will terminate with an error message.

### **Default Configuration**
- If no mode is specified, the program defaults to **GUI mode**.
- If no input file is provided in GUI mode, a default simulation configuration is created.

