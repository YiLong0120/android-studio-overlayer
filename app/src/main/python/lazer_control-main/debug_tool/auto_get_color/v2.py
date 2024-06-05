"""
追蹤算法
"""
import cv2

# 打開影片文件
cap = cv2.VideoCapture("./video/test2.mkv") 

# 選擇一種追蹤算法，例如 MOSSE
tracker = cv2.legacy.TrackerMOSSE_create()

# 讀取第一幀影片
# 從影片中讀取第一幀（frame），ret 是一個布林值，表示是否成功讀取了影片的一幀，而 frame 則是讀取到的影像
ret, frame = cap.read()    

# 選擇初始 ROI（雷射筆光點的位置）
x, y, w, h = cv2.selectROI(frame)   # 讓使用者可以使用鼠標框選感興趣的區域，然後按下 Enter 鍵確定選擇
roi = (x, y, w, h)         # xy座標，wh寬高

# 初始化追蹤器，並將其應用於影片的第一幀 frame 上的初始 ROI
tracker.init(frame, roi)

while True:     # 每一幀
    # 讀取下一幀的影片，並將其存儲在變數 frame 中。如果沒有成功讀取到影片，則退出迴圈
    ret, frame = cap.read()
    if not ret:
        break

    # 更新追蹤器，並獲取新的 ROI
    ret, roi = tracker.update(frame)

    # ret成功，繪製物件位置，綠色的矩形
    if ret:
        p1 = (int(roi[0]), int(roi[1]))
        p2 = (int(roi[0] + roi[2]), int(roi[1] + roi[3]))
        cv2.rectangle(frame, p1, p2, (0, 255, 0), 2)

    cv2.imshow("Video", frame)

    if cv2.waitKey(1) & 0xFF == ord("q"):
        break

cap.release()
cv2.destroyAllWindows()
