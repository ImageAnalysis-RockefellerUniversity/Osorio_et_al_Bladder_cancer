# Osorio_et_al_Bladder_cancer

List of Groovy scripts used in the multiplex immunofluorescence (mIF) analysis of PhenoCycler-Fusion system (Akoya Biosciences) generated QPTIFF images in QuPath  
1. Cellpose_v4.groovy  
  Creates full image annotation. Runs Cellpose, model:cyto3, diamater=15 pixels on each image

2. Remove_small_big_and_faint_DAPI_nuclei_detections.groovy  
  Filters cells out which are smaller than 10 um^2, or bigger than 200 um^2 or have faint DAPI signal (DAPI mean intensity < 18)

3. Get_number_of_annotations_and_detections_in_all_images.groovy  
   A utility script to keep track of number of detections (cells) before and after filtering (script #2)

4. Apply_phenotype_classifiers_and_export_measurments_v03.groovy  
   Exports cell measurments (centroid XY coordinates, mean nucleus and mean cell intensities in each channel) as csv files.


