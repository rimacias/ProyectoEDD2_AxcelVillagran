/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proyecto;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 *
 * @author AVGla
 */
public class ArbolFiles<T>{
    
    File root;
    PriorityQueue<ArbolFiles<File>> hijos;
    double pesoBytes;
    public ArbolFiles(File archivo) {
    if(archivo.isDirectory() && archivo.listFiles()!=null) {
	root=archivo;
        hijos=new PriorityQueue<>(new Comparator<ArbolFiles>(){
            @Override
            public int compare(ArbolFiles o1, ArbolFiles o2) {
                if(App.contarPeso(o1.root)>App.contarPeso(o2.root)){
                return -1;    
                }else if(App.contarPeso(o1.root)<App.contarPeso(o2.root)){
                return 0;
                }else{
                return 1;
            }
            }
            
        
        
        });
        //int contador=0;
        for(File fil:archivo.listFiles()){
        hijos.offer(new ArbolFiles(fil));
        //contador+=1;
        }
        pesoBytes=App.contarPeso(archivo);
	}else {
            root=archivo;
            pesoBytes=App.contarPeso(archivo);
            hijos=null;
	}
		
	}
    
    
}
