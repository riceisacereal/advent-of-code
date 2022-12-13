const fs = require("fs");

const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split(/\r?\n/);

let sumPairs = 0;

function compareInteger(left, right) {
    return right - left;
}

function compare(left, right) {
    if (Array.isArray(left) && Array.isArray(right)) {
        let order = 0;
        let i = 0;
        while (i < Math.min(left.length, right.length) && order === 0) {
            order = compare(left[i], right[i]);
            i++;
        }
        /* order
        0 > -1
        0 < 1 <-
        0 = 0
        1 > 1
        1 < 1
        1 = 1

        */


        if (order === 0 && left.length > right.length) {
            console.log("Not ordered: ");
            console.log(left);
            console.log(right);
            return -1;
        } if (order === 0 && left.length < right.length) {
            return 1;
        } else {
            return order;
        }
    } else if (Number.isInteger(left) && Number.isInteger(right)) {
        return compareInteger(left, right);
    } else {
        if (Number.isInteger(left)) {
            return compare([left], right);
        } else {
            return compare(left, [right]);
        }
    }
}

function makeList(line) {
    if (line === "[]"){
        return [];
    } else if (line[0] === "[") {
        // item in list is still list
        // loop through items and recursive call
        let bracket = 0;
        let start = 1;
        let thisList = [];
        for (let i = 1; i < line.length; i++) {
            if (line[i] === "[") bracket++;
            if (line[i] === "]") bracket--;
            if (line[i] === "," && bracket === 0) {
                thisList.push(makeList(line.substring(start, i)));
                start = i + 1;
            }
            if (i === line.length - 1) {
                thisList.push(makeList(line.substring(start, i)));
            }
        }
        return thisList;
    } else {
        return parseInt(line);
    }
}

function main() {
    let i = 0;
    while (i < lines.length) {
        let left = eval(lines[i++]);
        let right = eval(lines[i++]);

        if (compare(left, right) >= 0) {
            let pair = Math.floor(i / 3) + 1;
            console.log("Ordered: " + pair)
            sumPairs += pair;
        }
        i++;
    }
}

// function testParsing() {
//     let i = 0;
//     while (i < lines.length) {
//         let left = makeList(lines[i++]);
//         let right = makeList(lines[i++]);
//
//         i++;
//
//         console.log(left);
//         console.log(right);
//         console.log("\n");
//     }
// }

main();
console.log(sumPairs);
// console.log(compare([[[]]],[[]]));
// testParsing();

// 3090 too low
