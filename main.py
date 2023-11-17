import sys
import ruamel.yaml


oldWord=sys.argv[1]
newWord=sys.argv[2]
filePath=sys.argv[3]


def overwriteFile(oldWords,newWord,filePath):

    oldWords=oldWords.split(",")

    read=open(filePath,"r")
    text=read.read()

    replaceAction = ' '.join([newWord if idx in oldWords else idx for idx in text.split()])
    read.close()

    write=open(filePath,"w")

    writeToFile=yaml.dump(replaceAction)

    write.write(writeToFile)
    write.close()


overwriteFile(oldWord,newWord,filePath)