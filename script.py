file_path = 'file.txt'
old_word = 'oldWord'
new_word = 'newWord'

with open(file_path, 'r') as file:
    file_content = file.read()

updated_content = file_content.replace(old_word, new_word)

print("updated_content : ",updated_content)

with open(file_path, 'w') as file:
    file.write(updated_content)

print(f"Word '{old_word}' replaced with '{new_word}' in {file_path}")
