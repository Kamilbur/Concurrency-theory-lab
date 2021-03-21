
let Fork = function (id) {
    this.state = 0;
    this.id = id;
    return this;
};

let globalDelay = 0;
let forkUpdate = 0;

Fork.prototype.acquire = function () {
    this.state = 1;
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

Philosopher.prototype.getBothForks = function(callback) {
    let delayMax = 2;

    let f1 = this.f1,
        f2 = this.f2;

    let loop = () => {
        if (delayMax >= 2 << 15) delayMax = 2;
        let delay = Math.floor(Math.random() * delayMax);

        setTimeout(() => {

            globalDelay += delay;

            let goWait = false;

            if (forkUpdate === 1) {
                goWait = true;
            }
            else {
                forkUpdate = 1;
                if (forks[f1].state === 0 && forks[f2].state === 0) {
                    forks[f1].acquire();
                    forks[f2].acquire();
                    console.log("Philosopher " + this.id + " acquired both forks");
                    forkUpdate = 0;
                    if (callback) callback();
                }
                else {
                    goWait = true;
                    forkUpdate = 0;
                }
            }
            if (goWait) {
                delayMax *= 2;
                console.log("Waiting: " + delay + " ms");
                loop();
            }
        }, delay);
    };

    loop();
};

Philosopher.prototype.startBothForks = function (count) {
    let forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;

    let loop = () => {
        if (count > 0) {
            console.log("Philosopher: " + id + " starts cycle: " + count);

            this.getBothForks(eat);
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
            think();
        }, Math.floor(Math.random() * 100));
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
    philosophers.push(new Philosopher(ii, forks, forkUpdate));
}

for (ii = 0; ii < N; ii++) {
    philosophers[ii].startBothForks(10);
}