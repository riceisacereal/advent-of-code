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

module.exports = {
    yellNumbers: yellNumbers,
    parse: parse
}