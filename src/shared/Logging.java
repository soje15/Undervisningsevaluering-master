package shared;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

//TODO: Needs proper comments1231231
public class Logging {

    /**
    Opretter en metode som er tilgængelig for alle klasser
     **/
    protected static final Logger logger = Logger.getLogger("logging");

    /**
     * Opretter if-else statement der kigger ind i Config-filen og differentierer mellem de forskellige levels.
     * Der er defineret niveau 1 og niveau 2, hvis ingen af disse niveauer findes så er den pr. definition SEVERE
     *
     * @param debugLevel
     */
    public static void initiateLog(String debugLevel){
        if(debugLevel.equals("1")){
            logger.setLevel(Level.FINEST);
        }else if(debugLevel.equals("2")){
            logger.setLevel(Level.FINE);
        }else{
            logger.setLevel(Level.SEVERE);
        }
    }

    /**
     * Følgende opstilles som en switch case.
     * Opretter metode med tre parametre: Exception, level og msg.
     * Filehandler sørger at der oprettes en tekstfil ved navn "application.log" hvorpå data tilskrives.
     * Til sidst lukkes try-catch , ved et finally som altid eksekveres 
     *
     *

     */
    public static void log(Exception ex, int level, String msg) {
        FileHandler fh = null;

        try {
            fh = new FileHandler("application.log", true);
            logger.addHandler(fh);
            switch (level) {
                case 1:
                    logger.log(Level.FINEST, msg, ex);
                    if (!msg.equals(""))
                        System.out.println(msg + "finest error");
                    break;
                case 2:
                    logger.log(Level.FINE, msg, ex);
                    if (!msg.equals(""))
                        System.out.println(msg + "fine error");
                    break;
                case 3:
                    logger.log(Level.SEVERE, msg, ex);
                    if (!msg.equals(""))
                        System.out.println(msg + "severe error");
                    break;
                default:
                    logger.log(Level.CONFIG, msg, ex);
                    break;

            }
        } catch (IOException ex1) {
            logger.log(Level.SEVERE, null, ex1);
        } catch (SecurityException ex1) {
            logger.log(Level.SEVERE, null, ex1);
        }finally {
            if (fh != null) fh.close();
        }
    }
}

