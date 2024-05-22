package uca.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BarService {
    private MaquinaCafe maquinaCafe;

    // Inyecta la dependencia de MaquinaCafe
    @Autowired
    public BarService(MaquinaCafe maquinaCafe) {
        this.maquinaCafe = maquinaCafe;
    }

    // Devuelve un string con el pedido de cafe y la preparacion del cafe
    public String pedirCafe(String tipoCafe, int cucharadasAzucar) {
        String pedido = "";
        pedido += "Pedido de " + tipoCafe + " con ";
        pedido += cucharadasAzucar + " cucharadas de azucar\n";
        
        pedido += maquinaCafe.prepararCafe(cucharadasAzucar) + "\n";
        return pedido;
    }
}
