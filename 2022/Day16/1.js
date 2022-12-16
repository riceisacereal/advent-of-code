const fs = require("fs");

const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split(/\r?\n/);

let TIME = 30;

class Valve {
    constructor(flow, neighbours) {
        this.flow = flow;
        this.neighbours = neighbours;
    }
}

function parse(lines) {
    let info = lines.map(x => x.split(/Valve | has flow rate=|; tunnels* leads* to valves* |, /).filter(e => e));

    let valveMap = new Map();
    for (let i of info) {
        let valve = new Valve(parseInt(i[1]), i.slice(2, i.length));
        valveMap.set(i[0], valve);
    }

    return valveMap;
}

function getWorkingValves(valveMap) {
    let workingValves = new Map();
    for (let [key, value] of valveMap) {
        if (value.flow > 0) workingValves.set(key, 0);
    }

    return workingValves;
}

function getValveDistance(valveMap) {
    let distance = new Map();
    for (let [key, value] of valveMap) {
        distance.set(key, 0);
    }

    return distance;
}

function findNextValve(workingValves, valveMap) {
    let maxRelease = ["", 0];
    for (let [key, value] of workingValves) {
        let ratio = valveMap.get(key).flow / (value ** 2);
        if (ratio > maxRelease[1]) maxRelease = [key, ratio];
    }
    let potential = (TIME - workingValves.get(maxRelease[0]) - 1) * valveMap.get(maxRelease[0]).flow;
    return [maxRelease[0], potential];
}

function BFS(valveMap) {
    let releasedPressure = 0;

    let visited = [];
    let queue = [];
    queue.push("AA");
    let workingValves = getWorkingValves(valveMap);
    while (workingValves.size > 0 && TIME >= 0) {
        let checkedValves = 0;
        let level = 0;
        while (checkedValves < workingValves.size) {
            let nextValves = [];
            while (queue.length > 0) {
                let valve = queue.shift();
                visited.push(valve);
                if (workingValves.has(valve)) {
                    workingValves.set(valve, level);
                    checkedValves++;
                }

                for (let neighbour of valveMap.get(valve).neighbours) {
                    if (!visited.includes(neighbour) && !nextValves.includes(neighbour) && !queue.includes(neighbour)) {
                        nextValves.push(neighbour);
                    }
                }
            }
            queue = nextValves;
            level++;
        }

        let nextValve = findNextValve(workingValves, valveMap);

        let distance = workingValves.get(nextValve[0]);
        TIME -= distance + 1;
        releasedPressure += nextValve[1];

        console.log(nextValve[0]);
        workingValves.delete(nextValve[0]);

        visited = [];
        queue = [];
        queue.push(nextValve[0]);
    }

    return releasedPressure;
}

function main() {
    let valveMap = parse(lines);

    let releasedPressure = BFS(valveMap);
    // let workingValves = getWorkingValves(valveMap);



    console.log(releasedPressure);
}

main();

// 1657 high
// 1381 low