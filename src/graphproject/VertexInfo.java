/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphproject;

/**
 *
 * @author minhnhat
 */
public class VertexInfo implements Comparable<VertexInfo>{
    private int vertex;
    private int weight;

    public VertexInfo(int vertex, int weight) {
        this.vertex = vertex;
        this.weight = weight;
    }
    
    public VertexInfo(){}

    public int getVertex() {
        return vertex;
    }

    public void setVertex(int vertex) {
        this.vertex = vertex;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public int compareTo(VertexInfo o) {
        return Integer.compare(weight, o.getWeight());
    }

    @Override
    public String toString() {
        return "{" + vertex + ", " +weight + '}';
    }
    
    
    
    
}
