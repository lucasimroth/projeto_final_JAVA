package up.edu.br.views;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import up.edu.br.Exception.NotFoundException;
import up.edu.br.controllers.ClienteController;
import up.edu.br.models.Carro;
import up.edu.br.validar.ValidaCrud;
import up.edu.br.validar.ValidaMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClienteView {
    private static final Logger logger = LogManager.getLogger(ClienteView.class);
    static Scanner scan = new Scanner (System.in);

    /**
     * Método responsável por exibir o menu de Cliente
     */
    public static void menu(){
        int opcao;
        do {
            System.out.println("-------------------------------------Menu Cliente---------------------------------------\n");
            System.out.println("Digite a opção desejada: \n");
            System.out.println("1 - Cadastrar Cliente");
            System.out.println("2 - Alterar Cliente");
            System.out.println("3 - Excluir Cliente");
            System.out.println("4 - Listar Cliente");
            System.out.println("5 - Modificar Carro de Cliente");
            System.out.println("6 - Voltar");
            System.out.println("\n------------------------------------------------------------------------------------------------\n");

            opcao = ValidaMenu.validaOpcao();

            switch (opcao) {
                case 1:
                    CadastrarCliente();
                    ValidaMenu.rodape();
                    continue;
                case 2:
                    AlterarCliente();
                    ValidaMenu.rodape();
                    continue;
                case 3:
                    ExcluirCliente();
                    ValidaMenu.rodape();
                    continue;
                case 4:
                    ListarClientes();
                    ValidaMenu.rodape();
                    continue;
                case 5:
                    CarroView.menu();
                    ValidaMenu.rodape();
                    continue;
                case 6:
                    return;
                default:
                    System.out.println("Opção inválida");
            }
        } while (true);
    }

    /**
     * O método cadastrarCliente interage com o usuário, valida as entradas e chama o método CadastrarCliente no controller
     */
    public static void CadastrarCliente() {
        logger.info("Iniciando cadastro de cliente");
        System.out.println("-------------------------------------Cadastro de Funcionário---------------------------------------\n");
        System.out.println("Digite o nome do cliente: ");
        String nome = ValidaCrud.validaNome();
        System.out.println("Digite o CPF do cliente: ");
        String cpf = ValidaCrud.validaCpf();
        System.out.println("Digite o email do cliente: ");
        String email = ValidaCrud.validaEmail();
        System.out.println("Digite o telefone do cliente: ");
        String telefone = ValidaCrud.validaTelefone();

        List<Carro> carros = new ArrayList<>();

        int cont = 1;
        carros.add(CadastrarClienteCarro(cont));
        while(true){
            System.out.println("\nDeseja cadastrar mais um veiculo para o cliente? (S/N)\n");
            if(scan.nextLine().equalsIgnoreCase("S")){
                carros.add(CadastrarClienteCarro(++cont));
            }
            else {break;}
        }
        ClienteController.CadastrarCliente(nome, cpf, email, telefone, carros);
    }

    /**
     * O método CadastrarClienteCarro interage com o usuário, valida as entradas e retorna um objeto Carro
     * @param id o id do carro
     * @return um objeto Carro
     */
    public static Carro CadastrarClienteCarro(int id) {
        System.out.println("Digite a placa do carro: ");
        String placa = ValidaCrud.validaPlaca();
        System.out.println("Digite o modelo do carro: ");
        String modelo = ValidaCrud.validaModelo();
        System.out.println("Digite a cor do carro: ");
        String cor = ValidaCrud.validaCor();

        return new Carro(id, placa, modelo, cor);
    }


    /**
     * O método AlterarCliente interage com o usuário, valida as entradas e chama o método AlterarCliente no controller
     */
    public static void AlterarCliente() {
        logger.info("Iniciando alteração de Clientes");
        if(ClienteController.verificarArquivo()){
            logger.info("Não há Clientes cadastrados para alterar");
            System.out.println("Não há Clientes cadastrados\n");
           return;
        }
        ListarClientes();
        String cpf;

        System.out.println("-------------------------------------Alteração de Cliente---------------------------------------\n");
        do {
            System.out.println("Digite o CPF do Cliente que deseja alterar: ");
            cpf = ValidaCrud.validaCpf();
            try{
                ClienteController.imprimirClientes(ClienteController.buscarPorCpf(cpf));

            } catch (NotFoundException e) {
                System.out.println("Cliente não encontrado\n");
                return;
            }

        } while (ValidaCrud.confirmarCpf());
        System.out.println("Digite o novo nome do cliente: ");
        String nome = ValidaCrud.validaNome();
        System.out.println("Digite o novo email do cliente: ");
        String email = ValidaCrud.validaEmail();
        System.out.println("Digite o novo telefone do cliente: ");
        String telefone = ValidaCrud.validaTelefone();

        ClienteController.AlterarCliente(cpf, nome, email, telefone);
    }

    /**
     * O método ExcluirCliente interage com o usuário, valida as entradas e chama o método ExcluirCliente no controller
     */
    public static void ExcluirCliente() {
        logger.info("Iniciando exclusao de Clientes");
        if(ClienteController.verificarArquivo()){
            logger.info("Não há Clientes cadastrados para excluir");
            System.out.println("Não há Clientes cadastrados\n");
            return;
        }
        ListarClientes();
        String cpf;
        System.out.println("-------------------------------------Exclusão de Cliente---------------------------------------\n");
        do {
            System.out.println("Digite o CPF do Cliente que deseja excluir: ");
            cpf = ValidaCrud.validaCpf();
            try{
                ClienteController.imprimirClientes(ClienteController.buscarPorCpf(cpf));

            } catch (NotFoundException e) {
                System.out.println("Cliente não encontrado\n");
                return;
            }

        } while (ValidaCrud.confirmarCpf());
        ClienteController.ExcluirCliente(cpf);
    }

    /**
     * O método ListarClientes chama o método ListarClientes no controller
     */
    public static void ListarClientes() {
        System.out.println("-------------------------------------Lista de Clientes---------------------------------------\n");
        ClienteController.ListarClientes();
    }

}


