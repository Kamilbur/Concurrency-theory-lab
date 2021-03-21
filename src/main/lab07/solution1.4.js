const waterfall = require('async/waterfall');

async function printAsync(s) {
    const delay = Math.floor((Math.random() * 1000) + 500);
    return new Promise((resolve) => {
        setTimeout(function() {
            console.log(s);
            resolve();
        }, delay);
    })
}

async function sequence() {
    await printAsync("1");
    await printAsync("2");
    await printAsync("3");
}

async function loop(m) {
    let numbers = [];
    for (let ii = 0; ii < m; ii++) {
        numbers.push(ii);
    }
    let tasks = numbers.map((n) => sequence);
    waterfall(tasks,
            function() {
                console.log("done")
            });
}

loop(4);