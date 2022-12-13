function compareInteger(left, right) {
    return right - left;
}

/*
   order  length  return
     0       >      -1
     0       <      1 <-
     0       =      0
     1       >      1
     1       <      1
     1       =      1
*/
function compare(left, right) {
    if (Array.isArray(left) && Array.isArray(right)) {
        // Both are arrays
        // Order:
        // -1 -> not in the right order
        // 0  -> move on
        // 1  -> right order

        let order = 0;
        let i = 0;
        while (i < Math.min(left.length, right.length) && order === 0) {
            order = compare(left[i], right[i]);
            i++;
        }

        if (order === 0 && left.length > right.length) {
            return -1;
        } if (order === 0 && left.length < right.length) {
            return 1;
        } else {
            return order;
        }
    } else if (Number.isInteger(left) && Number.isInteger(right)) {
        // Both numbers are integers
        return compareInteger(left, right);
    } else {
        // One is an integer
        if (Number.isInteger(left)) {
            return compare([left], right);
        } else {
            return compare(left, [right]);
        }
    }
}

module.exports = {
    compare: compare
}