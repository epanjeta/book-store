package ba.unsa.etf.nbp.bookstorebackend.export;

import ba.unsa.etf.nbp.bookstorebackend.projection.*;
import com.lowagie.text.*;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.DottedLineSeparator;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderPDFExporter {
    private Map<UserProjection, List<OrderProjection>> listOrders;
    private LocalDate dateFrom;
    private LocalDate dateUntil;

    public OrderPDFExporter(Map<UserProjection, List<OrderProjection>> listOrders) {
        this.listOrders = listOrders;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(new Color(42, 98, 154));
        cell.setPadding(5);
        cell.setMinimumHeight(30);
        cell.setHorizontalAlignment(0);

        com.lowagie.text.Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("ISBN", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Title", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Description", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Price", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Language", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Genres", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Authors", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Quantity", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table, List<CartItem> booksForOrder) {
        for (var cart : booksForOrder) {
            BookProjection bookProjection = cart.getBook();

            PdfPCell cell = new PdfPCell();
            //cell.setBackgroundColor(new Color(42, 98, 154));
            cell.setPadding(5);
            cell.setMinimumHeight(30);
            cell.setHorizontalAlignment(0);

            com.lowagie.text.Font font = FontFactory.getFont(FontFactory.HELVETICA);
            font.setColor(Color.BLACK);

            cell.setPhrase(new Phrase(bookProjection.getISBN(), font));
            table.addCell(cell);

            cell.setPhrase(new Phrase(bookProjection.getTitle(), font));
            table.addCell(cell);

            cell.setPhrase(new Phrase(bookProjection.getDescription(), font));
            table.addCell(cell);

            cell.setPhrase(new Phrase(String.valueOf(bookProjection.getPrice()), font));
            table.addCell(cell);

            cell.setPhrase(new Phrase(bookProjection.getLanguageCode(), font));
            table.addCell(cell);

            cell.setPhrase(new Phrase(getGenres(bookProjection.getGenres()), font));
            table.addCell(cell);

            cell.setPhrase(new Phrase(getAuthors(bookProjection.getAuthors()), font));
            table.addCell(cell);

            cell.setPhrase(new Phrase(String.valueOf(cart.getQuantity()), font));
            table.addCell(cell);
            //table.addCell(user.getEmail());

        }
    }

    private String getGenres(List<String> languages) {
        return StringUtils.join(languages, ", ");
    }

    private String getAuthors(List<AuthorProjection> authors) {
        List<String> authorNames = authors.stream()
                .map(s -> s.getFirstName() + " " + s.getLastName()).toList();

        return StringUtils.join(authorNames, ", ");
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(new Rectangle(842.0F, 595.0F));
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        com.lowagie.text.Font font = FontFactory.getFont(FontFactory.TIMES);
        font.setSize(18);
        font.setColor(new Color(0, 50, 133));

        Paragraph p = new Paragraph("ORDERS", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(p);

        font.setColor(Color.BLACK);

        DottedLineSeparator dottedLineSeparator = new DottedLineSeparator();
        dottedLineSeparator.setGap(0);
        dottedLineSeparator.setLineWidth(2f);

        DottedLineSeparator dottedLineSeparatorWhite = new DottedLineSeparator();
        dottedLineSeparatorWhite.setGap(4);
        dottedLineSeparatorWhite.setLineWidth(1f);
        dottedLineSeparatorWhite.setLineColor(Color.GRAY);

        com.lowagie.text.Font whiteFont = FontFactory.getFont(FontFactory.TIMES);
        whiteFont.setColor(Color.WHITE);
        Paragraph white = new Paragraph("", whiteFont);
        white.setSpacingBefore(10f);


        if (dateFrom != null) {
            font.setSize(12);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedString = dateFrom.format(formatter);

            Paragraph date = new Paragraph("From: " + formattedString, font);
            p.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(date);
        }

        if (dateUntil != null) {
            font.setSize(12);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedString = dateUntil.format(formatter);

            Paragraph date = new Paragraph("Until: " + formattedString, font);
            p.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(date);
        }

        int totaltotal = 0;

        for (var user : listOrders.keySet()) {
            if (!CollectionUtils.isEmpty(listOrders.get(user))) {
                document.add(white);
                document.add(dottedLineSeparator);

                font.setSize(14);
                Paragraph nameMain = new Paragraph(user.getFirstName() + " " + user.getLastName(), font);
                nameMain.setAlignment(Paragraph.ALIGN_CENTER);
                document.add(nameMain);

                font.setSize(12);
                Paragraph name = new Paragraph("Name: "
                        + user.getFirstName() + " " + user.getLastName(), font);
                name.setAlignment(Paragraph.ALIGN_LEFT);
                document.add(name);

                Paragraph email = new Paragraph("E-mail: "
                        + user.getEmail() , font);
                email.setAlignment(Paragraph.ALIGN_LEFT);
                document.add(email);

                Paragraph username = new Paragraph("Username: "
                        + user.getUserName() , font);
                username.setAlignment(Paragraph.ALIGN_LEFT);
                document.add(username);

                int maxTotal = 0;
                for (var order: listOrders.get(user)) {
                    maxTotal += order.getTotal();
                    document.add(white);
                    document.add(dottedLineSeparatorWhite);

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    String formattedString = order.getOrderDate().format(formatter);
                    Paragraph orderDate = new Paragraph("Order date: " +
                            formattedString, font);
                    orderDate.setAlignment(Paragraph.ALIGN_LEFT);
                    document.add(orderDate);

                    Paragraph total = new Paragraph("Total: " +
                            order.getTotal() + " BAM", font);
                    total.setAlignment(Paragraph.ALIGN_LEFT);
                    document.add(total);

                    Paragraph status = new Paragraph("Status: " +
                            order.getStatus(), font);
                    status.setAlignment(Paragraph.ALIGN_LEFT);
                    document.add(status);

                    PdfPTable table = new PdfPTable(8);
                    table.setWidthPercentage(100f);
                    table.setWidths(new float[] {3.0f, 3.0f, 3.0f, 1.5f, 1.0f, 2.0f, 3.0f, 1.5f});
                    table.setSpacingBefore(10);

                    writeTableHeader(table);
                    writeTableData(table, order.getBooksForOrder());

                    document.add(table);
                }

                document.add(white);
                document.add(dottedLineSeparatorWhite);
                Paragraph total = new Paragraph("Total for " + user.getUserName() + " is: " +
                        maxTotal + " BAM", font);
                total.setAlignment(Paragraph.ALIGN_RIGHT);
                document.add(total);

                totaltotal += maxTotal;
            }
        }

        document.add(white);
        document.add(dottedLineSeparator);
        document.add(white);
        document.add(white);
        Paragraph total = new Paragraph("Total for all orders: " +
                totaltotal + " BAM", font);
        total.setAlignment(Paragraph.ALIGN_RIGHT);
        document.add(total);

        document.add(white);
        document.add(white);
        Paragraph sign = new Paragraph("___________________________________", font);
        sign.setAlignment(Paragraph.ALIGN_LEFT);
        document.add(sign);
        Paragraph signHere = new Paragraph("Chairperson", font);
        signHere.setAlignment(Paragraph.ALIGN_LEFT);
        document.add(signHere);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedString = LocalDate.now().format(formatter);

        Paragraph date = new Paragraph("Date: " + formattedString, font);
        date.setAlignment(Paragraph.ALIGN_LEFT);
        document.add(date);

        document.close();

    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateUntil() {
        return dateUntil;
    }

    public void setDateUntil(LocalDate dateUntil) {
        this.dateUntil = dateUntil;
    }
}
