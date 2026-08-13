package br.com.alura.screenmatch.service;

import tools.jackson.databind.ObjectMapper;

public class ConverteDados implements IConverteDados {
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T obterDados(String json, Class<T> classe) { // Ta fazendo a calsse T(Generics) usando o mapper, que
                                                            // seria o FromJson do Gson,e o Class, faz devolver o
                                                            //mapper devolver somente o que a gente quer
        return mapper.readValue(json, classe);
    }
}