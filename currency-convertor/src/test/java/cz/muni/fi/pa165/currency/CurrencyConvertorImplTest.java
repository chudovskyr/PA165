package cz.muni.fi.pa165.currency;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class CurrencyConvertorImplTest {

    private Currency EUR = Currency.getInstance("EUR");
    private Currency CZK = Currency.getInstance("CZK");

    @Mock
    private ExchangeRateTable exchangeRateTable;

    private CurrencyConvertor currencyConvertor;

    @Before
    public void init() {
        currencyConvertor = new CurrencyConvertorImpl(exchangeRateTable);
    }

    @Test
    public void testConvert() throws ExternalServiceFailureException {
        // Don't forget to test border values and proper rounding.
        when(exchangeRateTable.getExchangeRate(EUR, CZK)).thenReturn(new BigDecimal("1"));

        assertEquals(new BigDecimal("1"), currencyConvertor.convert(EUR, CZK, new BigDecimal("1")));
        assertEquals(new BigDecimal("1.06"), currencyConvertor.convert(EUR, CZK, new BigDecimal("1.055")));
        assertEquals(new BigDecimal("1.02"), currencyConvertor.convert(EUR, CZK, new BigDecimal("1.025")));
        assertEquals(new BigDecimal("1.02"), currencyConvertor.convert(EUR, CZK, new BigDecimal("1.016")));
        assertEquals(new BigDecimal("1.01"), currencyConvertor.convert(EUR, CZK, new BigDecimal("1.014")));
    }

    @Test
    public void testConvertWithNullSourceCurrency() {
        fail("Test is not implemented yet.");
    }

    @Test
    public void testConvertWithNullTargetCurrency() {
        fail("Test is not implemented yet.");
    }

    @Test
    public void testConvertWithNullSourceAmount() {
        fail("Test is not implemented yet.");
    }

    @Test
    public void testConvertWithUnknownCurrency() {
        fail("Test is not implemented yet.");
    }

    @Test
    public void testConvertWithExternalServiceFailure() {
        fail("Test is not implemented yet.");
    }

}
