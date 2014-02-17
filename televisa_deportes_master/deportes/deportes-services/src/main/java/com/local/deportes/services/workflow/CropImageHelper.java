package com.local.deportes.services.workflow;

import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.Rendition;
import com.day.image.Layer;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Note Management Data Access
 *
 * Helper that contain all the logical for crop the images and create all the renditions
 *
 * Changes History:
 *
 *         2013-01-25 Initial Development
 *
 * @author lsztul@xumak.com
 * @version 1.0
 */
public class CropImageHelper {

    /**
     * Create the rendition of the Asset making the resize to the new image.
     * @param asset
     * @param layer
     * @param widthLayer
     * @param heightLayer
     * @param typeImage
     * @param isGray
     * @return
     * @throws java.io.IOException
     */
    public static Rendition saveRenditionWithResize(Asset asset, Layer layer, int widthLayer, int heightLayer, String typeImage, boolean isGray) throws IOException {
        Layer newLayer = new Layer(layer.getImage());
        File tmpFile = File.createTempFile("thumbnail", ".tmp");
        java.io.OutputStream out = FileUtils.openOutputStream(tmpFile);
        newLayer.resize(widthLayer, heightLayer);
        if(isGray){
            newLayer.grayscale();
        }
        newLayer.write("image/" + typeImage, 0.80000000000000004D, out);
        IOUtils.closeQuietly(out);
        java.io.InputStream in = FileUtils.openInputStream(tmpFile);
        String name = widthLayer + "." + heightLayer + "." + typeImage;
        if(isGray){
            name = "cq5dam.thumbnail.gray." + widthLayer + "." + heightLayer + "." + typeImage;
        }else{
            name = "cq5dam.thumbnail." + widthLayer + "." + heightLayer + "." + typeImage;
        }
        Rendition rendition = asset.addRendition(name, in, "image/" + typeImage);
        IOUtils.closeQuietly(in);

		// Delete temporal file
		tmpFile.delete();
        return rendition;
    }

    /**
     * Create the rendition of the Asset making the crop to the new image
     * @param asset
     * @param layer
     * @param x
     * @param y
     * @param widthLayer
     * @param heightLayer
     * @param typeImage
     * @param isGray
     * @return
     * @throws java.io.IOException
     */
    public static Rendition saveRenditionWithCrop(Asset asset, Layer layer, int x, int y, int widthLayer, int heightLayer, String typeImage, boolean isGray) throws IOException {
        Layer newLayer = new Layer(layer.getImage());
        File tmpFile = File.createTempFile("thumbnail", ".tmp");
        java.io.OutputStream out = FileUtils.openOutputStream(tmpFile);
        newLayer.crop(new Rectangle(x, y, widthLayer, heightLayer));
        if(isGray){
            newLayer.grayscale();
        }
        newLayer.write("image/" + typeImage, 0.80000000000000004D, out);
        IOUtils.closeQuietly(out);
        java.io.InputStream in = FileUtils.openInputStream(tmpFile);
        String name = widthLayer + "." + heightLayer + "." + typeImage;
        if(isGray){
            name = "cq5dam.thumbnail.gray." + widthLayer + "." + heightLayer + "." + typeImage;
        }else{
            name = "cq5dam.thumbnail." + widthLayer + "." + heightLayer + "." + typeImage;
        }
        Rendition rendition = asset.addRendition(name, in, "image/" + typeImage);
        IOUtils.closeQuietly(in);

		// Delete temporal file
		tmpFile.delete();
        return rendition;
    }

    /**
     * Create the different renditions of the Asset about the list size
     * @param args
     * @param asset
     * @param layer
     * @param x
     * @param y
     * @param aspectWidth
     * @param aspectHeight
     * @param isGray
     * @throws java.io.IOException
     */
    public static void saveRenditions(String[] args, Asset asset, Layer layer, int x, int y, int aspectWidth, int aspectHeight, boolean isGray) throws IOException {
        if(args.length > 0){
            Rendition renditionXxY = saveRenditionWithCrop(asset, layer, x, y, aspectWidth, aspectHeight, "jpeg", isGray);
            Layer layerXxY = new Layer(renditionXxY.getStream());

            for(int i = 0; i < args.length; i++){
                String aspectRatio = args[i];

                CropImage cropImage = recoverDimensions(aspectRatio);
                if(cropImage != null){
                    saveRenditionWithResize(asset, layerXxY , cropImage.getWidth(), cropImage.getHeight(), cropImage.getTypeImage(), isGray);
                }
            }
            asset.removeRendition(renditionXxY.getName());
        }
    }

    /**
     * Prepare the layer about the new dimension of the Asset
     * @param layer
     * @param widthLayer
     * @param heightLayer
     * @param typeImage
     * @return
     * @throws java.io.IOException
     */
    public static Layer createLayer(Layer layer, int widthLayer, int heightLayer, String typeImage) throws IOException {
        return createLayer(layer, widthLayer, heightLayer, typeImage, 0.80000000000000004D);
    }

    /**
     * Prepare the layer about the new dimension of the Asset
     * @param layer
     * @param widthLayer
     * @param heightLayer
     * @param typeImage
     * @param quality
     * @return
     * @throws java.io.IOException
     */
    public static Layer createLayer(Layer layer, int widthLayer, int heightLayer, String typeImage, double quality) throws IOException {
        Layer tmpLayer = null;
        File tmpFile = File.createTempFile("thumbnail", ".tmp");
        java.io.OutputStream out = FileUtils.openOutputStream(tmpFile);
        tmpLayer = layer;
        tmpLayer.resize(widthLayer, heightLayer, true);
        tmpLayer.write("image/" + typeImage, quality, out);
        IOUtils.closeQuietly(out);
        java.io.InputStream in = FileUtils.openInputStream(tmpFile);
        tmpLayer = new Layer(in);
        IOUtils.closeQuietly(in);

		// Delete temporal file
		tmpFile.delete();
        return tmpLayer;
    }



    /**
     *
     * @param arg
     * @return
     */
    public static CropImage recoverDimensions(String arg){
        CropImage cropImage = null;
        if(arg != null){
            if(arg.indexOf(":") > 0){
                String strWidth = arg.substring(0, arg.indexOf(":"));
                Integer width = new Integer(strWidth);
                String strHeight = arg.substring(arg.lastIndexOf(":") + 1);
                Integer height = new Integer(strHeight);
//				 String typeImage = "png";
//				 if(width > 600 || height > 300){
//					 typeImage = "jpeg";
//				 }
                String typeImage = "jpg";
                cropImage = new CropImage(width, height, typeImage);
            }
        }
        return cropImage;
    }
}
