package ba.unsa.etf.nbp.bookstorebackend.export;

import jakarta.annotation.Nullable;

import java.time.LocalDate;

public class PdfProjection {
    private Integer userId;
    private LocalDate validTo;
    private LocalDate validFrom;

    @Nullable
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Nullable
    public LocalDate getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }

    @Nullable
    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }
}
