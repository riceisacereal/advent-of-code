const fs = require("fs");

const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split(/\r?\n/);

let maxScenic = 0;
let width = lines[0].length;
let height = lines.length;
for (let i = 0; i < height; i++) {
    for (let j = 0; j < width; j++) {
        let up, down, left, right;
        up = down = left = right = 0;
        let currentTree = lines[i][j];

        // up
        for (let k = i - 1; k >= 0; k--) {
            up++;
            if (lines[k][j] >= currentTree) {
                break;
            }
        }

        // down
        for (let k = i + 1; k < height; k++) {
            down++;
            if (lines[k][j] >= currentTree) {
                break;
            }
        }

        // left
        for (let k = j - 1; k >= 0; k--) {
            left++;
            if (lines[i][k] >= currentTree) {
                break;
            }
        }

        // right
        for (let k = j + 1; k < width; k++) {
            right++;
            if (lines[i][k] >= currentTree) {
                break;
            }
        }

        let scenic = up * down * left * right;
        if (maxScenic < scenic) maxScenic = scenic;
    }
}

console.log(maxScenic)