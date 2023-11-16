file_path = 'file.txt'
old_word = 'oldWord'
new_word = 'newWord'

with open(file_path, 'r') as file:
    file_content = file.read()

updated_content = file_content.replace(old_word, new_word)

print("updated_content : ",updated_content)

with open("file2.txt", 'w') as file:
    file.write("This is a sample text that will be written to the file.\n")
    file.write("Writing to a text file in Python is straightforward!\n")
    # You can write multiple lines by calling file.write() multiple times


print(f"Word '{old_word}' replaced with '{new_word}' in {file_path}")
