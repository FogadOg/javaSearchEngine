const fs = require('fs');

fs.readFile('./.github/actions/myText.txt', 'utf8', (err, data) => {
  if (err) {
    console.error(err);
    return;
  }

  const modifiedData = data.replace("color", 'colour');

  fs.writeFile('./.github/actions/myText.txt', modifiedData, 'utf8', (err) => {
    if (err) {
      console.error(err);
      return;
    }
    console.log('File updated successfully.');
  });
});
