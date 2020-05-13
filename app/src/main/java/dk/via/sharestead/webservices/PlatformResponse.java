package dk.via.sharestead.webservices;

import java.util.ArrayList;
import java.util.List;

public class PlatformResponse {
    private List<PlatformValue> results = new ArrayList<>();

    public int getPlatformId(String platform) {
        int platformId = 0;
        for (PlatformValue platformValue : results) {
            if(platformValue.name.equals(platform)){
                platformId = platformValue.id;
                break;
            }
        }
        return platformId;
    }

    private class PlatformValue {
        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
