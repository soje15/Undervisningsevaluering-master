package logic;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.sun.deploy.config.Config;

import java.io.FileReader;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/*ConfigLoader anvendes til at hente den unikke config.Json fil, vi har uploadet, hvilket gør f.eks database adgangen uafhægig af hvilken computer den ligger på*/
public class ConfigLoader {

    /* Static variable indlæses alle som String, fordi de hentes fra config.Json.
    OBS! ServerPort skal anvendes som en int, og dette løses med en
       Integer.parseInt(), der hvor ServerPorten skal anvendes.
     */
    public static String DB_TYPE;
    public static String DB_HOST;
    public static String DB_PORT;
    public static String DB_NAME;
    public static String DB_USER;
    public static String DB_PASS;
    public static String CBS_API_LINK;
    public static String COURSES_JSON;
    public static String STUDY_DATA_JSON;
    public static String HASH_SALT;
    public static String ENCRYPT_KEY;
    public static String SERVER_ADDRESS;
    public static String SERVER_PORT;
    public static String DEBUG;
    public static String ENCRYPTION;

    /* Der oprettes en static final kombineret med SINGLETON(design mønster), som har til formål,
    at kontrollere intialiseringen af klassen for derved at sørge for at objektet ikke kan instansieres mere end en gang */

    private static final ConfigLoader SINGLETON = new ConfigLoader();

    public ConfigLoader getInstance() {
        return SINGLETON;
    }

    /* Når metoden ConfigLoader køres, startes parseConfig()*/
    private ConfigLoader() {
        parseConfig();
    }

    /* parseConfig retunerer samtlige værdier fra den lokale Config.Json-fil*/
    public static void parseConfig() {
        JsonParser jparser = new JsonParser();
        JsonReader jsonReader;

        try {
            jsonReader = new JsonReader(new FileReader("config.json")); //opretter metode til at læse den lokale config.Json-fil.
            JsonObject jsonObject = jparser.parse(jsonReader).getAsJsonObject(); //opretter et objekt af config.Json-filen.

            Set<Map.Entry<String, JsonElement>> entries = jsonObject.entrySet();//retunerer værdierne af objektet

            for (Map.Entry<String, JsonElement> entry : entries) {
                try {
                    ConfigLoader.class.getDeclaredField(entry.getKey()).set(SINGLETON, entry.getValue().getAsString());//sætter static værdierne som strings

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

