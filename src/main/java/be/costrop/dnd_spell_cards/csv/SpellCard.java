package be.costrop.dnd_spell_cards.csv;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class SpellCard {
    private String spellLevel;
    private String name;
    private String type;
    private String castingDuration;
    private String range;
    private String requirements;
    private String duration;
    private String cost;
    private String description;
    private String classDescription;

    /**
     * Used by thymeleaf, to determine the size of the card
     */
    public CardClass getCardClass() {
        if (description.length() > 840) {
            if (description.length() > 1780) {
                return CardClass.PAGE;
            }
            return CardClass.DOUBLE;
        }
        return CardClass.SINGLE;
    }
}
