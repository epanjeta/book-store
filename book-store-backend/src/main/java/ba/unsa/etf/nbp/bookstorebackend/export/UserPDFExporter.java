package ba.unsa.etf.nbp.bookstorebackend.export;

import ba.unsa.etf.nbp.bookstorebackend.projection.AddressProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.UserProjection;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserPDFExporter {
    private List<UserProjection> listUsers;

    public UserPDFExporter(List<UserProjection> listUsers) {
        this.listUsers = listUsers;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(new Color(42, 98, 154));
        cell.setPadding(5);
        cell.setMinimumHeight(30);
        cell.setHorizontalAlignment(0);

        com.lowagie.text.Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("E-mail", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Full Name", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Username", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Address", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        for (UserProjection user : listUsers) {
            PdfPCell cell = new PdfPCell();
            //cell.setBackgroundColor(new Color(42, 98, 154));
            cell.setPadding(5);
            cell.setMinimumHeight(30);
            cell.setHorizontalAlignment(0);

            com.lowagie.text.Font font = FontFactory.getFont(FontFactory.HELVETICA);
            font.setColor(Color.BLACK);

            cell.setPhrase(new Phrase(user.getEmail(), font));
            table.addCell(cell);

            cell.setPhrase(new Phrase(user.getLastName() + " " + user.getFirstName(), font));
            table.addCell(cell);

            cell.setPhrase(new Phrase(user.getUserName(), font));
            table.addCell(cell);

            cell.setPhrase(new Phrase(getAddress(user), font));
            table.addCell(cell);

            //table.addCell(user.getEmail());

        }
    }

    private String getAddress(UserProjection user) {
        List<String> fullAddress = new ArrayList<>();
        AddressProjection address = user.getAddressProjection();

        if (StringUtils.isNotBlank(address.getStreet()) || StringUtils.isNotBlank(address.getZipCode())) {
            List<String> street = new ArrayList<>();
            if (StringUtils.isNotBlank(address.getStreet())) {
                street.add(address.getStreet());
            }
            if (StringUtils.isNotBlank(address.getZipCode())) {
                street.add(address.getZipCode());
            }
            fullAddress.add(StringUtils.join(street, " "));

        }

        if (StringUtils.isNotBlank(address.getCityName())) {
            fullAddress.add(address.getCityName());
        }

        if (StringUtils.isNotBlank(address.getCountryName())) {
            fullAddress.add(address.getCountryName());
        }

        return StringUtils.join(fullAddress, " ");

    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        com.lowagie.text.Font font = FontFactory.getFont(FontFactory.TIMES);
        font.setSize(18);
        font.setColor(new Color(0, 50, 133));

        Paragraph p = new Paragraph("USERS", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {4.0f, 2.9f, 2.5f, 4.7f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();

    }


}
