import sys

oldWord=sys.argv[1]
newWord=sys.argv[2]


def overwriteFile(oldWords,newWord):

    oldWords=oldWords.split(",")

    read=open("config.yml","r")
    text=read.read()

    replaceAction = ' '.join([newWord if idx in oldWords else idx for idx in text.split()])
    read.close()

    write=open("config.yml","w")
    write.write(replaceAction)
    write.close()


overwriteFile(oldWord,newWord)