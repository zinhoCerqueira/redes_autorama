package model;

import java.util.Collections;
import java.util.List;


public class Corrida {
    Pista pista;
    List <VoltaPiloto> voltaPilotoReturn;
    VoltaPiloto pi1, pi2, pi3, pi4;
    float melhorTempo;
    String donoMelhorTempo;
    
    public Corrida(Pista pista, Piloto p1, Piloto p2, Piloto p3, Piloto p4) {
        this.pista = pista;
        pi1.setPiloto(p1);
        pi2.setPiloto(p2);
        pi3.setPiloto(p3);
        pi4.setPiloto(p4);
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

    public List<VoltaPiloto> getVoltaPilotoReturn() {
        return voltaPilotoReturn;
    }

    public void setVoltaPilotoReturn(List<VoltaPiloto> voltaPilotoReturn) {
        this.voltaPilotoReturn = voltaPilotoReturn;
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
    
    
}
