package up.edu.br.validar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import up.edu.br.controllers.CarroController;
import up.edu.br.controllers.ClienteController;
import up.edu.br.controllers.FuncionarioController;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ValidaVallet {
    static Scanner scan = new Scanner (System.in);
    private static final Logger logger = LogManager.getLogger(ValidaVallet.class);

    public static int validaIdFuncionario(){
        logger.info("Iniciando validação do ID do funcionario");
        while(true) {
            int id = ValidaCrud.validaId();

            if (FuncionarioController.ExistsFuncionario(id)) {
                return id;
            }
            System.out.println("Funcionário não encontrado, digite novamente: ");
        }
    }


    public static String ValidaCpfCliente(){
        logger.info("Iniciando validação do CPF");
        while(true) {
            try {
                String cpf = ValidaCrud.validaCpf();
                if(ClienteController.jaExisteCpf(cpf)){
                    return cpf;
                }
                System.out.println("Cliente não encontrado, digite novamente: ");
            } catch (InputMismatchException e) {
                logger.error("Erro ao validar CPF", e);
                System.out.println("CPF inválido, digite novamente: ");
            }
        }
    }

    public static int ValidaIdCarro(String cpf){
        logger.info("Iniciando validação do ID do carro");
        while(true) {
            int id = ValidaCrud.validaId();
            if(CarroController.ExistsCarro(cpf, id)){
                return id;
            }
            System.out.println("Carro não encontrado");
        }
    }


}
