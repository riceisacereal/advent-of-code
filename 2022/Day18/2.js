const fs = require("fs");

const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split(/\r?\n/);


function updateMaximum(coordinate, max) {
    for (let i = 0; i < 3; i++) {
        if (coordinate[i] > max[i]) max[i] = coordinate[i];
    }
}

let max = [0, 0, 0];
let coordinates = [];
for (let line of lines) {
    let c = eval("[" + line + "]");
    for (let i = 0; i < 3; i++) {
        c[i]++; // Extra padding for actual input
    }
    updateMaximum(c, max);
    coordinates.push(c);
}

let space = [];
for (let i = 0; i <= max[0] + 2; i++) {
    let a = [];
    for (let j = 0; j <= max[1] + 2; j++) {
        let b = [];
        for (let k = 0; k <= max[2] + 2; k++) {
            b.push(0);
        }
        a.push(b);
    }
    space.push(a);
}

for (let c of coordinates) {
    let [x, y, z] = c;
    space[x][y][z] = 1;
}

let faces = 0;

function countFaces(queue) {
    while (queue.length > 0) {
        let coordinate = queue.shift();
        let [x, y, z] = coordinate;
        if (space[x][y][z] === -1) continue;
        space[x][y][z] = -1;

        let newCoordinate = [x, y, z];
        for (let c = 0; c < 3; c++) {
            for (let d = -1; d <= 1; d += 2) {
                newCoordinate = [x, y, z];
                newCoordinate[c] = coordinate[c] + d;
                if (newCoordinate[c] >= 0 && newCoordinate[c] <= max[c] + 1) {
                    let [dx, dy, dz] = newCoordinate;
                    let block = space[dx][dy][dz];
                    if (block === 1) {
                        faces++;
                    } else if (block === 0) {
                        queue.push(newCoordinate);
                    }
                }
            }
        }
    }
}

let queue = [];
queue.push([0, 0, 0]);
countFaces(queue);
console.log(faces);