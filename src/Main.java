import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Agenda agenda = new Agenda();

        while (true) {
            System.out.println("##################");
            System.out.println("##### AGENDA #####");
            System.out.println("##################");
            System.out.println(">>>> Contatos <<<<");
            System.out.println("Id | Nome");
            agenda.imprimirContatos();
            System.out.println(">>>> Menu <<<<");
            System.out.println("1 - Adicionar Contato");
            System.out.println("2 - Remover Contato");
            System.out.println("3 - Editar Contato");
            System.out.println("4 - Sair");

            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();  // consome a linha restante

            if (opcao == 1) {
                System.out.print("Digite o nome do contato: ");
                String nome = scanner.nextLine();
                System.out.print("Digite o sobrenome do contato: ");
                String sobreNome = scanner.nextLine();
                List<Telefone> telefones = new ArrayList<>();
                System.out.print("Quantos telefones você deseja adicionar? ");
                int numTelefones = scanner.nextInt();
                scanner.nextLine();  // consome a linha restante
                for (int i = 0; i < numTelefones; i++) {
                    System.out.print("Digite o DDD do telefone " + (i+1) + ": ");
                    String ddd = scanner.nextLine();
                    System.out.print("Digite o número do telefone " + (i+1) + ": ");
                    Long numero = scanner.nextLong();
                    scanner.nextLine();  // consome a linha restante
                    Telefone telefone = new Telefone(Long.valueOf(i), ddd, numero);
                    telefones.add(telefone);
                }
                agenda.adicionarContato(nome, sobreNome, telefones);
            } else if (opcao == 2) {
                System.out.print("Digite o ID do contato a ser removido: ");
                Long id = scanner.nextLong();
                agenda.removerContato(id);
            } else if (opcao == 3) {
                System.out.print("Digite o ID do contato a ser editado: ");
                Long id = scanner.nextLong();
                System.out.print("Digite o novo nome do contato: ");
                String novoNome = scanner.nextLine();
                System.out.print("Digite o novo sobrenome do contato: ");
                String novoSobreNome = scanner.nextLine();
                List<Telefone> novosTelefones = new ArrayList<>();
                System.out.print("Quantos telefones você deseja adicionar? ");
                int numTelefones = scanner.nextInt();
                scanner.nextLine();  // consome a linha restante
                for (int i = 0; i < numTelefones; i++) {
                    System.out.print("Digite o DDD do telefone " + (i+1) + ": ");
                    String ddd = scanner.nextLine();
                    System.out.print("Digite o número do telefone " + (i+1) + ": ");
                    Long numero = scanner.nextLong();
                    scanner.nextLine();  // consome a linha restante
                    Telefone telefone = new Telefone(Long.valueOf(i), ddd, numero);
                    novosTelefones.add(telefone);
                }
                agenda.editarContato(id, novoNome, novoSobreNome, novosTelefones);
            } else if (opcao == 4) {
                break;
            } else {
                System.out.println("Opção inválida. Por favor, tente novamente.");
            }
        }

        scanner.close();
    }
}
