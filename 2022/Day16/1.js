const fs = require("fs");

const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split(/\r?\n/);

class Valve {
    constructor(flow, neighbours) {
        this.flow = flow;
        this.neighbours = neighbours;
        this.distance = {};
    }
}

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

function parse(lines) {
    let info = lines.map(x => x.split(/Valve | has flow rate=|; tunnels* leads* to valves* |, /).filter(e => e));

    let valveMap = {};
    for (let i of info) {
        valveMap[i[0]] = new Valve(parseInt(i[1]), i.slice(2, i.length));
    }
    return valveMap;
}

function getWorkingValves(valveMap) {
    let workingValves = [];
    for (let key in valveMap) {
        if (valveMap[key].flow > 0) workingValves.push(key);
    }
    return workingValves;
}

function BFS(valveMap, valves, valve) {
    let queue = [valve];
    let distance = {};
    distance[valve] = 0;

    let count = 0;
    while (count < valves.length && queue.length > 0) {
        let v = queue.shift();
        if (valves.includes(v)) {
            // Add distance to distance map of valve
            valveMap[valve].distance[v] = distance[v];
            count++;
        }

        for (let n of valveMap[v].neighbours) {
            if (distance[n] === undefined) {
                distance[n] = distance[v] + 1;
                queue.push(n);
            }
        }
    }
}

function makeDistance(valveMap, valves, start) {
    BFS(valveMap, valves, start);
    for (let valve of valves) {
        BFS(valveMap, valves, valve);
    }
}

let maxPressure = 0;
function trackMaxPressure(pressure) {
    if (pressure > maxPressure) maxPressure = pressure;
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
    console.log(maxPressure);
}

main();

// 1657 high
// 1381 low