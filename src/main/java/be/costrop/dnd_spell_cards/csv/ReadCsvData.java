package be.costrop.dnd_spell_cards.csv;

import lombok.AllArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Spliterators;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
public class ReadCsvData {

    private static final CSVFormat FORMAT = CSVFormat.DEFAULT
            .withDelimiter(';')
            .withQuote('"')
            ;

    private final String file;

    public List<SpellCard> importSpellsFromCsv() throws IOException {
        final Reader in = new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(file));
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(FORMAT.parse(in).iterator(), 0), true)
                .map(ReadCsvData::convertRecordToSpellCardInfo)
                .collect(Collectors.toList());
    }

    private static SpellCard convertRecordToSpellCardInfo(CSVRecord record) {
        int i = 0;
        return SpellCard.builder()
                .spellLevel(record.get(i++))
                .name(record.get(i++))
                .type(record.get(i++))
                .castingDuration(record.get(i++))
                .range(record.get(i++))
                .requirements(record.get(i++))
                .duration(record.get(i++))
                .cost(getCostFromDescription(record.get(i)))
                .description(getDescriptionWithoutCost(record.get(i++)))
                .classDescription(record.get(i))
                .build();
    }


    private static String getCostFromDescription(String desc) {
        try {
            final Matcher matcher = Pattern.compile("\\(([^\\)]*)\\)").matcher(desc);
            if (matcher.find()) {
                final String match = matcher.group(0);
                return match.substring(1, match.length() - 1);
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    private static String getDescriptionWithoutCost(String desc) {
        if(desc.contains(")")) {
            return desc.substring(desc.indexOf(")") + 1 );
        }
        return desc;
    }
}
