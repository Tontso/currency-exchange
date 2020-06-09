package console;

import java.math.BigDecimal;

import domain.commands.Command;
import domain.commands.ConvertCommand;
import domain.commands.EmptyInput;
import domain.commands.EndCommand;
import domain.commands.HistoryCommand;
import domain.commands.ValidatedHistorySavingConvertCommand;
import domain.entity.Money;
import domain.external.CurrencyValidator;
import domain.external.ExchangeService;
import domain.io.Logger;
import domain.repository.ConversionHistoryRepository;

class ConsoleCommandExecutor{

    private ConversionHistoryRepository conversionHistoryRepository;
    private Logger logger;
    private ExchangeService exchangeService;
    private CurrencyValidator currencyValidator;

    public ConsoleCommandExecutor(ConversionHistoryRepository conversionHistoryRepository,
            Logger logger,
            ExchangeService exchangeService,
            CurrencyValidator currencyValidator
            ){

        this.conversionHistoryRepository = conversionHistoryRepository;
        this.logger = logger;
        this.exchangeService = exchangeService;
        this.currencyValidator = currencyValidator;
    }

    public void execute(String[] args){
        switch(args[0]){
            case "END":
                end();
                break;
            case "CONVERT":
                convert(args);
                break;
            case "HISTORY":
                history(args);
                break;
            default:
                logger.logLine("Incorrect command.");
            
        }
    }

    private void history(String[] split) {
        Command<HistoryCommand.Input> cmd = new HistoryCommand(conversionHistoryRepository, logger);
        cmd.execute(new HistoryCommand.Input(Integer.parseInt(split[1])));
    }

    private void convert(String[] split) {
        BigDecimal fromValue = new BigDecimal(split[1]);
        String fromCurrency = split[2];
        String toCurrency = split[3];
        ConvertCommand.Input input =  new ConvertCommand.Input( new Money(fromValue, fromCurrency), toCurrency);
        new ValidatedHistorySavingConvertCommand(exchangeService, logger, conversionHistoryRepository,currencyValidator).execute(input);
    }

    private void end(){
        new EndCommand().execute(new EmptyInput());
    }



}