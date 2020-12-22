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

import se.viktoria.gis.tools.portinfo.Port;
import se.viktoria.gis.tools.portinfo.PortInfoReader;

import java.util.List;

public class GisTool {


    private boolean makePython = false;
    private boolean makeEWKT = false;
    private String inFile = null;
    private String outName;

    public GisTool(String[] args) {
        readArgs(args);

        try {
            List<Port> ports = readPortInfo(inFile);
            if (makeEWKT)
                new PortInfo2EWKT(ports, outName);

            if (makePython)
                new PortInfo2Python(ports, outName);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static void main(String[] args) {
        GisTool gisTool = new GisTool(args);

    }

    private List<Port> readPortInfo(String fileName) throws Exception {
        PortInfoReader reader = new PortInfoReader();
        return reader.readPorts(fileName);
    }

    private void readArgs(String[] args) {
        for (String arg : args) {
            if (arg.startsWith("-")) {
                switch (arg) {
                    case "--help":
                    case "-h":
                        showHelp("Usage");
                        break;
                    case "--python":
                    case "-p":
                        makePython = true;
                        continue;
                    case "--ewkt":
                    case "-e":
                        makeEWKT = true;
                        continue;
                    default:
                        showHelp("Unknown parameter:" + arg);
                }
            }
            inFile = arg;
        }
        if (!(makePython || makeEWKT)) {
            showHelp("No output format was chosen. Nothing has been done.");
        }
        if (inFile == null) {
            showHelp("No input file was given.");
        }
        outName = inFile.replace(".xml", "");
    }

    private void showHelp(String message) {
        System.out.println(message);
        System.out.println("gistool <options> <infile>");
        System.out.println("options:");
        System.out.println("--python            generate Python code for AIS connector areas");
        System.out.println("-p");
        System.out.println("--ewkt              generate EWKT in a tsv file, for use in QGIS");
        System.out.println("-e");
        System.out.println("--help              show this help text");
        System.out.println("-h");
        System.exit(0);
    }
}
