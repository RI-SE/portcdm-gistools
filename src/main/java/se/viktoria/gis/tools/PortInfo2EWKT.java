/*
 * Copyright 2017-2019 Victoria Swedish ICT AB
 * Copyright 2020 RISE Research Institutes of Sweden AB
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package se.viktoria.gis.tools;

import se.viktoria.gis.tools.portinfo.Area;
import se.viktoria.gis.tools.portinfo.Port;
import se.viktoria.gis.tools.portinfo.PortLocation;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

public class PortInfo2EWKT {

    public PortInfo2EWKT(List<Port> ports, String outName){
        PrintWriter file = null;
        try {
            file = new PrintWriter(new FileWriter(outName + ".tsv"));
            file.println(String.format("%s\t%s\t%s\t%s\t%s\t%s", "name", "shortName", "URN", "type", "aliases", "EWKT"));
            PrintWriter finalFile = file;
            System.out.println("Converting " + ports.size() + " ports");//DEBUG
            ports.stream().forEach(port -> {
                System.out.println( "Converting port " + port.unlocode );//DEBUG
                if (port.portLocations != null) {
                    System.out.println("Converting " + port.portLocations.size() + " locations");//DEBUG
                    port.portLocations.stream().filter(location -> {
                        return (location.area != null);
                    }).forEach(location -> {
                        finalFile.println(asEWKT(location));
                    });
                }
            });
        } catch (IOException e) {
            System.err.println("Writing to TSV file failed:" + e.getMessage());
        }finally {
            if( file != null )
                file.close();
        }
    }

    private String asEWKT(PortLocation location) {
        System.out.println("Converting " + location.name); //DEBUG
        String aliases = "";
        if(location.alias != null && !location.alias.isEmpty()) {
            aliases = location.alias.stream().collect(Collectors.joining(","));
        }
        return String.format("%s\t%s\t%s\t%s\t%s\t%s", location.name, location.shortName, location.URN, location.type, aliases, asEWKT(location.area));
    }

    private String asEWKT(Area area) {
        StringBuilder sb = new StringBuilder();
        sb.append("SRID=4326;POLYGON((");
        sb.append(area.coordinates.stream().map(coordinate -> String.format("%s %s", coordinate.longitude, coordinate.latitude)).collect(Collectors.joining(",")));
        sb.append("))");
        return sb.toString();
    }
}
