from picamera import PiCamera
from time import sleep
camera = PiCamera()

camera.start_preview()
camera.capture('/home/pi/Desktop/depthmap5.jpg')
camera.stop_preview()
