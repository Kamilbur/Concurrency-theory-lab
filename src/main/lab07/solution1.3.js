function printAsync(s, cb) {
    const delay = Math.floor((Math.random() * 1000) + 500);
    setTimeout(function () {
        console.log(s);
        if (cb) cb();
    }, delay);
}

function task(n) {
    return new Promise((resolve, reject) => {
        printAsync(n, function () {
            resolve(n);
        });
    });
}

/*
** Zadanie:
** Napisz funkcje loop(m), ktora powoduje wykonanie powyzszej
** sekwencji zadan m razy.
**
*/

function sequence() {
    return task(1).then((n) => {
        console.log('task', n, 'done');
        return task(2);
    }).then((n) => {
        console.log('task', n, 'done');
        return task(3);
    }).then((n) => {
        console.log('task', n, 'done');
    });
}

function loop(m) {
    if (m === 0) return;
    let current_task = sequence();
    for (let ii = 1; ii < m; ii++) {
        current_task = current_task.then(() => {
            return sequence();
        });
    }
    current_task.then(() => {console.log("done");});
}

loop(4);