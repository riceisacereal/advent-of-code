const fs = require("fs");
const { makeNumberList, decrypt, sumCoordinates } = require("./shared");

const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split(/\r?\n/);

function main() {
    let numberList = makeNumberList(lines, 811589153);

    for (let i = 0; i < 10; i++) {
        decrypt(numberList);
    }
    console.log(sumCoordinates(numberList));
}

main();