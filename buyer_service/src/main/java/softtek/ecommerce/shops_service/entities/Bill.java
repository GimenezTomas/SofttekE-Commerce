package softtek.ecommerce.shops_service.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import softtek.ecommerce.shops_service.entities.dtos.DTOBillData;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Entity
@Table( name = "bills" )
public class Bill {
    @Id
    @Column( name = "id_bill" ) @GeneratedValue(generator = "UUID")
    @GenericGenerator( name = "UUID", strategy = "org.hibernate.id.UUIDGenerator" )
    private String idBill;

    @Column( name = "route" ) @NotBlank
    private String route;

    @Column( name = "active" )
    private Boolean active;

    @JsonIgnore
    @OneToOne( mappedBy = "bill" )
    private Order order;

    public Bill() {
        this.active = true;
    }

    public Bill(String route) {
        this();
        this.route = route;
    }

    public boolean generate(DTOBillData data, BillGenerator billGenerator ) {
        this.route = billGenerator/*Adapter*/.generate( data );
        return this.route != null ? true : false;
    }
}
