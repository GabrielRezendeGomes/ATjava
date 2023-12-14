package br.com.infnet.AtJava;

import br.com.infnet.AtJava.Model.CepResponse;
import br.com.infnet.AtJava.Model.Clientes;
import br.com.infnet.AtJava.Service.ClientService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ClienteServiceTest {
    @Autowired
    ClientService clientService;

    @Test
    @DisplayName("Retorna todos de clientes")
    void deveRetornarTodosOsClientes(){
        List<Clientes> clientes = clientService.getAll();
        assertEquals(3,clientes.size());


    }
    @Test
    @DisplayName("Retorna cliente por id")
    void deveRetornarClientePorId(){
        Clientes clientes = clientService.getById(1l);
        assertEquals(clientes.getNome(), "Pedro");
        assertEquals(clientes.getId(),1l);
        assertEquals(clientes.getCep(),24220380);
    }

    @Test
    @DisplayName("Deve remover cliente por id")
    void deveDeletarClientePorId(){
        clientService.deleteById(1l);
        List<Clientes> clientes = clientService.getAll();
        assertEquals(2,clientes.size());
    }
    @Test
    @DisplayName("Deve alterar dados de um cliente")
    void testaUpdate(){
        Clientes Cesar = new Clientes(1L,"Cesar","28415000");
        clientService.update(1L,Cesar);
        Clientes atualizado = clientService.getById(1L);
        assertEquals("Cesar",atualizado.getNome());
    }
    @Test
    @DisplayName("Adiciona um novo cliente com base nas informações do CEP")
    void deveAdicionarClienteComCep() {

        CepResponse cepResponse = new CepResponse(12345, "sp","sao paulo","guaruja","rua dos pinheiros","correio");
        clientService.addClientWithCep(cepResponse);

        Clientes addedClient = clientService.getClientByCep("12345");
        assertEquals("ExampleService", addedClient.getNome());
    }

    @Test
    @DisplayName("Verifica se um CEP já existe para algum cliente")
    void deveVerificarSeCepJaExiste() {

        String cepToCheck = "24220380";
        boolean cepExists = clientService.isCepAlreadyExists(cepToCheck);

    }



}
