package cz.muni.fi.pa165;

import cz.muni.fi.pa165.currency.CurrencyConvertor;
import cz.muni.fi.pa165.currency.CurrencyConvertorImpl;
import cz.muni.fi.pa165.currency.ExchangeRateTableImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.util.Currency;

public class MainAnnotations {

    private static final Currency CZK = Currency.getInstance("CZK");
    private static final Currency EUR = Currency.getInstance("EUR");

    public static void main(String[] args) {

        ApplicationContext applicationContext
                = new AnnotationConfigApplicationContext("cz/muni/fi/pa165/currency");

        CurrencyConvertor currencyConvertor = applicationContext.getBean(CurrencyConvertor.class);

        System.out.println(currencyConvertor.convert(EUR, CZK, new BigDecimal("1")));
    }
}
