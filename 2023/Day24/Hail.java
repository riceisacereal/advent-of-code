public class Hail {
    long[] loc;
    int[] vel;

    public Hail(String line) {
        loc = new long[3];
        vel = new int[3];

        String[] l = line.split(" @ ");
        int i = 0;
        for (String s : l[0].split(", ")) {
            loc[i] = Long.parseLong(s.trim());
            i++;
        }
        i = 0;
        for (String s : l[1].split(", ")) {
            vel[i] = Integer.parseInt(s.trim());
            i++;
        }
    }

    public Hail(long[] loc, int[] vel) {
        this.loc = loc;
        this.vel = vel;
    }
}
