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

package se.viktoria.gis.tools.portinfo;

import java.util.ArrayList;

public class LocationWriter {

    public static final void main(String [] args ) throws Exception {
        Ports ports = new Ports();
        ports.ports = new ArrayList<>();
        Port port = new Port();
        ports.ports.add(port);
        port.name = "Reykjavik";
        port.unlocode = "ISREY";
        port.portLocations = new ArrayList<>();
        PortLocation pl = new PortLocation();
        pl.name = "Kaj 15";
        pl.shortName = "Kaj 15";
        pl.URN = "urn:mrn:location:isrey:TRAFFIC_AREA";
        pl.type = "TRAFFIC_AREA";
        pl.alias = new ArrayList<>();
        pl.alias.add("Kajsa");
        pl.area = new Area();
        pl.area.coordinates = new ArrayList<>();
        Coordinate c = new Coordinate();
        c.longitude = "1.0";
        c.latitude = "58.4";
        pl.area.coordinates.add(c);
        c = new Coordinate();
        c.longitude = "13.0";
        c.latitude = "58.4";
        pl.area.coordinates.add(c);
        c = new Coordinate();
        c.longitude = "13.0";
        c.latitude = "27.4";
        pl.area.coordinates.add(c);
        c = new Coordinate();
        c.longitude = "13.0";
        c.latitude = "27.4";
        pl.area.coordinates.add(c);
        port.portLocations.add(pl);
        PortInfoWriter writer = new PortInfoWriter();
        writer.writePort(ports, "portinfo2.xml");

    }
}
