// Author: Ved Sharma, BIRC, Rockefeller University (6/27/2024)

// remove small nuclei
def toDelete = getDetectionObjects().findAll {measurement(it, 'Nucleus: Area µm^2') < 10}
removeObjects(toDelete, true)
println(toDelete.size() + " small nuclei (area < 10 um^2) deleted.")


// remove large nuclei
toDelete = getDetectionObjects().findAll {measurement(it, 'Nucleus: Area µm^2') > 200}
removeObjects(toDelete, true)
println(toDelete.size() + " big nuclei (area > 200 um^2) deleted.")

// remove faint DAPI nuclei
toDelete = getDetectionObjects().findAll {measurement(it, 'DAPI: Nucleus: Mean') < 18}
removeObjects(toDelete, true)
println(toDelete.size() + " faint DAPI nuclei (DAPI mean < 18) deleted.")

