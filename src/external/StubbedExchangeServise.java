package external;

import java.math.BigDecimal;

import domain.entity.Money;
import domain.external.ExchangeService;

public class StubbedExchangeServise implements ExchangeService {

    @Override
    public Money exchange(Money from, String toCurrency) {
        return new Money(BigDecimal.ONE, "USD");
    }

}

    
