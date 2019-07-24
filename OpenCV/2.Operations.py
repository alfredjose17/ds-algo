import cv2
import numpy as np

image = cv2.imread("red_panda.jpg")
print(image.shape)
rows, cols, ch = image.shape

roi = image[100: 280, 150: 320]

print(image[175, 200])
image[175, 200] = (255, 0, 0)
image[175, 201] = (255, 0, 0)
image[175, 202] = (255, 0, 0)
image[175, 203] = (255, 0, 0)
image[175, 204] = (255, 0, 0)

cv2.imshow("Panda", image)
cv2.imshow("Roi", roi)
cv2.waitKey(0)
cv2.destroyAllWindows()