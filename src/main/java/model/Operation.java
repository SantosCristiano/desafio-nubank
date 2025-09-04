package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class Operation {
    private String operation;

    @JsonProperty("unit-cost")
    private BigDecimal unitCost;

    private int quantity;

    public Operation(String operation, BigDecimal unitCost, int quantity) {
        this.operation = operation;
        this.unitCost = unitCost;
        this.quantity = quantity;
    }

    public Operation() {}

    public String getOperation() {
        return operation;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public int getQuantity() {
        return quantity;
    }
}
