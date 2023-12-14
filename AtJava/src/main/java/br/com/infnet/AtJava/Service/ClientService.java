package br.com.infnet.AtJava.Service;

import br.com.infnet.AtJava.Model.CepResponse;
import br.com.infnet.AtJava.Model.Clientes;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class ClientService {

    private List<Clientes> clientes = initClientes();

    public List<Clientes> initClientes() {
        Clientes Pedro = new Clientes(1L, "Pedro", "24220380");
        Clientes Paulo = new Clientes(2L, "Paulo", "24220450");
        Clientes Mauro = new Clientes(3L, "Mauro", "24240180");

        List<Clientes> clientes = new ArrayList<>();
        clientes.add(Pedro);
        clientes.add(Paulo);
        clientes.add(Mauro);
        return clientes;
    }

    public List<Clientes> getAll() {
        return clientes;
    }

    public Clientes getById(long id) {
        return clientes.stream()
                .filter(cliente -> cliente.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void deleteById(long id) {
        clientes.removeIf(cliente -> cliente.getId() == id);
    }

    public void update(long id, Clientes cliente) {

        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getId() == id) {
                clientes.set(i, cliente);
                break;
            }
        }
    }

    public void addClientWithCep(CepResponse cepResponse) {

        Clientes cliente = new Clientes();
        cliente.setNome(cepResponse.getService());
        cliente.setCep(String.valueOf(cepResponse.getCep()));
        clientes.add(cliente);
    }

    public Clientes getClientByCep(String cep) {
        return clientes.stream()
                .filter(cliente -> cliente.getCep().equals(cep))
                .findFirst()
                .orElse(null);
    }

    public boolean isCepAlreadyExists(String cep) {
        return clientes.stream()
                .anyMatch(cliente -> cliente.getCep().equals(cep));
    }
}


