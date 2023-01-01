const fs = require("fs");
const { yellNumbers, parse } = require("./shared");

const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split(/\r?\n/);

function getNextStep(delta) {
    // Gradient descent with step size 0.05
    let step = Math.floor(0.05 * delta);
    step = step < 1 ? 1 : step;

    return step;
}

function main() {
    let monkeys = parse(lines);

    let job = monkeys["root"];
    job = job.split(" ");

    monkeys["humn"] = 1;
    let left = yellNumbers(monkeys, job[0]);
    let right = yellNumbers(monkeys, job[2]);
    let step = getNextStep(Math.abs(left - right))

    while (left !== right) {
        monkeys["humn"] += step;
        left = yellNumbers(monkeys, job[0]);
        right = yellNumbers(monkeys, job[2]);
        step = getNextStep(Math.abs(left - right));
    }

    console.log(monkeys["humn"]);
}

main();