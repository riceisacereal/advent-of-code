const fs = require("fs");

const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split(/\r?\n/);

function yellNumbers(monkeys, monkey) {
    let job = monkeys[monkey];
    if (job.length !== 11) return parseInt(job);

    job = job.split(" ");
    let left = yellNumbers(monkeys, job[0]);
    let right = yellNumbers(monkeys, job[2]);
    switch (job[1]) {
        case "+":
            return left + right;
        case "-":
            return left - right;
        case "*":
            return left * right;
        case "/":
            return left / right;
    }
}

function parse(lines) {
    let monkeys = {};
    for (let line of lines) {
        let [name, job] = line.split(": ");
        monkeys[name] = job;
    }

    return monkeys;
}

function main() {
    let monkeys = parse(lines);

    let job = monkeys["root"];
    job = job.split(" ");

    monkeys["humn"] = 2;
    let left = yellNumbers(monkeys, job[0]);
    let right = yellNumbers(monkeys, job[2]);

    let leftRightDelta = Math.abs(left - right);
    let step = Math.floor(0.05 * leftRightDelta);
    step = step < 1 ? 1 : step;

    while (left !== right) {
        monkeys["humn"] += step;
        left = yellNumbers(monkeys, job[0]);
        right = yellNumbers(monkeys, job[2]);

        step = Math.floor(0.05 * (left - right));
        step = step < 1 ? 1 : step;
    }

    console.log(monkeys["humn"]);
}

main();