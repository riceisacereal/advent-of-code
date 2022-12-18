const fs = require("fs");

const input = fs.readFileSync("test.txt", "utf-8");
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
        this.visited = [...visited];
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
    if (pressure > maxPressure) {
        maxPressure = pressure;
        console.log(maxPressure)
    }
}

function updatePath(valveMap, valves, p, newPaths, e, time) {
    // valve reached, turn on
    if (p.stepsAway[e] === 0) p.pressure += valveMap[p.valve[e]].flow * time;

    // visited all valves
    if (p.visited.length === valves.length) {
        let pressure = p.pressure + valveMap[p.valve[1 - e]].flow * (time - p.stepsAway[1 - e] - 1);
        trackMaxPressure(pressure);
        return;
    }

    // if valve turned on, loop through next valves and make new path, push this valve onto visited
    p.visited.push(p.valve[e]);
    let foundNext = false;
    for (let v of valves) {
        if (!p.visited.includes(v) && p.valve[1 - e] !== v) {
            // can reach?
            let d = valveMap[p.valve[e]].distance[v];
            if (time - d - 1 > 0) {
                // visited, valve, stepsAway, pressure
                let newValve = [...p.valve];
                newValve[e] = v;
                let newStepsAway = [...p.stepsAway];
                newStepsAway[e] = d;
                let np = new Path(p.visited, newValve, newStepsAway, p.pressure);
                newPaths.push(np);
                foundNext = true;
            } else {
                trackMaxPressure(p.pressure);
            }
        }
    }

    // if (!foundNext) {
    //     p.stepsAway[e] = -1;
    // }
}

function getPressure(valveMap, valves) {
    let paths = [];
    let start = new Path([], ["AA", "AA"], [0, 0], 0);
    paths.push(start);
    for (let i = 26; i >= 0; i--) {
        let newPaths = [];

        while (paths.length > 0) {
            let p = paths.shift();
            if (p.stepsAway[0] > 0 && p.stepsAway[1] > 0) {
                // Neither me nor elephant have reached a valve
                p.stepsAway[0] -= 1;
                p.stepsAway[1] -= 1;
                newPaths.push(p);
            } else if (p.stepsAway[0] === 0 && p.stepsAway[1] === 0) {
                // Both have reached a valve - double seek
                let tempPath = [];
                updatePath(valveMap, valves, p, tempPath, 0, i);
                while (tempPath.length > 0) {
                    let p = tempPath.shift();
                    updatePath(valveMap, valves, p, newPaths, 1, i);
                }
            } else {
                if (p.stepsAway[0] === 0) {
                    // I have reached a valve
                    p.stepsAway[1] -= 1;
                    updatePath(valveMap, valves, p, newPaths, 0, i);
                } else {
                    // Elephant has reached valve
                    p.stepsAway[0] -= 1;
                    updatePath(valveMap, valves, p, newPaths, 1, i);
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

// 1688 low