const fs = require("fs");
const { yellNumbers, parse } = require("./shared");

const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split(/\r?\n/);

function main() {
    let monkeys = parse(lines);
    console.log(yellNumbers(monkeys, "root"));
}

main();