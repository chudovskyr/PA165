package cz.muni.fi.pa165.currency;

import javax.inject.Named;
import java.math.BigDecimal;
import java.util.Currency;

@Named
public class ExchangeRateTableImpl implements ExchangeRateTable {

    private static final Currency CZK = Currency.getInstance("CZK");
    private static final Currency EUR = Currency.getInstance("EUR");

    @Override
    public BigDecimal getExchangeRate(Currency sourceCurrency, Currency targetCurrency) throws ExternalServiceFailureException {
        if (sourceCurrency == null) {
            throw new IllegalArgumentException("Source currency is null.");
        }

        if (targetCurrency == null) {
            throw new IllegalArgumentException("Target currency is null.");
        }

        if (sourceCurrency == EUR && targetCurrency == CZK) {
            return new BigDecimal("27.00");
        }

        return null;
    }
}
