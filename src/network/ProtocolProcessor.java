package network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import controller.ServerController;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Classificacao;
import model.Equipe;
import model.Piloto;
import model.Pista;
import model.Request;
import model.TempoDono;

public class ProtocolProcessor implements Runnable{

    private Socket connection;
    private Object message;
    
    public ProtocolProcessor(Socket connection) throws IOException{
        this.connection = connection;
        this.message = deserializeObject();
    }
    
    private Object deserializeObject() throws IOException{
        try{
            InputStream input = new ObjectInputStream(this.connection.getInputStream());
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            ObjectOutputStream obj = new ObjectOutputStream(bytes);
            try{
                obj.writeObject(((ObjectInputStream)input).readObject());
                bytes.toByteArray();
                return this.deserializeMessage(bytes.toByteArray());
            }
            catch(ClassNotFoundException e){
                e.printStackTrace();
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }
    
    private Object deserializeMessage(byte[] data){
        ByteArrayInputStream message = new ByteArrayInputStream(data);
        try{
            ObjectInput reader = new ObjectInputStream(message);
            return (Object)reader.readObject();
        }
        catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        
        return null;
    }
    
    @Override
    public void run(){
        if(this.message instanceof Request){
            Request request = (Request)this.message;
            if(request.getTag().equals("equipe")){
                try {
                    ServerController.getInstance().cadEquipe(this.connection, (Equipe) request.getParams());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                return;
            }
            else if(request.getTag().equals("listaEquipe")){
                try {
                    ServerController.getInstance().returnListEquipe(this.connection);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            else if(request.getTag().equals("piloto")){
                try {
                    ServerController.getInstance().cadPiloto(this.connection, (Piloto) request.getParams());
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            }
            else if(request.getTag().equals("pista")){
                try {
                    ServerController.getInstance().cadPista(this.connection, (Pista) request.getParams());
                } catch (IOException ex) {
                    Logger.getLogger(ProtocolProcessor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if(request.getTag().equals("listaPista")){
                try {
                    ServerController.getInstance().returnListPista(this.connection);
                } catch (IOException ex) {
                    Logger.getLogger(ProtocolProcessor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if(request.getTag().equals("listaPiloto")){
                try {
                    ServerController.getInstance().returnListPiloto(this.connection);
                } catch (IOException ex) {
                    Logger.getLogger(ProtocolProcessor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if(request.getTag().equals("dadosCorrida")){
                try {
                    ServerController.getInstance().cadDadosCorrida(this.connection, (ArrayList) request.getParams());
                } catch (IOException ex) {
                    Logger.getLogger(ProtocolProcessor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if(request.getTag().equals("clas")){
                try {
                    ServerController.getInstance().getClassificacao(this.connection);
                } catch (IOException ex) {
                    Logger.getLogger(ProtocolProcessor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if(request.getTag().equals("corrida")){
                ServerController.getInstance().startCorrida(this.connection, (Classificacao) request.getParams());
            }
            else if(request.getTag().equals("returnCorrida")){
                try {
                    ServerController.getInstance().returnDadosCorrida(this.connection);
                } catch (IOException ex) {
                    Logger.getLogger(ProtocolProcessor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if(request.getTag().equals("pegarCodCarro")){
                try {
                    ServerController.getInstance().returnCodCarro(this.connection);
                } catch (IOException ex) {
                    Logger.getLogger(ProtocolProcessor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if(request.getTag().equals("enviarCodCarro")){
                try {
                    ServerController.getInstance().enviaCodCarro(this.connection, (Equipe) request.getParams());
                } catch (IOException ex) {
                    Logger.getLogger(ProtocolProcessor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if(request.getTag().equals("tempoClassificacao")){
                try {
                    ServerController.getInstance().enviaTempoClassificacao(this.connection, (long) request.getParams());
                } catch (IOException ex) {
                    Logger.getLogger(ProtocolProcessor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if(request.getTag().equals("tempoPiloto")){
                try {
                    ServerController.getInstance().enviaTempoDono(this.connection, (TempoDono) request.getParams());
                } catch (IOException ex) {
                    Logger.getLogger(ProtocolProcessor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if(request.getTag().equals("finalizaClassificacaoPiloto")){
                try {
                    ServerController.getInstance().finalizaClassificacaoPiloto(this.connection);
                } catch (IOException ex) {
                    Logger.getLogger(ProtocolProcessor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if(request.getTag().equals("resetaClassificacao")){
                try {
                    ServerController.getInstance().resetaClassificacao(this.connection);
                } catch (IOException ex) {
                    Logger.getLogger(ProtocolProcessor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }   
    }
}
