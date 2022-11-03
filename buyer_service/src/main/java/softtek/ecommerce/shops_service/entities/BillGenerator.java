package softtek.ecommerce.shops_service.entities;

import softtek.ecommerce.shops_service.entities.dtos.DTOBillData;

public interface BillGenerator {
    public String generate( DTOBillData data );
}
