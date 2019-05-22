import cv2
from PIL import Image
import numpy as np
import matplotlib.image as mpimg
import matplotlib.pyplot as plt

import numpy as numpy
from PIL import Image

import gc

def ndvi(img,
         imageOutPath,
         vmin = None,
         vmax = None,
         dynamic_range = True,
         show_colorbar  = True,
         colorbar_labelsize = 6,
         show_histogram = False,
         dpi = 600.0 #needs to be floating point
        ):
    if isinstance(img,str): #treat as a filename
        img = Image.open(img)
        
    imgR, imgB, imgG = img.split() #get channels
    #compute the NDVI
    arrR = numpy.asarray(imgR).astype('float64')
    arrG = numpy.asarray(imgG).astype('float64') #this channel is ignored
    arrB = numpy.asarray(imgB).astype('float64')
    num   = (arrR - arrB)
    denom = (arrR + arrB)
    arr_ndvi = num/denom

    #FIXME something is horribly wrong?
    if arr_ndvi.max() < 0.0:
        return
    
    #determine the range of NDVI values to colormap
    if dynamic_range:
        #the values of vmin and vmax are now interpreted as the quantile fractions
        if vmin is None:
            vmin = 0.0
        if vmax is None:
            vmax = 1.0
        h, bins = numpy.histogram(arr_ndvi)
        q = h.cumsum().astype("float64")
        q /= q[-1]
        #compute the values for the dynamic quantile boundaries
        try:
            vmin = bins[numpy.nonzero(q >= vmin)[0][0]]
        except IndexError: #thrown when no match is found
            vmin = -1.0    #force to minimum NDVI value
        try:
            vmax = bins[numpy.nonzero(q >= vmax)[0][0]]
        except IndexError: #thrown when no match is found
            vmax =  1.0    #force to maximum NDVI value
        
    else:
        #the values of vmin and vmax are now interpreted as NDVI values
        if vmin is None:
            vmin = -1.0
        if vmax is None:
            vmax = 1.0
            
    #create the matplotlib figure
    img_w,img_h=img.size

    fig_w=img_w/dpi
    fig_h=img_h/dpi
    fig=plt.figure(figsize=(fig_w,fig_h),dpi=dpi)
    fig.set_frameon(False)

    ax_rect = [0.0, #left
               0.0, #bottom
               1.0, #width
               1.0] #height
    ax = fig.add_axes(ax_rect)
    ax.yaxis.set_ticklabels([])
    ax.xaxis.set_ticklabels([])   
    ax.set_axis_off()
    ax.axes.get_yaxis().set_visible(False)
    ax.patch.set_alpha(0.0)

    axes_img = ax.imshow(arr_ndvi,
                         cmap=plt.cm.spectral, 
                         vmin = vmin,
                         vmax = vmax,
                         aspect = 'equal',
                         interpolation="nearest"
                        )
                        
    if show_colorbar:
        #make an axis for colorbar
        cax = fig.add_axes([0.8,0.05,0.05,0.85]) #left, bottom, width, height
        cbar = fig.colorbar(axes_img, cax=cax)  #this resizes the axis
        cbar.ax.tick_params(labelsize = colorbar_labelsize) #this changes the font size on the axis
        #cbar.ax.yaxis.set_ticks_position('left')
        #color of the colorbar text
        #cbytick_obj = plt.getp(cbar.ax.axes, 'yticklabels')                #tricky
        #plt.setp(cbytick_obj, color='r')
    
    #optional debugging data
    if show_histogram:
        normed = False
        #plot the Red histogram
        x = arrR.ravel()
        a = plt.axes([.05,.80,.18,.18], axisbg='y')
        bins=numpy.arange(0,255,8)
        n, bins, patches = plt.hist(x, bins, normed = normed, linewidth=.2)
        plt.setp(patches, 'facecolor', 'r', 'alpha', 0.75)
        plt.setp(a, xticks=[0,120,255], yticks=[])
        plt.setp(a, xticks=[], yticks=[])
        plt.xticks(fontsize=2)

        #plot the Green histogram
        x = arrG.ravel()
        a = plt.axes([.05,.55,.18,.18], axisbg='y')
        bins=numpy.arange(0,255,8)
        n, bins, patches = plt.hist(x, bins, normed = normed, linewidth=.2)
        plt.setp(patches, 'facecolor', 'g', 'alpha', 0.75)
        plt.setp(a, xticks=[0,120,255], yticks=[])
        plt.setp(a, xticks=[], yticks=[])
        plt.xticks(fontsize=2)

        #plot the Blue histogram
        x = arrB.ravel()
        a = plt.axes([.05,.3,.18,.18], axisbg='y')
        bins = numpy.arange(0,255,8)
        n, bins, patches = plt.hist(x, bins, normed = normed, linewidth=.2)
        plt.setp(patches, 'facecolor', 'b', 'alpha', 0.75)
        plt.setp(a, xticks=[0,120,255], yticks=[])
        plt.setp(a ,xticks=[], yticks=[])
        plt.xticks(fontsize=2)

        #plot the NDVI histogram
        x = arr_ndvi.ravel()
        a = plt.axes([.05,.05,.18,.18], axisbg='y')
        bins = numpy.arange(-1,1,.01)
        n, bins, patches = plt.hist(x, bins, normed = normed, linewidth=.2)
        plt.setp(patches, 'facecolor', 'w', 'alpha', 0.75)
        plt.setp(a, xticks=[-1,0,1], yticks=[])
        plt.setp(a, xticks=[], yticks=[])
        plt.xticks(fontsize=2)

    fig.savefig(imageOutPath,
                dpi=dpi,
                bbox_inches='tight',
                pad_inches=0.0, 
                )

    #plt.show()  #show the plot after saving
    fig.clf()
    plt.close()
    gc.collect()
#nir = Image.open('ir.jpg')
#nir_b,nir_g,nir_r=nir.split()
#nir_red=np.asarray(nir_r).astype('float64')
ir=cv2.imread('ir.jpg')
nir_red=ir[:,:,2]
print(nir_red.shape)
#nir2=cv2.applyColorMap(nir, cv2.COLORMAP_JET)
#print(nir_red)
cv2.imshow('image',nir_red)
cv2.waitKey(0)
cv2.destroyAllWindows()

vis = cv2.imread('nonir.jpg')
vis_red=vis[:,:,2]
print(vis_red.shape)
cv2.imshow('image',vis_red)
cv2.waitKey(0)
cv2.destroyAllWindows()

vis[:,:,2]=nir_red-vis_red
print(vis[:,:,2])
cv2.imwrite('infrablue.jpg',vis)
ndvi('infrablue.jpg','test_ndvi.png',show_histogram = True,)
