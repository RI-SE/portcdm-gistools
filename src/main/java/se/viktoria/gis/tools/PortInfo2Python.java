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

public class PortInfo2Python {

    public PortInfo2Python(List<Port> ports, String outName) throws IOException {
        PrintWriter file = new PrintWriter(new FileWriter( outName + ".py" ));
        ports.stream().forEach(port -> {
            if (port.portLocations != null) {
                port.portLocations.stream().filter(location -> {
                    return (location.area != null && !location.area.coordinates.isEmpty());
                }).forEach(location -> {
                    String pLine = asPython(location);
                    if( pLine != null ) {
                        file.println(pLine);
                    }
                });
            }
        });
        file.close();
    }

    private String asPython(PortLocation portLocation) {
        String array = null;
        String type = null;
        switch( portLocation.type ) {

            case "ANCHORING_AREA":
                array = "anchors";
                type = "ANCHORING_AREA";
                break;
            case "BERTH":
                array = "berths";
                type = "BERTH";
                break;
            case "BOUY":
                return null;
            case "ETUG_ZONE":
                array = "escorts";
                type = "ETUG_STATION/RENDEZVOUS_POINT";
                break;
            case "HOME_BASE":
                return null;
            case "PILOT_BOARDING_AREA":
                array = "pilots";
                type = "PILOT_STATION/RENDEZVOUS_POINT";
                break;
            case "PORT_AREA":
                return null;
            case "TRAFFIC_AREA":
                return asPythonTA( portLocation );
            case "TUG_ZONE":
                array = "tugs";
                type = "TUG_STATION/RENDEZVOUS_POINT";
                break;
            case "VTS_AREA":
                return null;
            default:
                throw new RuntimeException( portLocation.type + " is not a valid port location type" );
        }
        Area area = portLocation.area;
        return String.format("%s.append(Area(u'%s', '%s', %s))",array, portLocation.name, type, asPython(portLocation.area) );
    }

    private String asPythonTA(PortLocation portLocation) {
        return String.format("VTS = Area('VTS', 'VTS', %s)",asPython(portLocation.area));
    }

    public String asPython(Area area) {
        StringBuilder sb = new StringBuilder();
        sb.append("((");
        sb.append(area.coordinates.stream().map(coordinate -> String.format("%s,%s", coordinate.longitude, coordinate.latitude)).collect(Collectors.joining("),(")));
        sb.append("))");
        return sb.toString();
    }

}
