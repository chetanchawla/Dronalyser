import cv2
import numpy as np
norm=cv2.imread('nonir.jpg')
#print(norm.shape)
#print(norm[:,:,2].shape)
red=norm[:,:,2]
#print(red.shape)
#print(red)
ir=cv2.imread('ir.jpg')
infra=ir[:,:,2]
#print(infra.shape)
#print(infra)
ndvi=np.zeros(red.shape)
#ndvicol=np.zeros(norm.shape)
for i in range(0,720):
    for j in range(0,1280):
        a=int(infra[i][j])-int(red[i][j])
        b=int(infra[i][j])+int(red[i][j])
        ndvi[i][j]=a/b
        
        #if ndvi[i][j]>0:
            #ndvicol[i][j][1] = ndvi[i][j]
        #elif ndvi[i][j]<0:
            #print('in')
            #ndvicol[i][j][2]=-ndvi[i][j]
        
            #print(ndvi[i][j])
            #print('less it is')
        
one=np.ones(ndvi.shape)
ndvi=ndvi+one
ndvi=ndvi*255/2
#cv2.imwrite('temp2.jpg',ndvi)
im_color = cv2.applyColorMap(ndvi.astype(np.uint8), cv2.COLORMAP_JET)       
#print(ndvicol)
#print(ndvicol.shape)
cv2.namedWindow('image', cv2.WINDOW_NORMAL)
cv2.imshow('image',im_color)
cv2.waitKey(0)
cv2.destroyAllWindows()
