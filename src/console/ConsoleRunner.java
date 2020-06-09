package console;

import java.nio.file.Path;
import java.util.Scanner;

import domain.external.CurrencyValidator;
import domain.external.CurrencyValidatorFromFile;
import domain.external.ExchangeService;
import domain.io.Logger;
import domain.repository.ConversionHistoryRepository;
import external.CurrConvExchangeServise;
import repository.InMemoryCorsersionHistoryRepository;

public class ConsoleRunner {

    
    public void run(){
        
        Scanner scanner = new Scanner(System.in);
        ExchangeService exchangeService = new CurrConvExchangeServise();
        Logger logger = new ConsoleLogger();
        ConversionHistoryRepository conversionHistoryRepository = new InMemoryCorsersionHistoryRepository();
        CurrencyValidator currencyValidator = new CurrencyValidatorFromFile(Path.of("TestFilePath"));

        ConsoleCommandExecutor executor = new ConsoleCommandExecutor(conversionHistoryRepository, logger, exchangeService, currencyValidator);

        while(true){
            String line = scanner.nextLine();
            String[] split = line.split("\\s+");
            executor.execute(split);
        
        }
    }


}