package Proyecto;

import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import java.awt.Desktop;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.text.Text;

public class PrimaryController {

    @FXML
    VBox vboxMain;
    @FXML
    HBox hboxR;
    @FXML
    Label labelRuta;
    @FXML
    Label labelPeso;
    @FXML
    VBox tablaExtensiones;
    @FXML
    HBox hboxEncabezado;
    @FXML
    private void switchToSecondary() throws IOException {
        hboxR.getChildren().clear();
        tablaExtensiones.getChildren().clear();
        Stage s=new Stage();
        DirectoryChooser f = new DirectoryChooser();
        File archivo = f.showDialog(s);
        ArbolFiles<File> elegido=new ArbolFiles(archivo);
        
        hboxR.getChildren().addAll(recorridoEnAnchura(elegido));
        //System.out.println(elegido.pesoKB);
        // ver si esta bien
        //System.out.println(elegido.hijos[0].pesoKB);
        }
    
    
        public Pane recorridoEnAnchura(ArbolFiles<File> arbol) {
            
        Map<String,Integer>extensiones=new HashMap<>();
        Random ra = new Random();
        int low = 1;
        int high = 256;
        //int result = r.nextInt(high-low) + low;    
        Pane p=new Pane();
        Queue<ArbolFiles<File>> cola = new ArrayDeque();
        cola.offer(arbol);
        double referenciaX;
        double referenciaY;
        Rectangle referencia;
        Rectangle padre=new Rectangle(1200,600);
        p.getChildren().add(padre);
//        padre.setFocusTraversable(true);
//                    padre.setOnMouseEntered(e->{
//                        padre.setStyle("-fx-fill:blue");
//                    });
//                    padre.setOnMouseExited(e->{
//                        padre.setStyle("-fx-fill:black");
//                    });
        int contador=0;
        int corte=0;
        while (!cola.isEmpty()) {
            referencia=(Rectangle)p.getChildren().get(contador);
            referenciaX=referencia.getX();
            referenciaY=referencia.getY();
            ArbolFiles<File> eliminado = cola.poll();
            //System.out.println(eliminado.root.getName());
            if (eliminado.hijos != null) {
                for (ArbolFiles<File> ar : eliminado.hijos) {
                    if(!ar.root.isDirectory() && getExtension(ar.root.getName()).length()<=4){
                    String ext=getExtension(ar.root.getName());
                    if(extensiones.containsKey(ext)){
                    extensiones.put(ext,extensiones.get(ext)+1);
                    }else{
                    extensiones.put(ext,1);
                    }
                    }
                    cola.offer(ar);
                    Rectangle r;
                    if(corte%2==0){
                        r=new Rectangle(referencia.getWidth(),referencia.getHeight()*ar.pesoBytes/eliminado.pesoBytes);
                        r.setX(referenciaX);
                        r.setY(referenciaY);
                        referenciaY+=r.getHeight();
                    }else{
                        r=new Rectangle(referencia.getWidth()*ar.pesoBytes/eliminado.pesoBytes,referencia.getHeight());
                        r.setX(referenciaX);
                        r.setY(referenciaY);
                        referenciaX+=r.getWidth();
                    }
                    p.getChildren().add(r);
                    int a=ra.nextInt(high-low) + low;
                    int b=ra.nextInt(high-low) + low;
                    int c=ra.nextInt(high-low) + low;
                    r.setFill(Color.rgb(a,b,c));
                    r.setFocusTraversable(true);
                    r.setOnMouseEntered(e->{
                        labelRuta.setText(ar.root.getAbsolutePath());
                        labelPeso.setText(String.valueOf(Math.round((ar.pesoBytes/1024.0/1024.0)*100.0)/100.0)+" MB ("+ar.pesoBytes+" bytes)");
                    });
                    r.setOnMouseExited(e->{
                        labelRuta.setText("");
                        labelPeso.setText("");
                    });
                    r.setOnMouseClicked(e->{
                        abrirCarpeta(eliminado.root);
                    });
                }
                corte+=1;
            }
            contador+=1;
        }
        if(!extensiones.isEmpty()){
        hboxEncabezado.setVisible(true);    
        for(String exten:extensiones.keySet()){
        agregarHBoxInfoExt(exten,extensiones.get(exten));
        }
        }else{
        hboxEncabezado.setVisible(false);
        }
        return p;
    }
    
        public void abrirCarpeta(File ruta){
        try {
            Desktop desktop = Desktop.getDesktop();
            
            desktop.open(ruta);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        }
        
        public String getExtension(String nombreArch){
        //int i = nombreArch.lastIndexOf('.');
        String[]arrayDoc=nombreArch.split("\\.");
        String extension=arrayDoc[arrayDoc.length-1];
        return extension;
        }
        
        public void agregarHBoxInfoExt(String ext,int nrepeticiones){
            HBox hboxFile = new HBox();
            Text extension = new Text(ext);
            extension.setStyle("-fx-font-size:18");
            extension.setWrappingWidth(400);
            Text nro = new Text(String.valueOf(nrepeticiones));
            nro.setStyle("-fx-font-size:18");
            nro.setWrappingWidth(400);
            hboxFile.setSpacing(20);
            hboxFile.setStyle("-fx-border-color:black");
            hboxFile.getChildren().addAll(extension, nro);
            tablaExtensiones.getChildren().addAll(hboxFile);
        }
   
}