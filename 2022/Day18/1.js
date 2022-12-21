const fs = require("fs");
const { updateMaximum, getEmptySpace } = require("./shared");

const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split(/\r?\n/);

function main() {
    let max = [0, 0, 0];
    let coordinates = [];
    for (let line of lines) {
        let c = eval("[" + line + "]");
        updateMaximum(c, max);
        coordinates.push(c);
    }

    let space = getEmptySpace(max);

    let faces = 0;
    for (let c of coordinates) {
        let [x, y, z] = c;
        if (space[x][y][z] === 1) continue;
        space[x][y][z] = 1;
        // Every new block add 6 faces
        faces += 6;

        // Check 6 sides and if next to another block, -2 sides
        for (let c = 0; c < 3; c++) {
            for (let d = -1; d <= 1; d += 2) {
                let neighbour = [x, y, z];
                neighbour[c] += d;
                let [dx, dy, dz] = neighbour;
                if (neighbour[c] >= 0 && neighbour[c] <= max[c] && space[dx][dy][dz] === 1) faces -= 2;
            }
        }
    }

    console.log(faces);
}

main();