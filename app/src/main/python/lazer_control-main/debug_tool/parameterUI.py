# -*- coding: utf-8 -*-

# Form implementation generated from reading ui file '.\debug_tool\parameterUI.ui'
#
# Created by: PyQt5 UI code generator 5.15.9
#
# WARNING: Any manual changes made to this file will be lost when pyuic5 is
# run again.  Do not edit this file unless you know what you are doing.


from PyQt5 import QtCore, QtGui, QtWidgets


class Ui_MainWindow(object):
    def setupUi(self, MainWindow):
        MainWindow.setObjectName("MainWindow")
        MainWindow.setEnabled(True)
        MainWindow.resize(1667, 772)
        MainWindow.setMaximumSize(QtCore.QSize(1667, 1000))
        font = QtGui.QFont()
        font.setKerning(True)
        MainWindow.setFont(font)
        self.centralwidget = QtWidgets.QWidget(MainWindow)
        self.centralwidget.setEnabled(True)
        self.centralwidget.setMinimumSize(QtCore.QSize(1667, 723))
        self.centralwidget.setMaximumSize(QtCore.QSize(1667, 723))
        self.centralwidget.setObjectName("centralwidget")
        self.filter_label = QtWidgets.QLabel(self.centralwidget)
        self.filter_label.setGeometry(QtCore.QRect(850, 270, 800, 450))
        self.filter_label.setStyleSheet("background-color: #444654;")
        self.filter_label.setText("")
        self.filter_label.setObjectName("filter_label")
        self.horizontalLayoutWidget = QtWidgets.QWidget(self.centralwidget)
        self.horizontalLayoutWidget.setGeometry(QtCore.QRect(200, 70, 1271, 161))
        self.horizontalLayoutWidget.setObjectName("horizontalLayoutWidget")
        self.horizontalLayout = QtWidgets.QHBoxLayout(self.horizontalLayoutWidget)
        self.horizontalLayout.setContentsMargins(0, 0, 0, 0)
        self.horizontalLayout.setSpacing(30)
        self.horizontalLayout.setObjectName("horizontalLayout")
        self.verticalLayout_5 = QtWidgets.QVBoxLayout()
        self.verticalLayout_5.setObjectName("verticalLayout_5")
        self.maxH = QtWidgets.QSlider(self.horizontalLayoutWidget)
        self.maxH.setOrientation(QtCore.Qt.Horizontal)
        self.maxH.setObjectName("maxH")
        self.verticalLayout_5.addWidget(self.maxH)
        self.minH = QtWidgets.QSlider(self.horizontalLayoutWidget)
        self.minH.setOrientation(QtCore.Qt.Horizontal)
        self.minH.setObjectName("minH")
        self.verticalLayout_5.addWidget(self.minH)
        self.horizontalLayout.addLayout(self.verticalLayout_5)
        self.verticalLayout_4 = QtWidgets.QVBoxLayout()
        self.verticalLayout_4.setObjectName("verticalLayout_4")
        self.maxS = QtWidgets.QSlider(self.horizontalLayoutWidget)
        self.maxS.setOrientation(QtCore.Qt.Horizontal)
        self.maxS.setObjectName("maxS")
        self.verticalLayout_4.addWidget(self.maxS)
        self.minS = QtWidgets.QSlider(self.horizontalLayoutWidget)
        self.minS.setOrientation(QtCore.Qt.Horizontal)
        self.minS.setObjectName("minS")
        self.verticalLayout_4.addWidget(self.minS)
        self.horizontalLayout.addLayout(self.verticalLayout_4)
        self.verticalLayout_3 = QtWidgets.QVBoxLayout()
        self.verticalLayout_3.setObjectName("verticalLayout_3")
        self.maxV = QtWidgets.QSlider(self.horizontalLayoutWidget)
        self.maxV.setOrientation(QtCore.Qt.Horizontal)
        self.maxV.setObjectName("maxV")
        self.verticalLayout_3.addWidget(self.maxV)
        self.minV = QtWidgets.QSlider(self.horizontalLayoutWidget)
        self.minV.setOrientation(QtCore.Qt.Horizontal)
        self.minV.setObjectName("minV")
        self.verticalLayout_3.addWidget(self.minV)
        self.horizontalLayout.addLayout(self.verticalLayout_3)
        self.verticalLayout_6 = QtWidgets.QVBoxLayout()
        self.verticalLayout_6.setObjectName("verticalLayout_6")
        self.maxBrigtness = QtWidgets.QSlider(self.horizontalLayoutWidget)
        self.maxBrigtness.setOrientation(QtCore.Qt.Horizontal)
        self.maxBrigtness.setObjectName("maxBrigtness")
        self.verticalLayout_6.addWidget(self.maxBrigtness)
        self.minBrigtness = QtWidgets.QSlider(self.horizontalLayoutWidget)
        self.minBrigtness.setOrientation(QtCore.Qt.Horizontal)
        self.minBrigtness.setObjectName("minBrigtness")
        self.verticalLayout_6.addWidget(self.minBrigtness)
        self.horizontalLayout.addLayout(self.verticalLayout_6)
        self.label_2 = QtWidgets.QLabel(self.centralwidget)
        self.label_2.setGeometry(QtCore.QRect(120, 150, 61, 61))
        font = QtGui.QFont()
        font.setFamily("微軟正黑體")
        font.setPointSize(16)
        self.label_2.setFont(font)
        self.label_2.setAlignment(QtCore.Qt.AlignCenter)
        self.label_2.setObjectName("label_2")
        self.label_3 = QtWidgets.QLabel(self.centralwidget)
        self.label_3.setGeometry(QtCore.QRect(120, 90, 61, 61))
        font = QtGui.QFont()
        font.setFamily("微軟正黑體")
        font.setPointSize(16)
        self.label_3.setFont(font)
        self.label_3.setAlignment(QtCore.Qt.AlignCenter)
        self.label_3.setObjectName("label_3")
        self.label_4 = QtWidgets.QLabel(self.centralwidget)
        self.label_4.setGeometry(QtCore.QRect(330, 0, 61, 61))
        font = QtGui.QFont()
        font.setFamily("微軟正黑體")
        font.setPointSize(16)
        self.label_4.setFont(font)
        self.label_4.setAlignment(QtCore.Qt.AlignCenter)
        self.label_4.setObjectName("label_4")
        self.label_5 = QtWidgets.QLabel(self.centralwidget)
        self.label_5.setGeometry(QtCore.QRect(650, 0, 61, 61))
        font = QtGui.QFont()
        font.setFamily("微軟正黑體")
        font.setPointSize(16)
        self.label_5.setFont(font)
        self.label_5.setAlignment(QtCore.Qt.AlignCenter)
        self.label_5.setObjectName("label_5")
        self.label_7 = QtWidgets.QLabel(self.centralwidget)
        self.label_7.setGeometry(QtCore.QRect(1260, 0, 151, 61))
        font = QtGui.QFont()
        font.setFamily("微軟正黑體")
        font.setPointSize(16)
        self.label_7.setFont(font)
        self.label_7.setAlignment(QtCore.Qt.AlignCenter)
        self.label_7.setObjectName("label_7")
        self.label_6 = QtWidgets.QLabel(self.centralwidget)
        self.label_6.setGeometry(QtCore.QRect(960, 0, 61, 61))
        font = QtGui.QFont()
        font.setFamily("微軟正黑體")
        font.setPointSize(16)
        self.label_6.setFont(font)
        self.label_6.setAlignment(QtCore.Qt.AlignCenter)
        self.label_6.setObjectName("label_6")
        self.original_label = QtWidgets.QLabel(self.centralwidget)
        self.original_label.setGeometry(QtCore.QRect(20, 270, 800, 450))
        self.original_label.setStyleSheet("background-color: #444654;")
        self.original_label.setText("")
        self.original_label.setObjectName("original_label")
        MainWindow.setCentralWidget(self.centralwidget)
        self.menubar = QtWidgets.QMenuBar(MainWindow)
        self.menubar.setGeometry(QtCore.QRect(0, 0, 1667, 25))
        self.menubar.setObjectName("menubar")
        MainWindow.setMenuBar(self.menubar)
        self.statusbar = QtWidgets.QStatusBar(MainWindow)
        self.statusbar.setObjectName("statusbar")
        MainWindow.setStatusBar(self.statusbar)

        self.retranslateUi(MainWindow)
        QtCore.QMetaObject.connectSlotsByName(MainWindow)

    def retranslateUi(self, MainWindow):
        _translate = QtCore.QCoreApplication.translate
        MainWindow.setWindowTitle(_translate("MainWindow", "MainWindow"))
        self.label_2.setText(_translate("MainWindow", "MIN"))
        self.label_3.setText(_translate("MainWindow", "MAX"))
        self.label_4.setText(_translate("MainWindow", "H"))
        self.label_5.setText(_translate("MainWindow", "S"))
        self.label_7.setText(_translate("MainWindow", "Brigtness"))
        self.label_6.setText(_translate("MainWindow", "V"))


if __name__ == "__main__":
    import sys
    app = QtWidgets.QApplication(sys.argv)
    MainWindow = QtWidgets.QMainWindow()
    ui = Ui_MainWindow()
    ui.setupUi(MainWindow)
    MainWindow.show()
    sys.exit(app.exec_())
