from PIL import Image


def visualize_chiton(cavern):
    height = len(cavern)
    width = len(cavern[0])

    img = Image.new("RGB", (width, height), color="black")
    pixels = img.load()

    for col in range(height):
        for row in range(width):
            depth = int(25.5 * cavern[row][col])
            pixels[col, row] = (depth, depth, depth)

    img.show()


def make_image(to_visualize):
    height = len(to_visualize)
    width = len(to_visualize[0])
    depth_range = to_visualize[height - 1][width - 1]

    img = Image.new("RGB", (width, height), color="black")
    pixels = img.load()

    for col in range(height):
        for row in range(width):
            depth = 255 - int(255 * (to_visualize[row][col] / depth_range))
            pixels[col, row] = (depth, depth, depth)

    img.show()
    # img.save("pil_red.png")
