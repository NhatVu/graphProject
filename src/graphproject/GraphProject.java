/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphproject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * support "." is surround by odd "." => vertex
 *
 * @author minhnhat
 */
public class GraphProject {

    /*
    bieu dien graph: cach 3: incidence matrix 
     */
 /*
    Home work
    a. Transform text file with a 2-dimensional array 
    b. Determine the number of verteces in this array. 
    
    
    covert maze file to array 2 dem
    check a[i][j] is a vertex or not 
    
    a[i -1][j]
    a[i][j-1]
    a[i][j+1]
    a[i+1][j]
     */
    int col;
    int row;
    int matrix[][];
    Map<Integer, List<VertexInfo>> graphAdjList = new HashMap<>();

    /**
     * matrix: 0 = #, 1 = .
     *
     * @param mazeFile
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public int[][] readMaze(String mazeFile) throws FileNotFoundException, IOException {
        StrFileReader reader = new StrFileReader(mazeFile);
        List<String> allLine = reader.readAllLine();
        reader.close();
        col = allLine.get(0).length();
        row = allLine.size();
        matrix = new int[row][col];
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                if (allLine.get(r).charAt(c) == '#') {
                    matrix[r][c] = 0;
                } else if (allLine.get(r).charAt(c) == '.') {
                    matrix[r][c] = 1;
                }
            }
        }

        return matrix;
    }

    public void printVertex() {
        int numberVertex = 0;
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                if (isVertex(r, c)) {
                    System.out.println("vertex: (" + r + "," + c + "), " + (r * col + c));
                    numberVertex++;
                }
            }
        }
        System.out.println("number veteces: " + numberVertex);
    }

    private int vertexID(int colLength, int r, int c) {
        return (r * colLength + c);
    }

    private boolean isVertex(int r, int c) {
        int count = 0;
        if (r < 0 || c < 0) {
            return false;
        }
        if (matrix[r][c] == 1) {
            if (r - 1 >= 0 && matrix[r - 1][c] == 1) {
                count++;
            }
            if (r + 1 < row && matrix[r + 1][c] == 1) {
                count++;
            }
            if (c - 1 >= 0 && matrix[r][c - 1] == 1) {
                count++;
            }
            if (c + 1 < col && matrix[r][c + 1] == 1) {
                count++;
            }
            if (count == 1 || count == 3 || count == 4) {
                return true;
            }
        }
        return false;
    }

    private boolean isEdge(int r, int c) {
        int count = 0;
        if (r < 0 || c < 0) {
            return false;
        }
        if (matrix[r][c] == 1) {
            if (r - 1 >= 0 && matrix[r - 1][c] == 1) {
                count++;
            }
            if (r + 1 < row && matrix[r + 1][c] == 1) {
                count++;
            }
            if (c - 1 >= 0 && matrix[r][c - 1] == 1) {
                count++;
            }
            if (c + 1 < col && matrix[r][c + 1] == 1) {
                count++;
            }
            if (count == 2) {
                return true;
            }
        }
        return false;
    }

    public void findNextVertex(int vertexID, List<Integer> visitedEdge) {
        //        visitedVertex.add(vertexID);
        int r = vertexID / col;
        int c = vertexID % col;

        if (vertexID < 0 || visitedEdge.contains(vertexID)) {
            return;
        }

        if (isVertex(r, c)) {
            visitedEdge.add(vertexID);
            return;
        }
        if (isEdge(r, c)) {
            visitedEdge.add(vertexID);

            findNextVertex(vertexID(col, r - 1, c), visitedEdge);
            findNextVertex(vertexID(col, r + 1, c), visitedEdge);
            findNextVertex(vertexID(col, r, c - 1), visitedEdge);
            findNextVertex(vertexID(col, r, c + 1), visitedEdge);
        }

//
//        if (r - 1 >= 0 && matrix[r - 1][c] == 1) {
////            visitedVertex.add(vertexID);
//            if (isVertex(r -1, c)) {
//                return;
//            }else
//                findNextVertex(vertexID(col, r -1 , c), visitedVertex);
//        }
//        
//        if (r + 1 < row && matrix[r + 1][c] == 1) {
//        }
//        
//        if (c - 1 >= 0 && matrix[r][c - 1] == 1) {
//        }
//        
//        if (c + 1 < col && matrix[r][c + 1] == 1) {
//        }
    }

    public void convertToGraph() {
        List<Integer> listVertex = new ArrayList();
        int numVertex = 0;
        List<Integer> visitedEdge = new ArrayList<>();
        for (int r = 0; r < row; r++) {
            List<VertexInfo> vertexAdjancy = new ArrayList<>();
            VertexInfo tmp = null;
            for (int c = 0; c < col; c++) {
                if (isVertex(r, c)) {
                    visitedEdge.clear();
                    visitedEdge.add(vertexID(col, r, c));
                    findNextVertex(vertexID(col, r - 1, c), visitedEdge);
                    if (visitedEdge.size() > 1) {
                        tmp = new VertexInfo();
                        tmp.setVertex(visitedEdge.get(visitedEdge.size() - 1));
                        tmp.setWeight(visitedEdge.size() - 1);
                        vertexAdjancy.add(tmp);
                    }

                    visitedEdge.clear();
                    visitedEdge.add(vertexID(col, r, c));
                    findNextVertex(vertexID(col, r + 1, c), visitedEdge);
                    if (visitedEdge.size() > 1) {
                        tmp = new VertexInfo();
                        tmp.setVertex(visitedEdge.get(visitedEdge.size() - 1));
                        tmp.setWeight(visitedEdge.size() - 1);
                        vertexAdjancy.add(tmp);
                        visitedEdge.clear();
                    }

                    visitedEdge.clear();
                    visitedEdge.add(vertexID(col, r, c));
                    findNextVertex(vertexID(col, r, c - 1), visitedEdge);
                    if (visitedEdge.size() > 1) {
                        tmp = new VertexInfo();
                        tmp.setVertex(visitedEdge.get(visitedEdge.size() - 1));
                        tmp.setWeight(visitedEdge.size() - 1);
                        vertexAdjancy.add(tmp);
                    }

                    visitedEdge.clear();
                    visitedEdge.add(vertexID(col, r, c));
                    findNextVertex(vertexID(col, r, c + 1), visitedEdge);
                    if (visitedEdge.size() > 1) {
                        tmp = new VertexInfo();
                        tmp.setVertex(visitedEdge.get(visitedEdge.size() - 1));
                        tmp.setWeight(visitedEdge.size() - 1);
                        vertexAdjancy.add(tmp);
                        visitedEdge.clear();
                    }
                    System.out.println("vertex: " + vertexID(col, r, c) + " == " + vertexAdjancy.toString());
                    graphAdjList.put(vertexID(col, r, c), vertexAdjancy);
                    vertexAdjancy = new ArrayList<>();
                }
            }
        }

    }

    public void printMatrix(int[][] matrix) {
        int col = matrix[0].length;
        int row = matrix.length;
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                System.out.print(matrix[r][c] + " ");
            }
            System.out.println();
        }
        System.out.println("\n");
    }

    public void dijistra(Map<Integer, List<VertexInfo>> graph, int vertex) {
        int numberVertex = graph.size();
        Map<Integer, Integer> parent = new HashMap<>();
        Map<Integer, Integer> mw = new HashMap<>();
        Set<Integer> setDequeued = new HashSet<Integer>();
        Set<Integer> setEnqueued = new HashSet<Integer>();

        PriorityQueue<VertexInfo> queue = new PriorityQueue<>();

        parent.put(vertex, vertex);
        mw.put(vertex, 0);
        queue.offer(new VertexInfo(vertex, mw.get(vertex)));
        setEnqueued.add(vertex); // add to set enqueued

        do {
            VertexInfo s = queue.poll();
            setDequeued.add(s.getVertex()); // add to set dequeued
            List<VertexInfo> adjacencyList = graph.get(s.getVertex());
            for (VertexInfo t : adjacencyList) {
                // dinh t
                if (setDequeued.contains(t.getVertex()) == false) {
                    int p = mw.get(s.getVertex()) + t.getWeight();
                    if (setEnqueued.contains(t.getVertex()) == false) {
                        parent.put(t.getVertex(), s.getVertex());
                        mw.put(t.getVertex(), p);
                        queue.add(new VertexInfo(t.getVertex(), mw.get(t.getVertex())));
                        setEnqueued.add(t.getVertex());
                    } else if (mw.get(t.getVertex()) > p) {
                        parent.put(t.getVertex(), s.getVertex());
                        mw.put(t.getVertex(), p);
                        queue.remove(t);
                        queue.add(new VertexInfo(t.getVertex(), mw.get(t.getVertex())));
//                        Iterator<VertexInfo> it = queue.iterator();
//                        while (it.hasNext()) {
//                            VertexInfo tmp = it.next();
//                            if (tmp.getVertex() == t.getVertex()) {
//                                tmp.setWeight(mw.get(t.getVertex()));
//                                break;
//                            }
//                        }
                    }
                }

            }
        } while (queue.isEmpty() == false);

        // print result
        System.out.println("parent: " + parent);
        System.out.println("mw: " + mw);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        String file = "data/maze.txt";
        GraphProject graph = new GraphProject();
        int[][] readMaze = graph.readMaze(file);
        graph.printMatrix(graph.matrix);
        graph.printVertex();
        graph.convertToGraph();
        System.out.println(graph.graphAdjList);
        graph.dijistra(graph.graphAdjList, 17);
    }

}
