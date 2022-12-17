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

    for (let v of valves) {
        // Time for currentValve -> v
        time -= valveMap[currentValve].distance[v] + 1;
        if (time < 0) break;

        pressure += time * valveMap[v].flow;
        currentValve = v;
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
let count = 0;
function trackMaxPressure(pressure) {
    // console.log(pressure);
    count++;
    if (count % 10000000 === 0) console.log(count);
    if (pressure > maxPressure) maxPressure = pressure;
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

function main() {
    let valveMap = parse(lines);
    let workingValves = getWorkingValves(valveMap);
    // Make distance map
    makeDistance(valveMap, workingValves, "AA");
    // console.log(getPressure(valveMap, workingValves, "AA"));

    // // TODO: All possible combinations
    makeOrder(valveMap, workingValves, 0, workingValves.length - 1);
    // // console.log(getPressure(valveMap, workingValves, "AA"));
    // // makeOrder(workingValves, 0, workingValves.length - 1);
    //
    console.log(maxPressure);
}

main();

// 1657 high
// 1381 low