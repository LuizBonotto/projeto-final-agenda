import java.io.*;
import java.util.*;

public class Arquivos {

    public static final String NOME_ARQUIVO_TELEFONE = "telefones.txt";
    public static final String NOME_ARQUIVO_CONTATO = "contatos.txt";
    public static final String SEPARADOR = "|";

    public static void criarArquivos() {
        File arquivoTelefone = new File(NOME_ARQUIVO_TELEFONE);
        File arquivoContato = new File(NOME_ARQUIVO_CONTATO);

        if (!arquivoTelefone.exists()) {
            try {
                arquivoTelefone.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (!arquivoContato.exists()) {
            try {
                arquivoContato.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void apagarArquivos() {
        File arquivoTelefone = new File(NOME_ARQUIVO_TELEFONE);
        File arquivoContato = new File(NOME_ARQUIVO_CONTATO);

        if (arquivoTelefone.exists()) {
            arquivoTelefone.delete();
        }

        if (arquivoContato.exists()) {
            arquivoContato.delete();
        }
    }

    public static void escreverTelefone(Telefone telefone) {
        File arquivoTelefone = new File(NOME_ARQUIVO_TELEFONE);

        try {
            String linha = String.format("%d%s%s%s%d", telefone.getId(), SEPARADOR, telefone.getDdd(), SEPARADOR, telefone.getNumero());
            FileWriter fileWriter = new FileWriter(arquivoTelefone, true);
            BufferedWriter bw = new BufferedWriter(fileWriter);
            bw.newLine();
            bw.write(linha);
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void escreverContato(Contato contato) {
        File arquivoContato = new File(NOME_ARQUIVO_CONTATO);

        try {
            String linha = String.format("%d%s%s%s%s", contato.getId(), SEPARADOR, contato.getNome(), SEPARADOR, contato.getSobreNome());
            FileWriter fileWriter = new FileWriter(arquivoContato, true);
            BufferedWriter bw = new BufferedWriter(fileWriter);
            bw.newLine();
            bw.write(linha);
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Telefone> lerTelefones() {
        File arquivoTelefone = new File(NOME_ARQUIVO_TELEFONE);
        List<Telefone> telefones = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(arquivoTelefone);
            while (scanner.hasNext()) {
                String[] dadosTelefone = scanner.nextLine().split(SEPARADOR);
                Telefone telefone = new Telefone(Long.parseLong(dadosTelefone[0]), dadosTelefone[1], Long.parseLong(dadosTelefone[2]));
                telefones.add(telefone);
            }
            scanner.close();
            return telefones;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Contato> lerContatos() {
        File arquivoContato = new File(NOME_ARQUIVO_CONTATO);
        List<Contato> contatos = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(arquivoContato);
            while (scanner.hasNext()) {
                String[] dadosContato = scanner.nextLine().split(SEPARADOR);
                Contato contato = new Contato();
                if (!dadosContato[0].isEmpty()) contato.setId(Long.parseLong(dadosContato[0]));
                contato.setNome(dadosContato[1]);
                contato.setSobreNome(dadosContato[2]);
                contatos.add(contato);
            }
            scanner.close();
            return contatos;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void removerTelefone(Long id) {
        List<Telefone> telefones = lerTelefones();
        telefones.removeIf(t -> t.getId().equals(id));
        reescreverTelefones(telefones);
    }

    public static void removerContato(Long id) {
        List<Contato> contatos = lerContatos();
        contatos.removeIf(c -> c.getId().equals(id));
        reescreverContatos(contatos);
    }

    public static void atualizarTelefone(Telefone telefoneAtualizado) {
        List<Telefone> telefones = lerTelefones();
        for (int i = 0; i < telefones.size(); i++) {
            if (telefones.get(i).getId().equals(telefoneAtualizado.getId())) {
                telefones.set(i, telefoneAtualizado);
                break;
            }
        }
        reescreverTelefones(telefones);
    }

    public static void atualizarContato(Contato contatoAtualizado) {
        List<Contato> contatos = lerContatos();
        for (int i = 0; i < contatos.size(); i++) {
            if (contatos.get(i).getId().equals(contatoAtualizado.getId())) {
                contatos.set(i, contatoAtualizado);
                break;
            }
        }
        reescreverContatos(contatos);
    }

    private static void reescreverTelefones(List<Telefone> telefones) {
        File arquivoTelefone = new File(NOME_ARQUIVO_TELEFONE);
        try {
            FileWriter fileWriter = new FileWriter(arquivoTelefone, false);
            BufferedWriter bw = new BufferedWriter(fileWriter);
            for (Telefone telefone : telefones) {
                String linha = String.format("%d%s%s%s%d", telefone.getId(), SEPARADOR, telefone.getDdd(), SEPARADOR, telefone.getNumero());
                bw.write(linha);
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void reescreverContatos(List<Contato> contatos) {
        File arquivoContato = new File(NOME_ARQUIVO_CONTATO);
        try {
            FileWriter fileWriter = new FileWriter(arquivoContato, false);
            BufferedWriter bw = new BufferedWriter(fileWriter);
            for (Contato contato : contatos) {
                String linha = String.format("%d%s%s%s%s", contato.getId(), SEPARADOR, contato.getNome(), SEPARADOR, contato.getSobreNome());
                bw.write(linha);
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
