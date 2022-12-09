const fs = require("fs");

const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split(/\r?\n/);

let spanX, spanY, curX, curY;
spanX = [0, 0];
spanY = [0, 0];
curX = curY = 0;

for (let line of lines) {
    let steps = parseInt(line.substring(2, line.length));
    switch (line[0]) {
        case "U":
            curY += steps;
            if (curY > spanY[1]) spanY[1] = curY;
            break;
        case "D":
            curY -= steps;
            if (curY < spanY[0]) spanY[0] = curY;
            break;
        case "L":
            curX -= steps;
            if (curX < spanX[0]) spanX[0] = curX;
            break;
        case "R":
            curX += steps;
            if (curX > spanX[1]) spanX[1] = curX;
            break;
    }
}

console.log(spanX, spanY)

let visited = [];
for (let i = 0; i < spanY[1] - spanY[0] + 1; i++) {
    let row = [];
    for (let j = 0; j < spanX[1] - spanX[0] + 1; j++) {
        row.push(0)
    }
    visited.push(row);
}

let knots = [];
for (let i = 0; i < 10; i++) {
    knots.push([0, 0])
}
let count = 0;

function updateTail(head, tail) {
    let dX, dY;
    dX = head[0] - tail[0];
    dY = head[1] - tail[1];

    if (Math.abs(dX) > 1 || Math.abs(dY) > 1) {
        tail[0] += Math.sign(dX);
        tail[1] += Math.sign(dY);
    }
}

function updateTails() {
    for (let i = 0; i < 9; i++) {
        updateTail(knots[i], knots[i + 1]);
    }

    let cX = knots[9][0] + Math.abs(spanX[0]);
    let cY = spanY[1] - knots[9][1];
    if (visited[cY][cX] === 0) {
        visited[cY][cX] = 1;
        count++;
    }
}

for (let line of lines) {
    let steps = parseInt(line.substring(2, line.length));
    switch (line[0]) {
        case "U":
            for (let i = 0; i < steps; i++) {
                knots[0][1]++;
                updateTails();
            }
            break;
        case "D":
            for (let i = 0; i < steps; i++) {
                knots[0][1]--;
                updateTails();
            }
            break;
        case "L":
            for (let i = 0; i < steps; i++) {
                knots[0][0]--;
                updateTails();
            }
            break;
        case "R":
            for (let i = 0; i < steps; i++) {
                knots[0][0]++;
                updateTails();
            }
            break;
    }
}

console.log(count)
// console.log(visited)

// 3110 too low