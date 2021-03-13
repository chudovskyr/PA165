package cz.muni.fi.pa165.currency;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyConvertorImplTest {

    private final Currency EUR = Currency.getInstance("EUR");
    private final Currency CZK = Currency.getInstance("CZK");

    @Mock
    private ExchangeRateTable exchangeRateTable;

    private CurrencyConvertor currencyConvertor;

    @Before
    public void init() {
        currencyConvertor = new CurrencyConvertorImpl(exchangeRateTable);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testConvert() throws ExternalServiceFailureException {
        when(exchangeRateTable.getExchangeRate(EUR, CZK)).thenReturn(new BigDecimal("1"));

        assertEquals(new BigDecimal("1.00"), currencyConvertor.convert(EUR, CZK, new BigDecimal("1")));
        assertEquals(new BigDecimal("1.06"), currencyConvertor.convert(EUR, CZK, new BigDecimal("1.055")));
        assertEquals(new BigDecimal("1.02"), currencyConvertor.convert(EUR, CZK, new BigDecimal("1.025")));
        assertEquals(new BigDecimal("1.02"), currencyConvertor.convert(EUR, CZK, new BigDecimal("1.016")));
        assertEquals(new BigDecimal("1.01"), currencyConvertor.convert(EUR, CZK, new BigDecimal("1.014")));
    }

    @Test
    public void testConvertWithNullSourceCurrency() {
        thrown.expect(IllegalArgumentException.class);
        currencyConvertor.convert(null, CZK, new BigDecimal("1"));
    }

    @Test
    public void testConvertWithNullTargetCurrency() {
        thrown.expect(IllegalArgumentException.class);
        currencyConvertor.convert(EUR, null, new BigDecimal("1"));
    }

    @Test
    public void testConvertWithNullSourceAmount() {
        thrown.expect(IllegalArgumentException.class);
        currencyConvertor.convert(EUR, CZK, null);
    }

    @Test
    public void testConvertWithUnknownCurrency() throws ExternalServiceFailureException {
        when(exchangeRateTable.getExchangeRate(EUR, CZK)).thenReturn(null);

        thrown.expect(UnknownExchangeRateException.class);
        currencyConvertor.convert(EUR, CZK, new BigDecimal("1"));
    }

    @Test
    public void testConvertWithExternalServiceFailure() throws ExternalServiceFailureException {
        when(exchangeRateTable.getExchangeRate(EUR, CZK))
                .thenThrow(ExternalServiceFailureException.class);

        thrown.expect(UnknownExchangeRateException.class);
        currencyConvertor.convert(EUR, CZK, new BigDecimal("1"));
    }

}
