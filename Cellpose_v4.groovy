// Author: Ved Sharma, BIRC, Rockefeller University (7/18/2024)

import qupath.ext.biop.cellpose.Cellpose2D

// Specify the model name (cyto, nuclei, cyto2, omni_bact or a path to your custom model)
def pathModel = 'cyto3'

def cellpose = Cellpose2D.builder(pathModel)
        .pixelSize( 0.5054 )              // Resolution for detection
        .channels( 'DAPI' )            // Select detection channel(s)
        .normalizePercentilesGlobal(1, 99.8, 1) // Convenience global percentile normalization. arguments are percentileMin, percentileMax, dowsample.
//        .normalizePercentilesGlobal(1, 99, 1) // Convenience global percentile normalization. arguments are percentileMin, percentileMax, dowsample.

        .tileSize(2048)                // If your GPU can take it, make larger tiles to process fewer of them. Useful for Omnipose
//        .tileSize(4096)
//        .setOverlap(20)
        .diameter(15)                   // Median object diameter. Set to 0.0 for the `bact_omni` model or for automatic computation
//        .useGPU()                      // Optional: Use the GPU if configured, defaults to CPU only
        .cellExpansion(5.0)              // Approximate cells based upon nucleus expansion
        .measureIntensity()              // Add cell measurements (in all compartments)
        .measureShape()                  // Add shape measurements

//        .cellConstrainScale(1.5)         // Constrain cell expansion using nucleus size
        .simplify(1)
        .build()

// image cleanup
//setChannelNames("DAPI")
setChannelNames("DAPI", "CD8", "CD20", "CD11c", "Granzyme B", "HLA-DR", "CD40", "CD163", "CD31",
        "CD4", "CD21", "Pan-Cytokeratin", "CD68", "CD45", "Ki67", "CD3e", "TOX", "PD-L1",
        "CD66", "Podoplanin", "FOXP3", "CD34", "TCF-1", "IFNG", "PD-1", "CD44", "CXCL13", "CD39",
        "CD45RO", "CD141", "CD56", "LAG3")
clearAllObjects(); // delete everything (detections, annotations etc. if there are there)
createFullImageAnnotation(true) // create a whole tissue annotation

// Run detection for the selected objects
def imageData = getCurrentImageData()
def pathObjects = getSelectedObjects()
cellpose.detectObjects(imageData, pathObjects)
println 'Done!'