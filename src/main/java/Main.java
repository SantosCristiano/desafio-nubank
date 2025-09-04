import com.fasterxml.jackson.databind.ObjectMapper;
import service.CapitalGainsCalculator;
import io.InputHandler;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputHandler inputHandler = new InputHandler(mapper);
        CapitalGainsCalculator calculator = new CapitalGainsCalculator();

        inputHandler.readLines().forEach(operations -> {
            List<Map<String, BigDecimal>> result = calculator.processOperations(operations);
            try {
                System.out.println(mapper.writeValueAsString(result));
            } catch (IOException e) {
                throw new RuntimeException("Erro ao converter saída para JSON", e);
            }
        });
    }
}
