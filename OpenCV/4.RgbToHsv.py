import cv2
import numpy as np

def rgb_to_hsv(r, g, b): 
    r, g, b = r / 255.0, g / 255.0, b / 255.0
    cmax = max(r, g, b)
    cmin = min(r, g, b)
    diff = cmax-cmin 
  
    if cmax == cmin:  
        h = 0
    elif cmax == r:  
        h = (60 * ((g - b) / diff) + 360) % 360
    elif cmax == g: 
        h = (60 * ((b - r) / diff) + 120) % 360
    elif cmax == b: 
        h = (60 * ((r - g) / diff) + 240) % 360
    if cmax == 0: 
        s = 0
    else: 
        s = (diff / cmax) * 100

    v = cmax * 100
    return h, s, v 

image = cv2.imread('autumn-2371256_960_720.jpg')

a = image[100, 100]
b = np.hsplit(a, 3)
x, y, z = rgb_to_hsv(b[2][0], b[1][0], b[0][0])
print(x, y, z)

cv2.imshow('Image', image)

cv2.waitKey(0)
cv2.destroyAllWindows()