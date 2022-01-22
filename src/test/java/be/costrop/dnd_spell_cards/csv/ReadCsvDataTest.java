package be.costrop.dnd_spell_cards.csv;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ReadCsvDataTest {

    @Test
    void testSmallCsv() throws Exception {
        // given
        final var parser = new ReadCsvData("test.csv");

        // when
        final var spells = parser.importSpellsFromCsv();

        // then
        Assertions.assertThat(spells).containsExactlyInAnyOrder(
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
                        .build(),
                SpellCard.builder()
                        .spellLevel("3")
                        .name("Revivify")
                        .type("3rd level Necromancy")
                        .castingDuration("1 action")
                        .range("Touch")
                        .requirements("V, S, M")
                        .duration("Instantaneous")
                        .cost("diamonds worth 300 gp, which the spell consumes")
                        .description("You touch a creature that has died within the last minute. That creature returns to life with 1 hit point. This spell can't return to life a creature that has died of old age, nor can it restore any missing body parts.")
                        .classDescription("Cleric (*)(Life)(Grave)")
                        .build(),
                SpellCard.builder()
                        .spellLevel("9")
                        .name("Gate")
                        .type("9th level Conjuration")
                        .castingDuration("1 action")
                        .range("60 feet")
                        .requirements("V, S, M")
                        .duration("Concentration, up to 1 minute")
                        .cost("a diamond worth at least 5,000 gp")
                        .description("You conjure a portal linking an unoccupied space you can see within range to a precise location on a different plane of existence. The portal is a circular opening, which you can make 5 to 20 feet in diameter. You can orient the portal in any direction you choose. The portal lasts for the duration.<br> The portal has a front and a back on each plane where it appears. Travel through the portal is possible only by moving through its front. Anything that does so is instantly transported to the other plane, appearing in the unoccupied space nearest to the portal.<br> Deities and other planar rulers can prevent portals created by this spell from opening in their presence or anywhere within their domains.<br> When you cast this spell, you can speak the name of a specific creature (a pseudonym, title, or nickname doesn't work). If that creature is on a plane other than the one you are on, the portal opens in the named creature's immediate vicinity and draws the creature through it to the nearest unoccupied space on your side of the portal. You gain no special power over the creature, and it is free to act as the DM deems appropriate. It might leave, attack you, or help you. ")
                        .classDescription("Cleric")
                        .build(),
                SpellCard.builder()
                        .spellLevel("2")
                        .name("Augury (ritual)")
                        .type("2nd level Divination")
                        .castingDuration("1 minute")
                        .range("Self")
                        .requirements("V, S, M")
                        .duration("Instantaneous")
                        .cost("specially marked sticks, bones, or similar tokens worth at least 25 gp")
                        .description("By casting gem-inlaid sticks, rolling dragon bones, laying out ornate cards, or employing some other divining tool, you receive an omen from an otherworldly entity about the results of a specific course of action that you plan to take within the next 30 minutes. The DM chooses from the following possible omens.<br> • <i>Weal</i>...")
                        .classDescription("Cleric (*)(Knowledge)")
                        .build()
        );
    }
}
