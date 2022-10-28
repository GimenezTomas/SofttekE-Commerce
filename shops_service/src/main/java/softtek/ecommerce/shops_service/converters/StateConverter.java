package softtek.ecommerce.shops_service.converters;

import softtek.ecommerce.shops_service.entities.State;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter( autoApply = true )
public class StateConverter implements AttributeConverter<State, String> {
    @Override
    public String convertToDatabaseColumn(State state) {
        if (state == null) {
            return null;
        }
        return state.name();
    }

    @Override
    public State convertToEntityAttribute(String s) {
        if (s == null) {
            return null;
        }

        return Stream.of(State.values())
                .filter(c -> c.name().equals(s))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
