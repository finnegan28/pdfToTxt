import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;

public class PDFtoText {

    public static void main(String[] args) {
        String dirPath = "Change\\this\\to\\reports\\directory";
        File dir = new File(dirPath);
        File[] pdfFiles = dir.listFiles((d, name) -> name.endsWith(".pdf"));
        for (File pdfFile:pdfFiles) {
            convertPDFToText(pdfFile);
        }
    }

    public static void convertPDFToText(File pdfFile) {
        PDDocument document = null;
        String txtFilePath = pdfFile.getAbsolutePath().replace(".pdf", ".txt");
        Path path = Paths.get(txtFilePath);
        if (Files.notExists(path)) {
            try {
                document = PDDocument.load(pdfFile);
                PDFTextStripper pdfStripper = new PDFTextStripper();
                String text = pdfStripper.getText(document);
                System.out.println("Converting file \n");
                saveTextToFile(txtFilePath, text);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                closeDocument(document);
            }
        } else {
            System.out.println("File exists, skipping "+ txtFilePath + "\n");
        }
    }

    public static void saveTextToFile(String txtFilePath, String text) {
        try {
            File txtFile = new File(txtFilePath);
            BufferedWriter writer = new BufferedWriter(new FileWriter(txtFile));
            writer.write(text);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void closeDocument(PDDocument document) {
        try {
            if (document != null) {
                document.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}