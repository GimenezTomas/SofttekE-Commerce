package softtek.ecommerce.shops_service.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class AreaHasCustomizationId implements Serializable {
    @Column(name = "id_customization_area")
    private String idCustomizationArea;

    @Column(name = "id_customization")
    private String idCustomization;

    public AreaHasCustomizationId() {
    }

    public AreaHasCustomizationId(String idCustomizationArea, String idCustomization) {
        this.idCustomizationArea = idCustomizationArea;
        this.idCustomization = idCustomization;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCustomizationArea, idCustomization);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AreaHasCustomizationId areaHasCustomizationId = (AreaHasCustomizationId) obj;
        return idCustomizationArea.equals(areaHasCustomizationId.idCustomizationArea) && idCustomization.equals(areaHasCustomizationId.idCustomization);
    }
}