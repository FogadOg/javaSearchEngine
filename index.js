const fs = require('fs');

const filePath = 'file.txt';
const oldWord = 'oldWord';
const newWord = 'newWord';

fs.readFile(filePath, 'utf8', (err, data) => {
    if (err) {
        console.error('Error reading the file:', err);
        return;
    }

    const result = data.replace(new RegExp(oldWord, 'g'), newWord);

    fs.writeFile(filePath, result, 'utf8', (err) => {
        if (err) {
            console.error('Error writing to the file:', err);
            return;
        }
        console.log(`Word '${oldWord}' replaced with '${newWord}' in ${filePath}`);
    });
});
