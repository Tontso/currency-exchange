package domain.commands;

import domain.entity.Money;
import domain.external.ExchangeService;
import domain.io.Logger;

public class ConvertCommand implements Command<ConvertCommand.Input> {

    private ExchangeService exchangeService;
    private Logger logger;

    public ConvertCommand(ExchangeService exchangeService, Logger logger){
        this.exchangeService = exchangeService;
        this.logger = logger;
    }

    @Override
    public void execute(Input input) {
        this.protectedExecute(input);
    }

    protected Money protectedExecute(Input input){
        Money converted =  exchangeService.exchange(input.from, input.toCurrency);
        logger.logLine(converted.toString());
        return converted;
    }


    public static class Input extends EmptyInput{

        private final Money from;
        private final String toCurrency;
        
        public Input(Money from, String toCurrency){
            this.from = from;
            this.toCurrency = toCurrency;    
        }

        public Money getFrom(){
            return from;
        }

        public String getToCurrency(){
            return toCurrency;
        }

    
    }

}