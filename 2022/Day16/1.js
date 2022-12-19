const fs = require("fs");
const { parse, getWorkingValves, makeDistance, trackMaxPressure, getMaxPressure } = require("./shared");

const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split(/\r?\n/);

class Path {
    constructor(visited, valve, stepsAway, pressure) {
        this.visited = [];
        for (let v of visited) {
            this.visited.push(v);
        }
        this.valve = valve;
        this.stepsAway = stepsAway;
        this.pressure = pressure;
    }
}

function getPressure(valveMap, valves) {
    let paths = [];
    let start = new Path([], "AA", -1, 0);
    paths.push(start);
    for (let i = 30; i >= 0; i--) {
        let newPaths = [];

        while (paths.length > 0) {
            let p = paths.shift();
            if (p.stepsAway > 0) {
                // valve reached, turn on
                p.stepsAway -= 1;
                newPaths.push(p);
                continue;
            }
            if (p.stepsAway === 0) p.pressure += valveMap[p.valve].flow * i;

            // if valve turned on, loop through next valves and make new path, push this valve onto visited
            p.visited.push(p.valve);
            // visited all valves
            if (p.visited.length === valves.length + 1) {
                trackMaxPressure(p.pressure);
            }
            for (let v of valves) {
                if (!p.visited.includes(v)) {
                    // can reach?
                    let d = valveMap[p.valve].distance[v];
                    if (i - d - 1 > 0) {
                        let np = new Path(p.visited, v, d, p.pressure);
                        newPaths.push(np);
                    } else {
                        trackMaxPressure(p.pressure);
                    }
                }
            }
        }

        paths = newPaths;
    }
}

function main() {
    let valveMap = parse(lines);
    let workingValves = getWorkingValves(valveMap);
    // Make distance map
    makeDistance(valveMap, workingValves, "AA");
    getPressure(valveMap, workingValves);
    console.log(getMaxPressure());
}

main();

// 1657 high
// 1381 low