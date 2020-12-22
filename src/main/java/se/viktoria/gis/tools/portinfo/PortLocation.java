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

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root
public class PortLocation {
    @Element
    public String name;

    @Element
    public String shortName;

    @Element
    public String URN;

    @Element
    public String type;

    @ElementList(name="aliases", required = false)
    public List<String> alias;

    @Element(required = false)
    public Area area;

    @Override
    public String toString() {
        return "PortLocation{" +
                "name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", URN='" + URN + '\'' +
                ", type='" + type + '\'' +
                ", alias=" + alias +
                ", area=" + area +
                '}';
    }
}
