const fs = require("fs");
const { updateMaximum, getEmptySpace } = require("./shared");

const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split(/\r?\n/);

function countFaces(queue, space, max) {
    let faces = 0;
    while (queue.length > 0) {
        let coordinate = queue.shift();
        let [x, y, z] = coordinate;
        if (space[x][y][z] === -1) continue; // If already visited, skip
        space[x][y][z] = -1; // Visit

        for (let c = 0; c < 3; c++) {
            for (let d = -1; d <= 1; d += 2) {
                let neighbour = [x, y, z];
                neighbour[c] += d;
                if (neighbour[c] >= 0 && neighbour[c] <= max[c] + 1) {
                    let [dx, dy, dz] = neighbour;
                    let block = space[dx][dy][dz];
                    if (block === 1) {
                        // Empty block next to a block, add face
                        faces++;
                    } else if (block === 0) {
                        // Continue exploring empty blocks
                        queue.push(neighbour);
                    }
                }
            }
        }
    }
    return faces;
}

function main() {
    let max = [0, 0, 0];
    let coordinates = [];
    for (let line of lines) {
        let c = eval("[" + line + "]");
        for (let i = 0; i < 3; i++) {
            c[i]++; // Extra padding for actual puzzle input
        }
        updateMaximum(c, max);
        coordinates.push(c);
    }

    let space = getEmptySpace(max);
    // Mark block places
    for (let c of coordinates) {
        let [x, y, z] = c;
        space[x][y][z] = 1;
    }

    let queue = [];
    queue.push([0, 0, 0]);
    console.log(countFaces(queue, space, max));
}

main();