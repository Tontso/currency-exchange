package domain.commands;

import domain.entity.Money;
import domain.external.ExchangeService;
import domain.io.Logger;
import domain.repository.ConversionHistoryRepository;

public class HistorySavingConvertCommand extends ConvertCommand {

    private final ConversionHistoryRepository repo;

    public HistorySavingConvertCommand(final ExchangeService exchangeService, final Logger logger,
            final ConversionHistoryRepository repo) {
        super(exchangeService, logger);
        this.repo = repo;
    }

    @Override
    public void execute(Input input){
        Money converted = protectedExecute(input);
        repo.save(input.getFrom(), converted);
    }

}