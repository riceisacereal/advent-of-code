import java.util.ArrayList;
import java.util.Queue;

public class Component {
    String name;
//    boolean input;
    boolean output;
    char type; // 0 for flip-flop, 1 for NAND
    // % -> 1 nothing
    //   -> 0 toggle previous state
    // & -> multi input NAND (0 if all 1, else 1)
    ArrayList<Component> inputComponents;
    ArrayList<Component> outputComponents;

    public Component(String line) {
        output = false;
        outputComponents = new ArrayList<>();
        type = line.charAt(0); // broadcast will get type b but that's ok
        switch (line.charAt(0)) {
            case '&':
                inputComponents = new ArrayList<>();
            case '%':
                name = line.substring(1);
                break;
            default: name = line;
        }
    }

    public void addOutput(Component child) {
        outputComponents.add(child);
    }

    public long sendPulse(Queue<Tuple> q, boolean input) {
        int count = 0;

        // Determine output
        switch (type) {
            case '%' -> {
                if (input) {
                    // No pulse
                    return count;
                } else {
                    output = !output;
                }
            }
            case '&' -> {
                output = false;
                for (Component in : inputComponents) {
                    if (!in.output) {
                        output = true;
                        break;
                    }
                }
            }
        }

        // Send output
        for (Component o : outputComponents) {
            q.add(new Tuple(o ,output));
        }

        if (output) {
            return outputComponents.size();
        } else {
            return -outputComponents.size();
        }
    }
}
