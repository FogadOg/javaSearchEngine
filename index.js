const fs = require('fs');

// Get the word to replace and the replacement word from command line arguments
const wordToReplace= "repository"
const replacementWord = "not"

// Path to your YAML file
const filePath = 'config.yml'; // Update this with your YAML file path

fs.readFile(filePath, 'utf8', (err, data) => {
    if (err) {
        console.error(err);
        return;
    }

    // Replace the word in the YAML content
    const updatedData = data.replace(new RegExp(wordToReplace, 'g'), replacementWord);

    // Write the updated content back to the YAML file
    fs.writeFile(filePath, updatedData, 'utf8', (err) => {
        if (err) {
            console.error(err);
            return;
        }
        console.log('Word replaced successfully!');
    });
});
