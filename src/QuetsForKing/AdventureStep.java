package QuetsForKing;

import java.util.Map;

public class AdventureStep {
    String text;
    Map<String, String> options;

    public AdventureStep(String text, Map options) {
        this.text = text;
        this.options = options;
    }
}
