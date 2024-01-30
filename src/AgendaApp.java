import java.io.*;
import java.util.*;

public class AgendaApp {
    private static final String ARQUIVO_CONTATOS = "contatos.txt";
    private static final String ARQUIVO_TELEFONES = "telefones.txt";
    private static List<Contato> contatos = new ArrayList<>();
    private static Long proximoId = 1L;

    public static void main(String[] args) {
        carregarContatos();

        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            exibirMenu();
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1:
                    adicionarContato(scanner);
                    break;
                case 2:
                    removerContato(scanner);
                    break;
                case 3:
                    editarContato(scanner);
                    break;
                case 4:
                    salvarContatos();
                    System.out.println("Saindo da aplicação. Até mais!");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 4);

        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("##################");
        System.out.println("##### AGENDA #####");
        System.out.println(">>>> Contatos <<<<");
        System.out.println("Id | Nome ");
        for (Contato contato : contatos) {
            System.out.println(contato.getId() + " | " + contato.getNome() + " " + contato.getSobreNome());
        }
        System.out.println(">>>> Menu <<<<");
        System.out.println("1 - Adicionar Contato");
        System.out.println("2 - Remover Contato");
        System.out.println("3 - Editar Contato");
        System.out.println("4 - Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void adicionarContato(Scanner scanner) {
        System.out.print("Digite o nome do contato: ");
        String nome = scanner.nextLine();

        System.out.print("Digite o sobrenome do contato: ");
        String sobreNome = scanner.nextLine();

        Contato novoContato = new Contato();
        novoContato.setId(proximoId++);
        novoContato.setNome(nome);
        novoContato.setSobreNome(sobreNome);

        // Inicializar a lista de telefones
        novoContato.setTelefones(new ArrayList<>());

        System.out.print("Quantos telefones deseja adicionar? ");
        int numTelefones = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha

        for (int i = 0; i < numTelefones; i++) {
            System.out.print("Digite o DDD do telefone " + (i + 1) + ": ");
            String ddd = scanner.nextLine();

            System.out.print("Digite o número do telefone " + (i + 1) + ": ");
            Long numero = scanner.nextLong();
            scanner.nextLine(); // Consumir a quebra de linha

            Telefone telefone = new Telefone(1L, "DDD", 123456789L);
            telefone.setId(novoContato.getId());
            telefone.setDdd(ddd);
            telefone.setNumero(numero);

            novoContato.getTelefones().add(telefone);
        }

        contatos.add(novoContato);
        System.out.println("Contato adicionado com sucesso!");
        salvarContatos();
    }



    private static void removerContato(Scanner scanner) {
        System.out.print("Digite o ID do contato que deseja remover: ");
        Long idRemover = scanner.nextLong();
        scanner.nextLine(); // Consumir a quebra de linha

        contatos.removeIf(contato -> contato.getId().equals(idRemover));
        System.out.println("Contato removido com sucesso!");
        salvarContatos();
    }


    private static void editarContato(Scanner scanner) {
        System.out.print("Digite o ID do contato que deseja editar: ");
        Long idEditar = scanner.nextLong();
        scanner.nextLine(); // Consumir a quebra de linha

        for (Contato contato : contatos) {
            if (contato.getId().equals(idEditar)) {
                System.out.print("Digite o novo nome do contato: ");
                String novoNome = scanner.nextLine();
                contato.setNome(novoNome);

                System.out.print("Digite o novo sobrenome do contato: ");
                String novoSobreNome = scanner.nextLine();
                contato.setSobreNome(novoSobreNome);

                System.out.print("Deseja editar os telefones? (S/N): ");
                String editarTelefones = scanner.nextLine();
                if (editarTelefones.equalsIgnoreCase("S")) {
                    List<Telefone> novosTelefones = new ArrayList<>();

                    System.out.print("Quantos telefones deseja adicionar? ");
                    int numTelefones = scanner.nextInt();
                    scanner.nextLine(); // Consumir a quebra de linha

                    for (int i = 0; i < numTelefones; i++) {
                        System.out.print("Digite o DDD do telefone " + (i + 1) + ": ");
                        String ddd = scanner.nextLine();

                        System.out.print("Digite o número do telefone " + (i + 1) + ": ");
                        Long numero = scanner.nextLong();
                        scanner.nextLine(); // Consumir a quebra de linha

                        Telefone telefone = new Telefone(1L, "DDD", 123456789L);
                        telefone.setId(contato.getId());
                        telefone.setDdd(ddd);
                        telefone.setNumero(numero);

                        novosTelefones.add(telefone);
                    }

                    contato.setTelefones(novosTelefones);
                }

                System.out.println("Contato editado com sucesso!");
                salvarContatos();
                return;
            }
        }

        System.out.println("Contato não encontrado.");
    }


    private static void carregarContatos() {
        try (BufferedReader brContatos = new BufferedReader(new FileReader(ARQUIVO_CONTATOS));
             BufferedReader brTelefones = new BufferedReader(new FileReader(ARQUIVO_TELEFONES))) {

            // Mapa temporário para armazenar telefones antes de associá-los aos contatos
            Map<Long, List<Telefone>> telefonesPorContato = new HashMap<>();

            // Carregar dados do arquivo de contatos
            String linhaContatos;
            while ((linhaContatos = brContatos.readLine()) != null) {
                String[] dadosContatos = linhaContatos.split("\\|");

                Contato contato = new Contato();
                contato.setId(Long.parseLong(dadosContatos[0].trim()));
                contato.setNome(dadosContatos[1].trim());
                contato.setSobreNome(dadosContatos[2].trim());

                // Inicialize a lista de telefones para o contato
                contato.setTelefones(new ArrayList<>());

                // Adicione o contato ao mapa temporário
                telefonesPorContato.put(contato.getId(), new ArrayList<>());

                // Adicione o contato à lista de contatos
                contatos.add(contato);
            }

            // Carregar dados do arquivo de telefones
            String linhaTelefones;
            while ((linhaTelefones = brTelefones.readLine()) != null) {
                String[] dadosTelefones = linhaTelefones.split("\\|");

                if (dadosTelefones.length >= 4) {  // Verificar se há pelo menos 4 elementos
                    Long idTelefone = null;
                    if (!dadosTelefones[0].trim().isEmpty()) {
                        idTelefone = Long.parseLong(dadosTelefones[0].trim());
                    }
                    Long idContato = Long.parseLong(dadosTelefones[1].trim());
                    String ddd = dadosTelefones[2].trim();
                    Long numero = Long.parseLong(dadosTelefones[3].trim());

                    // Adicione o telefone ao mapa temporário
                    telefonesPorContato.get(idContato).add(new Telefone(idTelefone, ddd, numero));
                } else {
                    System.err.println("Linha do arquivo de telefones malformatada: " + linhaTelefones);
                }
            }

            // Associe os telefones aos contatos
            for (Contato contato : contatos) {
                List<Telefone> telefones = telefonesPorContato.get(contato.getId());
                if (telefones != null) {
                    contato.setTelefones(telefones);
                }
            }

        } catch (IOException e) {
            System.err.println("Erro ao carregar contatos do arquivo: " + e.getMessage());
        }
    }




    private static void salvarContatos() {
        try (BufferedWriter bwContatos = new BufferedWriter(new FileWriter(ARQUIVO_CONTATOS));
             BufferedWriter bwTelefones = new BufferedWriter(new FileWriter(ARQUIVO_TELEFONES))) {

            for (Contato contato : contatos) {
                // Salvar dados do contato no arquivo de contatos
                StringBuilder linhaContatos = new StringBuilder();
                linhaContatos.append(contato.getId()).append(" | ");
                linhaContatos.append(contato.getNome()).append(" | ");
                linhaContatos.append(contato.getSobreNome());
                bwContatos.write(linhaContatos.toString());
                bwContatos.newLine();

                // Salvar números de telefone no arquivo de telefones
                List<Telefone> telefones = contato.getTelefones();
                if (telefones != null && !telefones.isEmpty()) {
                    for (Telefone telefone : telefones) {
                        StringBuilder linhaTelefones = new StringBuilder();
                        linhaTelefones.append(telefone.getId()).append(" | ");
                        linhaTelefones.append(contato.getId()).append(" | ");  // Adicionando o ID do contato
                        linhaTelefones.append(telefone.getDdd()).append(" | ");
                        linhaTelefones.append(telefone.getNumero());
                        bwTelefones.write(linhaTelefones.toString());
                        bwTelefones.newLine();
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar contatos no arquivo: " + e.getMessage());
        }
    }

}
