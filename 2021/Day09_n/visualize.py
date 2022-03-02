f = open("input.txt")
terrain = open("terrain.txt", "w", encoding='utf-8')

replace = "丶二口曰田苗画魂葡龖"
# replace = "丶丶丶丶丶丶丶丶丶龖"
line = f.readline()[:-1]

while line:
    newLine = ""
    for i in range(len(line)):
        index = int(line[i])
        newLine += replace[index]

    terrain.write(newLine + '\n')
    line = f.readline()[:-1]

f.close()
terrain.close()
