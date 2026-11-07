package util;

import data.UserInfo;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class JSONUtilTest {

    @Test
    public void serializationCarriesNoJavaTypeIds() {
        String json = JSONUtil.toJSON(new UserInfo());
        assertFalse(json, json.contains("java.util."));
    }

    @Test
    public void polymorphicTypeIdsAreNotHonoured() {
        // With default typing enabled this would be read as a typed list; now it is rejected.
        String json = "{\"follows\":[\"java.util.ArrayList\",[\"a\"]],\"userId\":\"u\"}";
        UserInfo info = (UserInfo) JSONUtil.fromJSON(json, UserInfo.class);
        if (info != null) {
            assertFalse(info.getFollows().contains("a") && info.getFollows().size() == 1);
        }
    }
}
