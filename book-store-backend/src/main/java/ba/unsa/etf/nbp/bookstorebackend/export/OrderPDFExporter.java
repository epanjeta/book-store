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

import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderPDFExporter {
    private List<OrderProjection> listOrders;
    private UserProjection userProjection;

    private LocalDate dateFrom;
    private LocalDate dateUntil;

    public OrderPDFExporter(List<OrderProjection> listOrders) {
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
        dottedLineSeparatorWhite.setGap(0);
        dottedLineSeparatorWhite.setLineWidth(2f);
        dottedLineSeparatorWhite.setLineColor(Color.WHITE);

        com.lowagie.text.Font whiteFont = FontFactory.getFont(FontFactory.TIMES);
        whiteFont.setColor(Color.WHITE);
        Paragraph white = new Paragraph("", whiteFont);
        white.setSpacingBefore(10f);

        document.add(white);
        document.add(dottedLineSeparator);


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
            String formattedString = dateFrom.format(formatter);

            Paragraph date = new Paragraph("Until: " + formattedString, font);
            p.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(date);
        }

        if (userProjection != null) {
            Paragraph name = new Paragraph("Name: "
                    + userProjection.getFirstName() + " " + userProjection.getLastName(), font);
            p.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(name);

            Paragraph email = new Paragraph("E-mail: "
                    + userProjection.getEmail() , font);
            p.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(email);

            Paragraph username = new Paragraph("Username: "
                    + userProjection.getUserName() , font);
            p.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(username);
        }

        for (var order: listOrders) {
            document.add(white);
            document.add(dottedLineSeparator);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedString = order.getOrderDate().format(formatter);
            Paragraph orderDate = new Paragraph("Order date: " +
                formattedString, font);
            p.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(orderDate);

            Paragraph total = new Paragraph("Total: " +
                    formattedString, font);
            p.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(total);

            Paragraph status = new Paragraph("Status: " +
                    order.getStatus(), font);
            p.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(status);

            PdfPTable table = new PdfPTable(8);
            table.setWidthPercentage(100f);
            table.setWidths(new float[] {3.0f, 3.0f, 3.0f, 1.5f, 1.0f, 2.0f, 3.0f, 1.5f});
            table.setSpacingBefore(10);

            writeTableHeader(table);
            writeTableData(table, order.getBooksForOrder());

            document.add(table);
        }

        document.close();

    }

    public List<OrderProjection> getListOrders() {
        return listOrders;
    }

    public void setListOrders(List<OrderProjection> listOrders) {
        this.listOrders = listOrders;
    }

    public UserProjection getUserProjection() {
        return userProjection;
    }

    public void setUserProjection(UserProjection userProjection) {
        this.userProjection = userProjection;
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
