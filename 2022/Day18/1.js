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
    updateMaximum(c, max);
    coordinates.push(c);
}

let space = [];
for (let i = 0; i <= max[0]; i++) {
    let a = [];
    for (let j = 0; j <= max[1]; j++) {
        let b = [];
        for (let k = 0; k <= max[2]; k++) {
            b.push(0);
        }
        a.push(b);
    }
    space.push(a);
}

// console.log(space);
let faces = 0;
for (let c of coordinates) {
    let [x, y, z] = c;
    if (space[x][y][z] === 1) continue;
    space[x][y][z] = 1;
    // Every new block add 6 faces
    faces += 6;

    // Check 6 sides and if next to another block, -2 sides
    if (x - 1 >= 0 && space[x - 1][y][z] === 1) faces -= 2;
    if (x + 1 <= max[0] && space[x + 1][y][z] === 1) faces -= 2;
    if (y - 1 >= 0 && space[x][y - 1][z] === 1) faces -= 2;
    if (y + 1 <= max[1] && space[x][y + 1][z] === 1) faces -= 2;
    if (z - 1 >= 0 && space[x][y][z - 1] === 1) faces -= 2;
    if (z + 1 <= max[2] && space[x][y][z + 1] === 1) faces -= 2;
}

console.log(faces);

