/*
 * GUIA DE CONSULTA - POO II (Java)
 *
 * Arquivo único de referência para estudar e consultar conceitos:
 * - Encapsulamento e modificadores de acesso
 * - Associação, agregação e composição
 * - Classe abstrata e herança
 * - Interface
 * - Polimorfismo, sobrescrita (override) e sobrecarga (overload)
 * - Exceções checked e unchecked
 * - throw, throws, try, catch e finally
 *
 * Observação:
 * Este exemplo foi organizado em um único arquivo para facilitar a consulta.
 * Em um projeto real, cada classe pública normalmente ficaria em seu próprio arquivo.
 *
 * Para compilar/executar:
 *   javac Main.java
 *   java Main
 */

import java.util.ArrayList;
import java.util.List;

// ============================================================
// 1. CLASSE ABSTRATA: define características e comportamentos
//    comuns, mas não deve ser instanciada diretamente.
// ============================================================
abstract class Conteudo {
    // private = encapsulamento: o atributo só é acessado diretamente
    // dentro desta classe. Outras classes usam métodos públicos.
    private String titulo;
    private int duracaoMinutos;

    public Conteudo(String titulo, int duracaoMinutos) {
        this.titulo = titulo;
        this.duracaoMinutos = duracaoMinutos;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public void setDuracaoMinutos(int duracaoMinutos) {
        this.duracaoMinutos = duracaoMinutos;
    }

    // Método abstrato: cada subclasse precisa fornecer sua implementação.
    public abstract double calcularNota();

    // Método concreto: já possui implementação e pode ser herdado.
    public void exibirInformacoes() {
        System.out.println("Título: " + titulo);
        System.out.println("Duração: " + duracaoMinutos + " min");
        System.out.println("Nota: " + calcularNota());
    }
}

// ============================================================
// 2. HERANÇA + OVERRIDE
//    Filme e Serie são tipos específicos de Conteudo.
// ============================================================
class Filme extends Conteudo {
    private double avaliacaoPublico; // exemplo: escala de 0 a 10

    public Filme(String titulo, int duracaoMinutos, double avaliacaoPublico) {
        super(titulo, duracaoMinutos); // chama o construtor da classe-pai
        this.avaliacaoPublico = avaliacaoPublico;
    }

    // @Override indica sobrescrita de método herdado/abstrato.
    // A assinatura deve corresponder à definida na classe-pai.
    @Override
    public double calcularNota() {
        return avaliacaoPublico;
    }
}

class Serie extends Conteudo {
    private double mediaTemporadas;

    public Serie(String titulo, int duracaoMinutos, double mediaTemporadas) {
        super(titulo, duracaoMinutos);
        this.mediaTemporadas = mediaTemporadas;
    }

    @Override
    public double calcularNota() {
        // Regra ilustrativa: a série usa a média das temporadas.
        return mediaTemporadas;
    }
}

// ============================================================
// 3. INTERFACE
//    Uma interface define um contrato que classes implementam.
// ============================================================
interface Bloqueavel {
    void bloquear();
    void desbloquear();
    boolean estaBloqueado();
}

class PerfilControle implements Bloqueavel {
    private boolean bloqueado;

    @Override
    public void bloquear() {
        bloqueado = true;
    }

    @Override
    public void desbloquear() {
        bloqueado = false;
    }

    @Override
    public boolean estaBloqueado() {
        return bloqueado;
    }
}

// ============================================================
// 4. EXCEÇÕES PERSONALIZADAS
// ============================================================

// Checked: herda de Exception.
// O método que pode lançá-la precisa tratar com try/catch ou declarar throws.
class LimiteFavoritosExcedidoException extends Exception {
    public LimiteFavoritosExcedidoException(String mensagem) {
        super(mensagem);
    }
}

// Unchecked: herda de RuntimeException.
// Não exige declaração obrigatória com throws nem tratamento obrigatório.
class TempoInvalidoException extends RuntimeException {
    public TempoInvalidoException(String mensagem) {
        super(mensagem);
    }
}

// ============================================================
// 5. CLASSE DE HISTÓRICO
//    O PerfilUsuario cria e controla seu histórico internamente.
//    Exemplo didático de composição: o objeto é criado dentro do
//    perfil e faz parte de sua estrutura.
// ============================================================
class HistoricoVisualizacao {
    private List<String> registros = new ArrayList<>();

    public void registrar(String titulo, int minutosAssistidos) {
        registros.add(titulo + " - " + minutosAssistidos + " min");
    }

    public void exibirHistorico() {
        for (String registro : registros) {
            System.out.println(registro);
        }
    }
}

// ============================================================
// 6. PERFIL DE USUÁRIO
//    Mantém uma lista de conteúdos favoritos.
//    A lista recebe objetos Conteudo já existentes: exemplo de
//    agregação, pois o perfil referencia objetos independentes.
// ============================================================
class PerfilUsuario {
    private String nome;
    private int limiteFavoritos;
    private List<Conteudo> favoritos;
    private HistoricoVisualizacao historico;

    public PerfilUsuario(String nome, int limiteFavoritos) {
        this.nome = nome;
        this.limiteFavoritos = limiteFavoritos;
        this.favoritos = new ArrayList<>();

        // Composição no exemplo: o histórico nasce junto do perfil.
        this.historico = new HistoricoVisualizacao();
    }

    public String getNome() {
        return nome;
    }

    public List<Conteudo> getFavoritos() {
        return favoritos;
    }

    public HistoricoVisualizacao getHistorico() {
        return historico;
    }

    // throws declara que o método pode propagar uma exceção checked.
    public void adicionarFavorito(Conteudo conteudo)
            throws LimiteFavoritosExcedidoException {

        if (favoritos.size() >= limiteFavoritos) {
            // throw efetivamente lança uma exceção.
            throw new LimiteFavoritosExcedidoException(
                    "O perfil atingiu o limite de favoritos."
            );
        }

        favoritos.add(conteudo);
    }

    public void registrarVisualizacao(Conteudo conteudo, int minutosAssistidos) {
        // Exceção unchecked para validar um argumento inválido.
        if (minutosAssistidos < 0) {
            throw new TempoInvalidoException(
                    "O tempo assistido não pode ser negativo."
            );
        }

        historico.registrar(conteudo.getTitulo(), minutosAssistidos);
    }

    public void listarFavoritos() {
        for (Conteudo conteudo : favoritos) {
            // Polimorfismo: calcularNota() executa a versão adequada
            // ao tipo real do objeto (Filme ou Serie).
            System.out.println(
                    conteudo.getTitulo() + " | Nota: " + conteudo.calcularNota()
            );
        }
    }

    // ========================================================
    // 7. SOBRECARGA (OVERLOAD)
    //    Mesmo nome, parâmetros diferentes.
    // ========================================================
    public void exibirResumo() {
        System.out.println("Perfil: " + nome);
    }

    public void exibirResumo(boolean mostrarQuantidade) {
        System.out.println("Perfil: " + nome);
        if (mostrarQuantidade) {
            System.out.println("Favoritos: " + favoritos.size());
        }
    }
}

// ============================================================
// 8. CLASSE PRINCIPAL: demonstração de uso
// ============================================================
public class Main {
    public static void main(String[] args) {
        Filme filme = new Filme("Exemplo de filme", 120, 8.5);
        Serie serie = new Serie("Exemplo de série", 45, 9.0);

        // Polimorfismo: variável da classe-pai aponta para objetos-filhos.
        Conteudo conteudo1 = filme;
        Conteudo conteudo2 = serie;

        conteudo1.exibirInformacoes();
        conteudo2.exibirInformacoes();

        // Coleção polimórfica: uma lista do tipo-pai guarda subclasses.
        List<Conteudo> catalogo = new ArrayList<>();
        catalogo.add(filme);
        catalogo.add(serie);

        PerfilUsuario perfil = new PerfilUsuario("Ana", 2);

        // Checked exception: precisa ser tratada ou declarada.
        try {
            for (Conteudo conteudo : catalogo) {
                perfil.adicionarFavorito(conteudo);
            }

            // Tentativa extra para demonstrar a exceção de limite.
            perfil.adicionarFavorito(filme);

        } catch (LimiteFavoritosExcedidoException e) {
            System.out.println("Não foi possível adicionar: " + e.getMessage());

        } finally {
            // finally executa após try/catch, sendo útil para finalização.
            System.out.println("Operação de favoritos finalizada.");
        }

        perfil.listarFavoritos();

        // Unchecked exception: também pode ser capturada, embora não seja
        // obrigatório tratá-la para compilar.
        try {
            perfil.registrarVisualizacao(filme, -10);
        } catch (TempoInvalidoException e) {
            System.out.println("Tempo inválido: " + e.getMessage());
        }

        perfil.registrarVisualizacao(serie, 30);
        System.out.println("Histórico:");
        perfil.getHistorico().exibirHistorico();

        // Demonstração da interface: usamos uma referência do tipo contrato.
        Bloqueavel controle = new PerfilControle();
        controle.bloquear();
        System.out.println("Bloqueado? " + controle.estaBloqueado());

        perfil.exibirResumo();
        perfil.exibirResumo(true);

        // ========================================================
        // 9. CASTING (conversão de referência)
        // ========================================================
        Conteudo referencia = new Filme("Outro filme", 90, 7.5);

        // Upcasting: implícito e seguro (subclasse -> superclasse).
        Conteudo paraClassePai = new Filme("Upcasting", 100, 8.0);

        // Downcasting: superclasse -> subclasse; requer cuidado.
        if (referencia instanceof Filme) {
            Filme filmeConvertido = (Filme) referencia;
            System.out.println(filmeConvertido.getTitulo());
        }
    }
}

/*
 * ============================================================
 * RESUMO RÁPIDO PARA CONSULTA
 * ============================================================
 *
 * MODIFICADORES:
 * - private: acesso somente dentro da própria classe.
 * - (sem modificador): acesso dentro do pacote.
 * - protected: pacote e subclasses, conforme as regras de acesso.
 * - public: acesso amplo.
 *
 * RELACIONAMENTOS:
 * - Associação: relação geral entre classes/objetos.
 * - Agregação: relação todo-parte mais fraca; as partes podem existir
 *   independentemente do todo.
 * - Composição: relação todo-parte forte; o todo controla a criação/
 *   ciclo de vida da parte no modelo.
 *
 * ABSTRATA x INTERFACE:
 * - Classe abstrata pode ter atributos, construtores e métodos concretos
 *   ou abstratos; uma classe estende apenas uma classe.
 * - Interface define contrato; uma classe pode implementar várias interfaces.
 *
 * POLIMORFISMO:
 * - Uma referência do tipo mais geral pode apontar para um objeto específico.
 * - A sobrescrita permite que a implementação executada dependa do tipo real.
 * - Override: mesmo método/signatura herdada, nova implementação.
 * - Overload: mesmo nome, parâmetros diferentes.
 *
 * EXCEÇÕES:
 * - try: envolve código que pode falhar.
 * - catch: trata uma exceção.
 * - finally: bloco de finalização após try/catch.
 * - throw: lança uma exceção.
 * - throws: declara que um método pode propagar exceção.
 * - Checked: subclasses de Exception que não sejam RuntimeException.
 * - Unchecked: RuntimeException e subclasses.
 */

/* 1. Classe abstrata e polimorfismo 
Crie a classe abstrata Conteudo, com atributos privados titulo (String) e duracaoMinutos (int), 
construtor, getters, e o método abstrato calcularPontuacao() (retorna double). Inclua um método 
concreto exibirFicha(), que utiliza o método abstrato calcularPontuacao(). 
Crie pelo menos 2 subclasses concretas de da classe Conteudo (ex.: Filme, Serie, ou outras de sua 
escolha), cada uma com uma regra própria de calcularPontuacao(). 
2. Agregação 
Crie a classe PerfilUsuario, que mantém uma List<Conteudo> de favoritos, recebidos já criados de 
fora, através do método adicionarFavorito(Conteudo c). O perfil deve ter um limite máximo de 
favoritos, definido por você. 
3. Composição 
Crie a classe HistoricoVisualizacao, com um total de minutos assistidos (int) e um método 
registrarVisualizacao(int minutos). A classe PerfilUsuario deve criar seu próprio 
HistoricoVisualizacao DENTRO do construtor (não recebido por parâmetro). 
4. Interface 
Crie a interface Bloqueavel, com o método bloquear(). A classe PerfilUsuario deve implementar 
essa interface, marcando o perfil como bloqueado quando o método for chamado. 
5. Exceções customizadas 
Crie uma exceção CHECKED (extends Exception) chamada LimiteFavoritosExcedidoException, 
lançada pelo PerfilUsuario ao tentar adicionar um favorito além do limite máximo. 
Crie uma exceção UNCHECKED (extends RuntimeException) chamada TempoInvalidoException, 
lançada pelo HistoricoVisualizacao ao tentar registrar uma visualização com tempo menor ou igual 
a zero. 
6. Classe de teste 
Crie uma classe TesteStreaming com um método main que demonstre TODOS os requisitos acima 
funcionando: criação de um PerfilUsuario com favoritos de subclasses diferentes (mostrando o 
polimorfismo), registro de visualizações, bloqueio do perfil, e a ocorrência (tratada com try/catch) 
de cada uma das duas exceções customizadas.*/
/*
 * Classe abstrata que representa qualquer conteúdo da plataforma.
 */
public abstract class Conteudo {
    private String titulo;
    private int duracaoMinutos;

    public Conteudo(String titulo, int duracaoMinutos) {
        this.titulo = titulo;
        this.duracaoMinutos = duracaoMinutos;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getDuracaoMinutos() {
        return duracaoMinutos;
    }

    // Cada subclasse deverá definir sua própria regra de pontuação.
    public abstract double calcularPontuacao();

    /*
     * Método concreto que usa o método abstrato.
     * Isso evidencia o polimorfismo em tempo de execução.
     */
    public void exibirFicha() {
        System.out.println("Título: " + titulo);
        System.out.println("Duração: " + duracaoMinutos + " minutos");
        System.out.println("Pontuação: " + calcularPontuacao());
    }
}

//ESPAÇO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

/*
 * Subclasse concreta de Conteudo.
 */
public class Filme extends Conteudo {
    private boolean lancamento;

    public Filme(String titulo, int duracaoMinutos, boolean lancamento) {
        super(titulo, duracaoMinutos);
        this.lancamento = lancamento;
    }

    /*
     * Regra própria: filmes recebem 1 ponto a cada 30 minutos.
     * Lançamentos recebem 2 pontos extras.
     */
    @Override
    public double calcularPontuacao() {
        double pontos = getDuracaoMinutos() / 30.0;

        if (lancamento) {
            pontos += 2.0;
        }

        return pontos;
    }
}

//ESPAÇO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

/*
 * Outra subclasse concreta de Conteudo.
 */
public class Serie extends Conteudo {
    private int numeroTemporadas;

    public Serie(String titulo, int duracaoMinutos, int numeroTemporadas) {
        super(titulo, duracaoMinutos);
        this.numeroTemporadas = numeroTemporadas;
    }

    /*
     * Regra própria: séries recebem 1 ponto a cada 20 minutos,
     * mais 0,5 ponto por temporada.
     */
    @Override
    public double calcularPontuacao() {
        return (getDuracaoMinutos() / 20.0) + (numeroTemporadas * 0.5);
    }
}

//ESPAÇO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

/*
 * Interface para objetos que podem ser bloqueados.
 */
public interface Bloqueavel {
    void bloquear();
}

//ESPAÇO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

/*
 * Exceção checked: obriga o uso de try/catch ou throws.
 */
public class LimiteFavoritosExcedidoException extends Exception {
    public LimiteFavoritosExcedidoException(String mensagem) {
        super(mensagem);
    }
}

//ESPAÇO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

/*
 * Exceção unchecked: ocorre quando o tempo informado é inválido.
 */
public class TempoInvalidoException extends RuntimeException {
    public TempoInvalidoException(String mensagem) {
        super(mensagem);
    }
}

//ESPAÇO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

/*
 * Classe usada em composição pelo PerfilUsuario.
 */
public class HistoricoVisualizacao {
    private int totalMinutosAssistidos;

    public void registrarVisualizacao(int minutos) {
        // Não é permitido registrar zero ou minutos negativos.
        if (minutos <= 0) {
            throw new TempoInvalidoException(
                "O tempo de visualização deve ser maior que zero."
            );
        }

        totalMinutosAssistidos += minutos;
    }

    public int getTotalMinutosAssistidos() {
        return totalMinutosAssistidos;
    }
}

//ESPAÇO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

import java.util.ArrayList;
import java.util.List;

/*
 * PerfilUsuario agrega conteúdos criados externamente.
 * Também compõe um Histórico, pois ele é criado no próprio construtor.
 */
public class PerfilUsuario implements Bloqueavel {
    private static final int LIMITE_MAXIMO_FAVORITOS = 2;

    private String nome;
    private List<Conteudo> favoritos;
    private HistoricoVisualizacao historico;
    private boolean bloqueado;

    public PerfilUsuario(String nome) {
        this.nome = nome;
        this.favoritos = new ArrayList<>();

        // Composição: o histórico pertence exclusivamente a este perfil.
        this.historico = new HistoricoVisualizacao();
        this.bloqueado = false;
    }

    /*
     * Agregação: recebe um Conteudo criado fora da classe.
     */
    public void adicionarFavorito(Conteudo conteudo)
            throws LimiteFavoritosExcedidoException {

        if (favoritos.size() >= LIMITE_MAXIMO_FAVORITOS) {
            throw new LimiteFavoritosExcedidoException(
                "Limite máximo de " + LIMITE_MAXIMO_FAVORITOS +
                " favoritos atingido."
            );
        }

        favoritos.add(conteudo);
    }

    public void exibirFavoritos() {
        System.out.println("\n--- Favoritos de " + nome + " ---");

        for (Conteudo conteudo : favoritos) {
            /*
             * Polimorfismo: o método executado depende do objeto,
             * que pode ser Filme, Serie ou outra subclasse.
             */
            conteudo.exibirFicha();
            System.out.println("-----------------------");
        }
    }

    public HistoricoVisualizacao getHistorico() {
        return historico;
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    @Override
    public void bloquear() {
        bloqueado = true;
        System.out.println("Perfil de " + nome + " foi bloqueado.");
    }
}

//ESPAÇO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

/*
 * Classe principal para testar todos os requisitos.
 */
public class TesteStreaming {
    public static void main(String[] args) {
        // Objetos criados externamente ao perfil.
        Conteudo filme = new Filme("Interestelar", 169, true);
        Conteudo serie = new Serie("Stranger Things", 50, 4);
        Conteudo outroFilme = new Filme("O Poderoso Chefão", 175, false);

        PerfilUsuario perfil = new PerfilUsuario("Ana");

        try {
            // Agregação de conteúdos de subclasses diferentes.
            perfil.adicionarFavorito(filme);
            perfil.adicionarFavorito(serie);

            // Polimorfismo: Filme e Serie usam regras diferentes.
            perfil.exibirFavoritos();

            /*
             * Tentativa de adicionar o terceiro favorito.
             * O limite é 2, então a exceção checked será lançada.
             */
            perfil.adicionarFavorito(outroFilme);

        } catch (LimiteFavoritosExcedidoException e) {
            System.out.println("\nErro de favoritos: " + e.getMessage());
        }

        try {
            // Registro válido de visualização.
            perfil.getHistorico().registrarVisualizacao(90);

            System.out.println(
                "\nTotal assistido: " +
                perfil.getHistorico().getTotalMinutosAssistidos() +
                " minutos."
            );

            /*
             * Tentativa inválida: dispara a exceção unchecked.
             */
            perfil.getHistorico().registrarVisualizacao(0);

        } catch (TempoInvalidoException e) {
            System.out.println("Erro de visualização: " + e.getMessage());
        }

        // Teste da interface Bloqueavel.
        perfil.bloquear();
        System.out.println("Perfil está bloqueado? " + perfil.isBloqueado());
    }
}
