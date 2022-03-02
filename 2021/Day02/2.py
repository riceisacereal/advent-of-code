f = open("input.txt")
lines = f.readlines()
h = d = a = 0

for line in lines:
    i = int(line[-2:-1])
    match line[0]:
        case 'f':
            h += i
            d += a * i
            pass
        case 'u':
            a -= i
            pass
        case 'd':
            a += i
            pass

f.close()

print(h, d)
print(h * d)
