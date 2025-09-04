package integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Operation;
import org.junit.jupiter.api.Test;
import service.CapitalGainsCalculator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CapitalGainsCalculatorIT {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void integrationTestCase1() throws Exception {
        String jsonInput = """
            [
                {"operation": "buy", "unit-cost": 10.00, "quantity": 100},
                {"operation": "sell", "unit-cost": 15.00, "quantity": 50},
                {"operation": "sell", "unit-cost": 15.00, "quantity": 50}
            ]
            """;

        List<Operation> operations = mapper.readValue(jsonInput, new TypeReference<>() {});
        CapitalGainsCalculator calculator = new CapitalGainsCalculator();
        List<Map<String, BigDecimal>> result = calculator.processOperations(operations);

        List<Map<String, BigDecimal>> expected = List.of(
                Map.of("tax", new BigDecimal("0.00")),
                Map.of("tax", new BigDecimal("0.00")),
                Map.of("tax", new BigDecimal("0.00"))
        );

        assertEquals(expected, result);
    }

    @Test
    void integrationTestCase2() throws Exception {
        String jsonInput = """
            [
                {"operation": "buy", "unit-cost": 10.00, "quantity": 10000},
                {"operation": "sell", "unit-cost": 20.00, "quantity": 5000},
                {"operation": "sell", "unit-cost": 5.00, "quantity": 5000}
            ]
            """;

        List<Operation> operations = mapper.readValue(jsonInput, new TypeReference<>() {});
        CapitalGainsCalculator calculator = new CapitalGainsCalculator();
        List<Map<String, BigDecimal>> result = calculator.processOperations(operations);

        List<Map<String, BigDecimal>> expected = List.of(
                Map.of("tax", new BigDecimal("0.00")),
                Map.of("tax", new BigDecimal("10000.00")),
                Map.of("tax", new BigDecimal("0.00"))
        );

        assertEquals(expected, result);
    }

    @Test
    void integrationTestMultipleSimulations() throws Exception {
        String jsonInput1 = """
            [
                {"operation": "buy", "unit-cost": 10.00, "quantity": 100},
                {"operation": "sell", "unit-cost": 15.00, "quantity": 50},
                {"operation": "sell", "unit-cost": 15.00, "quantity": 50}
            ]
            """;

        String jsonInput2 = """
            [
                {"operation": "buy", "unit-cost": 10.00, "quantity": 10000},
                {"operation": "sell", "unit-cost": 20.00, "quantity": 5000},
                {"operation": "sell", "unit-cost": 5.00, "quantity": 5000}
            ]
            """;

        // Simulação 1
        List<Operation> operations1 = mapper.readValue(jsonInput1, new TypeReference<>() {});
        CapitalGainsCalculator calculator1 = new CapitalGainsCalculator();
        List<Map<String, BigDecimal>> result1 = calculator1.processOperations(operations1);
        List<Map<String, BigDecimal>> expected1 = List.of(
                Map.of("tax", new BigDecimal("0.00")),
                Map.of("tax", new BigDecimal("0.00")),
                Map.of("tax", new BigDecimal("0.00"))
        );
        assertEquals(expected1, result1);

        // Simulação 2
        List<Operation> operations2 = mapper.readValue(jsonInput2, new TypeReference<>() {});
        CapitalGainsCalculator calculator2 = new CapitalGainsCalculator();
        List<Map<String, BigDecimal>> result2 = calculator2.processOperations(operations2);
        List<Map<String, BigDecimal>> expected2 = List.of(
                Map.of("tax", new BigDecimal("0.00")),
                Map.of("tax", new BigDecimal("10000.00")),
                Map.of("tax", new BigDecimal("0.00"))
        );
        assertEquals(expected2, result2);
    }

    @Test
    void integrationTestCase3() throws Exception {
        String inputJson = """
                [
                  {"operation":"buy", "unit-cost":10.00, "quantity": 10000},
                  {"operation":"sell", "unit-cost":5.00, "quantity": 5000},
                  {"operation":"sell", "unit-cost":20.00, "quantity": 3000}
                ]
                """;

        List<Operation> operations = mapper.readValue(inputJson, new TypeReference<>() {});

        CapitalGainsCalculator calculator = new CapitalGainsCalculator();
        List<Map<String, BigDecimal>> result = calculator.processOperations(operations);

        String expectedJson = """
                [
                  {"tax":0.00},
                  {"tax":0.00},
                  {"tax":1000.00}
                ]
                """;

        List<Map<String, BigDecimal>> expected = mapper.readValue(expectedJson, new TypeReference<>() {});

        assertEquals(expected, result);
    }

    @Test
    void integrationTestCase4() throws Exception {
        String inputJson = """
                [
                  {"operation":"buy", "unit-cost":10.00, "quantity":10000},
                  {"operation":"buy", "unit-cost":25.00, "quantity":5000},
                  {"operation":"sell", "unit-cost":15.00, "quantity":10000}
                ]
                """;

        List<Operation> operations = mapper.readValue(inputJson, new TypeReference<>() {});

        CapitalGainsCalculator calculator = new CapitalGainsCalculator();
        List<Map<String, BigDecimal>> result = calculator.processOperations(operations);

        String expectedJson = """
                [
                  {"tax":0.00},
                  {"tax":0.00},
                  {"tax":0.00}
                ]
                """;

        List<Map<String, BigDecimal>> expected = mapper.readValue(expectedJson, new TypeReference<>() {});

        assertEquals(expected, result);
    }

    @Test
    void integrationTestCase5() throws Exception {
        String inputJson = """
                [
                  {"operation":"buy", "unit-cost":10.00, "quantity":10000},
                  {"operation":"buy", "unit-cost":25.00, "quantity":5000},
                  {"operation":"sell", "unit-cost":15.00, "quantity":10000},
                  {"operation":"sell", "unit-cost":25.00, "quantity":5000}
                ]
                """;

        List<Operation> operations = mapper.readValue(inputJson, new TypeReference<>() {});

        CapitalGainsCalculator calculator = new CapitalGainsCalculator();
        List<Map<String, BigDecimal>> result = calculator.processOperations(operations);

        String expectedJson = """
                [
                  {"tax":0.00},
                  {"tax":0.00},
                  {"tax":0.00},
                  {"tax":10000.00}
                ]
                """;

        List<Map<String, BigDecimal>> expected = mapper.readValue(expectedJson, new TypeReference<>() {});

        assertEquals(expected, result);
    }

    @Test
    void integrationTestCase6() throws Exception {
        String inputJson = """
            [
              {"operation":"buy", "unit-cost":10.00, "quantity":10000},
              {"operation":"sell", "unit-cost":2.00, "quantity":5000},
              {"operation":"sell", "unit-cost":20.00, "quantity":2000},
              {"operation":"sell", "unit-cost":20.00, "quantity":2000},
              {"operation":"sell", "unit-cost":25.00, "quantity":1000}
            ]
            """;

        List<Operation> operations = mapper.readValue(inputJson, new TypeReference<>() {});

        CapitalGainsCalculator calculator = new CapitalGainsCalculator();
        List<Map<String, BigDecimal>> result = calculator.processOperations(operations);

        String expectedJson = """
            [
              {"tax": 0.00},
              {"tax": 0.00},
              {"tax": 0.00},
              {"tax": 0.00},
              {"tax": 3000.00}
            ]
            """;

        List<Map<String, BigDecimal>> expected = mapper.readValue(expectedJson, new TypeReference<>() {});

        assertEquals(expected, result);
    }

    @Test
    void integrationTestCase7() throws Exception {
        String inputJson = """
        [
          {"operation":"buy", "unit-cost":10.00, "quantity":10000},
          {"operation":"sell", "unit-cost":2.00, "quantity":5000},
          {"operation":"sell", "unit-cost":20.00, "quantity":2000},
          {"operation":"sell", "unit-cost":20.00, "quantity":2000},
          {"operation":"sell", "unit-cost":25.00, "quantity":1000},
          {"operation":"buy", "unit-cost":20.00, "quantity":10000},
          {"operation":"sell", "unit-cost":15.00, "quantity":5000},
          {"operation":"sell", "unit-cost":30.00, "quantity":4350},
          {"operation":"sell", "unit-cost":30.00, "quantity":650}
        ]
        """;

        List<Operation> operations = mapper.readValue(inputJson, new TypeReference<>() {});

        CapitalGainsCalculator calculator = new CapitalGainsCalculator();
        List<Map<String, BigDecimal>> result = calculator.processOperations(operations);

        String expectedJson = """
        [
          {"tax": 0.00},
          {"tax": 0.00},
          {"tax": 0.00},
          {"tax": 0.00},
          {"tax": 3000.00},
          {"tax": 0.00},
          {"tax": 0.00},
          {"tax": 3700.00},
          {"tax": 0.00}
        ]
        """;

        List<Map<String, BigDecimal>> expected = mapper.readValue(expectedJson, new TypeReference<>() {});

        assertEquals(expected, result);
    }

    @Test
    void integrationTestCase8() throws Exception {
        String inputJson = """
        [
          {"operation":"buy", "unit-cost":10.00, "quantity":10000},
          {"operation":"sell", "unit-cost":50.00, "quantity":10000},
          {"operation":"buy", "unit-cost":20.00, "quantity":10000},
          {"operation":"sell", "unit-cost":50.00, "quantity":10000}
        ]
        """;

        List<Operation> operations = mapper.readValue(inputJson, new TypeReference<>() {});

        CapitalGainsCalculator calculator = new CapitalGainsCalculator();
        List<Map<String, BigDecimal>> result = calculator.processOperations(operations);

        String expectedJson = """
        [
          {"tax": 0.00},
          {"tax": 80000.00},
          {"tax": 0.00},
          {"tax": 60000.00}
        ]
        """;

        List<Map<String, BigDecimal>> expected = mapper.readValue(expectedJson, new TypeReference<>() {});

        assertEquals(expected, result);
    }

    @Test
    void integrationTestCase9() throws Exception {
        String inputJson = """
        [
          {"operation":"buy", "unit-cost":5000.00, "quantity":10},
          {"operation":"sell", "unit-cost":4000.00, "quantity":5},
          {"operation":"buy", "unit-cost":15000.00, "quantity":5},
          {"operation":"buy", "unit-cost":4000.00, "quantity":2},
          {"operation":"buy", "unit-cost":23000.00, "quantity":2},
          {"operation":"sell", "unit-cost":20000.00, "quantity":1},
          {"operation":"sell", "unit-cost":12000.00, "quantity":10},
          {"operation":"sell", "unit-cost":15000.00, "quantity":3}
        ]
        """;

        List<Operation> operations = mapper.readValue(inputJson, new TypeReference<>() {});

        CapitalGainsCalculator calculator = new CapitalGainsCalculator();
        List<Map<String, BigDecimal>> result = calculator.processOperations(operations);

        String expectedJson = """
        [
          {"tax": 0.00},
          {"tax": 0.00},
          {"tax": 0.00},
          {"tax": 0.00},
          {"tax": 0.00},
          {"tax": 0.00},
          {"tax": 1000.00},
          {"tax": 2400.00}
        ]
        """;

        List<Map<String, BigDecimal>> expected = mapper.readValue(expectedJson, new TypeReference<>() {});

        assertEquals(expected, result);
    }
}
