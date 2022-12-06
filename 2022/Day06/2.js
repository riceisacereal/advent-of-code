const fs = require("fs");

const input = fs.readFileSync("test.txt", "utf-8");
const lines = input.split(/\r?\n/);

const WINDOW_SIZE = 14;
const DISPLACEMENT = WINDOW_SIZE - 1;

let move = 0; // the number of steps needed to move to get rid of duplicate letter in the window
let position = 0;
for (let line of lines) {
    for (let i = 0; i < line.length; i++) {
        move--;
        // make sure start is at index 0 when window is smaller than 14
        let start = i - DISPLACEMENT < 0 ? 0 : i - DISPLACEMENT;
        for (let j = i - 1; j >= start; j--) {
            if (line[i] === line[j]) {
                let windowSize = i - start + 1;
                if (windowSize < WINDOW_SIZE) {
                    // window size smaller than 14
                    // needs to move remaining moves for it to be a size 14 window
                    // + j more to get rid of duplicate letter
                    move = WINDOW_SIZE - windowSize + j + 1;
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

