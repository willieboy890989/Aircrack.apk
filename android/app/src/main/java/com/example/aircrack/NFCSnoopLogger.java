import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.Tag;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.util.Log;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NFCSnoopLogger {
    private static final String TAG = "NFCSnoopLogger";
    private static final String LOG_DIRECTORY = "nfc_snoop_logs";
    private static final String LOG_TAG_ID = "Tag-ID:";

    public static void logNFCData(Tag tag) {
        String tagId = bytesToHex(tag.getId());
        String tech[] = tag.getTechList();
        StringBuilder logMessage = new StringBuilder();
        logMessage.append(LOG_TAG_ID).append(tagId).append("\n");
        for (String technology : tech) {
            logMessage.append("Technology: ").append(technology).append("\n");
        }
        writeLog(logMessage.toString());
    }

    private static void writeLog(String logContent) {
        File logDir = new File(LOG_DIRECTORY);
        if (!logDir.exists()) {
            logDir.mkdir();
        }
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.getDefault()).format(new Date());
        File logFile = new File(logDir, "nfc_log_" + timestamp + ".txt");
        try (FileWriter writer = new FileWriter(logFile, true)) {
            writer.append(logContent);
            writer.flush();
        } catch (IOException e) {
            Log.e(TAG, "Error writing log file", e);
        }
    }

    public static void clearAllLogs() {
        File logDir = new File(LOG_DIRECTORY);
        if (logDir.exists()) {
            File[] files = logDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
        }
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xFF & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}