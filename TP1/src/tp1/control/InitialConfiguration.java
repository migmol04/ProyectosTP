package tp1.control;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InitialConfiguration {

    public static final InitialConfiguration NONE = new InitialConfiguration();

    private List<String> descriptions;

    private InitialConfiguration() {
    }

    private InitialConfiguration(List<String> descriptions) {
        this.descriptions = descriptions;
    }

    public List<String> getShipDescription() {
        return Collections.unmodifiableList(descriptions);
    }

    public static InitialConfiguration readFromFile(String filename) throws IOException {
        List<String> descriptions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                descriptions.add(line.trim());
            }
        }

        return new InitialConfiguration(descriptions);
    }

}
