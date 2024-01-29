import java.util.*;

public class Contato {
    private Long id;
    private String nome;
    private String sobreNome;
    private List<Telefone> telefones;

    // construtor
    public Contato() {}

    // getters
    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome != null ? nome : "";
    }

    public String getSobreNome() {
        return sobreNome;
    }

    public List<Telefone> getTelefones() {
        return telefones;
    }

    // setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSobreNome(String sobreNome) {
        this.sobreNome = sobreNome;
    }

    public void setTelefones(List<Telefone> telefones) {
        this.telefones = telefones;
    }

    // método para imprimir o contato na tela
    public void imprimirContato() {
        System.out.println("Id: " + id);
        System.out.println("Nome: " + nome + " " + sobreNome);
        System.out.println("Telefones:");
        for (Telefone telefone : telefones) {
            System.out.println("(" + telefone.getDdd() + ") " + telefone.getNumero());
        }
    }
}
