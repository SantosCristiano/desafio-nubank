package unit.service;

import model.Operation;
import org.junit.jupiter.api.Test;
import service.CapitalGainsCalculator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CapitalGainsCalculatorTest {

    @Test
    void testCase1() {
        List<Operation> operations = List.of(
                new Operation("buy", new BigDecimal("10.00"), 100),
                new Operation("sell", new BigDecimal("15.00"), 50),
                new Operation("sell", new BigDecimal("15.00"), 50)
        );

        List<Map<String, BigDecimal>> expected = List.of(
                Map.of("tax", new BigDecimal("0.00")),
                Map.of("tax", new BigDecimal("0.00")),
                Map.of("tax", new BigDecimal("0.00"))
        );

        CapitalGainsCalculator calculator = new CapitalGainsCalculator();
        List<Map<String, BigDecimal>> result = calculator.processOperations(operations);

        assertEquals(expected, result);
    }

    @Test
    void testCase2() {
        List<Operation> operations = List.of(
                new Operation("buy", new BigDecimal("10.00"), 10000),
                new Operation("sell", new BigDecimal("20.00"), 5000),
                new Operation("sell", new BigDecimal("5.00"), 5000)
        );

        List<Map<String, BigDecimal>> expected = List.of(
                Map.of("tax", new BigDecimal("0.00")),
                Map.of("tax", new BigDecimal("10000.00")),
                Map.of("tax", new BigDecimal("0.00"))
        );

        CapitalGainsCalculator calculator = new CapitalGainsCalculator();
        List<Map<String, BigDecimal>> result = calculator.processOperations(operations);

        assertEquals(expected, result);
    }

    @Test
    void testMultipleIndependentLines() {
        CapitalGainsCalculator calculator1 = new CapitalGainsCalculator();
        List<Operation> operations1 = List.of(
                new Operation("buy", new BigDecimal("10.00"), 100),
                new Operation("sell", new BigDecimal("15.00"), 50),
                new Operation("sell", new BigDecimal("15.00"), 50)
        );
        List<Map<String, BigDecimal>> expected1 = List.of(
                Map.of("tax", new BigDecimal("0.00")),
                Map.of("tax", new BigDecimal("0.00")),
                Map.of("tax", new BigDecimal("0.00"))
        );
        assertEquals(expected1, calculator1.processOperations(operations1));

        CapitalGainsCalculator calculator2 = new CapitalGainsCalculator();
        List<Operation> operations2 = List.of(
                new Operation("buy", new BigDecimal("10.00"), 10000),
                new Operation("sell", new BigDecimal("20.00"), 5000),
                new Operation("sell", new BigDecimal("5.00"), 5000)
        );
        List<Map<String, BigDecimal>> expected2 = List.of(
                Map.of("tax", new BigDecimal("0.00")),
                Map.of("tax", new BigDecimal("10000.00")),
                Map.of("tax", new BigDecimal("0.00"))
        );
        assertEquals(expected2, calculator2.processOperations(operations2));
    }

    @Test
    void testCase3() {
        List<Operation> operations = List.of(
                new Operation("buy", new BigDecimal("10.00"), 10000),
                new Operation("sell", new BigDecimal("5.00"), 5000),
                new Operation("sell", new BigDecimal("20.00"), 3000)
        );

        List<Map<String, BigDecimal>> expected = List.of(
                Map.of("tax", new BigDecimal("0.00")),
                Map.of("tax", new BigDecimal("0.00")),
                Map.of("tax", new BigDecimal("1000.00"))
        );

        CapitalGainsCalculator calculator = new CapitalGainsCalculator();
        List<Map<String, BigDecimal>> result = calculator.processOperations(operations);

        assertEquals(expected, result);
    }

    @Test
    void testCase4() {
        List<Operation> operations = List.of(
                new Operation("buy", new BigDecimal("10.00"), 10000),
                new Operation("buy", new BigDecimal("25.00"), 5000),
                new Operation("sell", new BigDecimal("15.00"), 10000)
        );

        List<Map<String, BigDecimal>> expected = List.of(
                Map.of("tax", new BigDecimal("0.00")),
                Map.of("tax", new BigDecimal("0.00")),
                Map.of("tax", new BigDecimal("0.00"))
        );

        CapitalGainsCalculator calculator = new CapitalGainsCalculator();
        List<Map<String, BigDecimal>> result = calculator.processOperations(operations);

        assertEquals(expected, result);
    }

    @Test
    void testCase5() {
        List<Operation> operations = List.of(
                new Operation("buy", new BigDecimal("10.00"), 10000),
                new Operation("buy", new BigDecimal("25.00"), 5000),
                new Operation("sell", new BigDecimal("15.00"), 10000),
                new Operation("sell", new BigDecimal("25.00"), 5000)
        );

        List<Map<String, BigDecimal>> expected = List.of(
                Map.of("tax", new BigDecimal("0.00")),
                Map.of("tax", new BigDecimal("0.00")),
                Map.of("tax", new BigDecimal("0.00")),
                Map.of("tax", new BigDecimal("10000.00"))
        );

        CapitalGainsCalculator calculator = new CapitalGainsCalculator();
        List<Map<String, BigDecimal>> result = calculator.processOperations(operations);

        assertEquals(expected, result);
    }

    @Test
    void testCase6() {
        List<Operation> operations = List.of(
                new Operation("buy", new BigDecimal("10.00"), 10000),
                new Operation("sell", new BigDecimal("2.00"), 5000),
                new Operation("sell", new BigDecimal("20.00"), 2000),
                new Operation("sell", new BigDecimal("20.00"), 2000),
                new Operation("sell", new BigDecimal("25.00"), 1000)
        );

        List<Map<String, BigDecimal>> expected = List.of(
                Map.of("tax", new BigDecimal("0.00")),
                Map.of("tax", new BigDecimal("0.00")),
                Map.of("tax", new BigDecimal("0.00")),
                Map.of("tax", new BigDecimal("0.00")),
                Map.of("tax", new BigDecimal("3000.00"))
        );

        CapitalGainsCalculator calculator = new CapitalGainsCalculator();
        List<Map<String, BigDecimal>> result = calculator.processOperations(operations);

        assertEquals(expected, result);
    }

    @Test
    void testCase7() {
        List<Operation> operations = List.of(
                new Operation("buy", new BigDecimal("10.00"), 10000),
                new Operation("sell", new BigDecimal("2.00"), 5000),
                new Operation("sell", new BigDecimal("20.00"), 2000),
                new Operation("sell", new BigDecimal("20.00"), 2000),
                new Operation("sell", new BigDecimal("25.00"), 1000),
                new Operation("buy", new BigDecimal("20.00"), 10000),
                new Operation("sell", new BigDecimal("15.00"), 5000),
                new Operation("sell", new BigDecimal("30.00"), 4350),
                new Operation("sell", new BigDecimal("30.00"), 650)
        );

        List<Map<String, BigDecimal>> expected = List.of(
                Map.of("tax", new BigDecimal("0.00")),
                Map.of("tax", new BigDecimal("0.00")),
                Map.of("tax", new BigDecimal("0.00")),
                Map.of("tax", new BigDecimal("0.00")),
                Map.of("tax", new BigDecimal("3000.00")),
                Map.of("tax", new BigDecimal("0.00")),
                Map.of("tax", new BigDecimal("0.00")),
                Map.of("tax", new BigDecimal("3700.00")),
                Map.of("tax", new BigDecimal("0.00"))
        );

        CapitalGainsCalculator calculator = new CapitalGainsCalculator();
        List<Map<String, BigDecimal>> result = calculator.processOperations(operations);

        assertEquals(expected, result);
    }

    @Test
    void testCase8() {
        List<Operation> operations = List.of(
                new Operation("buy", new BigDecimal("10.00"), 10000),
                new Operation("sell", new BigDecimal("50.00"), 10000),
                new Operation("buy", new BigDecimal("20.00"), 10000),
                new Operation("sell", new BigDecimal("50.00"), 10000)
        );

        List<Map<String, BigDecimal>> expected = List.of(
                Map.of("tax", new BigDecimal("0.00")),
                Map.of("tax", new BigDecimal("80000.00")),
                Map.of("tax", new BigDecimal("0.00")),
                Map.of("tax", new BigDecimal("60000.00"))
        );

        CapitalGainsCalculator calculator = new CapitalGainsCalculator();
        List<Map<String, BigDecimal>> result = calculator.processOperations(operations);

        assertEquals(expected, result);
    }

    @Test
    void testCase9() {
        List<Operation> operations = List.of(
                new Operation("buy", new BigDecimal("5000.00"), 10),
                new Operation("sell", new BigDecimal("4000.00"), 5),
                new Operation("buy", new BigDecimal("15000.00"), 5),
                new Operation("buy", new BigDecimal("4000.00"), 2),
                new Operation("buy", new BigDecimal("23000.00"), 2),
                new Operation("sell", new BigDecimal("20000.00"), 1),
                new Operation("sell", new BigDecimal("12000.00"), 10),
                new Operation("sell", new BigDecimal("15000.00"), 3)
        );

        List<Map<String, BigDecimal>> expected = List.of(
                Map.of("tax", new BigDecimal("0.00")),
                Map.of("tax", new BigDecimal("0.00")),
                Map.of("tax", new BigDecimal("0.00")),
                Map.of("tax", new BigDecimal("0.00")),
                Map.of("tax", new BigDecimal("0.00")),
                Map.of("tax", new BigDecimal("0.00")),
                Map.of("tax", new BigDecimal("1000.00")),
                Map.of("tax", new BigDecimal("2400.00"))
        );

        CapitalGainsCalculator calculator = new CapitalGainsCalculator();
        List<Map<String, BigDecimal>> result = calculator.processOperations(operations);

        assertEquals(expected, result);
    }
}
