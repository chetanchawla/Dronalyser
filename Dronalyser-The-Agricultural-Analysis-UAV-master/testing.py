import cv2
import numpy as np
nir = cv2.imread('ir.jpg',0)
#nir2=cv2.applyColorMap(nir, cv2.COLORMAP_JET)
cv2.imshow('image',nir)
cv2.waitKey(0)
cv2.destroyAllWindows()
vis = cv2.imread('nonir.jpg',0)
cv2.imshow('Visibile image',vis)
cv2.waitKey(0)
cv2.destroyAllWindows()
add=nir+vis
#m_color = cv2.applyColorMap(add, cv2.COLORMAP_JET)
cv2.imshow('Added image',add)
cv2.waitKey(0)
cv2.destroyAllWindows()
sub=nir-vis
#subm = cv2.applyColorMap(sub, cv2.COLORMAP_JET)
cv2.imshow('Subtracted image',sub)
cv2.waitKey(0)
cv2.destroyAllWindows()
ndvi=sub/add
one=np.ones(ndvi.shape)
ndvi=ndvi+one
ndvi=ndvi*255/2
cv2.imwrite('temp2.jpg',ndvi)
im_color = cv2.applyColorMap(ndvi.astype(np.uint8), cv2.COLORMAP_JET)
cv2.imshow('NDVI',im_color)
cv2.waitKey(0)
cv2.destroyAllWindows()

