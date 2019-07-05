package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Classificacao implements Serializable{
    Pista pista;
    List <VoltaPiloto> voltaPilotoReturn;
    VoltaPiloto pi1, pi2, pi3, pi4;
    Piloto p1,p2,p3,p4;
    float melhorTempo;
    String donoMelhorTempo;

    
    public Classificacao(Pista pista, Piloto piloto1, Piloto piloto2, Piloto piloto3, Piloto piloto4) {
        this.pista = pista;
        this.p1 = piloto1; 
        this.p2 = piloto2;
        this.p3 = piloto3;
        this.p4 = piloto4;
    }
    
    public void sincronizaDados(){
        voltaPilotoReturn.clear();
        voltaPilotoReturn.add(pi1);
        voltaPilotoReturn.add(pi2);
        voltaPilotoReturn.add(pi3);
        voltaPilotoReturn.add(pi4);
        Collections.sort(voltaPilotoReturn);
    }

    public Pista getPista() {
        return pista;
    }

    public void setPista(Pista pista) {
        this.pista = pista;
    }

    public List<VoltaPiloto> getVoltaPiloto() {
        return voltaPilotoReturn;
    }

    public void setVoltaPiloto(List<VoltaPiloto> voltaPiloto) {
        this.voltaPilotoReturn = voltaPiloto;
    }

    public float getMelhorTempo() {
        return melhorTempo;
    }

    public void setMelhorTempo(float melhorTempo) {
        this.melhorTempo = melhorTempo;
    }

    public String getDonoMelhorTempo() {
        return donoMelhorTempo;
    }

    public void setDonoMelhorTempo(String donoMelhorTempo) {
        this.donoMelhorTempo = donoMelhorTempo;
    }

    public VoltaPiloto getPi1() {
        return pi1;
    }

    public void setPi1(VoltaPiloto pi1) {
        this.pi1 = pi1;
    }

    public VoltaPiloto getPi2() {
        return pi2;
    }

    public void setPi2(VoltaPiloto pi2) {
        this.pi2 = pi2;
    }

    public VoltaPiloto getPi3() {
        return pi3;
    }

    public void setPi3(VoltaPiloto pi3) {
        this.pi3 = pi3;
    }

    public VoltaPiloto getPi4() {
        return pi4;
    }

    public void setPi4(VoltaPiloto pi4) {
        this.pi4 = pi4;
    }

    
}
