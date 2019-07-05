package controller;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Classificacao;
import model.Corrida;
import model.Equipe;
import model.Piloto;
import model.Pista;
import model.TempoDono;
import model.VoltaPiloto;

public class ServerController {
    private static ServerController controller;
    List <Piloto> listaPiloto  = new ArrayList<>();
    List <Equipe> listaEquipe  = new ArrayList<>();
    List <Pista> listaPista = new ArrayList<>();
    Classificacao classificacao = null;
    VoltaPiloto voltaPiloto;
    long tempoClassificacao;
    Corrida corrida;
    String codCarro = null;

    
    public static ServerController getInstance(){
        if(controller == null){
            controller = new ServerController();
        }
        return controller;
    }

    private void enviarResposta(Socket client, String resposta) throws IOException {
        try{
            DataOutputStream saida = new DataOutputStream(client.getOutputStream());
            saida.write(resposta.getBytes());
            saida.flush();
            client.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        
    }



    public void cadEquipe(Socket connection, Equipe equipe) throws IOException {
        String resposta = "Sucesso";
        listaEquipe.add(equipe);
        this.enviarResposta(connection, resposta); 
    }

    public void cadPiloto(Socket connection, Piloto piloto) throws IOException {
        String resposta = "Sucesso";
        listaPiloto.add(piloto);
        this.enviarResposta(connection, resposta); 
    }

    public void cadPista(Socket connection, Pista pista) throws IOException {
        String resposta = "Sucesso";
        listaPista.add(pista);
        this.enviarResposta(connection, resposta);
    }
    
    public void returnListEquipe(Socket client) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
        out.writeObject(listaEquipe);
        client.close();
    }

    public void returnListPista(Socket client) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
        out.writeObject(listaPista);
        client.close();
    }

    public void returnListPiloto(Socket client) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
        out.writeObject(listaPiloto);
        client.close();
    }

    //Método que recebe os dados para cadastrar uma corrida.
    public void cadDadosCorrida(Socket connection, ArrayList arrayList) throws IOException {
        
        Pista pista  = retornaPista((String) arrayList.get(0));
        Piloto p1  = retornaPiloto((String) arrayList.get(1));
        Piloto p2 = retornaPiloto((String) arrayList.get(2));
        Piloto p3  = retornaPiloto((String) arrayList.get(3));
        Piloto p4  = retornaPiloto((String) arrayList.get(4));
        System.out.println("1");
        
        classificacao = new Classificacao(pista, p1, p2, p3, p4);
     
        System.out.println("aqui ta chegando");
        
        
        String mensagem = "Esperando dados da Classificação.";
        this.enviarResposta(connection, mensagem);
    }
    
    //Método que recebe os dados do sensor e organiza a classificação da corrida.
    public void recebeSensor(Socket connection, Piloto x, Float tempo){
        if(x == classificacao.getPi1().getPiloto()){
            classificacao.getPi1().getVoltas().add(tempo);
        }
        else if(x == classificacao.getPi2().getPiloto()){
            classificacao.getPi2().getVoltas().add(tempo);
        }
        else if(x == classificacao.getPi3().getPiloto()){
            classificacao.getPi3().getVoltas().add(tempo);
        }
        else if(x == classificacao.getPi4().getPiloto()){
            classificacao.getPi4().getVoltas().add(tempo);
        }
        
        classificacao.sincronizaDados();
        
        //Mandar esses dados que já estão listados nas colocações corretas pro client que visualiza a corrida.
        //-------------
        //pedir esses dados no cliente que viualiza a corrida
        //-------------
        
    }
    
    //Metodos que retornam um individuo de uma lista -----------------------------------------------------------
    // ---------------------------------------------------------------------------------------------------------
    
    //Método que retorna os dados de uma pista. ----------------------------------------------------------------
    public Pista retornaPista(String pista){
        Pista x = null;
        for(Pista i: listaPista){
            if(pista.equals(i.getNome())){
                return i;
            }
        }
        return x;
    }
    
    //Método que retorna os dados de um piloto. ----------------------------------------------------------------
    public Piloto retornaPiloto(String piloto){
        Piloto x = null;
        for(Piloto i: listaPiloto){
            if(piloto.equals(i.getNome())){
                return i;
            }
        }
        return x;
    }
    
    //Método que retorna a ultima classificação que teve.
    public void getClassificacao(Socket client) throws IOException { 
        ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
        out.writeObject(voltaPiloto);
        client.close();
    }

    public void startCorrida(Socket connection, Classificacao classificacao) {
        corrida.setPista(classificacao.getPista());
        corrida.setPi1(classificacao.getPi1());
        corrida.setPi2(classificacao.getPi2());
        corrida.setPi3(classificacao.getPi3());
        corrida.setPi4(classificacao.getPi4());
    }
    
    public void recebeSensorCorrida(Piloto x, Float tempo){
        if(x == corrida.getPi1().getPiloto()){
            corrida.getPi1().getVoltas().add(tempo);
        }
        else if(x == corrida.getPi2().getPiloto()){
            corrida.getPi2().getVoltas().add(tempo);
        }
        else if(x == corrida.getPi3().getPiloto()){
            corrida.getPi3().getVoltas().add(tempo);
        }
        else if(x == corrida.getPi4().getPiloto()){
            corrida.getPi4().getVoltas().add(tempo);
        }
        
        corrida.sincronizaDados();
        
    }
    
    public void returnDadosCorrida(Socket client) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
        out.writeObject(corrida);
        client.close();
    }

    public void returnCodCarro(Socket client) throws IOException {
        //Simulação do método que recebe os dados do sensor.
        if(codCarro != null ){
            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
            out.writeObject(codCarro);
            client.close();
            codCarro = null;
        }
    }

    public void enviaCodCarro(Socket connection, Equipe string) throws IOException {
        codCarro = string.getNome();
        connection.close();
    }

    public void enviaTempoClassificacao(Socket connection, long l) throws IOException {
        tempoClassificacao = l;
        System.out.println(l);
        connection.close();
    }

    public void enviaTempoDono(Socket connection, TempoDono tempoDono) throws IOException{
        Piloto p = retornaPilotoComBasenoCarro(tempoDono);
        voltaPiloto.setPiloto(p);
        voltaPiloto.getVoltas().add(tempoDono);
        connection.close();
    }

    private Piloto retornaPilotoComBasenoCarro(TempoDono tempoDono) {
        Piloto pilotoReturn;
        for(Object i : listaPiloto){
            Piloto p = (Piloto) i;
            if(p.getCarro().getCod().equals(tempoDono.getDono())){
                return p;
            }
        }
        return null;
    }

    public void finalizaClassificacaoPiloto(Socket connection) throws IOException {
        classificacao.getVoltaPiloto().add(voltaPiloto);
        voltaPiloto = null;
        connection.close();
    }

    public void resetaClassificacao(Socket connection) throws IOException {
        voltaPiloto = null;
        classificacao = null;
        corrida = null;
        connection.close();
    }
    
    public long diferencaTempo(Date x, Date y){
    
    }



    


}
