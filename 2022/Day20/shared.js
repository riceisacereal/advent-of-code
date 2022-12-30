class Component {
    constructor(previous, number) {
        this.left = previous;
        this.number = number;
    }

    setMovement(move) {
        this.move = move;
    }

    setLeft(previous) {
        this.left = previous;
    }

    setRight(next) {
        this.right = next;
    }
}

function printList(head, size) {
    let string = "";
    for (let i = 0; i < size; i++) {
        string += head.number + " ";
        head = head.right;
    }
    console.log(string);
}

function makeNumberList(lines, key) {
    let left = null;
    let numberList = [];
    for (let line of lines) {
        let n = key * parseInt(line);
        let component = new Component(left, n);
        component.setMovement(n % (lines.length - 1));

        numberList.push(component);
        left = component;
    }
    numberList[0].setLeft(numberList[numberList.length - 1]);

    for (let i = 0; i < numberList.length - 1; i++) {
        numberList[i].setRight(numberList[i + 1]);
    }
    numberList[numberList.length - 1].setRight(numberList[0]);
    return numberList;
}

function decrypt(numberList) {
    for (let component of numberList) {
        let n = component.move;
        if (n === 0) continue;

        component.left.setRight(component.right);
        component.right.setLeft(component.left);

        let newRight, newLeft;
        if (n < 0) {
            n = -n;
            newRight = component;
            for (let i = 0; i < n; i++) {
                newRight = newRight.left;
            }
            newLeft = newRight.left;
        } else {
            newLeft = component;
            for (let i = 0; i < n; i++) {
                newLeft = newLeft.right;
            }
            newRight = newLeft.right;
        }

        component.setLeft(newLeft);
        component.setRight(newRight);
        newLeft.setRight(component);
        newRight.setLeft(component);
    }
}

function sumCoordinates(numberList) {
    let start;
    for (let n of numberList) {
        if (n.number === 0) {
            start = n;
            break;
        }
    }

    let sum = 0;
    for (let i = 0; i < 3; i++) {
        for (let j = 0; j < 1000; j++) {
            start = start.right;
        }
        sum += start.number;
    }
    return sum;
}

module.exports = {
    Component: Component,
    makeNumberList: makeNumberList,
    decrypt: decrypt,
    sumCoordinates: sumCoordinates
}