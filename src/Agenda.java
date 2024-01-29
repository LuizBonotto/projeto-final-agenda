import java.util.*;

public class Agenda {
    private Map<Long, Contato> contatos = new HashMap<>();
    private Map<Long, Telefone> telefones = new HashMap<>();
    private Long idAtual = 0L;
    private PriorityQueue<Long> idsDisponiveis = new PriorityQueue<>();

    public void adicionarContato(String nome, String sobreNome, List<Telefone> telefones) {
        Long id;
        if (!idsDisponiveis.isEmpty()) {
            id = idsDisponiveis.poll();
        } else {
            id = idAtual++;
        }
        Contato contato = new Contato();
        contato.setId(id);
        contato.setNome(nome);
        contato.setSobreNome(sobreNome);
        contato.setTelefones(telefones);
        contatos.put(id, contato);

        for (Telefone telefone : telefones) {
            this.telefones.put(telefone.getId(), telefone);
            Arquivos.escreverTelefone(telefone);
        }
        Arquivos.escreverContato(contato);
    }

    public void removerContato(Long id) {
        Contato contato = contatos.get(id);
        if (contato != null) {
            for (Telefone telefone : contato.getTelefones()) {
                telefones.remove(telefone.getId());
                Arquivos.removerTelefone(telefone.getId());
            }
            contatos.remove(id);
            idsDisponiveis.add(id);
            Arquivos.removerContato(id);
        }
    }

    public void editarContato(Long id, String novoNome, String novoSobreNome, List<Telefone> novosTelefones) {
        Contato contato = contatos.get(id);
        if (contato != null) {
            contato.setNome(novoNome);
            contato.setSobreNome(novoSobreNome);
            contato.setTelefones(novosTelefones);
            Arquivos.atualizarContato(contato);
            for (Telefone telefone : novosTelefones) {
                Arquivos.atualizarTelefone(telefone);
            }
        }
    }

    public void imprimirContatos() {
        for (Map.Entry<Long, Contato> entry : contatos.entrySet()) {
            Contato contato = entry.getValue();
            if (contato != null && (contato.getNome() != null && !contato.getNome().isEmpty()) ||
                    (contato.getSobreNome() != null && !contato.getSobreNome().isEmpty())) {
                System.out.println(contato.getId() + " | " + (contato.getNome() + " " + contato.getSobreNome()).trim());
            }
        }
    }

    public void carregarContatos() {
        List<Contato> contatosDoArquivo = Arquivos.lerContatos();
        for (Contato contato : contatosDoArquivo) {
            contatos.put(contato.getId(), contato);
        }
    }

    public void carregarTelefones() {
        List<Telefone> telefonesDoArquivo = Arquivos.lerTelefones();
        for (Telefone telefone : telefonesDoArquivo) {
            telefones.put(telefone.getId(), telefone);
        }
    }
}
