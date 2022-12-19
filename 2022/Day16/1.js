const fs = require("fs");
const { trackMaxPressure, getMaxPressure, getAll } = require("./shared");

const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split(/\r?\n/);

function getPressure(valveMap, valves, visited, myPlace, myTime, pressure) {
    let theEnd = true;
    for (let i = 0; i < valves.length; i++) {
        if (visited[i] === 0) {
            theEnd = false;
            let nextPlace = valves[i];
            let newMeTime = myTime - valveMap[valves[myPlace]].distance[nextPlace] - 1;

            if (newMeTime > 0) {
                // If I can reach, visit
                visited[i] = 1;
                getPressure(valveMap, valves, visited, i, newMeTime, pressure + valveMap[nextPlace].flow * newMeTime);
                visited[i] = 0;
            } else {
                // Neither of us can visit, path too long
                trackMaxPressure(pressure);
            }
        }
    }

    if (theEnd) trackMaxPressure(pressure);
}

function main() {
    let [valveMap, workingValves, visited] = getAll(lines);
    getPressure(valveMap, workingValves, visited, visited.length - 1, 30, 0);
    console.log(getMaxPressure());
}

main();