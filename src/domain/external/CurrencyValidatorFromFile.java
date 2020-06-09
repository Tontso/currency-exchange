package domain.external;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import domain.exceptions.InvalidCurrencyException;

public class CurrencyValidatorFromFile implements CurrencyValidator {
    
    private Path filePath;

    public CurrencyValidatorFromFile(Path filePath){
        this.filePath = filePath;
    }

	@Override
	public void validate(String currency) throws InvalidCurrencyException {
        try {
            Files.lines(filePath)
                .filter(currency::equals)
                .findAny()
                .orElseThrow(InvalidCurrencyException::new);
        } catch (IOException e) {
            throw new InvalidCurrencyException();
        }
             

		
	}
    
}