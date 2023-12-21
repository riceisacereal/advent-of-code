import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static Component linkOutputLocateRx(HashMap<String, String[]> links,
                                          HashMap<String, Component> componentMap,
                                          ArrayList<String> nandInputs) {
        // Link all output components and locate rx
        Component rxParent = null;
        for (Map.Entry<String, String[]> e : links.entrySet()) {
            Component parent = componentMap.get(e.getKey());
            for (String child : e.getValue()) {
                Component c = componentMap.get(child);
                if (c != null) {
                    parent.addOutput(c);
                } else {
                    Component dummy = new Component(child);
                    parent.addOutput(dummy); // Add dummy component
                    if (child.equals("rx")) {
                        // Pretty sure it's only rx but what if
                        rxParent = parent;
                    }
                }
                // Link input components for NAND
                if (nandInputs.contains(child)) {
                    Component nand = componentMap.get(child);
                    nand.inputComponents.add(parent);
                }
            }
        }

        return rxParent;
    }
}
