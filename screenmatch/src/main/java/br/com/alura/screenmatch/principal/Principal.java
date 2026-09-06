package br.com.alura.screenmatch.principal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

public class Principal {
    private final String ENDERECO = "http://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=297a85c3";
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    Scanner scanner = new Scanner(System.in);

    public void exibeMenu() {
        System.out.println("Digite o nome da seria para busca");
        var nomeSerie = scanner.nextLine();
        var json = consumoApi.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

        List<DadosTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i <= dados.totalTemporadas(); i++) {

            json = consumoApi.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&Season=" + i + API_KEY);
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }
        temporadas.forEach(t -> System.out.println(t));
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()) // puxa todas inforamcoes dentro de outra lista
                .collect(Collectors.toList()); // pega a lista dadosEpisodio e tranfere as informcaoes
        // toList() e imutavel
        System.out.println("Top 5 episodios");

        dadosEpisodios.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                .limit(5)
                .forEach(e -> System.out.println(e));

                List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()) // puxa todas inforamcoes dentro de outra lista
                .map(e -> new Episodio(e.numero(), e))
                .collect(Collectors.toList()); // pega a lista dadosEpisodio e tranfere as informcaoes

                episodios.forEach(System.out::println);

                System.out.println("A partir de que ano voce deseja ver os episodios?");
                var ano = scanner.nextInt();
                scanner.nextLine();
                
                LocalDate dataBusca = LocalDate.of(ano, 1, 1);
                DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                episodios.stream()
                .filter(e -> e.getDataLanmcamento() != null && e.getDataLanmcamento().isAfter(dataBusca))
                .forEach(e -> System.out.println(
                    "Temporada: " + e.getTemproada() +
                    "Episodio: " + e.getNumero() +
                    "Data lancamento: " + e.getDataLanmcamento().format(formatador)
                ));
    }
}
