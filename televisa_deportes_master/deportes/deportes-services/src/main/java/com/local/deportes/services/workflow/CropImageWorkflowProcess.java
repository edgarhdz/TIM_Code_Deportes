package com.local.deportes.services.workflow;

import com.day.cq.dam.api.Asset;
import com.day.cq.dam.commons.process.AbstractAssetWorkflowProcess;
import com.day.cq.dam.commons.util.MemoryUtil;
import com.day.cq.workflow.WorkflowException;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.WorkItem;
import com.day.cq.workflow.metadata.MetaDataMap;
import com.day.image.Layer;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.osgi.framework.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.IIOException;
import java.io.IOException;

/**
 * Note Management Data Access
 *
 * Workflow Process that is allow you to Crop the images based in the different aspect ratios that can received in the parameteres
 *
 * Changes History:
 *
 *         2013-01-25 Initial Development
 *
 * @author lsztul@xumak.com
 * @version 1.0
 */
@Component
@Service
@Properties({
        @Property(name = Constants.SERVICE_DESCRIPTION, value = "Crop Image Deportes workflow process implementation."),
        @Property(name = Constants.SERVICE_VENDOR, value = "XumaK"),
        @Property(name = "process.label", value = "Crop Image Deportes")})
public class CropImageWorkflowProcess extends AbstractAssetWorkflowProcess {

    private static final Logger LOG = LoggerFactory.getLogger(CropImageWorkflowProcess.class);

    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaData) throws WorkflowException {
        LOG.info(">>>>>> Starting the Crop Image Workflow Process");

        String metaQuality = metaData.get("quality", "0.8");
        double quality = Double.parseDouble(metaQuality);

        String dimension43 = metaData.get("dimension43", "");
        boolean grayFor43 = metaData.get("grayFor43", Boolean.FALSE);

        String[] aspectRatio43 = metaData.get("aspectRatio43", new String[]{""});

        String dimension169 = metaData.get("dimension169", "");
        boolean grayFor169 = metaData.get("grayFor169", Boolean.FALSE);
        String[] aspectRatio169 = metaData.get("aspectRatio169", new String[]{""});

        String dimension11 = metaData.get("dimension11", "");
        boolean grayFor11 = metaData.get("grayFor11", Boolean.FALSE);
        String[] aspectRatio11 = metaData.get("aspectRatio11", new String[]{""});

        try {
            Asset asset = getAssetFromPayload(workItem, workflowSession.getSession());
            if (null != asset) {
                String assetPath = asset.getPath();
                if(assetPath.startsWith("/content/dam/televisa/deportes/")) {
                    asset.setBatchMode(true);
                    if (MemoryUtil.hasEnoughSystemMemory(asset)) {
                        LOG.info(">>>>>> The System has enough memory to process the Asset: " + asset.getName());
                        boolean isLoaded = false;
                        long maxTrials;
                        for (maxTrials = 100L; !isLoaded && maxTrials > 0L;) {
                            try {
                                if(dimension43 != null){

                                    CropImage standardCropImage = CropImageHelper.recoverDimensions(dimension43);

                                    if(standardCropImage != null){
                                        //Aspect Ratio 4:3
                                        Layer basedLayer43 = CropImageHelper.createLayer(new Layer(asset.getOriginal().getStream()), standardCropImage.getWidth(), standardCropImage.getHeight(), standardCropImage.getTypeImage(), quality);
                                        CropImage cropImage43 = standardCropImage;
                                        if(cropImage43 != null){
                                            CropImageHelper.saveRenditions(aspectRatio43, asset, basedLayer43, 0, 0, cropImage43.getWidth(), cropImage43.getHeight(), false);
                                            if(grayFor43){
                                                CropImageHelper.saveRenditions(aspectRatio43, asset, basedLayer43, 0, 0, cropImage43.getWidth(), cropImage43.getHeight(), true);
                                            }
                                        }

                                        //Aspect Ratio 16:9
                                        Layer basedLayer169 = CropImageHelper.createLayer(new Layer(asset.getOriginal().getStream()), standardCropImage.getWidth(), standardCropImage.getHeight(), standardCropImage.getTypeImage(), quality);
                                        CropImage cropImage169 = CropImageHelper.recoverDimensions(dimension169);
                                        if(cropImage169 != null){
                                            int x = cropImage43.getWidth() - cropImage169.getWidth();
                                            x = (x > 0) ? (x = (x / 2)) : (x = 0);
                                            int y = cropImage43.getHeight() - cropImage169.getHeight();
                                            y = (y > 0) ? (y = (y / 2)) : (y = 0);
                                            CropImageHelper.saveRenditions(aspectRatio169, asset, basedLayer169, x, y, cropImage169.getWidth(), cropImage169.getHeight(), false);
                                            if(grayFor169){
                                                CropImageHelper.saveRenditions(aspectRatio169, asset, basedLayer169, x, y, cropImage169.getWidth(), cropImage169.getHeight(), true);
                                            }
                                        }

                                        //Aspect Ratio 1:1
                                        Layer basedLayer11 = CropImageHelper.createLayer(new Layer(asset.getOriginal().getStream()), standardCropImage.getWidth(), standardCropImage.getHeight(), standardCropImage.getTypeImage(), quality);
                                        CropImage cropImage11 = CropImageHelper.recoverDimensions(dimension11);
                                        if(cropImage11 != null){
                                            int x = cropImage43.getWidth() - cropImage11.getWidth();
                                            x = (x > 0) ? (x = (x / 2)) : (x = 0);
                                            int y = cropImage43.getHeight() - cropImage11.getHeight();
                                            y = (y > 0) ? (y = (y / 2)) : (y = 0);
                                            CropImageHelper.saveRenditions(aspectRatio11, asset, basedLayer11, x, y, cropImage11.getWidth(), cropImage11.getHeight(), false);
                                            if(grayFor11){
                                                CropImageHelper.saveRenditions(aspectRatio11, asset, basedLayer11, x, y, cropImage11.getWidth(), cropImage11.getHeight(), true);
                                            }
                                        }
                                        isLoaded = true;
                                    }
                                }
                            } catch (IOException e) {
                                if ((e instanceof IIOException) && e.getMessage().contains("Not enough memory")) {
                                    isLoaded = false;
                                    maxTrials--;
                                    LOG.debug("execute: insufficient memory, reloading image. Free mem [{}]. Asset [{}].", Runtime.getRuntime().freeMemory(), asset.getPath());
                                    Thread.sleep((long)(2500D * (Math.random() + 0.5D)));
                                } else {
                                    LOG.debug("execute: error while loading image for [{}]: ", asset.getPath(), e);
                                    throw new IOException(e.getMessage());
                                }
                            } catch (Exception e) {
                                throw new WorkflowException(e);
                            }
                        }
                        LOG.info(">>>>>> Finished creating thumbnails");
                        if (maxTrials == 0L) {
                            LOG.warn("execute: failed creating thumbnails, insufficient memory even after [{}] trials for [{}].", 100, asset.getPath());
                        }
                    } else {
                        LOG.warn("execute: failed loading image, insufficient memory. Increase heap size up to [{}bytes] for asset [{}].", MemoryUtil.suggestMaxHeapSize(asset), asset.getPath());
                    }
                } else {
                    LOG.info("execute: asset is not on designated path to create renditions. not processing.");
                }
            } else {
                LOG.error("execute: cannot create thumbnails, asset [{}] in workflow [{}] does not exist.", workItem.getWorkflowData().getPayload().toString(), workItem.getId());
            }
        }catch (Exception e) {
            throw new WorkflowException(e);
        }
        LOG.info(">>>>>> Exiting the Crop Image Workflow process");
    }
}