//Questão 1: Classe Abstrata e Polimorfismo
//Questão 1: Classe Abstrata e Polimorfismo

//1. Classe abstrata e polimorfismo 
//Crie a classe abstrata Conteudo, com atributos privados titulo (String) e duracaoMinutos (int), 
//construtor, getters, e o método abstrato calcularPontuacao() (retorna double). Inclua um método 
//concreto exibirFicha(), que utiliza o método abstrato calcularPontuacao(). 
//Crie pelo menos 2 subclasses concretas de da classe Conteudo (ex.: Filme, Serie, ou outras de sua 
//escolha), cada uma com uma regra própria de calcularPontuacao(). 

//Questão 1: Classe Abstrata e Polimorfismo
//Questão 1: Classe Abstrata e Polimorfismo
//Questão 1: Classe Abstrata e Polimorfismo

// Código 1: A classe mãe (Abstrata)
public abstract class Conteudo {
    private String titulo;
    private int duracaoMinutos;

    public Conteudo(String titulo, int duracaoMinutos) {
        this.titulo = titulo;
        this.duracaoMinutos = duracaoMinutos;
    }

    public String getTitulo() { return titulo; }
    public int getDuracaoMinutos() { return duracaoMinutos; }

    // Método abstrato: não tem corpo {}, termina com ponto e vírgula
    public abstract double calcularPontuacao();

    // Método concreto: usa o método abstrato que será decidido nas filhas
    public void exibirFicha() {
        System.out.println("Título: " + titulo + " | Pontuação: " + calcularPontuacao());
    }
}

// Código 2: Subclasse Filme
public class Filme extends Conteudo {
    public Filme(String titulo, int duracaoMinutos) {
        super(titulo, duracaoMinutos); // repassa para o construtor da mãe
    }

    @Override
    public double calcularPontuacao() {
        return getDuracaoMinutos() > 120 ? 9.0 : 7.5; // Regra própria do Filme
    }
}

// Código 3: Subclasse Serie
public class Serie extends Conteudo {
    public Serie(String titulo, int duracaoMinutos) {
        super(titulo, duracaoMinutos);
    }

    @Override
    public double calcularPontuacao() {
        return 8.5; // Regra própria da Série
    }
}




//Questão 2: Agregação
//Questão 2: Agregação
//Questão 2: Agregação

//q2. Agregação 
//Crie a classe PerfilUsuario, que mantém uma List<Conteudo> de favoritos, recebidos já criados de 
//fora, através do método adicionarFavorito(Conteudo c). O perfil deve ter um limite máximo de 
//favoritos, definido por você.

//Questão 2: Agregação
//Questão 2: Agregação
//Questão 2: Agregação

import java.util.ArrayList;
import java.util.List;

public class PerfilUsuario {
    // Mantém a lista de favoritos
    private List<Conteudo> favoritos;
    private final int LIMITE_MAX = 3; // Limite definido

    public PerfilUsuario() {
        this.favoritos = new ArrayList<>();
    }

    // Método de Agregação: recebe o Conteudo já criado lá de fora
    public void adicionarFavorito(Conteudo c) throws Exception { 
        if (favoritos.size() >= LIMITE_MAX) {
            throw new Exception("Limite atingido!"); // Será customizada na Q5
        }
        favoritos.add(c);
    }
}


//Questão 3: Composição
//Questão 3: Composição

//3. Composição 
//Crie a classe HistoricoVisualizacao, com um total de minutos assistidos (int) e um método 
//registrarVisualizacao(int minutos). A classe PerfilUsuario deve criar seu próprio 
//HistoricoVisualizacao DENTRO do construtor (não recebido por parâmetro). 

//Questão 3: Composição
//Questão 3: Composição
//Questão 3: Composição

// Código 1: A classe que será contida
public class HistoricoVisualizacao {
    private int totalMinutosAssistidos;

    public HistoricoVisualizacao() {
        this.totalMinutosAssistidos = 0;
    }

    public int getTotalMinutosAssistidos() { return totalMinutosAssistidos; }

    public void registrarVisualizacao(int minutos) {
        this.totalMinutosAssistidos += minutos;
    }
}

// Código 2: A classe dona (PerfilUsuario modificada)
public class PerfilUsuario {
    private List<Conteudo> favoritos;
    private HistoricoVisualizacao historico; // Atributo da composição

    public PerfilUsuario() {
        this.favoritos = new ArrayList<>();
        // COMPOSIÇÃO REQUISITO: O "new" obrigatoriamente é feito aqui DENTRO
        this.historico = new HistoricoVisualizacao(); 
    }

    public HistoricoVisualizacao getHistorico() { return historico; }
}



//Questão 4: Interface
//Questão 4: Interface

//4. Interface 
//Crie a interface Bloqueavel, com o método bloquear(). A classe PerfilUsuario deve implementar 
//essa interface, marcando o perfil como bloqueado quando o método for chamado. 

//Questão 4: Interface
//Questão 4: Interface
//Questão 4: Interface

// Código 1: Criação da Interface
public interface Bloqueavel {
    void bloquear(); // Métodos de interface são públicos e sem corpo por padrão
}

// Código 2: PerfilUsuario implementando a interface
public class PerfilUsuario implements Bloqueavel {
    private boolean bloqueado = false; // Atributo para controlar o estado

    public boolean isBloqueado() { return bloqueado; }

    // Obrigatório implementar o método do contrato
    @Override
    public void bloquear() {
        this.bloqueado = true;
    }
}



//Questão 5: Exceções Customizadas
//Questão 5: Exceções Customizadas

//5. Exceções customizadas 
//Crie uma exceção CHECKED (extends Exception) chamada LimiteFavoritosExcedidoException, 
//lançada pelo PerfilUsuario ao tentar adicionar um favorito além do limite máximo. 
//Crie uma exceção UNCHECKED (extends RuntimeException) chamada TempoInvalidoException, 
//lançada pelo HistoricoVisualizacao ao tentar registrar uma visualização com tempo menor ou igual a zero.
    
//Questão 5: Exceções Customizadas
//Questão 5: Exceções Customizadas
//Questão 5: Exceções Customizadas

// 1. Exceção CHECKED (Herda de Exception) - Obriga o uso de try/catch no código
public class LimiteFavoritosExcedidoException extends Exception {
    public LimiteFavoritosExcedidoException(String mensagem) {
        super(mensagem);
    }
}

// 2. Exceção UNCHECKED (Herda de RuntimeException) - Não obriga try/catch para compilar
public class TempoInvalidoException extends RuntimeException {
    public TempoInvalidoException(String mensagem) {
        super(mensagem);
    }
}

//Onde aplicá-las nas classes anteriores:

public void adicionarFavorito(Conteudo c) throws LimiteFavoritosExcedidoException {
    if (favoritos.size() >= LIMITE_MAX) {
        throw new LimiteFavoritosExcedidoException("Limite excedido!");
    }
    favoritos.add(c);
}


////////////////////////////////////////////////////////////




public void registrarVisualizacao(int minutos) {
    if (minutos <= 0) {
        throw new TempoInvalidoException("Tempo não pode ser menor ou igual a zero!");
    }
    this.totalMinutosAssistidos += minutos;
}

////////////////////////////////////////////////////////////


//Questão 6: Classe de Teste (Main)
//Questão 6: Classe de Teste (Main)

//6. Classe de teste 
//Crie uma classe TesteStreaming com um método main que demonstre TODOS os requisitos acima 
//funcionando: criação de um PerfilUsuario com favoritos de subclasses diferentes (mostrando o 
//polimorfismo), registro de visualizações, bloqueio do perfil, e a ocorrência (tratada com try/catch) 
//de cada uma das duas exceções customizadas.


//Questão 6: Classe de Teste (Main)
//Questão 6: Classe de Teste (Main)
//Questão 6: Classe de Teste (Main)


public class TesteStreaming {
    public static void main(String[] args) {
        // 1. Instanciando objetos das subclasses (Polimorfismo)
        Conteudo f1 = new Filme("Matrix", 136);
        Conteudo s1 = new Serie("Succession", 60);
        Conteudo f2 = new Filme("Avatar", 162);

        PerfilUsuario perfil = new PerfilUsuario();

        // 2. Testando Agregação e Exceção CHECKED (com try-catch obrigatório)
        try {
            perfil.adicionarFavorito(f1);
            perfil.adicionarFavorito(s1);
            perfil.adicionarFavorito(f2); // Vai estourar o limite se o limite for 2
        } catch (LimiteFavoritosExcedidoException e) {
            System.out.println("Erro capturado (Checked): " + e.getMessage());
        }

        // 3. Testando Composição e Exceção UNCHECKED (try-catch opcional na compilação)
        try {
            perfil.getHistorico().registrarVisualizacao(50);  // Funciona
            perfil.getHistorico().registrarVisualizacao(-5); // Força o erro
        } catch (TempoInvalidoException e) {
            System.out.println("Erro capturado (Unchecked): " + e.getMessage());
        }

        // 4. Testando método da Interface
        System.out.println("Bloqueado antes? " + perfil.isBloqueado());
        perfil.bloquear();
        System.out.println("Bloqueado depois? " + perfil.isBloqueado());
    }
}




