package cz.muni.fi.pa165.currency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;


/**
 * This is base implementation of {@link CurrencyConvertor}.
 *
 * @author petr.adamek@embedit.cz
 */
public class CurrencyConvertorImpl implements CurrencyConvertor {

    private final ExchangeRateTable exchangeRateTable;
    private final Logger logger = LoggerFactory.getLogger(CurrencyConvertorImpl.class);

    public CurrencyConvertorImpl(ExchangeRateTable exchangeRateTable) {
        this.exchangeRateTable = exchangeRateTable;
    }

    @Override
    public BigDecimal convert(Currency sourceCurrency, Currency targetCurrency, BigDecimal sourceAmount) {
        logger.trace("Converting {} of {} to {}", sourceAmount, sourceCurrency, targetCurrency);
        if (sourceCurrency == null) {
            throw new IllegalArgumentException("Source currency is null");
        }

        if (targetCurrency == null) {
            throw new IllegalArgumentException("Target currency is null");
        }

        if (sourceAmount == null) {
            throw new IllegalArgumentException("Source amount is null");
        }

        try {
            BigDecimal rate = exchangeRateTable.getExchangeRate(sourceCurrency, targetCurrency);
            if (rate == null) {
                logger.warn("Converting failed because of missing rate from {} to {}", sourceCurrency, targetCurrency);
                throw new UnknownExchangeRateException("Exchange rate is unknown");
            }
            return rate.multiply(sourceAmount).setScale(2, RoundingMode.HALF_EVEN);
        } catch (ExternalServiceFailureException e) {
            logger.error("Converting from {} to {} failed because of ExternalServiceFailureException",
                    sourceCurrency, targetCurrency);
            throw new UnknownExchangeRateException("Error getting exchange rate", e);
        }
    }

}
