package be.costrop.dnd_spell_cards;

import be.costrop.dnd_spell_cards.csv.ReadCsvData;
import be.costrop.dnd_spell_cards.csv.SpellCard;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
public class PdfController {

    private final ReadCsvData readCsvData;

    @GetMapping("/html")
    public String getCardsAsHtml(Model model) throws IOException {
        final List<SpellCard> spellCards = readCsvData.importSpellsFromCsv("light-cleric.csv");
        spellCards.sort(Comparator.comparing(sc -> sc.getDescription().length()));
        model.addAttribute("spellCards", spellCards);
        return "cards";
    }
}
