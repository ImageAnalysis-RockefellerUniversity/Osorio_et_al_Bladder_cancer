// Author: Ved Sharma, BIRC, Rockefeller University (7/15/2024)
import qupath.lib.gui.tools.MeasurementExporter
import qupath.lib.objects.PathAnnotationObject
import qupath.lib.objects.PathDetectionObject

// Get the list of all images in the current project
def project = getProject()
def imagesToExport = project.getImageList()

def separator = ","
def exportType_Ann = PathAnnotationObject.class
def exportType_Det = PathDetectionObject.class

def phenotypes = ["CD11c", "CD141", "CD163", "CD20", "CD21", "CD31", "CD39", "CD3e", "CD4", "CD40", "CD45", "CD45RO", "CD45_CD11c_HLA-DR_CD141", "CD45_CD11c_HLA-DR_CD141_CD40", "CD45_CD11c_HLA-DR_CD40", "CD45_CD11c_HLA-DR_DCs", "CD45_CD20_Bcells", "CD45_CD20_CD21_CXCL13", "CD45_CD20_CD21_Ki67", "CD45_CD20_HLA-DR_CD40", "CD45_CD31", "CD45_CD31_Podoplanin", "CD45_CD3e_CD4_FOXP3", "CD45_CD3e_Tcells", "CD45_CD66", "CD45_CD68", "CD45_CD68_CD163", "CD45_CD68_HLA-DR_CD40", "CD45_Pan-CK", "CD4_FOXP3_Ki67", "CD4_FOXP3_PD-1_TOX_Tfh", "CD4_FOXP3_Tregs", "CD66", "CD68", "CD8", "CD8_CD45RO_TCF-1", "CD8_CD45RO_TOX_PD-1", "CD8_GranzymeB", "CD8_Ki67", "CXCL13", "FOXP3", "GranzymeB", "HLA-DR", "Ki67", "Pan-Cytokeratin", "PD-1", "Podoplanin", "TCF-1", "TOX"]
def markers = ["DAPI", "CD8", "CD20", "CD11c", "Granzyme B", "HLA-DR", "CD40", "CD163", "CD31", 
                 "CD4", "CD21", "Pan-Cytokeratin", "CD68", "CD45", "Ki67", "CD3e", "TOX", "PD-L1",
                 "CD66", "Podoplanin", "FOXP3", "CD34", "TCF-1", "IFNG", "PD-1", "CD44", "CXCL13", "CD39",
                 "CD45RO", "CD141", "CD56", "LAG3"]
                
def columnsToInclude_Det_expanded =
                ["Image", "Object ID", "Object type", "Name", "Classification", "Parent", "ROI",
                "Centroid X µm", "Centroid Y µm", "Nucleus: Area µm^2", "Cell: Area µm^2"
                ]

for (marker in markers) {
    columnsToInclude_Det_expanded.add(marker+": Nucleus: Mean")
    columnsToInclude_Det_expanded.add(marker+": Cell: Mean")    
}
//println(columnsToInclude_Det_first)

def columnsToInclude_Det = ["Image", "Object ID", "Object type", "Classification", "Centroid X µm", "Centroid Y µm"]

for(phenotype in phenotypes) {
    
    for(entry in getProject().getImageList()) {
        def imageData = entry.readImageData()
        resetDetectionClassifications();
        runObjectClassifier(imageData, phenotype);
        entry.saveImageData(imageData)
    }

    // Export annotation measurements
    def filename1 = phenotype+"_Ann.csv"
    def outputPath1 = buildFilePath(PROJECT_BASE_DIR, filename1)
    def outputFile1 = new File(outputPath1)
    def exporter1  = new MeasurementExporter()
                      .imageList(imagesToExport)
                      .separator(separator)
    //                  .excludeColumns(columnsToExclude)
                      .exportType(exportType_Ann)
                      .exportMeasurements(outputFile1)

    // Export detection measurements
    def filename2 = phenotype+"_Det.csv"
    def outputPath2 = buildFilePath(PROJECT_BASE_DIR, filename2)
    def outputFile2 = new File(outputPath2)
    def exporter2  = new MeasurementExporter()
                      .imageList(imagesToExport)
                      .separator(separator)
                      .includeOnlyColumns(columnsToInclude_Det as String[])
                      .exportType(exportType_Det)
                      .exportMeasurements(outputFile2)

    // Export expanded detection measurements for the first phenotype
    if(phenotype.matches(phenotypes[0])) {
        def filename3 = phenotype+"_Det_expanded.csv"
        def outputPath3 = buildFilePath(PROJECT_BASE_DIR, filename3)
        def outputFile3 = new File(outputPath3)
        def exporter3  = new MeasurementExporter()
                          .imageList(imagesToExport)
                          .separator(separator)
                          .includeOnlyColumns(columnsToInclude_Det_expanded as String[])
                          .exportType(exportType_Det)
                          .exportMeasurements(outputFile3)
    }
}

print "Done!"
