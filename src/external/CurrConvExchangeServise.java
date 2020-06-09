package external;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


import domain.entity.Money;
import domain.external.ExchangeService;

public class CurrConvExchangeServise implements ExchangeService {

    private static final String API_KEY = System.getenv("API_KEY");

    @Override
    public Money exchange(Money from, String toCurrency) {
        BigDecimal rate = fetchExchangeRateFor(from.getCurrency(), toCurrency);
        return new Money(from.getValue().multiply(rate), toCurrency);
    }


    private BigDecimal fetchExchangeRateFor(String currencyFrom, String currencyTo) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().GET().uri(buildRequestUrl(currencyFrom, currencyTo)).build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return (extractExchangeRAte(response.body()));
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong during HTTP request.");
        }
    }


    private BigDecimal extractExchangeRAte(String response) {
        int columnInd = response.lastIndexOf(':');
        int closingBracket = response.lastIndexOf('}');
        String exchangeRate = response.substring(columnInd + 1, closingBracket);
        return new BigDecimal(exchangeRate);
    }


    private URI buildRequestUrl(String from, String to) {
        String url = String.format("https://free.currconv.com/api/v7/convert?apiKey=%s&q=%s_%s&compact=ultra", API_KEY,
                from, to);
        try {
            return new URI(url);
        } catch (Exception e) {

            throw new IllegalArgumentException("Failed to build URI.");
        }
    }
    
}
