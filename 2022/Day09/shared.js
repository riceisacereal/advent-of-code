function getSpans(lines) {
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

    return [spanX, spanY]
}

function getCleanGraph(spanX, spanY) {
    let visited = [];
    for (let i = 0; i < spanY[1] - spanY[0] + 1; i++) {
        let row = [];
        for (let j = 0; j < spanX[1] - spanX[0] + 1; j++) {
            row.push(".");
        }

        visited.push(row);
    }

    return visited;
}

function updateTail(head, tail) {
    let dX, dY;
    dX = head[0] - tail[0];
    dY = head[1] - tail[1];

    if (Math.abs(dX) > 1 || Math.abs(dY) > 1) {
        tail[0] += Math.sign(dX);
        tail[1] += Math.sign(dY);
    }
}

function footprint(visited, spanX, spanY, knot) {
    let cX = knot[0] + Math.abs(spanX[0]);
    let cY = spanY[1] - knot[1];
    if (visited[cY][cX] === ".") {
        visited[cY][cX] = "#";
        return 1;
    }

    return 0;
}

function pullRope(knot, update, lines) {
    for (let line of lines) {
        let steps = parseInt(line.substring(2, line.length));
        switch (line[0]) {
            case "U":
                for (let i = 0; i < steps; i++) {
                    knot[1]++;
                    update();
                }
                break;
            case "D":
                for (let i = 0; i < steps; i++) {
                    knot[1]--;
                    update();
                }
                break;
            case "L":
                for (let i = 0; i < steps; i++) {
                    knot[0]--;
                    update();
                }
                break;
            case "R":
                for (let i = 0; i < steps; i++) {
                    knot[0]++;
                    update();
                }
                break;
        }
    }
}

module.exports = {
    getSpans: getSpans,
    getCleanGraph: getCleanGraph,
    updateTail: updateTail,
    footprint: footprint,
    pullRope: pullRope
}