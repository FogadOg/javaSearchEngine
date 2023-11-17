const { execSync } = require('child_process');
const fs = require('fs');

// Get inputs from command line arguments or a configuration file
const file = process.argv[2]; // file you want to change
const wordToReplace = process.argv[3]; // word you want to replace
const newWord = process.argv[4]; // word you want to put in
const username = process.argv[5]; // your GitHub username
const email = process.argv[6]; // your GitHub email

// Function to replace a word in a file
function replaceWordInFile(filePath, oldWord, newWord) {
    try {
        let fileContent = fs.readFileSync(filePath, 'utf8');
        fileContent = fileContent.replace(new RegExp(oldWord, 'g'), newWord);
        fs.writeFileSync(filePath, fileContent, 'utf8');
        return true;
    } catch (error) {
        console.error('Error replacing word:', error);
        return false;
    }
}

// Replace the word in the file
if (replaceWordInFile(file, wordToReplace, newWord)) {
    try {
        // Stage changes and commit
        execSync(`git add ${file}`);
        execSync(`git commit -m "Replace ${wordToReplace} with ${newWord} in ${file}"`);

        // Set user email and username
        execSync(`git config --global user.email "${email}"`);
        execSync(`git config --global user.name "${username}"`);

        // Push changes
        execSync('git push');
        console.log('Word replaced and changes pushed to GitHub.');
    } catch (error) {
        console.error('Error pushing changes to GitHub:', error);
    }
}
