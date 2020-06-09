package domain.commands;

import java.util.List;

import domain.io.Logger;
import domain.repository.ConversionHistoryRepository;

public class HistoryCommand implements Command<HistoryCommand.Input>{

    private final ConversionHistoryRepository repo;
    private Logger logger;

    public HistoryCommand(ConversionHistoryRepository repo, Logger logger){
        this.repo = repo;
        this.logger = logger;
    }

    @Override
    public void execute(Input input) {
        List<String> lastConversions = repo.getLast(input.getCommandsToShow());
        lastConversions.forEach(cmd -> logger.logLine(cmd));

    }


    public static class Input extends EmptyInput{
        private final int commandsToShow;


        public Input(int commandsToShow){ 
            if (commandsToShow < 1){
                throw new IllegalArgumentException("Input must be possitive");
            } 
            this.commandsToShow = commandsToShow;
        }

        public int getCommandsToShow(){
            return commandsToShow;
        }
    }
  
}