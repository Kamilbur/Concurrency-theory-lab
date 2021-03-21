const fs = require('fs');
const walkdir = require('walkdir');
const waterfall = require('async/waterfall');
const parallel = require('async/parallel');

let global_count = 0;
const directory_path = './PAM08' // the source of data http://home.agh.edu.pl/~balis/dydakt/tw/lab7/pam08.zip


async function count_lines(path, cb) {
    let line_num = 0;

    if (fs.lstatSync(path).isFile()) {
        fs.createReadStream(path).on('data', function (chunk) {
            line_num = chunk.toString('utf8')
                .split(/\r\n|[\n\r\u0085\u2028\u2029]/g)
                .length - 1;
            global_count += line_num;
        }).on('end', function () {
            console.log(path, line_num);
            cb();
        }).on('error', function (err) {
            console.error(err);
            cb();
        });
    }
    else {
        cb();
    }
}

function count_lines_async() {
    const start = new Date();
    parallel(walkdir.sync(directory_path).map(file => (callback) => count_lines(file, callback)), () => {
        console.log(global_count);
        console.log('Execution time: %dms', new Date() - start);
    });
}


function count_lines_sync() {
    const start = new Date();
    waterfall(walkdir.sync(directory_path).map(file => (callback) => count_lines(file, callback)), () => {
        console.log(global_count);
        console.log('Execution time: %dms', new Date() - start);
    });
}

console.log(walkdir(directory_path));
count_lines_async();
//count_lines_sync();