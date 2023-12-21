import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Shared {
    public static void mapComponentMap(List<String> lines, HashMap<String, Component> componentMap,
                                       HashMap<String, String[]> links, ArrayList<String> nandInputs) {
        // Create all components and put links into map
        for (String line : lines) {
            String[] link = line.split(" -> ");
            Component c = new Component(link[0]);
            componentMap.put(c.name, c);
            links.put(c.name, link[1].split(", "));
            if (c.type == '&') nandInputs.add(c.name);
        }
    }
}
