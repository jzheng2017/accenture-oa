package oa.countries.api;

import java.util.ArrayList;
import java.util.List;

public record Filter(Region region, List<String> desiredFields){
    public enum Region {
        ALL("all"),
        ASIA("asia");

        private final String regionText;

        Region(String regionText) {
            this.regionText = regionText;
        }

        public String region() {
            return regionText;
        }
    }

    public static Filter empty() {
        return new Filter(null, new ArrayList<>());
    }

    public boolean isEmpty() {
        return region == null && (desiredFields == null || desiredFields.isEmpty());
    }
}