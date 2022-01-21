package be.costrop.dnd_spell_cards.csv;

import lombok.*;

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
}
