class Monkey {
    constructor() {
        this.startingItems = [];
        this.inspectCount = 0;
    }

    addStartingItem(worry) {
        this.startingItems.push(worry);
    }

    setPassTo(divisibleBy, trueMonkey, falseMonkey) {
        this.divisibleBy = divisibleBy;
        this.passTo = function (worry) {
            if (worry % divisibleBy === 0) {
                return trueMonkey;
            } else {
                return falseMonkey;
            }
        }
    }

    setOperation(operation) {
        this.operation = function (old) {
            return eval("old " + operation.substring(10, operation.length));
        }
    }
}

function getMonkeys(lines) {
    let monkeys = [];

    for (let i = 0; i < lines.length; i++) {
        let monkey = new Monkey();

        // monkey items
        monkey.startingItems = lines[++i].split("Starting items: ")[1].split(", ").map(x => parseInt(x));

        // monkey operation
        monkey.setOperation(lines[++i].split("Operation: ")[1]);

        // monkey passing to next monkey
        let divisibleBy = parseInt(lines[++i].split("Test: divisible by ")[1]);
        let trueMonkey = parseInt(lines[++i].split("If true: throw to monkey ")[1]);
        let falseMonkey = parseInt(lines[++i].split("If false: throw to monkey ")[1]);
        monkey.setPassTo(divisibleBy, trueMonkey, falseMonkey);

        monkeys.push(monkey);
        i++;
    }

    return monkeys;
}

module.exports = {
    getMonkeys: getMonkeys
}