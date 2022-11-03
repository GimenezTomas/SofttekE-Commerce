package softtek.ecommerce.shops_service.entities;

import softtek.ecommerce.shops_service.entities.dtos.DTOBillData;

public class BillGeneratorAdapter implements BillGenerator{
    @Override
    public String generate(DTOBillData data) {
        boolean ok = true;
        return ok ? "ROUTE://" : null;
    }
}
