const fs = require('fs');

function replaceWord(filePath, oldWord, newWord) {
    console.log("from replaceWord")
    fs.readFile(filePath, 'utf8', (err, data) => {
        if (err) {
            console.error(err);
            return;
        }

        const result = data.replace(new RegExp(oldWord, 'g'), newWord);

        fs.writeFile(filePath, result, 'utf8', (err) => {
            if (err) {
                console.error(err);
                return;
            }
            console.log(`Word '${oldWord}' replaced with '${newWord}' in ${filePath}`);
        });
    });
}

replaceWord('file.txt', 'oldWord', 'newWord');
