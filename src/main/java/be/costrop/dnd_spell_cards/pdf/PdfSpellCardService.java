package be.costrop.dnd_spell_cards.pdf;

import be.costrop.dnd_spell_cards.csv.SpellCard;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.util.List;

public class PdfSpellCardService {
    private static final float MARGIN_TOP_BOTTOM = 70f;
    private static final float MARGIN_SIDE = 70f;
    private final PdfFont font;

    public PdfSpellCardService() {
        font = getFont();
    }

    private PdfFont getFont() {
        try {
            return PdfFontFactory.createFont(StandardFonts.HELVETICA);
        } catch (Exception e) {
            return null;
        }
    }

    public byte[] renderSpellCards(List<SpellCard> spellCards) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            final PdfDocument pdf = new PdfDocument(new PdfWriter(baos));
            pdf.getDocumentInfo().setAuthor("Tim Costrop");
            pdf.getDocumentInfo().setTitle("Light Cleric Spell cards");

            final Dimension cardDimensions = calculateCardDimensions(pdf);

            final PdfCanvas pdfCanvas = new PdfCanvas(pdf.addNewPage());
            final Rectangle rectangle = new Rectangle(MARGIN_SIDE, MARGIN_TOP_BOTTOM, cardDimensions.width, cardDimensions.height);
            pdfCanvas.rectangle(rectangle);
            pdfCanvas.stroke();

            final Canvas canvas = new Canvas(pdfCanvas, rectangle);

            final SpellCard firstCard = spellCards.get(0);
            final Text spellLvl = new Text(firstCard.getSpellLevel())
                    .setFont(font)
                    .setFontSize(60f)
                    .setBold();
            canvas.showTextAligned(new Paragraph(spellLvl), 10f,10f, TextAlignment.LEFT);

            canvas.close();

            pdf.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Dimension calculateCardDimensions(PdfDocument template) {
        final PageSize pageSize = template.getDefaultPageSize();
        final float width = (pageSize.getWidth() - (MARGIN_SIDE * 2)) / 2;
        final float height = (pageSize.getHeight() - (MARGIN_TOP_BOTTOM * 2)) / 2;
        return new Dimension((int) width, (int) height);
    }
}
