// Author: Ved Sharma, BIRC, The Rockefeller University (9/11/2024)
// This script runs for all images in a project
// It prints the filename and total number of annotations and detections in each image

print('Total images: '+getProject().getImageList().size())
print('ImageName\t\t\t#Annotations\t\t#Detections')

for(entry in getProject().getImageList()) {
    imageName = entry.getImageName().split('.qptiff')[0].padRight(20)
    numAnn = entry.readImageData().getHierarchy().getAnnotationObjects().size()
    numDet = entry.readImageData().getHierarchy().getDetectionObjects().size()
    print(imageName+'\t'+numAnn+'\t\t\t'+numDet)
}
