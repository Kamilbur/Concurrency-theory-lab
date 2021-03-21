
let Fork = function (id) {
    this.state = 0;
    this.id = id;
    return this;
};

Fork.prototype.acquire = function (id, callback) {
    let delayMax = 2;

    let loop = () => {

        if (delayMax >= 2 << 15) delayMax = 2;
        let delay = Math.floor(Math.random() * delayMax) + 1;
        setTimeout(() => {
            if (this.state === 1) {
                delayMax *= 2;
                console.log("Waiting: " + delay + " ms");
                loop();
            } else {
                console.log("Philosopher " + id + " acquired fork " + this.id);
                this.state = 1;
                if (callback) callback();
            }
        }, delay)
    };

    loop();
};

Fork.prototype.release = function () {
    this.state = 0;
};

let Philosopher = function (id, forks) {
    this.id = id;
    this.forks = forks;
    this.f1 = id % forks.length;
    this.f2 = (id + 1) % forks.length;
    return this;
};


Philosopher.prototype.startNaive = function (count) {
    let forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;

    let loop = () => {
        if (count > 0) {
            console.log("Philosopher: " + id + " starts cycle: " + count);
            forks[f1].acquire(id, eatWithSecondFork);
            --count;
        }
    };

    let eatWithSecondFork = () => {
        forks[f2].acquire(id, eat)
    };

    let think = () => {
        setTimeout(() => {
                console.log("Philosopher " + id + " thinks...");
                loop();
            },
            Math.floor(Math.random * 100)
        )
    };

    let eat = () => {
        setTimeout(() => {
                console.log('\x1b[45m%s\x1b[0m', "Philosopher " + id + " eats...");
                forks[f1].release();
                forks[f2].release();
                think();
            },
            Math.floor(Math.random * 100)
        )
    };

    loop();
};



let N = 5;
let forks = [];
let philosophers = [];

let ii;
for (ii = 0; ii < N; ii++) {
    forks.push(new Fork(ii));
}

for (ii = 0; ii < N; ii++) {
    philosophers.push(new Philosopher(ii, forks));
}

for (ii = 0; ii < N; ii++) {
    philosophers[ii].startNaive(10);
}