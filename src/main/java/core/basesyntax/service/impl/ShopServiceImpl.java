package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.ShopService;
import java.util.List;

public class ShopServiceImpl implements ShopService {
    private final OperationStrategy operationStrategy;

    public ShopServiceImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void process(List<FruitTransaction> transactions) {
        for (FruitTransaction transaction : transactions) {
            OperationHandler handler = operationStrategy.getHandler(transaction.getOperation());
            try {
                handler.handle(transaction);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Failed to process transaction for fruit: "
                        + transaction.getFruit() + " with quantity: "
                        + transaction.getQuantity() + ". " + e.getMessage(), e);
            }
        }
    }
}
