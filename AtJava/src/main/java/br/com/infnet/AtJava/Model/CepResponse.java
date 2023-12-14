package br.com.infnet.AtJava.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CepResponse {



    private int cep;
    private String state;
    private String city;
    private String neighborhood;
    private String street;
    private String service;

    public CepResponse(int cep, String state, String city, String neighborhood, String street, String service) {
        this.cep = cep;
        this.state = state;
        this.city = city;
        this.neighborhood = neighborhood;
        this.street = street;
        this.service = service;
    }


}

