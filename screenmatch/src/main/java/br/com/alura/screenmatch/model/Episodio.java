package br.com.alura.screenmatch.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episodio {
    private Integer temporada;
    private String titulo;
    private Integer numero;
    private Double avaliacao;
    private LocalDate dataLanmcamento;
    
    public Episodio(Integer numeroTemporada, DadosEpisodio dadosEpisodio) {
        this.temporada = numeroTemporada;
        this.titulo = dadosEpisodio.titulo();
        this.numero = dadosEpisodio.numero();
        try{
        this.avaliacao = Double.valueOf(dadosEpisodio.avaliacao());    
        } catch(NumberFormatException ex){
            this.avaliacao = 0.0;
        }
        try{
                    this.dataLanmcamento = LocalDate.parse(dadosEpisodio.dataLanmcamento());
        }catch(DateTimeParseException ex){
            this.dataLanmcamento = null;
        }
    }
    public Integer getTemproada() {
        return temporada;
    }
    public void setTemproada(Integer temporada) {
        this.temporada = temporada;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public Integer getNumero() {
        return numero;
    }
    public void setNumero(Integer numero) {
        this.numero = numero;
    }
    public Double getAvaliacao() {
        return avaliacao;
    }
    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }
    public LocalDate getDataLanmcamento() {
        return dataLanmcamento;
    }
    public void setDataLanmcamento(LocalDate dataLanmcamento) {
        this.dataLanmcamento = dataLanmcamento;
    }

    @Override
    public String toString() {
        return "temporadas=" + temporada + 
        ", titulo=" + titulo +
        ", numeroEpisodio=" + numero + 
        ", avaliacao=" + avaliacao +
        ",dataLancamento=" + dataLanmcamento;
    }

}
