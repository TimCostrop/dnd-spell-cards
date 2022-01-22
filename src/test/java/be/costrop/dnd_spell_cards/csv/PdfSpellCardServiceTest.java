package be.costrop.dnd_spell_cards.csv;

import be.costrop.dnd_spell_cards.pdf.PdfSpellCardService;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class PdfSpellCardServiceTest {
    @Test
    void testRendering() {
        // given
        final List<SpellCard> mockedCards = List.of(
                SpellCard.builder()
                        .spellLevel("0")
                        .name("Guidance")
                        .type("Divination cantrip")
                        .castingDuration("1 action")
                        .range("Touch")
                        .requirements("V, S")
                        .duration("Concentration, up to 1 minute")
                        .cost("")
                        .description("You touch one willing creature. Once before the spell ends, the target can roll a d4 and add the number rolled to one ability check of its choice. It can roll the die before or after making the ability check. The spell then ends.")
                        .classDescription("Cleric")
                        .build()
        );

        // when
        final byte[] pdf = new PdfSpellCardService().renderSpellCards(mockedCards);

        // then validate the file
        try {
            final File file = File.createTempFile("result-spellcards", ".pdf");
            IOUtils.write(pdf, new FileOutputStream(file));
            System.out.println("Wrote test file to " + file.getAbsolutePath());
        } catch (IOException iox) {
            Assertions.fail();
        }
    }
}
