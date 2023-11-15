

const fs = require('fs');

function replaceTextInFile(filePath, oldText, newText) {
  fs.readFile(filePath, 'utf8', (err, data) => {
    if (err) {
      console.error(err);
      return;
    }

    const modifiedData = data.replace(new RegExp(oldText, 'g'), newText);

    fs.writeFile(filePath, modifiedData, 'utf8', (err) => {
      if (err) {
        console.error(err);
        return;
      }
      console.log(`Text '${oldText}' replaced with '${newText}' in file '${filePath}'.`);
    });
  });
}

module.exports = replaceTextInFile;







const replaceTextInFile = require('./replaceText');

const filePath = './.github/actions/myText.txt'; // Replace this with your file path
const oldText = 'color';
const newText = 'colour';

replaceTextInFile(filePath, oldText, newText);