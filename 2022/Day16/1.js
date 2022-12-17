const fs = require("fs");

const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split(/\r?\n/);

class Valve {
    constructor(flow, neighbours) {
        this.flow = flow;
        this.neighbours = neighbours;
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
    for (let [key, value] of Object.entries(valveMap)) {
        if (value.flow > 0) workingValves.push(key);
    }
    return workingValves;
}

function getPressure(valveMap, valves, start) {
    let pressure = 0;

    let time = 30;
    let currentValve = start;
    while (valves.length > 0) {
        // Next valve to find
        let valve = valves.shift();

        // BFS queue
        let queue = [];
        queue.push(currentValve);

        // Distance to visiting valves
        let visited = {};
        visited[currentValve] = 0;
        while (queue.length > 0) {
            let v = queue.shift();
            // Found value
            if (valve === v) break;

            for (let n of valveMap[v].neighbours) {
                if (visited[n] === undefined) {
                    queue.push(n);
                    // Add 1 to distance from current valve
                    visited[n] = visited[v] + 1;
                }
            }
        }

        time -= visited[valve] + 1;
        if (time < 0) break;
        pressure += time * valveMap[valve].flow;
        currentValve = valve;
    }

    return pressure;
}

function newSwap(valves, a, b) {
    let newOrder = [];
    for (let i = 0; i < valves.length; i++) {
        newOrder.push(valves[i]);
    }

    let temp = newOrder[a];
    newOrder[a] = newOrder[b];
    newOrder[b] = temp;
    return newOrder;
}

function makeOrder(valveMap, valves, start, end) {
    if (start === end) trackMaxPressure(getPressure(valveMap, valves, "AA"));
    for (let i = start; i <= end; i++) {
        makeOrder(valveMap, newSwap(valves, start, i), start + 1, end);
    }
}

let maxPressure = 0;
function trackMaxPressure(pressure) {
    // console.log(pressure);
    if (pressure > maxPressure) maxPressure = pressure;
}

function main() {
    let valveMap = parse(lines);
    // console.log(valveMap);

    let workingValves = getWorkingValves(valveMap);
    // TODO: All possible combinations
    makeOrder(valveMap, workingValves, 0, workingValves.length - 1);
    // console.log(getPressure(valveMap, workingValves, "AA"));
    // makeOrder(workingValves, 0, workingValves.length - 1);

    console.log(maxPressure);
}

main();

// 1657 high
// 1381 low