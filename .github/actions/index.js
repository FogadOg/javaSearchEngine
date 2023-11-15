const fs = require('fs');

fs.readFile('myText.txt', 'utf8', (err, data) => {
  if (err) {
    console.error(err);
    return;
  }

  const modifiedData = data.replace("color", 'colour');

  fs.writeFile('myText.txt', modifiedData, 'utf8', (err) => {
    if (err) {
      console.error(err);
      return;
    }
    console.log('File updated successfully.');
  });
});
