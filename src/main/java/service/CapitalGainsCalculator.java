package service;

import model.Operation;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CapitalGainsCalculator {
    private BigDecimal avgPrice = BigDecimal.ZERO;
    private int totalQuantity = 0;
    private BigDecimal accumulatedLoss = BigDecimal.ZERO;

    private static final BigDecimal TAX_RATE = new BigDecimal("0.20");
    private static final BigDecimal TAX_FREE_LIMIT = new BigDecimal("20000.00");

    public List<Map<String, BigDecimal>> processOperations(List<Operation> operations) {
        List<Map<String, BigDecimal>> taxes = new ArrayList<>();

        for (Operation op : operations) {
            BigDecimal tax;

            if ("buy".equals(op.getOperation())) {
                tax = BigDecimal.ZERO;
                applyBuy(op);
            } else {
                tax = applySell(op);
            }

            taxes.add(Map.of("tax", tax.setScale(2, RoundingMode.HALF_UP)));
        }

        return taxes;
    }

    private void applyBuy(Operation op) {
        BigDecimal totalCostNow = avgPrice.multiply(BigDecimal.valueOf(totalQuantity));
        BigDecimal newCost = op.getUnitCost().multiply(BigDecimal.valueOf(op.getQuantity()));

        totalQuantity += op.getQuantity();

        if (totalQuantity > 0) {
            avgPrice = (totalCostNow.add(newCost))
                    .divide(BigDecimal.valueOf(totalQuantity), 10, RoundingMode.HALF_UP);
        }
    }

    private BigDecimal applySell(Operation op) {
        BigDecimal sellValue = op.getUnitCost().multiply(BigDecimal.valueOf(op.getQuantity()));
        BigDecimal costBasis = avgPrice.multiply(BigDecimal.valueOf(op.getQuantity()));
        BigDecimal profit = sellValue.subtract(costBasis);

        totalQuantity -= op.getQuantity();

        // Se o valor total da venda está dentro do limite de isenção
        if (sellValue.compareTo(TAX_FREE_LIMIT) <= 0) {
            if (profit.compareTo(BigDecimal.ZERO) < 0) {
                accumulatedLoss = accumulatedLoss.add(profit.abs());
            }
            return BigDecimal.ZERO;
        }

        // Se há prejuízo acumulado, desconta do lucro
        if (profit.compareTo(BigDecimal.ZERO) > 0 && accumulatedLoss.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal offset = profit.min(accumulatedLoss);
            profit = profit.subtract(offset);
            accumulatedLoss = accumulatedLoss.subtract(offset);
        }

        // Se houver lucro líquido, aplica imposto
        if (profit.compareTo(BigDecimal.ZERO) > 0) {
            return profit.multiply(TAX_RATE);
        }

        // Se prejuízo líquido, acumula
        if (profit.compareTo(BigDecimal.ZERO) < 0) {
            accumulatedLoss = accumulatedLoss.add(profit.abs());
        }

        return BigDecimal.ZERO;
    }

}
