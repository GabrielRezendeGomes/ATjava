package br.com.infnet.AtJava.Controller;

import br.com.infnet.AtJava.Model.CepResponse;
import br.com.infnet.AtJava.Model.Clientes;
import br.com.infnet.AtJava.Service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("consulta-cep")
public class ApiCep {

    private final RestTemplate restTemplate;
    private final ClientService clientService;

    public ApiCep(RestTemplate restTemplate, ClientService clientService) {
        this.restTemplate = restTemplate;
        this.clientService = clientService;
    }

    @GetMapping("/{cep}")
    public CepResponse consultaCep(@PathVariable("cep") String cep) {
        ResponseEntity<CepResponse> resp = restTemplate.getForEntity(
                String.format("https://brasilapi.com.br/api/cep/v1/%s", cep), CepResponse.class);


        clientService.addClientWithCep(resp.getBody());

        return resp.getBody();
    }

    @GetMapping("/all")
    public List<Clientes> getAllClientes() {
        return clientService.initClientes();
    }


    @PostMapping
    public ResponseEntity<String> cadastraCep(@RequestBody CepResponse cepResponse) {
        if (clientService.isCepAlreadyExists(String.valueOf(cepResponse.getCep()))) {
            return ResponseEntity.badRequest().body("Cliente com o CEP fornecido já existe.");
        }


        clientService.addClientWithCep(cepResponse);

        return ResponseEntity.ok("CEP cadastrado com sucesso!");
    }

    @PutMapping("/{cep}")
    public ResponseEntity<String> atualizaCep(@PathVariable("cep") String cep,
                                              @RequestBody CepResponse cepResponse) {
        Clientes existingClient = clientService.getClientByCep(cep);

        if (existingClient != null) {

            existingClient.setCep(String.valueOf(cepResponse.getCep()));
            existingClient.setNome(cepResponse.getService());
            clientService.update(existingClient.getId(), existingClient);
            return ResponseEntity.ok("CEP atualizado com sucesso!");
        } else {
            return ResponseEntity.badRequest().body("Cliente não encontrado para o CEP fornecido.");
        }
    }

    @DeleteMapping("/{cep}")
    public ResponseEntity<String> deletaCep(@PathVariable("cep") String cep) {
        Clientes clientToDelete = clientService.getClientByCep(cep);

        if (clientToDelete != null) {

            clientService.deleteById(clientToDelete.getId());
            return ResponseEntity.ok("CEP deletado com sucesso!");
        } else {
            return ResponseEntity.badRequest().body("Cliente não encontrado para o CEP fornecido.");
        }
    }
}

