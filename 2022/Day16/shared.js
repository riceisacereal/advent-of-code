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
        console.log(maxPressure);
    }
}

function getMaxPressure() {
    return maxPressure;
}

module.exports = {
    Valve: Valve,
    parse: parse,
    getWorkingValves: getWorkingValves,
    makeDistance: makeDistance,
    trackMaxPressure: trackMaxPressure,
    getMaxPressure: getMaxPressure
}