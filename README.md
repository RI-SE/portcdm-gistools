# PortCDM gistools
Simple tools to facilitate handling of location data in PortCDM demonstrator environments

## Building portcdm-gistools
    mvn package

## Running portcdm-gistools
    java -jar gis-tools-1.0-SNAPSHOT.jar [<options>] <portinfo file>

    options:
    --python            generate Python code for AIS connector areas
    -p
    --ewkt              generate EWKT in a tsv file, for use in QGIS
    -e
    --help              show this help text
    -h

## Using the gistool script
There is a simple convenience script in src/main/scripts called gistool. To use it, place in gis-tools-1.0-SNAPSHOT.jar 
and the script in /usr/local/bin and make it runnable with 

    chmod +x /usr/local/bin/gistool
