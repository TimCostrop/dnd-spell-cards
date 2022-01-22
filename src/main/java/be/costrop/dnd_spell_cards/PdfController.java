package be.costrop.dnd_spell_cards;

import be.costrop.dnd_spell_cards.csv.ReadCsvData;
import be.costrop.dnd_spell_cards.csv.SpellCard;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
public class PdfController {

    private final ReadCsvData readCsvData;
    private final ServletContext servletContext;
    private final TemplateEngine templateEngine;

    private List<SpellCard> getSpellCards() throws IOException {
        final List<SpellCard> spellCards = readCsvData.importSpellsFromCsv("light-cleric.csv");
        spellCards.sort(Comparator.comparing(sc -> sc.getDescription().length()));
        return spellCards;
    }

    @GetMapping("/html")
    public String getCardsAsHtml(Model model) throws IOException {
        final List<SpellCard> spellCards = getSpellCards();
        model.addAttribute("spellCards", spellCards);
        return "cards";
    }

    @GetMapping("/pdf")
    public ResponseEntity<?> getPDF(HttpServletRequest request, HttpServletResponse response) throws IOException {

        /* Do Business Logic*/
        final List<SpellCard> spellCards = getSpellCards();
        /* Create HTML using Thymeleaf template Engine */

        final WebContext context = new WebContext(request, response, servletContext);
        context.setVariable("spellCards", spellCards);
        final String html = templateEngine.process("cards", context);

        /* Setup Source and target I/O streams */

        final ByteArrayOutputStream target = new ByteArrayOutputStream();

        /*Setup converter properties. */
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setBaseUri("http://localhost:8080");

        /* Call convert method */
        HtmlConverter.convertToPdf(html, target, converterProperties);

        /* extract output as bytes */
        byte[] bytes = target.toByteArray();

        /* Send the response as downloadable PDF */
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(bytes);
    }
}
