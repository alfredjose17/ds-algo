import cv2
import numpy as np

image = cv2.imread("autumn-2371256_960_720.jpg")

blurred_image = cv2.GaussianBlur(image, (5, 5), 1)
hsv = cv2.cvtColor(blurred_image, cv2.COLOR_BGR2HSV)
    
lower_color = np.array([10, 86, 30])
upper_color = np.array([20, 255, 255])
mask = cv2.inRange(hsv, lower_color, upper_color)
    
contours, _ = cv2.findContours(mask,cv2.RETR_TREE,cv2.CHAIN_APPROX_SIMPLE)
    
for contour in contours:
    cv2.drawContours(image, contour, -1, (0, 255, 0), 3)
    
cv2.imshow("Image", image)
cv2.imshow("Mask", mask)

cv2.waitKey(0)
cv2.destroyAllWindows()
