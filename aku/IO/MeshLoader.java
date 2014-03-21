package aku.IO;

import akgl.Units.Buffers.*;
import akgl.Units.Geometry.*;
import akgl.Units.Geometry.Vectors.*;
import aku.AncientKemetRegistry;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.*;

/**
 * @author Robert Kollar
 */
public class MeshLoader {

    private static MeshLoader instance;

    public static MeshLoader getInstance() {
        if (instance == null) {
            instance = new MeshLoader();
        }
        return instance;
    }

    private Mesh[] meshes = new Mesh[1024];

    public Mesh getMesh(int id) {
        if (meshes[id] == null) {
            reloadMesh(id);
        }
        return meshes[id];
    }

    private void reloadMesh(int id) {
        Mesh mesh = new Mesh();

        URL url = null;
        InputStream in = null;

        {//TODO add run on download thread
            try {
                url = new URL(AncientKemetRegistry.getDataHost() + "meshes/" + id + ".obj");
                in = url.openStream();
            } catch (MalformedURLException ex) {
                Logger.getLogger(BMFontLoader.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(BMFontLoader.class.getName()).log(Level.SEVERE, null, ex);
            }

            OBJModel model = new OBJModel(in);

            mesh.setVertices(model.vertices);
            mesh.setTexCoords(model.uvs);
            mesh.setNormals(model.normals);
            mesh.setTriangles(model.triangles);
        }
        meshes[id] = mesh;
    }

    private class OBJModel {

        /**
         * Cached lists impossible to use in element array
         */
        protected List<Vec2> cuvs = new ArrayList<>();
        protected List<Vec3> cnormals = new ArrayList<>();
        protected List<Vec3> cvertices = new ArrayList<>();
        protected List<Triangle> ctriangles = new ArrayList<>();

        /**
         * Real lists
         */
        private List<Vec2> uvs = new ArrayList<>();
        private List<Vec3> normals = new ArrayList<>();
        private List<Vec3> vertices = new ArrayList<>();
        private List<Triangle> triangles = new ArrayList<>();

        public OBJModel(InputStream in) {
            LoadOBJModel(in);
        }

        private void LoadOBJModel(InputStream in) {
            try {
                // Open a file handle and read the models data
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String line = null;
                while ((line = br.readLine()) != null) {
                    if (line.startsWith("#")) { // Read Any Descriptor Data in the File
                        //System.out.println("Descriptor: "+line); //Uncomment to print out file descriptor data
                    } else if (line.equals("")) {
                        //Ignore whitespace data
                    } else if (line.startsWith("v ")) { // Read in Vertex Data
                        cvertices.add(ProcessDataVec3(line));
                    } else if (line.startsWith("vt ")) { // Read Texture Coordinates
                        cuvs.add(ProcessDataVec2(line));
                    } else if (line.startsWith("vn ")) { // Read Normal Coordinates
                        cnormals.add(ProcessDataVec3(line));
                    } else if (line.startsWith("f ")) { // Read Face Data
                        ProcessDataTriangle(line);
                    }
                }
                br.close();
            } catch (IOException e) {
                System.out.println("Failed to find or read OBJ: ");
                System.err.println(e);
            }
        }

        private Vec3 ProcessDataVec3(String read) {
            final String s[] = read.split("\\s+");
            return (Vec3) (ProcessFloatData(s)); //returns an array of processed float data
        }

        private Vec2 ProcessDataVec2(String read) {
            final String s[] = read.split("\\s+");
            return (Vec2) (ProcessFloatData(s)); //returns an array of processed float data
        }

        private Object ProcessFloatData(String sdata[]) {
            float data[] = new float[sdata.length - 1];
            for (int loop = 0; loop < data.length; loop++) {
                data[loop] = Float.parseFloat(sdata[loop + 1]);
            }
            if (data.length == 2) {
                return new Vec2(data[0], data[1]);
            } else {
                return new Vec3(data[0], data[1], data[2]);
            }
        }

        private void ProcessDataTriangle(String fread) {
            String s[] = fread.split("\\s+");
            if (fread.contains("//")) { // Pattern is present if obj has only v and vn in face data
                for (int loop = 1; loop < s.length; loop++) {
                    s[loop] = s[loop].replaceAll("//", "/0/"); //insert a zero for missing vt data
                }
            }
            ProcessfIntData(s); // Pass in face data
        }

        private void ProcessfIntData(String sdata[]) {
            int vdata[] = new int[sdata.length - 1];
            int vtdata[] = new int[sdata.length - 1];
            int vndata[] = new int[sdata.length - 1];
            for (int loop = 1; loop < sdata.length; loop++) {
                String s = sdata[loop];
                String[] temp = s.split("/");
                vdata[loop - 1] = Integer.valueOf(temp[0]) - 1; //always add vertex indices
                if (temp.length > 1) { // we have v and vt data
                    vtdata[loop - 1] = Integer.valueOf(temp[1]) - 1; // add in vt indices
                } else {
                    vtdata[loop - 1] = -1; // if no vt data is present fill in zeros
                }
                if (temp.length > 2) { // we have v, vt, and vn data
                    vndata[loop - 1] = Integer.valueOf(temp[2]) - 1; // add in vn indices
                } else {
                    vndata[loop - 1] = -1; //if no vn data is present fill in zeros
                }
            }

            int[] pointers = new int[3];

            //check if the a vertex with the same normal and texture coord already exists
            for (int i = 0; i < vdata.length; i++) {
                Vec3 catchedVertex = cvertices.get(vdata[i]);
                Vec3 catchedNormal = vndata[i] == -1 ? null : cnormals.get(vndata[i]);
                Vec2 catchedUV = vtdata[i] == -1 ? null : cuvs.get(vtdata[i]);

                boolean success = false;

                if (vertices.contains(cvertices.get(vdata[i]))) {
                    for (int vi = 0; vi < vertices.size(); vi++) {
                        Vec3 v = vertices.get(vi);
                        if (v.equals(catchedVertex)) {
                            if (catchedNormal != null) {
                                if (normals.get(vi).equals(catchedNormal)) {
                                    if (catchedUV != null) {
                                        if (uvs.get(vi).equals(catchedUV)) {
                                            success = true;
                                            pointers[i] = vi;
                                            break;
                                        }
                                    } else {
                                        success = true;
                                        pointers[i] = vi;
                                        break;
                                    }
                                }
                            } else if (catchedUV != null) {
                                if (uvs.get(vi).equals(catchedUV)) {
                                    success = true;
                                    pointers[i] = vi;
                                    break;
                                }
                            }
                        }
                    }
                }
                if (!success) {
                    pointers[i] = vertices.size();

                    //record the vertex its normal and its tex coord
                    vertices.add(cvertices.get(vdata[i]));
                    if (catchedNormal != null) {
                        normals.add(catchedNormal);
                    }
                    if (catchedUV != null) {
                        uvs.add(catchedUV);
                    }
                }
            }
            //create the triangle
            triangles.add(new Triangle(pointers[0], pointers[1], pointers[2]));
        }

    }
}
