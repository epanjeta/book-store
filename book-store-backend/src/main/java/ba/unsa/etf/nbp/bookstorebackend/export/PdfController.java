package ba.unsa.etf.nbp.bookstorebackend.export;

import ba.unsa.etf.nbp.bookstorebackend.Role;
import ba.unsa.etf.nbp.bookstorebackend.projection.OrderProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.UserProjection;
import ba.unsa.etf.nbp.bookstorebackend.repository.OrderRepository;
import ba.unsa.etf.nbp.bookstorebackend.repository.UserRepository;
import com.lowagie.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Controller
public class PdfController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/users/export/pdf")
    public void exportUsersToPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<UserProjection> listUsers = userRepository.findAllWithAddress(Role.BOOK_BUYER);

        UserPDFExporter exporter = new UserPDFExporter(listUsers);
        exporter.export(response);

    }

    @GetMapping("/orders/export/pdf")
    public void exportOrdersToPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<OrderProjection> listOrders
                = orderRepository.findAll(10);

        OrderPDFExporter exporter = new OrderPDFExporter(listOrders);

        exporter.setDateFrom(LocalDate.now());
        exporter.setDateUntil(LocalDate.now().minusDays(50));
        exporter.setUserProjection(userRepository.findUserWithAddress(Role.BOOK_BUYER, 10));

        exporter.export(response);

    }
}
