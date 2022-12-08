const fs = require("fs");

const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split(/\r?\n/);

let visible = 0;
let width = lines[0].length;
let height = lines.length;
for (let i = 0; i < height; i++) {
    for (let j = 0; j < width; j++) {
        let up, down, left, right;
        up = down = left = right = true;
        let currentTree = lines[i][j];

        // up
        for (let k = i - 1; k >= 0; k--) {
            if (lines[k][j] >= currentTree) {
                up = false;
                break;
            }
        }

        // down
        for (let k = i + 1; k < height; k++) {
            if (lines[k][j] >= currentTree) {
                down = false;
                break;
            }
        }

        // left
        for (let k = j - 1; k >= 0; k--) {
            if (lines[i][k] >= currentTree) {
                left = false;
                break;
            }
        }

        // right
        for (let k = j + 1; k < width; k++) {
            if (lines[i][k] >= currentTree) {
                right = false;
                break;
            }
        }

        visible += up || down || left || right;
    }
}

console.log(visible)