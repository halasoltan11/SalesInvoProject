package ModelPackage;
import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
public class FileOperations {
    public ArrayList<HeaderInvoice> readFile(String filePath, String lineFilePath) throws IOException {
        File headerInvoiceFile = new File(filePath);
        //  File headerInvoiceFile = fch.getSelectedFile();
        Path FileHeaderPath = Paths.get(headerInvoiceFile.getAbsolutePath());
        List<String> headerInvoiceLines = Files.readAllLines(FileHeaderPath);
        ArrayList<HeaderInvoice> arrayInvoices = new ArrayList<>();
        for (String headerInvoiceLine : headerInvoiceLines) {
            String[] headers = headerInvoiceLine.split(",");
            int invoiceNumber = Integer.parseInt(headers[0]);
            String invDate = headers[1];
            String clientName = headers[2];
            HeaderInvoice invoice = new HeaderInvoice(invoiceNumber, clientName, invDate);
            arrayInvoices.add(invoice);
        }
        // result = fch.showOpenDialog(frame);
        File linesFile = new File(lineFilePath);
        //File linesFile = fch.getSelectedFile();
        Path linesPath = Paths.get(linesFile.getAbsolutePath());
        try {
            List<String> lineLines = Files.readAllLines(linesPath);
            for (String lineLine : lineLines) {
                String lineParts[] = lineLine.split(",");
                int invoiceNum = Integer.parseInt(lineParts[0]);
                String itemName = lineParts[1];
                double itemPrice = Double.parseDouble(lineParts[2]);
                int count = Integer.parseInt(lineParts[3]);
                HeaderInvoice inv = null;
                for (HeaderInvoice invoice : arrayInvoices) {
                    if (invoice.getNum() == invoiceNum) {
                        inv = invoice;
                        break;
                    }
                }
                InvoiceLine item = new InvoiceLine(inv, itemName, count, itemPrice);
                inv.getItems().add(item);
            }
        } catch (Exception e) {
            System.out.println("Wrong File Format");
            return null;
        }
        return arrayInvoices;
    }
    public void writeFile(ArrayList<HeaderInvoice> invoices, String filePath, String lineFilePath) {
        String headers = "";
        String lines = "";
        for (HeaderInvoice invoice : invoices) {
            String invoiceCSV = invoice.getAsCSVFormat();
            headers += invoiceCSV;
            headers += "\n";
            for (InvoiceLine item : invoice.getItems()) {
                String lineCSV = item.getAsCSVFormat();
                lines += lineCSV;
                lines += "\n";
            }
        }
        try {
            JFileChooser fch = new JFileChooser();
            File headerFile = new File(filePath);
            File ItemFile = new File(lineFilePath);
            if (!ItemFile.exists() && !headerFile.exists()) {
                System.out.println("Wrong file format and not saved");
                System.exit(0);
            } else {
                //int result = fch.showSaveDialog(frame);
                FileWriter headerFwriter = new FileWriter(headerFile ,true);
                headerFwriter.write(headers);
                headerFwriter.flush();
                headerFwriter.close();
                //result = fch.showSaveDialog(frame);
                FileWriter itemFwriter = new FileWriter(ItemFile , true);
                itemFwriter.write(lines);
                itemFwriter.flush();
                itemFwriter.close();
            }
        } catch (Exception ex) {
            System.out.println("Wrong file format and not saved");
            System.exit(0);
        }
    }
}