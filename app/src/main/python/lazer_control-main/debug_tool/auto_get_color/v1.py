"""
單張圖片抓特徵點
"""
import cv2


# https://blog.csdn.net/weixin_43843069/article/details/122208702
img = cv2.imread("./image/test.mp4_20230429_012949.513.jpg")

# Initiate FAST object with default values
fast = cv2.FastFeatureDetector_create(
    threshold=16,                   # FAST檢測器的閾值參數
    nonmaxSuppression=True,         # 是否應用非最大值抑制=True
    type=cv2.FAST_FEATURE_DETECTOR_TYPE_9_16,   # FAST檢測器的類型參數
)

# find and draw the keypoints
kp = fast.detect(img, None)         # FAST特徵檢測器對輸入圖像img進行特徵點檢測，檢測到的特徵點被儲存在kp中
img2 = cv2.drawKeypoints(img, kp, None, color=(255, 0, 0))  # 檢測到的特徵點繪製在原始圖像img上，結果保存在img2中
# img: 原始圖像。
# kp: 檢測到的特徵點。
# None: 掩膜，這裡設置為None表示沒有使用掩膜。
# color=(255, 0, 0): 特徵點的顏色，這裡設置為藍色。

# Print all default params
print("Threshold: {}".format(fast.getThreshold()))      # 列印特徵檢測器的閾值參數
print("nonmaxSuppression:{}".format(fast.getNonmaxSuppression()))       # 列印是否使用非極大值抑制的參數
print("neighborhood: {}".format(fast.getType()))        # 測器的鄰域大小參數
print("Total Keypoints with nonmaxSuppression: {}".format(len(kp)))     # 使用非極大值抑制的情況下檢測到的特徵點的總數

cv2.imwrite("fast_true.png", img2)      # 繪製了特徵點的圖像保存img2成PNG，"fast_true.png"

# Disable nonmaxSuppression
fast.setNonmaxSuppression(0)        # 非極大值抑制關閉，將參數設置為0
kp = fast.detect(img, None)         # 使用禁用非極大值抑制的設置來檢測圖像中的特徵點，並將檢測到的特徵點存儲在kp變數中

print("Total Keypoints without nonmaxSuppression: {}".format(len(kp)))      # 列印在禁用非極大值抑制的情況下檢測到的特徵點的總數

img3 = cv2.drawKeypoints(img, kp, None, color=(255, 0, 0))      # 將檢測到的特徵點繪製在圖像上，並將結果保存在img3

cv2.imwrite("fast_false.png", img3)     # 繪製了特徵點的圖像img3保存成PNG，"fast_false.png"
