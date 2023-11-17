import fileinput
import sys

file_path = 'file.txt'
old_word = 'oldWord'
new_word = 'newWord'

with fileinput.FileInput(file_path, inplace=True, backup='.bak') as file:
    for line in file:
        print(line.replace(old_word, new_word), end='')

print(f"Word '{old_word}' replaced with '{new_word}' in {file_path}")