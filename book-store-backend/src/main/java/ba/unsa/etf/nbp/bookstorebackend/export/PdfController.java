package ba.unsa.etf.nbp.bookstorebackend.export;

import ba.unsa.etf.nbp.bookstorebackend.Role;
import ba.unsa.etf.nbp.bookstorebackend.projection.BookProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.OrderProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.UserProjection;
import ba.unsa.etf.nbp.bookstorebackend.repository.OrderRepository;
import ba.unsa.etf.nbp.bookstorebackend.repository.UserRepository;
import com.lowagie.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/orders/export/pdf")
    public void exportOrdersToPDF(HttpServletResponse response,
                                  @RequestParam(value = "userId", required = false) Integer userId,
                                  @RequestParam(value = "dateFrom", required = false) @DateTimeFormat(pattern="dd/MM/yyyy") LocalDate dateFrom,
                                  @RequestParam(value = "dateUntil", required = false) @DateTimeFormat(pattern="dd/MM/yyyy") LocalDate dateUntil) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=orders_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        Map<UserProjection, List<OrderProjection>> orders = new HashMap<>();
        if (userId != null) {
            orders.put(
                    userRepository.findUserWithAddress(Role.BOOK_BUYER, userId),
                    orderRepository.findAll(userId).stream().filter(
                            o -> {
                                boolean isAfter = true;
                                if (dateFrom != null) {
                                    isAfter = o.getOrderDate().isAfter(dateFrom);
                                }
                                boolean isBefore = true;
                                if (dateUntil != null) {
                                    isBefore = o.getOrderDate().isBefore(dateUntil);
                                }
                                return isBefore && isAfter;
                            }
                    ).collect(Collectors.toList()));
        } else {
            List<UserProjection> users = userRepository.findAllWithAddress(Role.BOOK_BUYER);
            for (var user : users) {
                orders.put(
                        user,
                        orderRepository.findAll(user.getId()).stream().filter(
                                o -> {
                                    boolean isAfter = true;
                                    if (dateFrom != null) {
                                        isAfter = o.getOrderDate().isAfter(dateFrom) || o.getOrderDate().isEqual(dateFrom);
                                    }
                                    boolean isBefore = true;
                                    if (dateUntil != null) {
                                        isBefore = o.getOrderDate().isBefore(dateUntil) || o.getOrderDate().isEqual(dateUntil);;
                                    }
                                    return isBefore && isAfter;
                                }
                        ).collect(Collectors.toList()));
            }
        }
        OrderPDFExporter exporter = new OrderPDFExporter(orders);

        exporter.setDateFrom(dateFrom);
        exporter.setDateUntil(dateUntil);
        exporter.export(response);

    }
}
