
let Fork = function () {
    this.state = 0;
    return this;
};

let globalDelay = 0;

Fork.prototype.acquire = function (id, callback) {
    let delayMax = 2;

    let loop = () => {
        if (delayMax >= 2 << 15) delayMax = 2;
        let delay = Math.floor(Math.random() * delayMax);

        setTimeout(() => {
            globalDelay += delay;
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

let Waiter = function (N) {
    this.freePlaces = N - 1;
    return this;
};

Waiter.prototype.getAccessToTable = function (id, callback) {
    let delayMax = 2;

    let loop = () => {
        if (delayMax >= 2 << 15) delayMax = 2;
        let delay = Math.floor(Math.random() * delayMax);

        setTimeout(() => {
            globalDelay += delay;

            if (this.freePlaces === 0) {
                delayMax *= 2;
                console.log("Philosopher " + id + " was stopped by arbiter, he has to wait: " + delay + " ms");

                loop();
            } else {
                console.log("Philosopher " + id + " sat down to the table");
                this.freePlaces--;
                if (callback) callback();
            }
        }, delay);
    };

    loop();
};

Waiter.prototype.leaveTable = function (id) {
    console.log("Philosopher " + id + " rose from the table");
    this.freePlaces++;
};

let Philosopher = function (id, forks, waiter) {
    this.id = id;
    this.forks = forks;
    this.f1 = id % forks.length;
    this.f2 = (id + 1) % forks.length;
    this.waiter = waiter;
    return this;
};

Philosopher.prototype.startConductor = function (count) {
    let forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id,
        waiter = this.waiter;

    let loop = () => {
        if (count > 0) {
            console.log("Philosopher: " + id + " starts cycle: " + count);
            waiter.getAccessToTable(id, getFirstFork);
            count--;
        }
        else {
            console.log("Total time per philosopher(" + id + ") and : " + globalDelay / N / 10 + " ms");
        }
    };

    let think = () => {
        setTimeout(() => {
            console.log("Philosopher " + id + " thinks...");
            loop();
        }, Math.floor(Math.random() * 100));
    };

    let eat = () => {
        setTimeout(() => {
            console.log('\x1b[45m%s\x1b[0m', "Philosopher " + id + " eats...");
            forks[f1].release();
            forks[f2].release();
            waiter.leaveTable(id);
            think();
        }, Math.floor(Math.random() * 100));
    };

    let getFirstFork = () => {
        forks[f1].acquire(id, getSecondFork);
    };

    let getSecondFork = () => {
        forks[f2].acquire(id, eat);
    };

    loop();
};

let N = 5;
let forks = [];
let philosophers = [];

let waiter = new Waiter(N);

let ii;

for (ii = 0; ii < N; ii++) {
    forks.push(new Fork());
}

for (ii = 0; ii < N; ii++) {
    philosophers.push(new Philosopher(ii, forks, waiter));
}

for (ii = 0; ii < N; ii++) {
    philosophers[ii].startConductor(10);
}
