const fs = require("fs");

const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split(/\r?\n/);

let move = 0; // the number of steps needed to move to get rid of duplicate letter in the window
let position = 0;
for (let line of lines) {
    for (let i = 0; i < line.length; i++) {
        move--;
        // make sure start is at index 0 when window is smaller than 4
        let start = i - 3 < 0 ? 0 : i - 3;
        for (let j = i - 1; j >= start; j--) {
            if (line[i] === line[j]) {
                // if size of window smaller than 4, need to move more blocks
                if (i - start < 3) {
                    // needs to move remaining moves for it to be a size 4 window
                    // + one more to get rid of duplicate letter
                    move = 4 - (i - start) + 1;
                } else if (j - start + 1 > move) {
                    // if duplicate letter appears later than previous duplicate letter
                    // replace the number of steps needed to move
                    move = j - start + 1;
                }
                break;
            }
        }

        if (move === 0) {
            position = i + 1;
            break;
        }
    }

    console.log(position)
}

