f = open("input.txt")
lines = f.readlines()
h = d = 0

for line in lines:
    i = int(line[-2:-1])
    match line[0]:
        case 'f':
            h += i
            pass
        case 'u':
            d -= i
            pass
        case 'd':
            d += i
            pass

f.close()

print(h, d)
print(h * d)
